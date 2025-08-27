package db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {

    public boolean addTransaction(int userId, String type, double amount, String description) {
        return addTransaction(userId, type, amount, description, null);
    }

    public boolean addTransaction(int userId, String type, double amount, String description, Integer relatedUserId) {
        String sql = "INSERT INTO transactions (user_id, type, amount, description, related_user_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setString(2, type);
            ps.setDouble(3, amount);
            ps.setString(4, description);
            if (relatedUserId == null) ps.setNull(5, Types.INTEGER); else ps.setInt(5, relatedUserId);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<String> getTransactions(int userId) {
        List<String> list = new ArrayList<>();
        String sql = """
                SELECT type, amount, description, created_at
                FROM transactions
                WHERE user_id = ?
                ORDER BY created_at DESC
                """;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String row = String.format("%s: $%.2f - %s (%s)",
                        rs.getString("type"),
                        rs.getDouble("amount"),
                        rs.getString("description"),
                        rs.getTimestamp("created_at"));
                list.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // --- Aggregate demo (DBMS requirement) ---
    public double totalByType(int userId, String type) {
        String sql = "SELECT COALESCE(SUM(amount),0) AS total FROM transactions WHERE user_id = ? AND type = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setString(2, type);
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getDouble("total") : 0.0;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0.0;
        }
    }
}
package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionDAO {

    public boolean addTransaction(int userId, String type, double amount, int toUserId) {
        String query = "INSERT INTO transactions(user_id, type, amount, to_user_id) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.setString(2, type);
            stmt.setDouble(3, amount);
            if (toUserId == -1) stmt.setNull(4, java.sql.Types.INTEGER);
            else stmt.setInt(4, toUserId);
            int affected = stmt.executeUpdate();
            return affected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void showTransactions(int userId) {
        String query = "SELECT * FROM transactions WHERE user_id = ? OR to_user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, userId);
            ResultSet rs = stmt.executeQuery();
            System.out.println("\n--- Transaction History ---");
            while (rs.next()) {
                String type = rs.getString("type");
                double amount = rs.getDouble("amount");
                int toId = rs.getInt("to_user_id");
                System.out.println("Type: " + type + ", Amount: " + amount + (toId > 0 ? ", To User ID: " + toId : ""));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
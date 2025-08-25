package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionDAO {
    public boolean addTransaction(int userId, String type, double amount) {
        String sql = "INSERT INTO transactions (user_id, type, amount) VALUES (?, ?, ?)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, userId);
            ps.setString(2, type);
            ps.setDouble(3, amount);
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error adding transaction: " + e.getMessage());
            return false;
        }
    }

    public void showTransactions(int userId) {
        String sql = "SELECT txn_id, type, amount, date FROM transactions WHERE user_id = ? ORDER BY date DESC";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            
            System.out.println("\n--- Transaction History ---");
            boolean hasTransactions = false;
            
            while (rs.next()) {
                hasTransactions = true;
                System.out.println(
                    "ID: " + rs.getInt("txn_id") +
                    ", Type: " + rs.getString("type") +
                    ", Amount: " + rs.getDouble("amount") +
                    ", Date: " + rs.getTimestamp("date")
                );
            }
            
            if (!hasTransactions) {
                System.out.println("No transactions found.");
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving transactions: " + e.getMessage());
        }
    }
}
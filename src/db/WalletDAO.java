package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WalletDAO {
    public boolean createWallet(int userId) {
        String sql = "INSERT INTO wallets (user_id, balance) VALUES (?, 0.00)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, userId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error creating wallet: " + e.getMessage());
            return false;
        }
    }

    public double getBalance(int userId) {
        String sql = "SELECT balance FROM wallets WHERE user_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return rs.getDouble("balance");
            }
        } catch (SQLException e) {
            System.err.println("Error getting balance: " + e.getMessage());
        }
        return 0.0;
    }

    public boolean updateBalance(int userId, double newBalance) {
        String sql = "UPDATE wallets SET balance = ? WHERE user_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setDouble(1, newBalance);
            ps.setInt(2, userId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating balance: " + e.getMessage());
            return false;
        }
    }
}
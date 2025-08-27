package db;

import java.sql.*;
import java.math.BigDecimal;

public class WalletDAO {

    public boolean createWallet(int userId) {
        String check = "SELECT id FROM wallets WHERE user_id = ?";
        String insert = "INSERT INTO wallets (user_id, balance) VALUES (?, 0.00)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement psCheck = conn.prepareStatement(check)) {

            psCheck.setInt(1, userId);
            ResultSet rs = psCheck.executeQuery();
            if (rs.next()) return true; 

            try (PreparedStatement psIns = conn.prepareStatement(insert)) {
                psIns.setInt(1, userId);
                psIns.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void ensureWallet(int userId) {
        createWallet(userId);
    }

    public double getBalance(int userId) {
        String sql = "SELECT balance FROM wallets WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                BigDecimal bd = rs.getBigDecimal("balance");
                return bd != null ? bd.doubleValue() : 0.0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    public boolean updateBalance(int userId, double amount) {
        String sql = "UPDATE wallets SET balance = ? WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBigDecimal(1, BigDecimal.valueOf(amount));
            ps.setInt(2, userId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
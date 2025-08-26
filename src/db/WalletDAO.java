package db;

import java.sql.*;
import java.math.BigDecimal;

public class WalletDAO {

    public boolean createWallet(int userId) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO wallets (user_id, balance) VALUES (?, 0.00)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public double getBalance(int userId) {
        double balance = 0.0;
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT balance FROM wallets WHERE user_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                BigDecimal bd = rs.getBigDecimal("balance");
                if (bd != null) balance = bd.doubleValue();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return balance;
    }

    public boolean updateBalance(int userId, double amount) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "UPDATE wallets SET balance = ? WHERE user_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setBigDecimal(1, BigDecimal.valueOf(amount));
            ps.setInt(2, userId);
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
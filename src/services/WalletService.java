package services;

import db.WalletDAO;
import db.TransactionDAO;

public class WalletService {
    private WalletDAO walletDAO = new WalletDAO();
    private TransactionDAO transactionDAO = new TransactionDAO();

    public boolean createWallet(int userId) {
        return walletDAO.createWallet(userId);
    }

    public boolean deposit(int userId, double amount, String description) {
        double currentBalance = walletDAO.getBalance(userId);
        boolean updated = walletDAO.updateBalance(userId, currentBalance + amount);
        if (updated) {
            transactionDAO.addTransaction(userId, "DEPOSIT", amount, description);
        }
        return updated;
    }

    public boolean withdraw(int userId, double amount, String description) {
        double currentBalance = walletDAO.getBalance(userId);
        if (currentBalance < amount) return false;
        boolean updated = walletDAO.updateBalance(userId, currentBalance - amount);
        if (updated) {
            transactionDAO.addTransaction(userId, "WITHDRAW", amount, description);
        }
        return updated;
    }

    public boolean transfer(int fromUserId, String fromEmail, int toUserId, String toEmail, double amount) {
        double fromBalance = walletDAO.getBalance(fromUserId);
        if (fromBalance < amount) return false;

        double toBalance = walletDAO.getBalance(toUserId);
        boolean withdrawSuccess = walletDAO.updateBalance(fromUserId, fromBalance - amount);
        boolean depositSuccess = walletDAO.updateBalance(toUserId, toBalance + amount);

        if (withdrawSuccess && depositSuccess) {
            transactionDAO.addTransaction(fromUserId, "TRANSFER_OUT", amount, "Transfer to " + toEmail);
            transactionDAO.addTransaction(toUserId, "TRANSFER_IN", amount, "Transfer from " + fromEmail);
            return true;
        }
        return false;
    }

    public double checkBalance(int userId) {
        return walletDAO.getBalance(userId);
    }
}
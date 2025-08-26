package services;

import db.WalletDAO;
import db.TransactionDAO;

public class WalletService {
    private WalletDAO walletDAO = new WalletDAO();
    private TransactionDAO transactionDAO = new TransactionDAO();

    public boolean deposit(int userId, double amount) {
        double currentBalance = walletDAO.getBalance(userId);
        boolean updated = walletDAO.updateBalance(userId, currentBalance + amount);
        if (updated) transactionDAO.addTransaction(userId, "Deposit", amount, -1);
        return updated;
    }

    public boolean withdraw(int userId, double amount) {
        double currentBalance = walletDAO.getBalance(userId);
        if (currentBalance < amount) return false;
        boolean updated = walletDAO.updateBalance(userId, currentBalance - amount);
        if (updated) transactionDAO.addTransaction(userId, "Withdraw", amount, -1);
        return updated;
    }

    public boolean transfer(int fromUserId, int toUserId, double amount) {
        double fromBalance = walletDAO.getBalance(fromUserId);
        if (fromBalance < amount) return false;

        double toBalance = walletDAO.getBalance(toUserId);
        boolean withdrawSuccess = walletDAO.updateBalance(fromUserId, fromBalance - amount);
        boolean depositSuccess = walletDAO.updateBalance(toUserId, toBalance + amount);

        if (withdrawSuccess && depositSuccess) {
            transactionDAO.addTransaction(fromUserId, "Transfer", amount, toUserId);
            return true;
        }
        return false;
    }

    public double checkBalance(int userId) {
        return walletDAO.getBalance(userId);
    }
}
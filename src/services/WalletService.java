package services;

import db.WalletDAO;
import db.TransactionDAO;

public class WalletService {
    private WalletDAO walletDAO = new WalletDAO();
    private TransactionDAO transactionDAO = new TransactionDAO();

    public boolean deposit(int userId, double amount) {
        if (amount <= 0) {
            System.out.println("Deposit amount must be positive.");
            return false;
        }
        
        double currentBalance = walletDAO.getBalance(userId);
        double newBalance = currentBalance + amount;
        
        if (walletDAO.updateBalance(userId, newBalance)) {
            transactionDAO.addTransaction(userId, "DEPOSIT", amount);
            return true;
        }
        return false;
    }

    public boolean withdraw(int userId, double amount) {
        if (amount <= 0) {
            System.out.println("Withdrawal amount must be positive.");
            return false;
        }
        
        double currentBalance = walletDAO.getBalance(userId);
        
        if (currentBalance < amount) {
            System.out.println("Insufficient balance.");
            return false;
        }
        
        double newBalance = currentBalance - amount;
        
        if (walletDAO.updateBalance(userId, newBalance)) {
            transactionDAO.addTransaction(userId, "WITHDRAW", amount);
            return true;
        }
        return false;
    }

    public boolean transfer(int fromUserId, int toUserId, double amount) {
        if (amount <= 0) {
            System.out.println("Transfer amount must be positive.");
            return false;
        }
        
        double fromBalance = walletDAO.getBalance(fromUserId);
        
        if (fromBalance < amount) {
            System.out.println("Insufficient balance for transfer.");
            return false;
        }
        
        double toBalance = walletDAO.getBalance(toUserId);

        if (walletDAO.updateBalance(fromUserId, fromBalance - amount) &&
            walletDAO.updateBalance(toUserId, toBalance + amount)) {
            
            transactionDAO.addTransaction(fromUserId, "TRANSFER_SENT", amount);
            transactionDAO.addTransaction(toUserId, "TRANSFER_RECEIVED", amount);
            return true;
        }
        return false;
    }

    public double checkBalance(int userId) {
        return walletDAO.getBalance(userId);
    }
}
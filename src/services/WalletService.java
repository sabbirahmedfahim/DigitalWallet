package services;

import db.TransactionDAO;
import db.WalletDAO;
import models.Transaction;

public class WalletService {
    private final WalletDAO walletDAO = new WalletDAO();
    private final TransactionDAO transactionDAO = new TransactionDAO();

    public boolean createWallet(int userId) {
        return walletDAO.createWallet(userId);
    }

    public boolean deposit(int userId, double amount, String description) {
        if (amount <= 0) return false;
        Transaction.DepositOp op = new Transaction.DepositOp(userId, amount, description);
        return op.apply(walletDAO, transactionDAO);
    }

    public boolean withdraw(int userId, double amount, String description) {
        if (amount <= 0) return false;
        Transaction.WithdrawOp op = new Transaction.WithdrawOp(userId, amount, description);
        return op.apply(walletDAO, transactionDAO);
    }

    public boolean transfer(int fromUserId, String fromEmail, int toUserId, String toEmail, double amount) {
        if (amount <= 0) return false;
        Transaction.TransferOp op = new Transaction.TransferOp(fromUserId, fromEmail, toUserId, toEmail, amount);
        return op.apply(walletDAO, transactionDAO);
    }

    public double checkBalance(int userId) {
        walletDAO.ensureWallet(userId);
        return walletDAO.getBalance(userId);
    }
}
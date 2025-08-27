package models;

import db.TransactionDAO;
import db.WalletDAO;

public abstract class Transaction {

    protected int userId;
    protected double amount;
    protected String description;

    public Transaction(int userId, double amount, String description) {
        this.userId = userId;
        this.amount = amount;
        this.description = description;
    }

    public abstract boolean apply(WalletDAO walletDAO, TransactionDAO txnDAO);

    public static class DepositOp extends Transaction {
        public DepositOp(int userId, double amount, String description) { super(userId, amount, description); }
        @Override
        public boolean apply(WalletDAO walletDAO, TransactionDAO txnDAO) {
            walletDAO.ensureWallet(userId);
            double current = walletDAO.getBalance(userId);
            boolean ok = walletDAO.updateBalance(userId, current + amount);
            if (ok) txnDAO.addTransaction(userId, "DEPOSIT", amount, description);
            return ok;
        }
    }

    public static class WithdrawOp extends Transaction {
        public WithdrawOp(int userId, double amount, String description) { super(userId, amount, description); }
        @Override
        public boolean apply(WalletDAO walletDAO, TransactionDAO txnDAO) {
            walletDAO.ensureWallet(userId);
            double current = walletDAO.getBalance(userId);
            if (current < amount) return false;
            boolean ok = walletDAO.updateBalance(userId, current - amount);
            if (ok) txnDAO.addTransaction(userId, "WITHDRAWAL", amount, description);
            return ok;
        }
    }

    public static class TransferOp extends Transaction {
        private final int toUserId;
        private final String toEmail;
        private final String fromEmail;

        public TransferOp(int fromUserId, String fromEmail, int toUserId, String toEmail, double amount) {
            super(fromUserId, amount, "Transfer to " + toEmail);
            this.toUserId = toUserId;
            this.toEmail = toEmail;
            this.fromEmail = fromEmail;
        }

        @Override
        public boolean apply(WalletDAO walletDAO, TransactionDAO txnDAO) {
            walletDAO.ensureWallet(userId);
            walletDAO.ensureWallet(toUserId);

            double fromBal = walletDAO.getBalance(userId);
            if (fromBal < amount) return false;

            double toBal = walletDAO.getBalance(toUserId);

            boolean w1 = walletDAO.updateBalance(userId, fromBal - amount);
            boolean w2 = walletDAO.updateBalance(toUserId, toBal + amount);
            if (w1 && w2) {
                txnDAO.addTransaction(userId,  "TRANSFER", amount, "Transfer to " + toEmail,  toUserId);
                txnDAO.addTransaction(toUserId,"TRANSFER", amount, "Transfer from " + fromEmail, userId);
                return true;
            }
            return false;
        }
    }
}
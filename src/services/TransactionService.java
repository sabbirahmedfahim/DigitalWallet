package services;

import db.TransactionDAO;

public class TransactionService {
    private TransactionDAO transactionDAO = new TransactionDAO();

    public void showTransactions(int userId) {
        transactionDAO.showTransactions(userId);
    }
}
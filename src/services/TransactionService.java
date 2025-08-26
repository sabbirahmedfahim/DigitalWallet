package services;

import db.TransactionDAO;
import java.util.List;

public class TransactionService {
    private TransactionDAO transactionDAO = new TransactionDAO();

    public void showTransactions(int userId) {
        List<String> transactions = transactionDAO.getTransactions(userId);
        
        System.out.println("\n=== Transaction History ===");
        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
        } else {
            for (String transaction : transactions) {
                System.out.println(transaction);
            }
        }
    }

    public boolean recordTransaction(int userId, String type, double amount, String description) {
        return transactionDAO.addTransaction(userId, type, amount, description);
    }
}
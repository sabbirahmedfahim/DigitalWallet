package services;

import db.TransactionDAO;
import java.util.List;

public class TransactionService {
    private final TransactionDAO transactionDAO = new TransactionDAO();

    public void showTransactions(int userId) {
        List<String> transactions = transactionDAO.getTransactions(userId);
        System.out.println("\n=== Transaction History ===");
        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
        } else {
            for (String t : transactions) System.out.println(t);
        }
    }

    public void showAggregateSummary(int userId) {
        double totalDeposits   = transactionDAO.totalByType(userId, "DEPOSIT");
        double totalWithdraws  = transactionDAO.totalByType(userId, "WITHDRAWAL");
        double totalTransfers  = transactionDAO.totalByType(userId, "TRANSFER");
        System.out.printf("\n=== Summary for User %d ===\n", userId);
        System.out.printf("Total Deposited : $%.2f\n", totalDeposits);
        System.out.printf("Total Withdrawn : $%.2f\n", totalWithdraws);
        System.out.printf("Total Transferred: $%.2f\n", totalTransfers);
    }
}
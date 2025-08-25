package models;

import java.sql.Timestamp;

public class Transaction {
    private int txnId;
    private int userId;
    private String type;
    private double amount;
    private Timestamp date;

    public Transaction() {}

    public Transaction(int txnId, int userId, String type, double amount, Timestamp date) {
        this.txnId = txnId;
        this.userId = userId;
        this.type = type;
        this.amount = amount;
        this.date = date;
    }

    public int getTxnId() {
        return txnId;
    }

    public void setTxnId(int txnId) {
        this.txnId = txnId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }
}
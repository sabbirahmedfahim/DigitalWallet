package models;

public class Wallet {
    private int walletId;
    private int userId;
    private double balance;

    public Wallet() {}
    public Wallet(int walletId, int userId, double balance) {
        this.walletId = walletId; this.userId = userId; this.balance = balance;
    }

    public int getWalletId() { return walletId; }
    public void setWalletId(int walletId) { this.walletId = walletId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public double getBalance() { return balance; }
    public void setBalance(double balance) { if (balance >= 0) this.balance = balance; }
}
import db.UserDAO;
import services.WalletService;
import services.TransactionService;
import java.util.Scanner;

public class Main {

    private static final Scanner sc = new Scanner(System.in);
    private static final UserDAO userDAO = new UserDAO();
    private static final WalletService walletService = new WalletService();
    private static final TransactionService transactionService = new TransactionService();
    private static int currentUserId = -1;
    private static String currentUserEmail = "";

    public static void main(String[] args) {
        while (true) {
            if (currentUserId == -1) showLoginMenu();
            else showMainMenu();
        }
    }

    private static void showLoginMenu() {
        System.out.println("\n=== Digital Wallet System ===");
        System.out.println("--- Please Login or Register ---");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Exit");
        System.out.print("Choose option: ");

        int option = safeInt();
        switch (option) {
            case 1 -> login();
            case 2 -> register();
            case 3 -> { System.out.println("Thank you for using Digital Wallet. Goodbye!"); System.exit(0); }
            default -> System.out.println("Invalid option. Try again.");
        }
    }

    private static void showMainMenu() {
        System.out.println("\n=== Digital Wallet Main Menu ===");
        System.out.println("Welcome, " + currentUserEmail);
        System.out.println("1. Deposit");
        System.out.println("2. Withdraw");
        System.out.println("3. Transfer");
        System.out.println("4. Check Balance");
        System.out.println("5. Transaction History");
        System.out.println("6. Summary (Aggregates)");
        System.out.println("7. Search Users by Name (LIKE)");
        System.out.println("8. Logout");
        System.out.print("Choose option: ");

        int option = safeInt();
        switch (option) {
            case 1 -> deposit();
            case 2 -> withdraw();
            case 3 -> transfer();
            case 4 -> checkBalance();
            case 5 -> transactionHistory();
            case 6 -> transactionService.showAggregateSummary(currentUserId);
            case 7 -> searchUsersByName();
            case 8 -> logout();
            default -> System.out.println("Invalid option. Try again.");
        }
    }

    private static void login() {
        System.out.print("Enter email: ");
        String email = sc.nextLine();
        System.out.print("Enter password: ");
        String password = sc.nextLine();

        if (userDAO.validateUser(email, password)) {
            currentUserId = userDAO.getUserId(email);
            currentUserEmail = email;
            walletService.createWallet(currentUserId);
            System.out.println("Login successful! Welcome.");
        } else {
            System.out.println("Invalid email or password. Please try again.");
        }
    }

    private static void register() {
        System.out.print("Enter name: ");
        String name = sc.nextLine();
        System.out.print("Enter email: ");
        String email = sc.nextLine();
        System.out.print("Enter password: ");
        String password = sc.nextLine();

        if (userDAO.addUser(name, email, password)) {
            int userId = userDAO.getUserId(email);
            walletService.createWallet(userId);
            System.out.println("Registration successful! Wallet created.");
        } else {
            System.out.println("Registration failed. Please try again.");
        }
    }

    private static void deposit() {
        System.out.print("Enter amount to deposit: $");
        double amount = safeDouble();
        if (amount <= 0) { System.out.println("Amount must be positive."); return; }

        if (walletService.deposit(currentUserId, amount, "Cash deposit")) {
            System.out.printf("Deposited $%.2f successfully!\n", amount);
        } else {
            System.out.println("Deposit failed.");
        }
    }

    private static void withdraw() {
        System.out.print("Enter amount to withdraw: $");
        double amount = safeDouble();
        if (amount <= 0) { System.out.println("Amount must be positive."); return; }

        if (walletService.withdraw(currentUserId, amount, "Cash withdrawal")) {
            System.out.printf("Withdrew $%.2f successfully!\n", amount);
        } else {
            System.out.println("Withdrawal failed. Insufficient balance.");
        }
    }

    private static void transfer() {
        System.out.print("Enter recipient's email: ");
        String recipientEmail = sc.nextLine();
        System.out.print("Enter amount to transfer: $");
        double amount = safeDouble();
        if (amount <= 0) { System.out.println("Amount must be positive."); return; }

        int recipientId = userDAO.getUserId(recipientEmail);
        if (recipientId == -1) { System.out.println("Recipient not found."); return; }

        if (walletService.transfer(currentUserId, currentUserEmail, recipientId, recipientEmail, amount)) {
            System.out.printf("Transferred $%.2f to %s successfully!\n", amount, recipientEmail);
        } else {
            System.out.println("Transfer failed. Insufficient balance.");
        }
    }

    private static void checkBalance() {
        double balance = walletService.checkBalance(currentUserId);
        System.out.printf("Your current balance: $%.2f\n", balance);
    }

    private static void transactionHistory() { transactionService.showTransactions(currentUserId); }

    private static void logout() {
        currentUserId = -1;
        currentUserEmail = "";
        System.out.println("Logged out successfully.");
    }

    private static void searchUsersByName() {
        System.out.print("Enter name prefix: ");
        String prefix = sc.nextLine();
        var list = new db.UserDAO().findUsersByNamePrefix(prefix);
        if (list.isEmpty()) System.out.println("No users found.");
        else {
            System.out.println("Matched users:");
            for (var u : list) System.out.println("- " + u.getName() + " <" + u.getEmail() + ">");
        }
    }

    private static int safeInt() {
        while (true) {
            String s = sc.nextLine();
            try { return Integer.parseInt(s.trim()); } catch (Exception ignored) { System.out.print("Enter a number: "); }
        }
    }
    private static double safeDouble() {
        while (true) {
            String s = sc.nextLine();
            try { return Double.parseDouble(s.trim()); } catch (Exception ignored) { System.out.print("Enter a number: "); }
        }
    }
}
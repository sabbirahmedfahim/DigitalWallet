import db.UserDAO;
import services.WalletService;
import services.TransactionService;
import java.util.Scanner;

public class Main {

    private static Scanner sc = new Scanner(System.in);
    private static UserDAO userDAO = new UserDAO();
    private static WalletService walletService = new WalletService();
    private static TransactionService transactionService = new TransactionService();
    private static int currentUserId = -1;
    private static String currentUserEmail = "";

    public static void main(String[] args) {
        while (true) {
            if (currentUserId == -1) {
                showLoginMenu();
            } else {
                showMainMenu();
            }
        }
    }

    private static void showLoginMenu() {
        System.out.println("\n=== Digital Wallet System ===");
        System.out.println("--- Please Login or Register ---");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Exit");
        System.out.print("Choose option: ");

        int option = sc.nextInt();
        sc.nextLine();

        switch (option) {
            case 1:
                login();
                break;
            case 2:
                register();
                break;
            case 3:
                System.out.println("Thank you for using Digital Wallet. Goodbye!");
                System.exit(0);
            default:
                System.out.println("Invalid option. Try again.");
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
        System.out.println("6. Logout");
        System.out.print("Choose option: ");

        int option = sc.nextInt();
        sc.nextLine();

        switch (option) {
            case 1:
                deposit();
                break;
            case 2:
                withdraw();
                break;
            case 3:
                transfer();
                break;
            case 4:
                checkBalance();
                break;
            case 5:
                transactionHistory();
                break;
            case 6:
                logout();
                break;
            default:
                System.out.println("Invalid option. Try again.");
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
            // Ensure wallet exists for login
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
        // Ensure wallet exists before deposit
        walletService.createWallet(currentUserId);

        System.out.print("Enter amount to deposit: $");
        double amount = sc.nextDouble();
        sc.nextLine();

        if (amount <= 0) {
            System.out.println("Amount must be positive.");
            return;
        }

        if (walletService.deposit(currentUserId, amount, "Cash deposit")) {
            System.out.printf("Deposited $%.2f successfully!\n", amount);
        } else {
            System.out.println("Deposit failed.");
        }
    }

    private static void withdraw() {
        // Ensure wallet exists before withdrawal
        walletService.createWallet(currentUserId);

        System.out.print("Enter amount to withdraw: $");
        double amount = sc.nextDouble();
        sc.nextLine();

        if (amount <= 0) {
            System.out.println("Amount must be positive.");
            return;
        }

        if (walletService.withdraw(currentUserId, amount, "Cash withdrawal")) {
            System.out.printf("Withdrew $%.2f successfully!\n", amount);
        } else {
            System.out.println("Withdrawal failed. Insufficient balance.");
        }
    }

    private static void transfer() {
        // Ensure wallet exists before transfer
        walletService.createWallet(currentUserId);

        System.out.print("Enter recipient's email: ");
        String recipientEmail = sc.nextLine();
        System.out.print("Enter amount to transfer: $");
        double amount = sc.nextDouble();
        sc.nextLine();

        if (amount <= 0) {
            System.out.println("Amount must be positive.");
            return;
        }

        int recipientId = userDAO.getUserId(recipientEmail);
        if (recipientId == -1) {
            System.out.println("Recipient not found.");
            return;
        }

        // Ensure recipient wallet exists
        walletService.createWallet(recipientId);

        if (walletService.transfer(currentUserId, currentUserEmail, recipientId, recipientEmail, amount)) {
            System.out.printf("Transferred $%.2f to %s successfully!\n", amount, recipientEmail);
        } else {
            System.out.println("Transfer failed. Insufficient balance.");
        }
    }

    private static void checkBalance() {
        // Ensure wallet exists before checking
        walletService.createWallet(currentUserId);

        double balance = walletService.checkBalance(currentUserId);
        System.out.printf("Your current balance: $%.2f\n", balance);
    }

    private static void transactionHistory() {
        // Ensure wallet exists before showing history
        walletService.createWallet(currentUserId);

        transactionService.showTransactions(currentUserId);
    }

    private static void logout() {
        currentUserId = -1;
        currentUserEmail = "";
        System.out.println("Logged out successfully.");
    }
}
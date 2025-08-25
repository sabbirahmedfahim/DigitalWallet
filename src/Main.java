import java.util.Scanner;
import db.UserDAO;
import db.WalletDAO;
import services.WalletService;
import services.TransactionService;

public class Main {
    private static Scanner sc = new Scanner(System.in);
    private static UserDAO userDAO = new UserDAO();
    private static WalletDAO walletDAO = new WalletDAO();
    private static WalletService walletService = new WalletService();
    private static TransactionService transactionService = new TransactionService();
    
    private static int currentUserId = -1;
    private static String currentUserEmail = "";

    public static void main(String[] args) {
        System.out.println("=== Digital Wallet System ===");
        
        while (true) {
            if (currentUserId == -1) {
                showLoginMenu();
            } else {
                showMainMenu();
            }
        }
    }
    
    private static void showLoginMenu() {
        System.out.println("\n--- Please Login or Register ---");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Exit");
        System.out.print("Choose option: ");
        
        int choice = getIntInput();
        
        switch (choice) {
            case 1 -> login();
            case 2 -> register();
            case 3 -> { 
                System.out.println("Thank you for using Digital Wallet. Goodbye!");
                System.exit(0); 
            }
            default -> System.out.println("Invalid option. Please try again.");
        }
    }
    
    private static void showMainMenu() {
        System.out.println("\n--- Digital Wallet Main Menu ---");
        System.out.println("Welcome, " + currentUserEmail);
        System.out.println("1. Deposit");
        System.out.println("2. Withdraw");
        System.out.println("3. Transfer");
        System.out.println("4. Check Balance");
        System.out.println("5. Transaction History");
        System.out.println("6. Logout");
        System.out.print("Choose option: ");
        
        int choice = getIntInput();
        
        switch (choice) {
            case 1 -> deposit();
            case 2 -> withdraw();
            case 3 -> transfer();
            case 4 -> checkBalance();
            case 5 -> showTransactions();
            case 6 -> logout();
            default -> System.out.println("Invalid option. Please try again.");
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
        
        if (userDAO.userExists(email)) {
            System.out.println("User with this email already exists.");
            return;
        }
        
        if (userDAO.addUser(name, email, password)) {
            int userId = userDAO.getUserId(email);
            walletDAO.createWallet(userId);
            System.out.println("Registration successful! You can now login.");
        } else {
            System.out.println("Registration failed. Please try again.");
        }
    }
    
    private static void deposit() {
        System.out.print("Enter amount to deposit: ");
        double amount = getDoubleInput();
        
        if (walletService.deposit(currentUserId, amount)) {
            System.out.println("Deposit successful!");
        } else {
            System.out.println("Deposit failed. Please try again.");
        }
    }
    
    private static void withdraw() {
        System.out.print("Enter amount to withdraw: ");
        double amount = getDoubleInput();
        
        if (walletService.withdraw(currentUserId, amount)) {
            System.out.println("Withdrawal successful!");
        } else {
            System.out.println("Withdrawal failed. Please try again.");
        }
    }
    
    private static void transfer() {
        System.out.print("Enter recipient email: ");
        String email = sc.nextLine();
        
        int toUserId = userDAO.getUserId(email);
        if (toUserId == -1) {
            System.out.println("Recipient not found.");
            return;
        }
        
        if (toUserId == currentUserId) {
            System.out.println("Cannot transfer to yourself.");
            return;
        }
        
        System.out.print("Enter amount to transfer: ");
        double amount = getDoubleInput();
        
        if (walletService.transfer(currentUserId, toUserId, amount)) {
            System.out.println("Transfer successful!");
        } else {
            System.out.println("Transfer failed. Please try again.");
        }
    }
    
    private static void checkBalance() {
        double balance = walletService.checkBalance(currentUserId);
        System.out.println("Your current balance: $" + String.format("%.2f", balance));
    }
    
    private static void showTransactions() {
        transactionService.showTransactions(currentUserId);
    }
    
    private static void logout() {
        currentUserId = -1;
        currentUserEmail = "";
        System.out.println("Logged out successfully.");
    }
    
    private static int getIntInput() {
        while (!sc.hasNextInt()) {
            System.out.println("Please enter a valid number.");
            sc.next();
        }
        int input = sc.nextInt();
        sc.nextLine();
        return input;
    }
    
    private static double getDoubleInput() {
        while (!sc.hasNextDouble()) {
            System.out.println("Please enter a valid amount.");
            sc.next();
        }
        double input = sc.nextDouble();
        sc.nextLine();
        return input;
    }
}
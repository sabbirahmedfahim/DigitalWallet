import db.UserDAO;
import java.util.Scanner;

public class Main {

    private static Scanner sc = new Scanner(System.in);
    private static UserDAO userDAO = new UserDAO();
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
        sc.nextLine(); // consume newline

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
                System.out.println("Deposit feature coming soon!");
                break;
            case 2:
                System.out.println("Withdraw feature coming soon!");
                break;
            case 3:
                System.out.println("Transfer feature coming soon!");
                break;
            case 4:
                System.out.println("Check Balance feature coming soon!");
                break;
            case 5:
                System.out.println("Transaction History feature coming soon!");
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
            System.out.println("Registration successful!");
        } else {
            System.out.println("Registration failed. Please try again.");
        }
    }

    private static void logout() {
        currentUserId = -1;
        currentUserEmail = "";
        System.out.println("Logged out successfully.");
    }
}
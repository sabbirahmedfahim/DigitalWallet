import db.UserDAO;
import java.util.Scanner;

public class Main {

    private static Scanner sc = new Scanner(System.in);
    private static UserDAO userDAO = new UserDAO();

    public static void main(String[] args) {
        while (true) {
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
                    System.out.println("Login feature coming soon!");
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
}
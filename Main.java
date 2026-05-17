import com.model.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("===== Welcome to Library Management System =====");

        System.out.print("Enter Username: ");
        String username = sc.nextLine();
        System.out.print("Enter Password: ");
        String password = sc.nextLine();
        if (!Admin.login(username, password)) {
            System.out.println("Invalid login /n"+"Exiting...");
            sc.close();
            return;
        }

        System.out.println("Login Successful! Welcome " + username + ".\n");

        int choice;
        do {
            System.out.println("\n--- Main Menu ---");
            System.out.println("1. Add Book");
            System.out.println("2. View Books");
            System.out.println("3. Issue Book");
            System.out.println("4. Return Book");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter Book Title: ");
                    String title = sc.nextLine();
                    System.out.print("Enter Author: ");
                    String author = sc.nextLine();
                    System.out.print("Enter Quantity: ");
                    int qty = sc.nextInt();
                    sc.nextLine();
                    BookDAO.addBook(title, author, qty);
                }

                case 2 -> {
                    System.out.println("\n--- Available Books ---");
                    BookDAO.viewBooks();
                }

                case 3 -> {
                    System.out.print("Enter Book ID: ");
                    int bookId = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter Student Name: ");
                    String student = sc.nextLine();
                    TransactionDAO.issueBook(bookId, student);
                }

                case 4 -> {
                    System.out.print("Enter Transaction ID: ");
                    int transId = sc.nextInt();
                    sc.nextLine();
                    TransactionDAO.returnBook(transId);
                }

                case 5 -> System.out.println("Exiting system");

                default -> System.out.println("Invalid choice. Try again!");
            }
        } while (choice != 5);

        sc.close();
    }
}

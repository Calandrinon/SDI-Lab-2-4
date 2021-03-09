package Main.UI;

import Main.Controller.UserController;
import Main.Model.RecordType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import static Main.Model.RecordType.*;
import static Main.Model.RecordType.CD;

public class AdminUI {
    private final Main.Controller.UserController userController;
    private final Main.Controller.RecordController recordController;
    private final Main.Controller.TransactionController transactionController;
    private boolean running;

    public AdminUI(Main.Controller.UserController userController, Main.Controller.RecordController recordController, Main.Controller.TransactionController transactionController) {
        this.userController = userController;
        this.recordController = recordController;
        this.transactionController = transactionController;
        this.running = true;
    }

    public void displayMenu() {
        System.out.println(" ------------------------------------- ");
        System.out.println("1. Add record");
        System.out.println("2. Add user");
        System.out.println("3. List records");
        System.out.println("4. List users");
        System.out.println("5. Remove record");
        System.out.println("6. Remove user");
        System.out.println("7. Update record");
        System.out.println("8. Update user");
        System.out.println("9. Get most purchased records");
        System.out.println("10. Filter transactions by date");
        System.out.println("11. Filter users by the minimum amount of transactions");
        System.out.println("0. Exit");
        System.out.println(" ------------------------------------- ");
    }


    public void addRecord() throws Exception {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the record ID: ");
        int recordId = input.nextInt(); input.nextLine();
        System.out.print("Enter the album name: ");
        String name = input.nextLine();
        System.out.print("Enter the price: ");
        int price = input.nextInt(); input.nextLine();
        System.out.print("Enter the record type: ");
        String recordTypeAsString = input.nextLine().toLowerCase();
        RecordType recordType = switch (recordTypeAsString) {
            case "vinyl" -> VINYL;
            case "tape" -> TAPE;
            default -> CD;
        };

        this.recordController.add(recordId, price, name, 1, recordType);
    }


    public void listRecords() {
        List<String> recordsAsStrings = this.recordController.getRepository();
        System.out.println("Records:");

        recordsAsStrings.forEach(System.out::println);
    }


    public void removeRecord() throws Exception {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the record ID to delete: ");
        int recordId = input.nextInt(); input.nextLine();

        this.recordController.remove(recordId);
    }


    public void updateRecord() throws Exception {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the record ID to update: ");
        int recordId = input.nextInt(); input.nextLine();
        System.out.print("Enter the new album name: ");
        String name = input.nextLine();
        System.out.print("Enter the new price: ");
        int price = input.nextInt(); input.nextLine();
        System.out.print("Enter the new number of copies in stock: ");
        int newInStock = input.nextInt(); input.nextLine();
        System.out.print("Enter the new record type: ");
        String recordTypeAsString = input.nextLine().toLowerCase();
        RecordType recordType = switch (recordTypeAsString) {
            case "vinyl" -> VINYL;
            case "tape" -> TAPE;
            default -> CD;
        };

        System.out.println(recordType.toString());
        this.recordController.update(recordId, price, name, newInStock, recordType);
    }


    public void addUser() throws Exception {
        Scanner input = new Scanner(System.in);

        System.out.print("User ID: ");
        int userId = input.nextInt(); input.nextLine();
        System.out.print("First name: ");
        String firstName = input.nextLine();
        System.out.print("Second name: ");
        String lastName = input.nextLine();

        this.userController.add(userId, firstName, lastName, 0);
    }


    public void listUsers() {
        List<String> usersAsStrings = this.userController.getRepository();
        System.out.println("Users:");

        usersAsStrings.forEach(System.out::println);
    }


    public void removeUser() throws Exception {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the user ID to delete: ");
        int userId = input.nextInt(); input.nextLine();

        this.userController.remove(userId);
    }


    public void updateUser() throws Exception {
        Scanner input = new Scanner(System.in);

        System.out.print("User ID to update: ");
        int userId = input.nextInt(); input.nextLine();
        System.out.print("New first name: ");
        String firstName = input.nextLine();
        System.out.print("New second name: ");
        String lastName = input.nextLine();

        this.userController.update(userId, firstName, lastName, 0);
    }


    public void run() {
        Scanner input = new Scanner(System.in);
        this.running = true;
        int option = 0;

        while (this.running) {
            this.displayMenu();
            System.out.print("Enter an option: ");
            option = input.nextInt();

            try {
                switch (option) {
                    case 0 -> this.running = false;
                    case 1 -> addRecord();
                    case 2 -> addUser();
                    case 3 -> listRecords();
                    case 4 -> listUsers();
                    case 5 -> removeRecord();
                    case 6 -> removeUser();
                    case 7 -> updateRecord();
                    case 8 -> updateUser();
                    case 9 -> mostPurchasedRecords();
                    case 10 -> filterTransactionsByDate();
                    case 11 -> filterUsersByMinimumTransactions();
                    default -> System.out.println("Enter an option between 1 and 11.");
                }
            }catch(Exception exception){
                System.out.println(exception.getMessage());
            }
        }
    }

    private void filterUsersByMinimumTransactions() {
        Scanner input = new Scanner(System.in);
        System.out.println("please enter the minimum amount of transactions: ");
        this.userController.filterByNumberOfTransactions(input.nextInt()).forEach(System.out::println);
    }

    private void filterTransactionsByDate() {
        System.out.println("enter the day you want the transactions from (dd/MM/yyyy): ");
        Scanner input = new Scanner(System.in);
        Date date = null;
        try {
            date = new SimpleDateFormat("dd/MM/yyyy").parse(input.nextLine());
            System.out.println(this.transactionController.filterByDate(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void mostPurchasedRecords() {
        System.out.println(this.transactionController.getMostPurchasedRecords());
    }
}


package Main.UI;

import java.util.List;
import java.util.Scanner;

import Main.Model.RecordType;

import static Main.Model.RecordType.*;

public class UI {
    private final Main.Controller.UserController UserController;
    private final Main.Controller.RecordController RecordController;
    private boolean running;

    public UI(Main.Controller.UserController UserController, Main.Controller.RecordController RecordController) {
        this.UserController = UserController;
        this.RecordController = RecordController;
        this.running = true;
    }

    public void displayMenu() {
        System.out.println("1. Add record");
        System.out.println("2. Add user");
        System.out.println("3. List records");
        System.out.println("4. List users");
        System.out.println("5. Remove record");
        System.out.println("6. Remove user");
        System.out.println("7. Update record");
        System.out.println("8. Update user");
        System.out.println("0. Exit");
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

        this.RecordController.add(recordId, price, name, 1, recordType);
    }


    public void listRecords() {
        List<String> recordsAsStrings = this.RecordController.getRepository();
        System.out.println("Records:");

        recordsAsStrings.forEach(System.out::println);
    }


    public void removeRecord() {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the record ID to delete: ");
        int recordId = input.nextInt(); input.nextLine();

        this.RecordController.remove(recordId);
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
        this.RecordController.update(recordId, price, name, newInStock, recordType);
    }


    public void addUser() throws Exception {
        Scanner input = new Scanner(System.in);

        System.out.print("User ID: ");
        int userId = input.nextInt(); input.nextLine();
        System.out.print("First name: ");
        String firstName = input.nextLine();
        System.out.print("Second name: ");
        String lastName = input.nextLine();

        this.UserController.add(userId, firstName, lastName, 0);
    }


    public void listUsers() {
        List<String> usersAsStrings = this.UserController.getRepository();
        System.out.println("Users:");

        usersAsStrings.forEach(System.out::println);
    }


    public void removeUser() {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the user ID to delete: ");
        int userId = input.nextInt(); input.nextLine();

        this.UserController.remove(userId);
    }


    public void updateUser() throws Exception {
        Scanner input = new Scanner(System.in);

        System.out.print("User ID to update: ");
        int userId = input.nextInt(); input.nextLine();
        System.out.print("New first name: ");
        String firstName = input.nextLine();
        System.out.print("New second name: ");
        String lastName = input.nextLine();

        this.UserController.update(userId, firstName, lastName, 0);
    }


    public void run() {
        Scanner input = new Scanner(System.in);
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
                    default -> System.out.println("Enter an option between 1 and 8.");
                }
            }catch(Exception exception){
                System.out.println(exception.getMessage());
            }
        }
    }
}
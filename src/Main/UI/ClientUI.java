package Main.UI;

import Main.Controller.ClientController;
import Main.Controller.TransactionController;
import Main.Controller.UserController;
import Main.Model.RecordType;

import java.util.Map;
import java.util.Scanner;

public class ClientUI {
    private final Main.Controller.RecordController recordController;
    private final TransactionController transactionController;
    private final UserController userController;
    private boolean running;
    private int userId;

    public ClientUI(UserController userController, Main.Controller.RecordController recordController, TransactionController transactionController, int userId) {
        this.userController = userController;
        this.recordController = recordController;
        this.transactionController = transactionController;
        this.userId = userId;
        this.running = true;
    }


    public void displayMenu() {
        System.out.println("1. Buy record");
        System.out.println("2. List all records");
        System.out.println("0. Exit");
    }


    public void buyRecord() throws Exception {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter the ID of the record: ");
        int recordId = input.nextInt(); input.nextLine();

        System.out.print("Enter the quantity: ");
        int quantity = input.nextInt(); input.nextLine();

        transactionController.makeTransaction(userId, recordId, quantity);
    }

    private void prettyPrintMapEntry(Map.Entry<Integer, Integer> entry){
        System.out.println("you own " + entry.getValue() + " instances of this record: " + this.recordController.getRecordByID(entry.getKey()));
    }

    public void listOwnedRecords() {
        System.out.println("Your records:");
        this.transactionController.getRecordsByUser(this.userId).entrySet().forEach(this::prettyPrintMapEntry);
    }


    public void run(Integer userId) {
        this.running = true;
        Scanner input = new Scanner(System.in);
        int option = 0;

        while (this.running) {
            this.displayMenu();
            System.out.print("Enter an option: ");
            option = input.nextInt();

            this.userId = userId;

            try {
                switch (option) {
                    case 0 -> this.running = false;
                    case 1 -> this.buyRecord();
                    case 2 -> this.listOwnedRecords();
                    case 3 -> this.filterRecordsByPrice();
                    default -> System.out.println("Enter an option between 0 and 3.");
                }
            } catch(Exception exception){
                System.out.println(exception.getMessage());
            }
        }
    }

    private void filterRecordsByPrice() {
        System.out.println("please enter the upper limit for the price");
        Scanner input = new Scanner(System.in);
        this.recordController.filterByPrice(input.nextInt()).forEach(System.out::println);
    }

}

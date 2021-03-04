package Main.UI;

import Main.Controller.ClientController;
import Main.Controller.TransactionController;
import Main.Model.RecordType;

import java.util.Scanner;

public class ClientUI {
    private final Main.Controller.RecordController recordController;
    private final TransactionController transactionController;
    private final ClientController clientController;
    private boolean running;
    private int userId;

    public ClientUI(ClientController clientController, Main.Controller.RecordController recordController, TransactionController transactionController, int userId) {
        this.clientController = clientController;
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
        clientController.acquireRecord(recordId, RecordType.VINYL);
    }


    public void listOwnedRecords() {
        System.out.println("Your records:");
        clientController.getOwnedRecords().forEach(record -> System.out.println(record.toString()));
    }


    public void run() {
        this.running = true;
        Scanner input = new Scanner(System.in);
        int option = 0;

        while (this.running) {
            this.displayMenu();
            System.out.print("Enter an option: ");
            option = input.nextInt();

            try {
                switch (option) {
                    case 0 -> this.running = false;
                    case 1 -> this.buyRecord();
                    case 2 -> this.listOwnedRecords();
                    default -> System.out.println("Enter an option between 0 and 2.");
                }
            } catch(Exception exception){
                System.out.println(exception.getMessage());
            }
        }
    }

}

package UI;

import java.util.Scanner;

import Controller.IController;
import Controller.RecordController;
import Controller.UserController;
import Model.Record;
import Model.RecordType;
import Model.User;

import javax.swing.*;

import static Model.RecordType.*;

public class UI {
    private final Controller.UserController UserController;
    private final Controller.RecordController RecordController;
    private boolean running;

    public UI(Controller.UserController UserController, Controller.RecordController RecordController) {
        this.UserController = UserController;
        this.RecordController = RecordController;
        this.running = true;
    }

    public void displayMenu() {
        System.out.println("1. Add record");
        System.out.println("2. Add user");
        System.out.println("0. Exit");
    }

    public void addRecord() throws Exception {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the record ID: ");
        int recordId = input.nextInt(); input.nextLine();
        System.out.print("Enter the record name: ");
        String name = input.nextLine();
        System.out.print("Enter the price: ");
        int price = input.nextInt(); input.nextLine();
        System.out.print("Enter the record type: ");
        String recordTypeAsString = input.nextLine().toLowerCase();
        RecordType recordType = CD;

        switch (recordTypeAsString) {
            case "cd":
                recordType = CD;
                break;
            case "vinyl":
                recordType = VINYL;
                break;
            case "tape":
                recordType = TAPE;
                break;
        }

        System.out.println(recordType.toString());
        this.RecordController.add(recordId, price, name, 1, recordType);
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

    public void run() {
        Scanner input = new Scanner(System.in);
        int option = 0;

        while (this.running) {
            this.displayMenu();
            System.out.print("Enter an option: ");
            option = input.nextInt();

            try {
                switch (option) {
                    case 0:
                        this.running = false;
                        break;
                    case 1:
                        addRecord();
                        break;
                    case 2:
                        addUser();
                        break;
                    default:
                        System.out.println("Enter an option between 1 and 2.");
                }
            }catch(Exception ignore){}
        }
    }
}
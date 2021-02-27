package UI;

import Controller.IController;

import java.util.Scanner;
import Model.Record;
import Model.RecordType;
import Model.User;

import static Model.RecordType.*;

public class UI {
    private IController controller;
    private boolean running;

    public UI(IController controller) {
        this.controller = controller;
        this.running = true;
    }

    public void displayMenu() {
        System.out.println("1. Add record");
        System.out.println("2. Add user");
        System.out.println("0. Exit");
    }

    public void addRecord() {
        Scanner input = new Scanner(System.in);
        int recordId = input.nextInt();
        String name = input.nextLine();
        int price = input.nextInt();
        String recordTypeAsString = input.nextLine().toLowerCase();
        RecordType recordType;

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
            default:
                recordType = CD;
        }
        Record record = new Record(recordId, price, name, 1, recordType);
        //add record in repo through the RecordController
    }

    public void addUser() {
        Scanner input = new Scanner(System.in);

        int userId = input.nextInt();
        String firstName = input.nextLine();
        String lastName = input.nextLine();

        User user = new User(userId, firstName, lastName, 0);
        //add user in repo through the UserController
    }

    public void run() {
        Scanner input = new Scanner(System.in);
        int option = 0;

        while (this.running) {
            this.displayMenu();
            System.out.print("Enter an option: ");
            input.nextInt();

            switch (option) {
                case 1:
                    addRecord();
                    break;
                case 2:
                    addUser();
                    break;
                default:
                    System.out.println("Enter an option between 1 and 2.");
            }
        }
    }
}

package Main.UI;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import Main.Model.RecordType;

import static Main.Model.RecordType.*;

public class UI {
    private final Main.Controller.UserController UserController;
    private final Main.Controller.RecordController RecordController;
    private final ClientUI clientUI;
    private final AdminUI adminUI;
    private boolean running;

    public UI(Main.Controller.UserController UserController, Main.Controller.RecordController RecordController, ClientUI clientUI, AdminUI adminUI) {
        this.UserController = UserController;
        this.RecordController = RecordController;
        this.clientUI = clientUI;
        this.adminUI = adminUI;
        this.running = true;
    }


    public void displayMenu() {
        System.out.println("1. Client role");
        System.out.println("2. Admin role");
        System.out.println("0. Exit");
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
                    case 1 -> {
                        System.out.println("login with your id: ");
                        Integer givenID = input.nextInt();
                        this.UserController.exists(givenID).orElseThrow(() -> new Exception("no user has this id"));
                        this.clientUI.run(givenID);
                    }
                    case 2 -> this.adminUI.run();
                    default -> System.out.println("Enter an option between 0 and 2.");
                }
            }catch(Exception exception){
                exception.printStackTrace();
            }
        }
    }
}
package Main;

import Main.Controller.ClientController;
import Main.Controller.RecordController;
import Main.Controller.TransactionController;
import Main.Controller.UserController;
import Main.Model.Record;
import Main.Model.Transaction;
import Main.Model.User;
import Main.Repository.InMemoryRepository;
import Main.UI.AdminUI;
import Main.UI.ClientUI;
import Main.UI.UI;
import Main.Validator.RecordValidator;
import Main.Validator.TransactionValidator;
import Main.Validator.UserValidator;

public class Main {
    public static void main(String[] args) {
        InMemoryRepository<Integer, Transaction> transactionRepository = new InMemoryRepository<Integer, Transaction>(new TransactionValidator());
        InMemoryRepository<Integer, Record> clientRecordRepository = new InMemoryRepository<Integer, Record>(new RecordValidator());
        InMemoryRepository<Integer, Record> recordRepository = new InMemoryRepository<Integer, Record>(new RecordValidator());
        InMemoryRepository<Integer, User> userRepository = new InMemoryRepository<Integer, User>(new UserValidator());
        TransactionController transactionController = new TransactionController(recordRepository, userRepository, transactionRepository);
        ClientController clientController = new ClientController(clientRecordRepository, recordRepository);
        UserController userController = new UserController(userRepository);
        RecordController recordController = new RecordController(recordRepository);
        ClientUI clientUserInterface = new ClientUI(clientController, recordController, transactionController, 1);
        AdminUI adminUserInterface = new AdminUI(userController, recordController);
        UI userInterface = new UI(userController, recordController, clientUserInterface, adminUserInterface);
        userInterface.run();
    }
}

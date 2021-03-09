package Main;

import Controller.RecordController;
import Controller.TransactionController;
import Controller.UserController;
import Model.Record;
import Model.Transaction;
import Model.User;
import Repository.InMemoryRepository;
import Repository.Repository;
import UI.AdminUI;
import UI.ClientUI;
import UI.UI;
import Validator.RecordValidator;
import Validator.TransactionValidator;
import Validator.UserValidator;

public class Main {
    public static void main(String[] args) {
        Repository<Integer, Transaction> transactionRepository = new InMemoryRepository<Integer, Transaction>(new TransactionValidator());
        Repository<Integer, Record> recordRepository = new InMemoryRepository<Integer, Record>(new RecordValidator());
        Repository<Integer, User> userRepository = new InMemoryRepository<Integer, User>(new UserValidator());
        TransactionController transactionController = new TransactionController(recordRepository, userRepository, transactionRepository);
        UserController userController = new UserController(userRepository);
        RecordController recordController = new RecordController(recordRepository);
        ClientUI clientUserInterface = new ClientUI(userController, recordController, transactionController, 1);
        AdminUI adminUserInterface = new AdminUI(userController, recordController, transactionController);
        UI userInterface = new UI(userController, recordController, clientUserInterface, adminUserInterface);
        userInterface.run();
    }
}

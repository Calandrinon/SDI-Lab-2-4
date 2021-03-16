package Main;

import Controller.RecordController;
import Controller.TransactionController;
import Controller.UserController;
import Model.Record;
import Model.Transaction;
import Model.User;
import Repository.Repository;
import Repository.FileRepository;
import UI.AdminUI;
import UI.ClientUI;
import UI.UI;
import Validator.RecordValidator;
import Validator.TransactionValidator;
import Validator.UserValidator;

public class FileRepoMain {
    public static void main(String[] args) {
        Repository<Integer, Transaction> transactionRepository = new FileRepository<Integer, Transaction>(new TransactionValidator(), "transactions.txt", Transaction.reader(Transaction::fileReader), Transaction.writer(Transaction::fileWriter));
        Repository<Integer, Record> recordRepository = new FileRepository<Integer, Record>(new RecordValidator(), "records.txt", Record.reader(Record::fileReader), Record.writer(Record::fileWriter));
        Repository<Integer, User> userRepository = new FileRepository<Integer, User>(new UserValidator(), "users.txt", User.reader(User::fileReader), User.writer(User::fileWriter));
        TransactionController transactionController = new TransactionController(recordRepository, userRepository, transactionRepository);
        UserController userController = new UserController(userRepository);
        RecordController recordController = new RecordController(recordRepository);
        ClientUI clientUserInterface = new ClientUI(userController, recordController, transactionController, 1);
        AdminUI adminUserInterface = new AdminUI(userController, recordController, transactionController);
        UI userInterface = new UI(userController, recordController, clientUserInterface, adminUserInterface);
        userInterface.run();
    }
}

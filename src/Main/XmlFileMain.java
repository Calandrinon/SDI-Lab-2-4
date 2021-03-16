import Controller.RecordController;
import Controller.TransactionController;
import Controller.UserController;
import Model.Record;
import Model.Transaction;
import Model.User;
import Repository.Repository;
import UI.AdminUI;
import UI.ClientUI;
import UI.UI;
import Validator.RecordValidator;
import Validator.TransactionValidator;
import Validator.UserValidator;
import Repository.XmlFileRepository;

public class XmlFileMain {
    public static void main(String[] args) {
        Repository<Integer, Record> recordRepository = new XmlFileRepository<Integer, Record>(new RecordValidator(), Record.recordEncoder, Record.recordDecoder, "src/Main/Repository/RepositoryFiles/records.xml");
        Repository<Integer, Transaction> transactionRepository = new XmlFileRepository<Integer, Transaction>(new TransactionValidator(), Transaction.transactionEncoder, Transaction.transactionDecoder, "src/Main/Repository/RepositoryFiles/transactions.xml");
        Repository<Integer, User> userRepository = new XmlFileRepository<Integer, User>(new UserValidator(), User.userEncoder, User.userDecoder, "src/Main/Repository/RepositoryFiles/users.xml");

        TransactionController transactionController = new TransactionController(recordRepository, userRepository, transactionRepository);
        UserController userController = new UserController(userRepository);
        RecordController recordController = new RecordController(recordRepository);
        ClientUI clientUserInterface = new ClientUI(userController, recordController, transactionController, 1);
        AdminUI adminUserInterface = new AdminUI(userController, recordController, transactionController);
        UI userInterface = new UI(userController, recordController, clientUserInterface, adminUserInterface);
        userInterface.run();
    }
}


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
import Repository.PostgresRepository;

public class DatabaseMain {
    public static void main(String[] args) {
        //String url = "jdbc:postgresql://localhost:5432/onlinemusicstore";
        String url = "jdbc:postgresql://ec2-52-50-171-4.eu-west-1.compute.amazonaws.com:5432/dequ4hdpce8hbn?user=kgjjyzuqvgooml&password=1cf52939657451151fad34a67b01a285ba4a927aea25bda2f98fda5948ba1842";
        Repository<Integer, Transaction> transactionRepository = new PostgresRepository<Integer, Transaction>(new TransactionValidator(), "UserTransaction", url);
        Repository<Integer, Record> recordRepository = new PostgresRepository<Integer, Record>(new RecordValidator(), "Record", url);
        Repository<Integer, User> userRepository = new PostgresRepository<Integer, User>(new UserValidator(), "ClientUser", url);

        TransactionController transactionController = new TransactionController(recordRepository, userRepository, transactionRepository);
        UserController userController = new UserController(userRepository);
        RecordController recordController = new RecordController(recordRepository);
        ClientUI clientUserInterface = new ClientUI(userController, recordController, transactionController, 1);
        AdminUI adminUserInterface = new AdminUI(userController, recordController, transactionController);
        UI userInterface = new UI(userController, recordController, clientUserInterface, adminUserInterface);
        userInterface.run();
    }
}

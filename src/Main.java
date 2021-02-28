import Controller.RecordController;
import Controller.UserController;
import Model.Record;
import Model.User;
import Repository.InMemoryRepository;
import UI.UI;
import Validator.RecordValidator;
import Validator.UserValidator;

public class Main {
    public static void main(String[] args) {

        InMemoryRepository<Integer, Record> recordRepository = new InMemoryRepository<Integer, Record>(new RecordValidator());
        InMemoryRepository<Integer, User> userRepository = new InMemoryRepository<Integer, User>(new UserValidator());
        UserController userController = new UserController(userRepository);
        RecordController recordController = new RecordController(recordRepository);
        UI userInterface = new UI(userController, recordController);
        userInterface.run();
    }
}

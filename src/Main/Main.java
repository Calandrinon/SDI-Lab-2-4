package Main;

import Main.Controller.RecordController;
import Main.Controller.UserController;
import Main.Model.Record;
import Main.Model.User;
import Main.Repository.FileRepository;
import Main.Repository.InMemoryRepository;
import Main.UI.UI;
import Main.Validator.RecordValidator;
import Main.Validator.UserValidator;

public class Main {
    public static void main(String[] args) {
        FileRepository<Integer, Record> recordRepository = new FileRepository<Integer, Record>(new RecordValidator(), "records.txt");
        InMemoryRepository<Integer, User> userRepository = new InMemoryRepository<Integer, User>(new UserValidator());
        UserController userController = new UserController(userRepository);
        RecordController recordController = new RecordController(recordRepository);
        UI userInterface = new UI(userController, recordController);
        userInterface.run();
    }
}

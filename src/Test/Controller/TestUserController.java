package Test.Controller;

import Main.Controller.UserController;
import Main.Model.Record;
import Main.Model.RecordType;
import Main.Model.User;
import Main.Repository.InMemoryRepository;
import Main.Repository.Repository;
import Main.Validator.RecordValidator;
import Main.Validator.UserValidator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class TestUserController {
    private Repository<Integer, User> userRepository;
    private UserController userController;


    @Before
    public void setUp() {
        this.userRepository = new InMemoryRepository<>(new UserValidator());
        this.userController = new UserController(userRepository);
    }


    @After
    public void tearDown() {
        this.userRepository = null;
        this.userController = null;
    }


    @Test
    public void testAddAverageCase() throws Exception {
        this.userController.add(1, "abc", "def", 0);
        assert(this.userController.getRepository().size() == 1);
    }


    @Test
    public void testAddDuplicateID() throws Exception {
        this.userController.add(1, "abc", "def", 0);
        try {
            this.userController.add(1, "aaa", "bbb", 0);
            assert(false);
        }catch (Exception ignore){
            assert(this.userController.getRepository().size() == 1);
        }
    }


    @Test
    public void testFirstNameAfterUpdate() throws Exception {
        this.userController.add(1, "abc", "def", 0);
        this.userController.update(1, "aaa", "def", 0);
        Stream<User> stream = StreamSupport.stream(this.userRepository.findAll().spliterator(), false);
        List<User> users = stream.filter(user -> user.getId() == 1).collect(Collectors.toList());
        assert(users.get(0).getFirstName().equals("aaa"));
    }


    @Test
    public void testLastNameAfterUpdate() throws Exception {
        this.userController.add(1, "abc", "def", 0);
        this.userController.update(1, "abc", "ddd", 0);
        Stream<User> stream = StreamSupport.stream(this.userRepository.findAll().spliterator(), false);
        List<User> users = stream.filter(user -> user.getId() == 1).collect(Collectors.toList());
        assert(users.get(0).getLastName().equals("ddd"));
    }


    @Test
    public void testNumberOfTransactionsAfterUpdate() throws Exception {
        this.userController.add(1, "bbb", "aaa", 25);
        this.userController.update(1, "bbb", "aaa", 30);
        Stream<User> stream = StreamSupport.stream(this.userRepository.findAll().spliterator(), false);
        List<User> users = stream.filter(user -> user.getId() == 1).collect(Collectors.toList());
        assert(users.get(0).getNumberOfTransactions() == 30);
    }


    @Test
    public void testRemove() throws Exception {
        this.userController.add(1, "bbb", "aaa", 25);
        this.userController.remove(1);
        assert(this.userController.getRepository().size() == 0);
    }


    @Test
    public void testRemoveNonexistentUser() throws Exception {
        boolean passed;
        try {
            this.userController.remove(5);
            passed = false;
        } catch (Exception exception) {
            passed = true;
        }

        assert(passed);
    }


    @Test
    public void testFilterByNumberOfTransactions_numberOfUsersReturned() throws Exception {
        this.userController.add(1, "bbb", "aaa", 100);
        this.userController.add(2, "ccc", "aaa", 50);
        this.userController.add(3, "ddd", "aaa", 25);

        List<String> users = this.userController.filterByNumberOfTransactions(75);
        assert(users.size() == 1);
    }


    @Test
    public void testFilterByNumberOfTransactions_userReturned() throws Exception {
        this.userController.add(1, "bbb", "aaa", 100);
        this.userController.add(2, "ccc", "aaa", 50);
        this.userController.add(3, "ddd", "aaa", 25);

        List<String> stringsOfFilteredUsers = this.userController.filterByNumberOfTransactions(75);
        Stream<User> streamOfUsers = StreamSupport.stream(this.userRepository.findAll().spliterator(), false);
        List<User> filteredUsers = streamOfUsers.filter(user -> user.getId() == 1).collect(Collectors.toList());

        System.out.println(filteredUsers.get(0).toString());
        System.out.println(stringsOfFilteredUsers.get(0));

        assert(filteredUsers.get(0).toString().equals(stringsOfFilteredUsers.get(0)));
    }
}

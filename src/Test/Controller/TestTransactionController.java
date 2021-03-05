package Test.Controller;

import Main.Controller.RecordController;
import Main.Controller.TransactionController;
import Main.Controller.UserController;
import Main.Exceptions.ValidationException;
import Main.Model.Record;
import Main.Model.RecordType;
import Main.Model.Transaction;
import Main.Model.User;
import Main.Repository.InMemoryRepository;
import Main.Repository.Repository;
import Main.Validator.RecordValidator;
import Main.Validator.TransactionValidator;
import Main.Validator.UserValidator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class TestTransactionController {
    private final static int TRANSACTION_ID = 1;
    private final static int NEW_TRANSACTION_ID = 2;
    private final static int USER_ID = 1;
    private final static String USER_FIRSTNAME = "abc";
    private final static String USER_LASTNAME = "def";
    private final static int RECORD_ID = 1;
    private final static int RECORD_ID_2 = 2;
    private final static int RECORDS_IN_STOCK = 200;
    private final static int RECORDS_IN_STOCK_2 = 200;
    private final static int RECORD_QUANTITY = 50;
    private final static String RECORD_NAME = "aaa";
    private final static String RECORD_NAME_2 = "bbb";
    private final static Date DATE = new Date();
    private final static int QUANTITY = 1;
    private Repository<Integer, Record> recordRepository;
    private Repository<Integer, User> userRepository;
    private Repository<Integer, Transaction> transactionRepository;
    private TransactionController transactionController;
    private UserController userController;
    private RecordController recordController;


    @Before
    public void setUp() throws Exception {
        this.recordRepository = new InMemoryRepository<>(new RecordValidator());
        this.userRepository = new InMemoryRepository<>(new UserValidator());
        this.transactionRepository = new InMemoryRepository<>(new TransactionValidator());
        this.transactionController = new TransactionController(recordRepository, userRepository, transactionRepository);
        this.userController = new UserController((InMemoryRepository<Integer, User>) this.userRepository);
        this.recordController = new RecordController((InMemoryRepository<Integer, Record>) recordRepository);
        this.userController.add(USER_ID, USER_FIRSTNAME, USER_LASTNAME, 0);
        this.recordController.add(RECORD_ID, 59, RECORD_NAME, RECORDS_IN_STOCK, RecordType.VINYL);
        this.recordController.add(RECORD_ID_2, 99, RECORD_NAME_2, RECORDS_IN_STOCK_2, RecordType.VINYL);
    }


    @After
    public void tearDown() {
        this.recordRepository = null;
        this.userRepository = null;
        this.transactionRepository = null;
        this.transactionController = null;
    }


    @Test
    public void testMakeTransaction_oneTransaction() throws Exception {
        this.transactionController.makeTransaction(USER_ID, RECORD_ID, 15);
        assert(StreamSupport.stream(this.transactionRepository.findAll().spliterator(), false).count() == 1);
    }


    @Test
    public void testMakeTransaction_multipleTransactions() throws Exception {
        this.transactionController.makeTransaction(USER_ID, RECORD_ID, RECORD_QUANTITY);
        this.transactionController.makeTransaction(USER_ID, RECORD_ID_2, RECORD_QUANTITY);
        assert(StreamSupport.stream(this.transactionRepository.findAll().spliterator(), false).count() == 2);
    }


    @Test
    public void testMakeTransaction_quantityUpdate() throws Exception {
        this.transactionController.makeTransaction(USER_ID, RECORD_ID, 50);
        Stream<Record> streamOfRecords = StreamSupport.stream(this.recordRepository.findAll().spliterator(), false);
        List<Record> records = streamOfRecords.filter(transaction -> transaction.getId() == RECORD_ID).collect(Collectors.toList());
        assert(records.get(0).getInStock() == RECORDS_IN_STOCK - 50);
    }


    @Test
    public void testMakeTransaction_updatedNumberOfTransactionsForUser() throws Exception {
        this.transactionController.makeTransaction(USER_ID, RECORD_ID, 50);
        Stream<User> streamOfUsers = StreamSupport.stream(this.userRepository.findAll().spliterator(), false);
        List<User> users = streamOfUsers.filter(user -> user.getId() == USER_ID).collect(Collectors.toList());
        assert(users.get(0).getNumberOfTransactions() == 1);
    }
}

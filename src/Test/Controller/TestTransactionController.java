package Controller;

import Model.Record;
import Model.RecordType;
import Model.Transaction;
import Model.User;
import Repository.InMemoryRepository;
import Repository.Repository;
import Validator.RecordValidator;
import Validator.TransactionValidator;
import Validator.UserValidator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

<<<<<<< HEAD
=======

import java.util.Calendar;
>>>>>>> 3894a95864a7a867354efaf05c213f1850c50d6f
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class TestTransactionController {
    private final static int TRANSACTION_ID = 1;
    private final static int NEW_TRANSACTION_ID = 2;
    private final static int USER_ID = 1;
    private final static int USER_ID_2 = 2;
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
        this.recordRepository = new InMemoryRepository<Integer, Record>(new RecordValidator());
        this.userRepository = new InMemoryRepository<Integer, User>(new UserValidator());
        this.transactionRepository = new InMemoryRepository<Integer, Transaction>(new TransactionValidator());
        this.transactionController = new TransactionController(recordRepository, userRepository, transactionRepository);
        this.userController = new UserController((InMemoryRepository<Integer, User>) this.userRepository);
        this.recordController = new RecordController((InMemoryRepository<Integer, Record>) recordRepository);
        this.userController.add(USER_ID, USER_FIRSTNAME, USER_LASTNAME, 0);
        this.userController.add(USER_ID_2, USER_FIRSTNAME, USER_LASTNAME, 0);
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


    @Test
    public void testFilterByDate() throws Exception {
        this.transactionController.makeTransaction(USER_ID, RECORD_ID, 50);

        Date date = new Date();
        List<String> list = this.transactionController.filterByDate(date);
        assert(list.size() == 1);
    }


    @Test
    public void testGetQuantityPurchasedByRecordID() throws Exception {
        this.transactionController.makeTransaction(USER_ID, RECORD_ID, 50);
        this.transactionController.makeTransaction(USER_ID, RECORD_ID, 25);
        this.transactionController.makeTransaction(USER_ID_2, RECORD_ID, 25);
        assert(this.transactionController.getTotalQuantityPurchasedByRecordID(RECORD_ID) == 100);
    }


    @Test
    public void testGetMostPurchasedRecords() throws Exception {
        this.transactionController.makeTransaction(USER_ID, RECORD_ID, 3);
        this.transactionController.makeTransaction(USER_ID_2, RECORD_ID_2, 1);
        this.transactionController.makeTransaction(USER_ID_2, RECORD_ID_2, 1);
        assert(this.transactionController.getMostPurchasedRecords().get(0).getId() == RECORD_ID);
    }
}

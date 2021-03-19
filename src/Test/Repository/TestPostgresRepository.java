package Repository;

import Exceptions.ValidationException;
import Model.RecordType;
import Model.Transaction;
import Model.User;
import Validator.Validator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Model.Record;

import javax.swing.text.html.Option;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;
import Validator.UserValidator;
import Validator.RecordValidator;
import Validator.TransactionValidator;

public class TestPostgresRepository {
    private PostgresRepository<Integer, User> userRepository;
    private PostgresRepository<Integer, Record> recordRepository;
    private PostgresRepository<Integer, Transaction> transactionRepository;
    private String url, username, password;

    @Before
    public void setUp() {
        this.url = "jdbc:postgresql://localhost:5432/onlinemusicstore";
        this.username = "calandrinon";
        this.password = "12345";
        this.userRepository = new PostgresRepository<Integer, User>(new UserValidator(), "ClientUserTestTable", this.url);
        this.recordRepository = new PostgresRepository<Integer, Record>(new RecordValidator(), "RecordTestTable", this.url);
        this.transactionRepository = new PostgresRepository<Integer, Transaction>(new TransactionValidator(), "UserTransactionTestTable", this.url);

        String deleteStatement = "DELETE FROM UserTransactionTestTable; DELETE FROM ClientUserTestTable; DELETE FROM RecordTestTable;";
        String resetIncrementalPKStatement = "TRUNCATE TABLE UserTransactionTestTable RESTART IDENTITY;";
        try (var connection = DriverManager.getConnection(this.url, this.username, this.password);
             var firstPreparedStatement = connection.prepareStatement(deleteStatement);
             var secondPreparedStatement= connection.prepareStatement(resetIncrementalPKStatement)) {
                firstPreparedStatement.executeUpdate();
                secondPreparedStatement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }



        System.out.println("SETUP METHOD CALLED");
    }


    @After
    public void tearDown() {
        this.url = null;
        this.username = null;
        this.password = null;
        this.userRepository = null;
        this.recordRepository = null;
        this.transactionRepository = null;
        System.out.println("TEARDOWN METHOD CALLED");
    }


    @Test
    public void someTest() {
        assert(this.url != null);
        assert(this.username != null);
        assert(this.password != null);
        assert(this.userRepository!= null);
        assert(this.recordRepository!= null);
        assert(this.transactionRepository!= null);
        System.out.println("> setUp and tearDown run properly.");
    }


    @Test
    public void testFindAllForUsers() {
        String firstInsertStatement = "INSERT INTO ClientUserTestTable (UserId, FirstName, LastName, NumberOfTransactions) VALUES (15, 'userfirstname', 'userlastname', 22)";
        String secondInsertStatement = "INSERT INTO ClientUserTestTable (UserId, FirstName, LastName, NumberOfTransactions) VALUES (72, 'somename', 'somename', 52)";

        try (var connection = DriverManager.getConnection(this.url, this.username, this.password);
             var firstPreparedStatement = connection.prepareStatement(firstInsertStatement);
             var secondPreparedStatement = connection.prepareStatement(secondInsertStatement)) {
            firstPreparedStatement.executeUpdate();
            secondPreparedStatement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        Iterable<User> list = this.userRepository.findAll();
        Iterator<User> userIterator = list.iterator();
        List<User> users = new ArrayList<>();

        while (userIterator.hasNext()) {
            users.add(userIterator.next());
        }

        assert(users.size() == 2);
    }


    @Test
    public void testFindAllForRecords() {
        String insertStatement = "INSERT INTO RecordTestTable (RecordId, AlbumName, Price, InStock, RecordType) VALUES (14, 'abc', 99, 200, 'VINYL');";

        try (var connection = DriverManager.getConnection(this.url, this.username, this.password);
             var preparedStatement = connection.prepareStatement(insertStatement)) {
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        Iterable<Record> list = this.recordRepository.findAll();
        Iterator<Record> recordIterator = list.iterator();
        List<Record> records = new ArrayList<>();

        while (recordIterator.hasNext()) {
            records.add(recordIterator.next());
        }

        assert(records.size() == 1);
    }


    @Test
    public void testFindAllForTransactions() throws ValidationException {
        User user = new User("testuserfirstname", "testuserlastname", 20);
        user.setId(2);
        Optional<User> optionalUser = this.userRepository.save(user);

        Record record = new Record(99, "Dark Side Of The Moon", 100, RecordType.VINYL);
        record.setId(3);
        Optional<Record> optionalRecord = this.recordRepository.save(record);

        Transaction transaction = new Transaction(2, 3, new Date(), 2);
        transaction.setId(1);
        Optional<Transaction> optionalTransaction = this.transactionRepository.save(transaction);

        Iterable<Transaction> list = this.transactionRepository.findAll();
        Iterator<Transaction> transactionIterator = list.iterator();
        List<Transaction> transactions = new ArrayList<>();

        while (transactionIterator.hasNext()) {
            transactions.add(transactionIterator.next());
        }

        assert(transactions.size() == 1 && transactions.get(0).getUserID() == 2 && transactions.get(0).getRecordID() == 3);
    }


    @Test
    public void testSaveForUsers() throws ValidationException, SQLException {
        User user = new User("testuserfirstname", "testuserlastname", 20);
        user.setId(1);

        int oldNumberOfEntities = this.userRepository.getNumberOfEntities();
        Optional<User> optionalUser = this.userRepository.save(user);
        int newNumberOfEntities = this.userRepository.getNumberOfEntities();

        assert(oldNumberOfEntities == 0 && newNumberOfEntities == 1);
    }


    @Test
    public void testSaveForRecords() throws ValidationException, SQLException {
        Record record = new Record(99, "Dark Side Of The Moon", 100, RecordType.VINYL);
        record.setId(1);

        int oldNumberOfEntities = this.recordRepository.getNumberOfEntities();
        Optional<Record> optionalRecord = this.recordRepository.save(record);
        int newNumberOfEntities = this.recordRepository.getNumberOfEntities();

        assert(oldNumberOfEntities == 0 && newNumberOfEntities == 1);
    }


    @Test
    public void testSaveForTransactions() throws ValidationException, SQLException {
        User user = new User("testuserfirstname", "testuserlastname", 20);
        user.setId(1);
        Optional<User> optionalUser = this.userRepository.save(user);

        Record record = new Record(99, "Dark Side Of The Moon", 100, RecordType.VINYL);
        record.setId(1);
        Optional<Record> optionalRecord = this.recordRepository.save(record);

        Transaction transaction = new Transaction(1, 1, new Date(), 2);
        transaction.setId(1);

        int oldNumberOfEntities = this.transactionRepository.getNumberOfEntities();
        Optional<Transaction> optionalTransaction = this.transactionRepository.save(transaction);
        int newNumberOfEntities = this.transactionRepository.getNumberOfEntities();

        assert(oldNumberOfEntities == 0 && newNumberOfEntities == 1);
    }


    @Test
    public void testDeleteForUsers() throws ValidationException {
        User user = new User("abc", "def", 2);
        user.setId(1);

        this.userRepository.save(user);
        int oldNumberOfEntities = this.userRepository.getNumberOfEntities();
        Optional<User> optionalUser = this.userRepository.delete(1);
        int newNumberOfEntities = this.userRepository.getNumberOfEntities();

        assert(oldNumberOfEntities == 1 && newNumberOfEntities == 0);
    }


    @Test
    public void testDeleteForRecords() throws ValidationException {
        Record record = new Record(99, "Dark Side Of The Moon", 100, RecordType.VINYL);
        record.setId(1);

        this.recordRepository.save(record);
        int oldNumberOfEntities = this.recordRepository.getNumberOfEntities();
        Optional<Record> optionalRecord = this.recordRepository.delete(1);
        int newNumberOfEntities = this.recordRepository.getNumberOfEntities();

        assert(oldNumberOfEntities == 1 && newNumberOfEntities == 0);
    }


    @Test
    public void testDeleteForTransactions() throws ValidationException {
        User user = new User("abc", "def", 2);
        user.setId(1);
        this.userRepository.save(user);

        Record record = new Record(99, "Dark Side Of The Moon", 100, RecordType.VINYL);
        record.setId(1);
        this.recordRepository.save(record);

        Transaction transaction = new Transaction(1, 1, new Date(), 2);
        transaction.setId(1);
        this.transactionRepository.save(transaction);

        int oldNumberOfEntities = this.transactionRepository.getNumberOfEntities();
        Optional<Transaction> optionalTransaction = this.transactionRepository.delete(1);
        int newNumberOfEntities = this.transactionRepository.getNumberOfEntities();

        assert(oldNumberOfEntities == 1 && newNumberOfEntities == 0);
    }


    @Test
    public void testUpdateForUsers() throws ValidationException {
        User user = new User("abc", "def", 2);
        user.setId(1);
        this.userRepository.save(user);

        user = new User("aaa", "ddd", 500);
        user.setId(1);
        this.userRepository.update(user);

        Iterable<User> list = this.userRepository.findAll();
        Iterator<User> userIterator = list.iterator();
        List<User> users = new ArrayList<>();

        while (userIterator.hasNext()) {
            users.add(userIterator.next());
        }

        String firstName = users.get(0).getFirstName();
        String lastName = users.get(0).getLastName();
        int numberOfTransactions = users.get(0).getNumberOfTransactions();

        assert(users.size() == 1 && firstName.equals("aaa") && lastName.equals("ddd") && numberOfTransactions == 500);
    }


    @Test
    public void testUpdateForRecords() throws ValidationException {
        Record record = new Record(99, "Dark Side Of The Moon", 100, RecordType.VINYL);
        record.setId(1);
        this.recordRepository.save(record);
        assert (this.recordRepository.getNumberOfEntities() == 1);

        record = new Record(100, "Meddle", 200, RecordType.VINYL);
        record.setId(1);
        this.recordRepository.update(record);

        Iterable<Record> list = this.recordRepository.findAll();
        Iterator<Record> recordIterator = list.iterator();
        List<Record> records = new ArrayList<>();

        while (recordIterator.hasNext()) {
            records.add(recordIterator.next());
        }

        int price = records.get(0).getPrice();
        String albumName = records.get(0).getAlbumName();
        int inStock = records.get(0).getInStock();
        String recordType = records.get(0).getTypeOfRecord().toString();

        assert(records.size() == 1 && price == 100 && albumName.equals("Meddle") && inStock == 200 && recordType.equals(RecordType.VINYL.toString()));
    }


    @Test
    public void testFindOneForUsers() throws ValidationException {
        User user = new User("abc", "def", 2);
        user.setId(1);
        this.userRepository.save(user);

        assert(this.userRepository.findOne(1).isPresent());
        User databaseUser = this.userRepository.findOne(1).get();
        assert(databaseUser.getId() == 1 && databaseUser.getFirstName().equals("abc") && databaseUser.getNumberOfTransactions() == 2);
    }


    @Test
    public void testFindOneForRecords() throws ValidationException {
        Record record = new Record(99, "Dark Side Of The Moon", 100, RecordType.VINYL);
        record.setId(1);
        this.recordRepository.save(record);

        assert(this.recordRepository.findOne(1).isPresent());
        Record databaseRecord = this.recordRepository.findOne(1).get();
        assert(databaseRecord.getId() == 1 && databaseRecord.getPrice() == 99 && databaseRecord.getAlbumName().equals("Dark Side Of The Moon"));
    }


    @Test
    public void testFindOneForTransactions() throws ValidationException {
        User user = new User("abc", "def", 2);
        user.setId(1);
        this.userRepository.save(user);

        Record record = new Record(99, "Dark Side Of The Moon", 100, RecordType.VINYL);
        record.setId(1);
        this.recordRepository.save(record);

        Transaction transaction = new Transaction(1, 1, new Date(), 50);
        transaction.setId(1);
        this.transactionRepository.save(transaction);

        assert(this.transactionRepository.findOne(1).isPresent());
        Transaction databaseTransaction = this.transactionRepository.findOne(1).get();
        System.out.println(databaseTransaction.toString());
        assert(databaseTransaction.getUserID() == 1 && databaseTransaction.getRecordID() == 1);
    }


    @Test(expected = ValidationException.class)
    public void testSaveForUser_nullEntityCase() throws ValidationException {
        this.userRepository.save(null);
    }


    @Test(expected = ValidationException.class)
    public void testSaveForUser_negativeIdCase() throws ValidationException {
        User user = new User("abc", "def", 2);
        user.setId(-1);

        this.userRepository.save(user);
    }


    @Test(expected = ValidationException.class)
    public void testSaveForRecord_nullEntityCase() throws ValidationException {
        this.recordRepository.save(null);
    }


    @Test(expected = ValidationException.class)
    public void testSaveForRecord_negativeIdCase() throws ValidationException {
        Record record = new Record(99, "Dark Side Of The Moon", 100, RecordType.VINYL);
        record.setId(-1);

        this.recordRepository.save(record);
    }


    @Test(expected = ValidationException.class)
    public void testSaveForTransaction_nullEntityCase() throws ValidationException {
        this.transactionRepository.save(null);
    }



    @Test(expected = ValidationException.class)
    public void testSaveForTransaction_negativeIdCase() throws ValidationException {
        User user = new User("abc", "def", 2);
        user.setId(1);
        this.userRepository.save(user);
        Record record = new Record(99, "Dark Side Of The Moon", 100, RecordType.VINYL);
        record.setId(1);
        this.recordRepository.save(record);
        Transaction transaction = new Transaction(1, 1, new Date(), 50);
        transaction.setId(-1);

        this.transactionRepository.save(transaction);
    }


    @Test(expected = ValidationException.class)
    public void testUpdateForUser_nullEntityCase() throws ValidationException {
        this.userRepository.update(null);
    }


    @Test(expected = ValidationException.class)
    public void testUpdateForUser_negativeIdCase() throws ValidationException {
        User user = new User("abc", "def", 2);
        user.setId(-1);

        this.userRepository.save(user);
    }


    @Test(expected = ValidationException.class)
    public void testUpdateForRecord_nullEntityCase() throws ValidationException {
        this.recordRepository.update(null);
    }


    @Test(expected = ValidationException.class)
    public void testUpdateForRecord_negativeIdCase() throws ValidationException {
        Record record = new Record(99, "Dark Side Of The Moon", 100, RecordType.VINYL);
        record.setId(-1);

        this.recordRepository.save(record);
    }


    @Test(expected = ValidationException.class)
    public void testUpdateForTransaction_nullEntityCase() throws ValidationException {
        this.transactionRepository.update(null);
    }


    @Test(expected = ValidationException.class)
    public void testUpdateForTransaction_negativeIdCase() throws ValidationException {
        User user = new User("abc", "def", 2);
        user.setId(1);
        this.userRepository.save(user);
        Record record = new Record(99, "Dark Side Of The Moon", 100, RecordType.VINYL);
        record.setId(1);
        this.recordRepository.save(record);
        Transaction transaction = new Transaction(1, 1, new Date(), 50);
        transaction.setId(-1);

        this.transactionRepository.save(transaction);
    }


    @Test(expected = IllegalArgumentException.class)
    public void testFindOne_nullIdCase() {
        this.userRepository.findOne(null);
    }
}

package Repository;

import Exceptions.ValidationException;
import Model.RecordType;
import Model.Transaction;
import Model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Model.Record;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

public class TestPostgresRepository {
    private PostgresRepository<Integer, User> userRepository;
    private PostgresRepository<Integer, Record> recordRepository;
    private PostgresRepository<Integer, Transaction> transactionRepository;
    private String url, username, password, tableName;

    @Before
    public void setUp() {
        this.url = "jdbc:postgresql://localhost:5432/onlinemusicstore";
        this.username = "calandrinon";
        this.password = "12345";
        this.userRepository = new PostgresRepository<Integer, User>("ClientUserTestTable", this.url);
        this.recordRepository = new PostgresRepository<Integer, Record>("RecordTestTable", this.url);
        this.transactionRepository = new PostgresRepository<Integer, Transaction>("UserTransactionTestTable", this.url);

        String deleteStatement = "DELETE FROM UserTransactionTestTable; DELETE FROM ClientUserTestTable; DELETE FROM RecordTestTable;";
        try (var connection = DriverManager.getConnection(this.url, this.username, this.password);
             var firstPreparedStatement = connection.prepareStatement(deleteStatement)) {
            firstPreparedStatement.executeUpdate();
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

        System.out.println(users);
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

        System.out.println(transactions);
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
}

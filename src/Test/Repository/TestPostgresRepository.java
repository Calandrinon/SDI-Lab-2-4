package Repository;

import Exceptions.ValidationException;
import Model.RecordType;
import Model.Transaction;
import Model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;

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
    private Connection connection;

    @Before
    public void setUp() throws SQLException {
        this.userRepository = new PostgresRepository<Integer, User>("User", this.url);
        this.recordRepository = new PostgresRepository<Integer, Record>("Record", this.url);
        this.transactionRepository = new PostgresRepository<Integer, Transaction>("Transaction", this.url);
        this.username = "calandrinon";
        this.password = "12345";
    }


    @After
    public void tearDown() {
        this.userRepository = null;
        this.recordRepository = null;
        this.transactionRepository = null;
    }


    @Test
    public void testFindAllForUsers() {
        this.tableName = "ClientUserTestTable";
        this.url = "jdbc:postgresql://localhost:5432/onlinemusicstore";
        this.userRepository = new PostgresRepository<Integer, User>(this.tableName, this.url);
        Iterable<User> list = this.userRepository.findAll();
        Iterator<User> userIterator = list.iterator();
        List<User> users = new ArrayList<>();

        while (userIterator.hasNext()) {
            users.add(userIterator.next());
        }

        System.out.println(users);
        assert(users.size() == 1 && users.get(0).getId() == 15);
    }


    @Test
    public void testFindAllForRecords() {
        this.tableName = "RecordTestTable";
        this.url = "jdbc:postgresql://localhost:5432/onlinemusicstore";
        this.recordRepository = new PostgresRepository<Integer, Record>(this.tableName, this.url);
        Iterable<Record> list = this.recordRepository.findAll();
        Iterator<Record> recordIterator = list.iterator();
        List<Record> records = new ArrayList<>();

        while (recordIterator.hasNext()) {
            records.add(recordIterator.next());
        }

        System.out.println(records);
        assert(records.size() == 1 && records.get(0).getId() == 14);
    }


    @Test
    public void testFindAllForTransactions() {
        this.tableName = "UserTransactionTestTable";
        this.url = "jdbc:postgresql://localhost:5432/onlinemusicstore";
        this.transactionRepository = new PostgresRepository<Integer, Transaction>(this.tableName, this.url);
        Iterable<Transaction> list = this.transactionRepository.findAll();
        Iterator<Transaction> recordIterator = list.iterator();
        List<Transaction> transactions = new ArrayList<>();

        while (recordIterator.hasNext()) {
            transactions.add(recordIterator.next());
        }

        System.out.println(transactions);
        assert(transactions.size() == 1 && transactions.get(0).getId() == 1 && transactions.get(0).getDate().toString().equals("Thu Feb 25 00:00:00 EET 2021"));
    }


    @Test
    public void testSaveForUsers() throws ValidationException, SQLException {
        this.tableName = "ClientUserTestTable2";
        this.url = "jdbc:postgresql://localhost:5432/onlinemusicstore";
        this.username = "calandrinon";
        this.password = "12345";
        this.userRepository = new PostgresRepository<Integer, User>(this.tableName, this.url);
        this.connection = DriverManager.getConnection(this.url, this.username, this.password);

        var preparedStatement = connection.prepareStatement("DELETE FROM " + this.tableName);
        preparedStatement.executeUpdate();

        User user = new User("testuserfirstname", "testuserlastname", 20);
        user.setId(1);

        System.out.println("Column names: " + this.userRepository.getColumnsOfTheTableFromTheDatabase(connection));
        Optional<User> optionalUser = this.userRepository.save(user);

        int newNumberOfEntities = this.userRepository.getNumberOfEntities();
        assert(newNumberOfEntities == 1);
    }


    @Test
    public void testSaveForRecords() throws ValidationException, SQLException {
        this.tableName = "RecordTestTable2";
        this.url = "jdbc:postgresql://localhost:5432/onlinemusicstore";
        this.username = "calandrinon";
        this.password = "12345";
        this.recordRepository = new PostgresRepository<Integer, Record>(this.tableName, this.url);
        this.connection = DriverManager.getConnection(this.url, this.username, this.password);

        var preparedStatement = connection.prepareStatement("DELETE FROM " + this.tableName);
        preparedStatement.executeUpdate();

        Record record = new Record(99, "Dark Side Of The Moon", 200, RecordType.VINYL);
        record.setId(1);

        Optional<Record> optionalRecord = this.recordRepository.save(record);

        int newNumberOfEntities = this.recordRepository.getNumberOfEntities();
        assert(newNumberOfEntities == 1);
    }


    @Test
    public void testSaveForTransactions() throws ValidationException, SQLException {
        this.tableName = "UserTransactionTestTable2";
        this.url = "jdbc:postgresql://localhost:5432/onlinemusicstore";
        this.username = "calandrinon";
        this.password = "12345";
        this.transactionRepository = new PostgresRepository<Integer, Transaction>(this.tableName, this.url);
        this.connection = DriverManager.getConnection(this.url, this.username, this.password);

        var preparedStatement = connection.prepareStatement("DELETE FROM " + this.tableName);
        preparedStatement.executeUpdate();

        Transaction transaction = new Transaction(1, 1, new Date(), 1);
        transaction.setId(1);

        Optional<Transaction> optionalTransaction = this.transactionRepository.save(transaction);

        int newNumberOfEntities = this.transactionRepository.getNumberOfEntities();
        assert(newNumberOfEntities == 1);
    }
}

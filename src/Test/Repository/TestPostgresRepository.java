package Repository;

import Model.Transaction;
import Model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import Model.Record;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TestPostgresRepository {
    private PostgresRepository<Integer, User> userRepository;
    private PostgresRepository<Integer, Record> recordRepository;
    private PostgresRepository<Integer, Transaction> transactionRepository;
    private String url, tableName;
    private Connection connection;

    @Before
    public void setUp() throws SQLException {
        this.userRepository = new PostgresRepository<Integer, User>("User", this.url);
        this.recordRepository = new PostgresRepository<Integer, Record>("Record", this.url);
        this.transactionRepository = new PostgresRepository<Integer, Transaction>("Transaction", this.url);
    }


    @After
    public void tearDown() {
        this.userRepository = null;
        this.recordRepository = null;
        this.transactionRepository = null;
    }


    @Test
    public void testFindAllForUser() {
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
    public void testFindAllForRecord() {
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
    public void testFindAllForTransaction() {
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
}

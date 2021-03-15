package Repository;

import Exceptions.ValidationException;
import Model.Record;
import Model.RecordType;
import Model.Transaction;
import Model.User;
import Validator.RecordValidator;
import Validator.TransactionValidator;
import Validator.UserValidator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Set;

public class TestFileRepository {
    private final static Record RECORD_1 = new Record(1, "a", 1, RecordType.CD);
    private final static Record RECORD_2 = new Record(1, "b", 1, RecordType.CD);

    private final static User USER_1 = new User("a", "a", 0);
    private final static User USER_2 = new User("b", "b", 0);

    private final static Transaction TRANSACTION_1 = new Transaction(1,1, new Date(), 1);
    private final static Transaction TRANSACTION_2 = new Transaction(1, 1, new Date(), 10);

    private final static RecordValidator RECORD_VALIDATOR = new RecordValidator();
    private final static UserValidator USER_VALIDATOR = new UserValidator();
    private final static TransactionValidator TRANSACTION_VALIDATOR = new TransactionValidator();

    private FileRepository<Integer, Record> recordFileRepository;
    private FileRepository<Integer, User> userFileRepository;
    private FileRepository<Integer, Transaction> transactionFileRepository;

    @Before
    public void setUp() {
        RECORD_1.setId(1);
        RECORD_2.setId(1);
        USER_1.setId(1);
        USER_2.setId(1);
        TRANSACTION_1.setId(1);
        TRANSACTION_2.setId(1);

        recordFileRepository = new FileRepository<Integer, Record>(RECORD_VALIDATOR, "test_records.txt", Record.reader(Record::fileReader), Record.writer(Record::fileWriter));
        userFileRepository = new FileRepository<Integer, User>(USER_VALIDATOR, "test_users.txt", User.reader(User::fileReader), User.writer(User::fileWriter));
        transactionFileRepository = new FileRepository<Integer, Transaction>(TRANSACTION_VALIDATOR, "test_transactions.txt", Transaction.reader(Transaction::fileReader), Transaction.writer(Transaction::fileWriter));

    }

    @After
    public void tearDown() {
        recordFileRepository = null;
        userFileRepository = null;
        transactionFileRepository = null;

        try {
            Files.delete(Paths.get("src/Main/Repository/RepositoryFiles/test_records.txt"));
            Files.delete(Paths.get("src/Main/Repository/RepositoryFiles/test_users.txt"));
            Files.delete(Paths.get("src/Main/Repository/RepositoryFiles/test_transactions.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testFindOne() throws Exception {
        assert(recordFileRepository.findOne(1).isEmpty());
        assert(userFileRepository.findOne(1).isEmpty());
        assert(transactionFileRepository.findOne(1).isEmpty());
    }

    @Test
    public void testFindAll() throws Exception {
        Set<Record> records = (Set<Record>) recordFileRepository.findAll();
        assert(records.isEmpty());

        Set<User> users = (Set<User>) userFileRepository.findAll();
        assert(users.isEmpty());

        Set<Transaction> transactions = (Set<Transaction>) transactionFileRepository.findAll();
        assert(transactions.isEmpty());
    }

    @Test
    public void testSave() throws Exception {
        recordFileRepository.save(RECORD_1);
        assert(recordFileRepository.findOne(1).get().equals(RECORD_1));

        userFileRepository.save(USER_1);
        assert(userFileRepository.findOne(1).get().equals(USER_1));

        transactionFileRepository.save(TRANSACTION_1);
        assert(transactionFileRepository.findOne(1).get().equals(TRANSACTION_1));
    }

    @Test(expected = ValidationException.class)
    public void testSaveException() throws Exception {
        RECORD_2.setId(-1);
        recordFileRepository.save(RECORD_2);
        RECORD_2.setId(1);
    }

    @Test
    public void testUpdate() throws Exception {
        recordFileRepository.save(RECORD_1);
        recordFileRepository.update(RECORD_2);
        assert(recordFileRepository.findOne(1).get().getAlbumName().equals(RECORD_2.getAlbumName()));

        userFileRepository.save(USER_1);
        userFileRepository.update(USER_2);
        assert(userFileRepository.findOne(1).get().getFirstName().equals(USER_2.getFirstName()));

        transactionFileRepository.save(TRANSACTION_1);
        transactionFileRepository.update(TRANSACTION_2);
        assert(transactionFileRepository.findOne(1).get().getQuantity() == TRANSACTION_2.getQuantity());
    }

    @Test(expected = ValidationException.class)
    public void testUpdateException() throws Exception {
        RECORD_2.setId(-1);
        recordFileRepository.update(RECORD_2);
        RECORD_2.setId(1);
    }

    @Test
    public void testDelete() throws Exception {
        recordFileRepository.delete(1);
        assert(recordFileRepository.findOne(1).isEmpty());

        userFileRepository.delete(1);
        assert(userFileRepository.findOne(1).isEmpty());

        transactionFileRepository.delete(1);
        assert(transactionFileRepository.findOne(1).isEmpty());
    }
}

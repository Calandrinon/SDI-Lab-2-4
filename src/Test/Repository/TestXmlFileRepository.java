package Repository;

import Exceptions.ValidationException;
import Model.*;
import Model.Record;
import Validator.BaseEntityValidator;
import Validator.RecordValidator;
import Validator.TransactionValidator;
import Validator.UserValidator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Date;
import java.util.Set;
import java.util.stream.StreamSupport;

public class TestXmlFileRepository {
    private static final Record RECORD_1= new Record(1, "1", 1, RecordType.VINYL);
    private static final Record RECORD_2 = new Record(2, "2", 2, RecordType.CD);

    private static final User USER_1 = new User("f", "l", 1);
    private static final User USER_2 = new User("ff", "ll", 2);

    private static final Transaction TRANSACTION_1 = new Transaction(1, 1, new Date(), 10);
    private static final Transaction TRANSACTION_2 = new Transaction(1, 1, new Date(), 20);

    private XmlFileRepository<Integer, Record> recordRepository;
    private XmlFileRepository<Integer, User> userRepository;
    private XmlFileRepository<Integer, Transaction> transactionRepository;

    @Before
    public void setUp() {
        RECORD_1.setId(1);
        RECORD_2.setId(1);

        USER_1.setId(1);
        USER_2.setId(1);

        TRANSACTION_1.setId(1);
        TRANSACTION_2.setId(1);

        recordRepository = new XmlFileRepository<Integer, Record>(
                new RecordValidator()
                , Record.recordEncoder
                , Record.recordDecoder
                , "src/Test/Repository/XmlFiles/recordTestFile.xml"
        );

        userRepository = new XmlFileRepository<Integer, User>(
                new UserValidator()
                , User.userEncoder
                , User.userDecoder
                , "src/Test/Repository/XmlFiles/userTestFile.xml"
        );
        transactionRepository = new XmlFileRepository<Integer, Transaction>(
                new TransactionValidator()
                , Transaction.transactionEncoder
                , Transaction.transactionDecoder
                , "src/Test/Repository/XmlFiles/transactionTestFile.xml"
        );
    }

    @After
    public void tearDown() {
        recordRepository = null;
        userRepository = null;
        transactionRepository = null;
    }

    @Test
    public void testFindOne(){
        assert(recordRepository.findOne(1).isEmpty());
        assert(userRepository.findOne(1).isEmpty());
        assert(transactionRepository.findOne(1).isEmpty());
    }

    @Test
    public void testFindAll() {
        assert(StreamSupport.stream(recordRepository.findAll().spliterator(), false).findAny().isEmpty());
        assert(StreamSupport.stream(userRepository.findAll().spliterator(), false).findAny().isEmpty());
        assert(StreamSupport.stream(transactionRepository.findAll().spliterator(), false).findAny().isEmpty());
    }

    @Test
    public void testSaveAndDelete() throws ValidationException {
        recordRepository.save(RECORD_1);
        assert(recordRepository.findOne(1).get().equals(RECORD_1));

        userRepository.save(USER_1);
        assert(userRepository.findOne(1).get().equals(USER_1));

        transactionRepository.save(TRANSACTION_1);
        assert(transactionRepository.findOne(1).get().equals(TRANSACTION_1));


        recordRepository.delete(1);
        assert(StreamSupport.stream(recordRepository.findAll().spliterator(), false).findAny().isEmpty());

        userRepository.delete(1);
        assert(StreamSupport.stream(userRepository.findAll().spliterator(), false).findAny().isEmpty());

        transactionRepository.delete(1);
        assert(StreamSupport.stream(transactionRepository.findAll().spliterator(), false).findAny().isEmpty());
    }

    @Test
    public void testUpdate() throws ValidationException {
        recordRepository.save(RECORD_1);
        recordRepository.update(RECORD_2);
        assert(recordRepository.findOne(1).get().equals(RECORD_2));

        userRepository.save(USER_1);
        userRepository.update(USER_2);
        assert(userRepository.findOne(1).get().equals(USER_2));

        transactionRepository.save(TRANSACTION_1);
        transactionRepository.update(TRANSACTION_2);
        assert(transactionRepository.findOne(1).get().equals(TRANSACTION_2));
    }
}

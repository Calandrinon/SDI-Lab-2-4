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
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
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

    private static void createFiles(){
        Path records, transactions, users;

        records = Paths.get("src/Test/Repository/XmlFiles/").resolve("recordTestFile.xml");
        transactions = Paths.get("src/Test/Repository/XmlFiles/").resolve("transactionTestFile.xml");
        users = Paths.get("src/Test/Repository/XmlFiles/").resolve("userTestFile.xml");

        Stream.of(records, transactions, users)
                .forEach(path -> {
                    if(!Files.exists(path)) {
                        try {
                            Files.createFile(path);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = null;
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            Element rootElement = doc.createElement("elems");
            doc.appendChild(rootElement);



            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);

            StreamResult result1 = new StreamResult(new File("src/Test/Repository/XmlFiles/recordTestFile.xml"));
            StreamResult result2 = new StreamResult(new File("src/Test/Repository/XmlFiles/transactionTestFile.xml"));
            StreamResult result3 = new StreamResult(new File("src/Test/Repository/XmlFiles/userTestFile.xml"));

            transformer.transform(source, result1);
            transformer.transform(source, result2);
            transformer.transform(source, result3);
        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }

    @Before
    public void setUp() {
        RECORD_1.setId(1);
        RECORD_2.setId(1);

        USER_1.setId(1);
        USER_2.setId(1);

        TRANSACTION_1.setId(1);
        TRANSACTION_2.setId(1);

        createFiles();

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

        try {
            Files.delete(Paths.get("src/Test/Repository/XmlFiles/recordTestFile.xml"));
            Files.delete(Paths.get("src/Test/Repository/XmlFiles/transactionTestFile.xml"));
            Files.delete(Paths.get("src/Test/Repository/XmlFiles/userTestFile.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
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

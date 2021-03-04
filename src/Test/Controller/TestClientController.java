package Test.Controller;

import Main.Controller.ClientController;
import Main.Model.Record;
import Main.Model.RecordType;
import Main.Repository.InMemoryRepository;
import Main.Validator.RecordValidator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.stream.StreamSupport;

public class TestClientController {
    private ClientController clientController;
    private InMemoryRepository<Integer, Record> clientRecordRepository;
    private InMemoryRepository<Integer, Record> recordRepository;
    private int id = 1;
    private int price = 5;
    private String name = "Dark Side Of The Moon";
    private RecordType type = RecordType.VINYL;

    private Record record;

    @Before
    public void setUp() throws Exception
    {
        this.clientRecordRepository = new InMemoryRepository<>(new RecordValidator());
        this.recordRepository = new InMemoryRepository<>(new RecordValidator());

        this.record = new Record(price, name, 1, type);
        this.record.setId(this.id);
        this.recordRepository.save(this.record);

        this.clientController = new ClientController(this.clientRecordRepository, this.recordRepository);
    }

    @After
    public void tearDown() throws Exception {
        this.recordRepository = null;
        this.clientRecordRepository = null;
        this.clientController = null;
    }

    @Test
    public void testAcquireRecordAddition() throws Exception {
        this.clientController.acquireRecord(this.id, this.type);
        long numberOfElementsInClientRepository = StreamSupport.stream(this.clientRecordRepository.findAll().spliterator(), false).count();
        assert(numberOfElementsInClientRepository == 1);
    }


    @Test
    public void testOwnedRecordId() throws Exception {
        this.clientController.acquireRecord(this.id, this.type);
        Iterator<Record> recordsIterator = this.clientController.getOwnedRecords().iterator();
        assert(recordsIterator.next().getId() == this.id);
    }


    @Test
    public void testOwnedRecordName() throws Exception {
        this.clientController.acquireRecord(this.id, this.type);
        Iterator<Record> recordsIterator = this.clientController.getOwnedRecords().iterator();
        assert(recordsIterator.next().getAlbumName() == this.name);
    }


    @Test
    public void testOwnedRecordType() throws Exception {
        this.clientController.acquireRecord(this.id, this.type);
        Iterator<Record> recordsIterator = this.clientController.getOwnedRecords().iterator();
        assert(recordsIterator.next().getTypeOfRecord() == this.type);
    }
}

package Controller;

import Controller.RecordController;
import Model.RecordType;
import Repository.InMemoryRepository;
import Repository.Repository;
import Validator.RecordValidator;
import Model.Record;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
// import org.junit.Test;
import org.junit.jupiter.api.Test;


import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class TestRecordController {
    private Repository<Integer, Record> recordRepository;
    private RecordController recordController;


    @Before
    public void setUp() {
        this.recordRepository = new InMemoryRepository<Integer, Record>(new RecordValidator());
        this.recordController = new RecordController(recordRepository);
    }


    @After
    public void tearDown() {
        this.recordRepository = null;
        this.recordController = null;
    }


    @Test
    public void testAddAverageCase() throws Exception {
        this.recordController.add(1, 5, "aaa", 25, RecordType.CD);
        assert(this.recordController.getRepository().size() == 1);
    }


    @Test
    public void testAddDuplicateID() throws Exception {
        this.recordController.add(1, 5, "aaa", 25, RecordType.CD);
        try {
            this.recordController.add(1, 12, "bbb", 500, RecordType.TAPE);
            assert(false);
        }catch (Exception ignore){
            assert(this.recordController.getRepository().size() == 1);
        }
    }


    @Test
    public void testPriceAfterUpdate() throws Exception {
        this.recordController.add(1, 5, "aaa", 25, RecordType.CD);
        this.recordController.update(1, 6, "aaa", 25, RecordType.CD);
        Stream<Record> stream = StreamSupport.stream(this.recordRepository.findAll().spliterator(), false);
        List<Record> records = stream.filter(record -> record.getId() == 1).collect(Collectors.toList());
        assert(records.get(0).getPrice() == 6);
    }


    @Test
    public void testAlbumNameAfterUpdate() throws Exception {
        this.recordController.add(1, 5, "aaa", 25, RecordType.CD);
        this.recordController.update(1, 5, "zzz", 25, RecordType.CD);
        Stream<Record> stream = StreamSupport.stream(this.recordRepository.findAll().spliterator(), false);
        List<Record> records = stream.filter(record -> record.getId() == 1).collect(Collectors.toList());
        assert(records.get(0).getAlbumName().equals("zzz"));
    }


    @Test
    public void testInStockAfterUpdate() throws Exception {
        this.recordController.add(1, 5, "aaa", 25, RecordType.CD);
        this.recordController.update(1, 5, "zzz", 2000, RecordType.CD);
        Stream<Record> stream = StreamSupport.stream(this.recordRepository.findAll().spliterator(), false);
        List<Record> records = stream.filter(record -> record.getId() == 1).collect(Collectors.toList());
        assert(records.get(0).getInStock() == 2000);
    }


    @Test
    public void testRecordTypeAfterUpdate() throws Exception {
        this.recordController.add(1, 5, "aaa", 25, RecordType.CD);
        this.recordController.update(1, 5, "zzz", 2000, RecordType.VINYL);
        Stream<Record> stream = StreamSupport.stream(this.recordRepository.findAll().spliterator(), false);
        List<Record> records = stream.filter(record -> record.getId() == 1).collect(Collectors.toList());
        assert(records.get(0).getTypeOfRecord() == RecordType.VINYL);
    }


    @Test
    public void testRemove() throws Exception {
        this.recordController.add(1, 5, "aaa", 25, RecordType.CD);
        this.recordController.remove(1);
        assert(this.recordController.getRepository().size() == 0);
    }


    @Test
    public void testRemoveNonexistentItem() throws Exception {
        boolean passed;
        try {
            this.recordController.remove(5);
            passed = false;
        } catch (Exception exception) {
            passed = true;
        }

        assert(passed);
    }


    @Test
    public void testFilterByPrice() throws Exception {
        this.recordController.add(1, 10, "aaa", 25, RecordType.CD);
        this.recordController.add(2, 6, "aaa", 25, RecordType.TAPE);
        this.recordController.add(3, 99, "aaa", 25, RecordType.VINYL);
        assert(this.recordController.filterByPrice(10).size() == 1);
    }
}

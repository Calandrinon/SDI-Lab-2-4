package Controller;

import Model.BaseEntity;
import Model.RecordType;
import Model.Record;
import Model.User;
import Repository.InMemoryRepository;
import Repository.Repository;

import java.nio.charset.IllegalCharsetNameException;
import java.security.spec.ECField;
import java.util.ArrayList;
import java.util.List;

public class RecordController implements IController {
    private final InMemoryRepository<Integer, Record> RecordRepository;

    public RecordController(InMemoryRepository<Integer, Record> recordRepository) {
        RecordRepository = recordRepository;
    }

    public void add(Integer id, Integer price, String AlbumName, Integer InStock, RecordType RecordType) throws Exception {
        Record record = new Record(price, AlbumName, InStock,RecordType);
        record.setId(id);
        this.RecordRepository.save(record);
    }

    public void update(Integer id, Integer price, String AlbumName, Integer InStock, RecordType RecordType) throws Exception {
        Record record = new Record(price, AlbumName, InStock,RecordType);
        record.setId(id);
        this.RecordRepository.update(record);
    }

    public void remove(Integer id) {
        this.RecordRepository.delete(id);
    }

    public List<String> getRepository() {
        List<String> list = new ArrayList<>();
        this.RecordRepository.findAll().forEach(e -> list.add(e.toString()));
        return list;
    }
}

package Main.Controller;

import Main.Model.RecordType;
import Main.Model.Record;
import Main.Repository.FileRepository;
import Main.Repository.InMemoryRepository;

import java.util.ArrayList;
import java.util.List;

public class RecordController implements IController {
    private final FileRepository<Integer, Record> RecordRepository;

    public RecordController(FileRepository<Integer, Record> recordRepository) {
        RecordRepository = recordRepository;
    }

    /**
     * creates and adds to the repository a record
     *
     * @param id must be unique
     * @param price must be positive
     * @param AlbumName -
     * @param InStock -
     * @param RecordType -
     *
     *
     */

    public void add(Integer id, Integer price, String AlbumName, Integer InStock, RecordType RecordType) throws Exception {
        Record record = new Record(price, AlbumName, InStock,RecordType);
        record.setId(id);
        this.RecordRepository.save(record);
    }

    /**
     * creates and updates to the repository the record with another one having the same id
     *
     * @param id must be already found in the repository
     * @param price must be positive
     * @param AlbumName -
     * @param InStock -
     * @param RecordType -
     *
     *
     */

    public void update(Integer id, Integer price, String AlbumName, Integer InStock, RecordType RecordType) throws Exception {
        Record record = new Record(price, AlbumName, InStock,RecordType);
        record.setId(id);
        this.RecordRepository.update(record);
    }

    /**
     * removes from the repository the record having the
     *
     * @param id must be already found in the repository
     *
     *
     */

    public void remove(Integer id) {
        this.RecordRepository.delete(id);
    }

    /**
     * @return the a list of String values corresponding to the record objects
     *
     */

    public List<String> getRepository() {
        List<String> list = new ArrayList<>();
        this.RecordRepository.findAll().forEach(e -> list.add(e.toString()));
        return list;
    }
}

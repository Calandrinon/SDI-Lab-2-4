package Main.Controller;

import Main.Model.RecordType;
import Main.Model.Record;
import Main.Model.User;
import Main.Repository.InMemoryRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class RecordController implements IController {
    private final InMemoryRepository<Integer, Record> RecordRepository;

    public RecordController(InMemoryRepository<Integer, Record> recordRepository) {
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
     * removes from the repository the record having the given id
     *
     * @param id must be already found in the repository
     *
     *
     */

    public void remove(Integer id) {
        this.RecordRepository.delete(id);
    }

    /**
     *
     * @param pred a given Predicate object
     * @return the list of string transactions matching the given predicate return true when applied
     */

    private List<String> filter(Predicate<Record> pred){
        return StreamSupport.stream(this.RecordRepository.findAll().spliterator(), false)
                .filter(pred)
                .map(Record::toString)
                .collect(Collectors.toList());
    }

    /**
     *
     * @return the the a list of String values corresponding to the record objects
     */

    public List<String> getRepository() {
        return filter(e -> true);
    }

    /**
     *
     * @param maximumPrice the maximum price of the
     * @return a list of all records having the value of the price attribute smaller than the given @maximumPrice
     */

    public List<String> filterByPrice(Integer maximumPrice) {
        return filter(e -> e.getPrice() < maximumPrice);
    }
}

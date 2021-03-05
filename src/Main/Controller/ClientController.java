package Main.Controller;

import Main.Model.Record;
import Main.Model.RecordType;
import Main.Model.Transaction;
import Main.Repository.InMemoryRepository;
import Main.Repository.Repository;

import java.util.Set;

public class ClientController {
    private final Repository<Integer, Record> clientRecordRepository;
    private final Repository<Integer, Record> recordRepository;

    public ClientController(Repository<Integer, Record> clientRecordRepository, Repository<Integer, Record> recordRepository) {
        this.clientRecordRepository = clientRecordRepository;
        this.recordRepository = recordRepository;
    }


    // TODO: Write proper specifications (adds a bought record to the client's repository)
    public void acquireRecord(Integer id, RecordType RecordType) throws Exception {
        Iterable<Record> availableRecords = this.recordRepository.findAll();
        for (Record record: availableRecords) {
            if (record.getId().equals(id) && record.getTypeOfRecord() == RecordType) {
                clientRecordRepository.save(record);
                break;
            }
        }
    }


    public Iterable<Record> getOwnedRecords() {
        return clientRecordRepository.findAll();
    }
}

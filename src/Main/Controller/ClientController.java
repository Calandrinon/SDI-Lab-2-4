package Main.Controller;

import Main.Model.Record;
import Main.Model.RecordType;
import Main.Model.Transaction;
import Main.Repository.InMemoryRepository;

import java.util.Set;

public class ClientController {
    private final InMemoryRepository<Integer, Record> clientRecordRepository;
    private final InMemoryRepository<Integer, Record> recordRepository;

    public ClientController(InMemoryRepository<Integer, Record> clientRecordRepository, InMemoryRepository<Integer, Record> recordRepository) {
        this.clientRecordRepository = clientRecordRepository;
        this.recordRepository = recordRepository;
    }


    // TODO: Write proper specifications (adds a bought record to the client's repository)
    public void acquireRecord(Integer id, RecordType RecordType) throws Exception {
        Iterable<Record> availableRecords = this.recordRepository.findAll();
        for (Record record: availableRecords) {
            if (record.getId().equals(id) && record.getTypeOfRecord() == RecordType) {
                clientRecordRepository.save(record);
                // The transaction should be handled here as well (by using a reference to the TransactionController)
                break;
            }
        }
    }


    public Iterable<Record> getOwnedRecords() {
        return clientRecordRepository.findAll();
    }
}

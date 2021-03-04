package Main.Controller;

import Main.Model.BaseEntity;
import Main.Model.Record;
import Main.Model.Transaction;
import Main.Model.User;
import Main.Repository.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.lang.Math.abs;

public class TransactionController {
    private final Repository<Integer, Record> RecordRepository;
    private final Repository<Integer, User> UserRepository;
    private final Repository<Integer, Transaction> TransactionRepository;

    public TransactionController(Repository<Integer, Record> recordRepository, Repository<Integer, User> userRepository, Repository<Integer, Transaction> transactionRepository) {
        RecordRepository = recordRepository;
        UserRepository = userRepository;
        TransactionRepository = transactionRepository;
    }

    public void makeTransaction(Integer userID, Integer recordID, Integer quantity) throws Exception{
        User user = UserRepository.findOne(userID).orElseThrow(() -> new Exception("no such user"));
        Record record = RecordRepository.findOne(recordID).orElseThrow(() -> new Exception("no such record"));

        this.UserRepository.update(new User(user.getFirstName(), user.getLastName(), user.getNumberOfTransactions() + 1));
        this.RecordRepository.update(new Record(record.getPrice(), record.getAlbumName(), record.getInStock() - quantity, record.getTypeOfRecord()));
        this.TransactionRepository.save(new Transaction(userID, recordID, new Date(), quantity));
    }

    private List<String> filter(Predicate<Transaction> pred){
        return StreamSupport.stream(this.TransactionRepository.findAll().spliterator(), false)
                .filter(pred)
                .map(Transaction::toString)
                .collect(Collectors.toList());
    }

    public List<String> getRepository() {
        return filter(e -> true);
    }

    public List<String> filterByDate(Date date) {
        return filter(e -> abs(TimeUnit.DAYS.convert(e.getDate().getTime() - date.getTime(), TimeUnit.MILLISECONDS)) <= 1);
    }

    public Record getMostPurchasedRecords(){
        return null;
    }
}

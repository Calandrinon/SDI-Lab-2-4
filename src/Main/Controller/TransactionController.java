package Controller;

import Exceptions.TransactionException;
import Model.BaseEntity;
import Model.Record;
import Model.Transaction;
import Model.User;
import Repository.Repository;

import java.sql.Time;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
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
    private AtomicInteger counter = new AtomicInteger(0);

    /**
     *
     * @param recordRepository reference to the record repository
     * @param userRepository reference to the user repository
     * @param transactionRepository reference to the transaction repository
     */

    public TransactionController(Repository<Integer, Record> recordRepository, Repository<Integer, User> userRepository, Repository<Integer, Transaction> transactionRepository) {
        RecordRepository = recordRepository;
        UserRepository = userRepository;
        TransactionRepository = transactionRepository;
    }

    /**
     *
     * @param userID the user ID should already exist in the user repository
     * @param recordID the record ID should already exist in the record repository
     * @param quantity the given quantity which should be positive
     * @throws Exception
     *              throws TransactionException if the record IDs are non existent
     *
     */

    public void makeTransaction(Integer userID, Integer recordID, Integer quantity) throws Exception {
        User user = UserRepository.findOne(userID).orElseThrow(() -> new TransactionException("no such user"));
        Record record = RecordRepository.findOne(recordID).orElseThrow(() -> new TransactionException("no such record"));

        User updatedUser = new User(user.getFirstName(), user.getLastName(), user.getNumberOfTransactions() + 1);
        updatedUser.setId(user.getId());
        this.UserRepository.update(updatedUser);
        Record updatedRecord = new Record(record.getPrice(), record.getAlbumName(), record.getInStock() - quantity, record.getTypeOfRecord());
        updatedRecord.setId(record.getId());
        this.RecordRepository.update(updatedRecord);

        Transaction newTransaction = new Transaction(userID, recordID, new Date(), quantity);
        newTransaction.setId(this.counter.incrementAndGet());
        this.TransactionRepository.save(newTransaction);
    }

    /**
     *
     * @param pred Predicate type object
     * @return the list of string transactions matching the given predicate return true when applied
     */

    private List<String> filter(Predicate<Transaction> pred){
        return StreamSupport.stream(this.TransactionRepository.findAll().spliterator(), false)
                .filter(pred)
                .map(Transaction::toString)
                .collect(Collectors.toList());
    }

    /**
     *
     * @param userID the id of the user
     * @return a map containing the record id as key and the the quantity as value,
     * if we encounter a duplicate key then the we execute integer sum on the two values under the same key
     */

    public Map<Integer, Integer> getRecordsByUser(Integer userID) {
        return StreamSupport.stream(this.TransactionRepository.findAll().spliterator(), false)
                .filter(t -> t.getUserID() == userID)
                .collect(Collectors.toMap(Transaction::getRecordID, Transaction::getQuantity, Integer::sum));
    }

    /**
     *
     * @return the a list of String values corresponding to the transaction objects
     */

    public List<String> getRepository() {
        return filter(e -> true);
    }

    /**
     *
     * @param date the date to filter by
     * @return returns all the transactions which happened on the same day as the given date as Strings
     */

    public List<String> filterByDate(Date date) {
        return filter(e -> abs(TimeUnit.DAYS.convert(e.getDate().getTime() - date.getTime(), TimeUnit.MILLISECONDS)) <= 1);
    }

    /**
     *
     * @param id the id of the record you want the sum calculated for
     * @return the total sum of the record having the id = @id found in the transaction repository
     */

    public Integer getTotalQuantityPurchasedByRecordID(Integer id) {
        return StreamSupport.stream(this.TransactionRepository.findAll().spliterator(), false)
                .filter(t -> t.getRecordID() == id)
                .map(Transaction::getQuantity)
                .reduce(0, Integer::sum);
    }

    /**
     *
     * @return the record having the maximum amount of purchases
     */

    public List<Record> getMostPurchasedRecords(){
        return StreamSupport.stream(this.RecordRepository.findAll().spliterator(), false)
                .max((r1, r2) -> getTotalQuantityPurchasedByRecordID(r1.getId()) - getTotalQuantityPurchasedByRecordID(r2.getId())).stream().collect(Collectors.toList());
    }
}

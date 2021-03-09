package Model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Function;

public class Transaction extends BaseEntity<Integer>{
    private final int userID;
    private final int recordID;
    private final Date date;
    private final int quantity;

    /**
     * @param userID - Integer
     * @param recordID - Integer
     * @param date - Date
     * @param quantity - Integer
     */
    public Transaction(int userID, int recordID, Date date, int quantity) {
        this.userID = userID;
        this.recordID = recordID;
        this.date = date;
        this.quantity = quantity;
    }

    /**
     * @return the string for a Transaction
     */
    @Override
    public String toString() {
        return "Transaction " + getId() + ": " +
                "User " + userID +
                "Record " + recordID +
                "Date " + date.toString() +
                "Quantity " + quantity;
    }

    /**
     * @param obj - and object
     * @return -true - if the objects are the same or if their ids are the same
     *         -false - if the objects don't have the same class
     */
    @Override
    public boolean equals(Object obj) {
        if(this == obj)return true;
        if(obj == null || getClass() != obj.getClass()) return false;

        Transaction transaction = (Transaction) obj;

        return getId().equals(transaction.getId());
    }

    /**
     * @return the Date for a Transaction
     */
    public Date getDate() {
        return date;
    }

    /**
     * @return the quantity for a Transaction
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * @return the ID of the User that did the Transaction
     */
    public int getUserID() {
        return userID;
    }

    /**
     * @return the ID of the Record that was bought in the Transaction
     */
    public int getRecordID() {
        return recordID;
    }

    public static Function reader(Function<String, Transaction> function){
        return function;
    }

    public static Function writer(Function<Transaction, String> function){
        return function;
    }

    public static String fileWriter(BaseEntity<Integer> baseEntity){
        if (baseEntity.getClass() == Transaction.class) {
            Transaction transaction = (Transaction) baseEntity;
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
            return transaction.getId() + " | " + transaction.userID + " | " + transaction.recordID + " | " + dateFormat.format(transaction.date) + " | " + transaction.quantity;
        }
        return "";
    }

    public static Transaction fileReader(String line){
        String[] parts = line.split(" \\| ");
        try {
            Date date = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").parse(parts[3]);
            Transaction transaction = new Transaction(Integer.parseInt(parts[1]), Integer.parseInt(parts[2]), date, Integer.parseInt(parts[4]));
            transaction.setId(Integer.parseInt(parts[0]));
            return transaction;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}

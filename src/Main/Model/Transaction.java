package Main.Model;

import java.util.Date;

public class Transaction extends BaseEntity<Integer>{
    private final int userID;
    private final int recordID;
    private final Date date;
    private final int quantity;
    private static int transactionNumber = 0;

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
        transactionNumber++;
        this.setId(transactionNumber);
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
}

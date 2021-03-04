package Main.Model;

import java.util.Date;

public class Transaction extends BaseEntity<Integer>{
    private final User user;
    private final Record record;
    private final Date date;
    private final int quantity;

    /**
     * @param user - User class
     * @param record - Record class
     * @param date - Date
     * @param quantity - Integer
     */
    public Transaction(User user, Record record, Date date, int quantity) {
        this.user = user;
        this.record = record;
        this.date = date;
        this.quantity = quantity;
    }

    /**
     * @return the string for a Transaction
     */
    @Override
    public String toString() {
        return "Transaction " + getId() + ": " +
                "User " + user.getId() +
                "Record " + record.getId() +
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
     * @return the User from a Transaction
     */
    public User getUser() {
        return user;
    }

    /**
     * @return the Record from a Transaction
     */
    public Record getRecord() {
        return record;
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
}

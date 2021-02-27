package Model;

public class User {
    private final int userID;
    private final String firstName;
    private final String lastName;
    private final int numberOfTransactions;

    public User(int userID, String firstName, String lastName, int numberOfTransactions) {
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.numberOfTransactions = numberOfTransactions;
    }

    @Override
    public String toString() {
        return "Client " + Integer.toString(userID) + ": " + firstName + " " + lastName + " No. of transactions: " + Integer.toString(numberOfTransactions);
    }

    public int getNumberOfTransactions() {
        return numberOfTransactions;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public int getUserID() {
        return userID;
    }
}

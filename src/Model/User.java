package Model;

public class User extends BaseEntity<Integer>{
    private final String FirstName;
    private final String LastName;
    private final int NumberOfTransactions;

    /**
     * @param FirstName - string
     * @param LastName - string
     * @param NumberOfTransactions - Integer
     */
    public User(String FirstName, String LastName, int NumberOfTransactions) {
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.NumberOfTransactions = NumberOfTransactions;
    }

    /**
     *
     * @return String of the User object
     */
    @Override
    public String toString() {
        return "Client " + Integer.toString(this.getId()) + ": " + FirstName + " " + LastName + " No. of transactions: " + Integer.toString(NumberOfTransactions);
    }

    /**
     *
     * @param obj - object to be compared
     * @return -true - if the objects are the same or if their ids are the same
     *         -false - if the objects don't have the same class
     */
    @Override
    public boolean equals(Object obj) {
        if(this == obj)return true;
        if(obj == null || getClass() != obj.getClass()) return false;

        User user = (User) obj;

        return getId().equals(user.getId());
    }

    /**
     * @return the number of transactions for an User
     */
    public int getNumberOfTransactions() {
        return NumberOfTransactions;
    }

    /**
     *
     * @return the last name of an User
     */
    public String getLastName() {
        return LastName;
    }

    /**
     *
     * @return the first name of an User
     */
    public String getFirstName() {
        return FirstName;
    }
}

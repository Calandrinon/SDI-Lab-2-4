package Model;

public class User extends BaseEntity<Long>{
    private final int UserID;
    private final String FirstName;
    private final String LastName;
    private final int NumberOfTransactions;

    public User(int UserID, String FirstName, String LastName, int NumberOfTransactions) {
        this.UserID = UserID;
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.NumberOfTransactions = NumberOfTransactions;
    }

    @Override
    public String toString() {
        return "Client " + Integer.toString(UserID) + ": " + FirstName + " " + LastName + " No. of transactions: " + Integer.toString(NumberOfTransactions);
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)return true;
        if(obj == null || getClass() != obj.getClass()) return false;

        User user = (User) obj;

        return UserID == user.getUserID();
    }

    public int getNumberOfTransactions() {
        return NumberOfTransactions;
    }

    public String getLastName() {
        return LastName;
    }

    public String getFirstName() {
        return FirstName;
    }

    public int getUserID() {
        return UserID;
    }
}

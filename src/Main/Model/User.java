package Model;

import java.util.function.Function;

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
        return "User " + Integer.toString(this.getId()) + ": " +
                FirstName + " " + LastName +
                " No. of transactions: " + Integer.toString(NumberOfTransactions);
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

    public static Function reader(Function<String, User> function){
        return function;
    }

    public static Function writer(Function<User, String> function){
        return function;
    }

    public static String fileWriter(BaseEntity<Integer> baseEntity){
        if (baseEntity.getClass() == User.class) {
            User user = (User) baseEntity;
            return user.getId() + " | " + user.FirstName + " | " + user.LastName + " | " + user.NumberOfTransactions;
        }
        return "";
    }

    public static User fileReader(String line){
        String[] parts = line.split(" \\| ");
        User user = new User(parts[1], parts[2], Integer.parseInt(parts[3]));
        user.setId(Integer.parseInt(parts[0]));
        return user;
    }
}

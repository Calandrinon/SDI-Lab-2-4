package Model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.function.BiFunction;
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





    public static BiFunction<User, Document, Node> userEncoder = (u, d) -> {
        Element userElement = d.createElement("user");
        userElement.setAttribute("id", Integer.toString(u.getId()));
        addChildWithTextContent(d, userElement, "firstName", u.getFirstName());
        addChildWithTextContent(d, userElement, "lastName", u.getLastName());
        addChildWithTextContent(d, userElement, "numberOfTransactions", Integer.toString(u.getNumberOfTransactions()));
        return userElement;
    };

    private static void addChildWithTextContent(Document document, Element parent, String tagName, String textContent){
        Element childElement = document.createElement(tagName);
        childElement.setTextContent(textContent);
        parent.appendChild(childElement);
    }

    public static Function<Element, User> userDecoder = e ->{
        User user = null;
        user = new User(
                e.getElementsByTagName("firstName").item(0).getTextContent(),
                e.getElementsByTagName("lastName").item(0).getTextContent(),
                Integer.parseInt(e.getElementsByTagName("numberOfTransactions").item(0).getTextContent())
        );
        user.setId(Integer.parseInt(e.getAttribute("id")));
        return user;
    };
}

package Main.Controller;

import Main.Model.User;
import Main.Repository.InMemoryRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class UserController implements IController{
    private final InMemoryRepository<Integer, User> UserRepository;

    public UserController(InMemoryRepository<Integer, User> UserRepository) {
        this.UserRepository = UserRepository;
    }

    /**
     * creates and adds to the repository a user
     *
     * @param id must be already found in the repository
     * @param FirstName must be at least one and at most 64 characters long
     * @param LastName must be at least one and at most 64 characters long
     * @param NumberOfTransactions will default to 0
     *
     *
     */

    public void add(Integer id, String FirstName, String LastName, Integer NumberOfTransactions) throws Exception {
        User user = new User(FirstName, LastName, NumberOfTransactions);
        user.setId(id);
        this.UserRepository.save(user);
    }

    /**
     * creates and updates to the repository the user with another one having the same id
     *
     * @param id must be already found in the repository
     * @param FirstName must be at least one and at most 64 characters long
     * @param LastName must be at least one and at most 64 characters long
     * @param NumberOfTransactions will default to 0
     *
     *
     */

    public void update(Integer id, String FirstName, String LastName, Integer NumberOfTransactions) throws Exception {
        User user = new User(FirstName, LastName, NumberOfTransactions);
        user.setId(id);
        this.UserRepository.update(user);
    }

    /**
     * removes from the repository the user having the
     *
     * @param id must be already found in the repository
     *
     *
     */

    public void remove(Integer id) {
        this.UserRepository.delete(id);
    }

    /**
     *
     * @param pred a given Predicate object
     * @return the list of string transactions matching the given predicate return true when applied
     */

    private List<String> filter(Predicate<User> pred){
        return StreamSupport.stream(this.UserRepository.findAll().spliterator(), false)
                .filter(pred)
                .map(User::toString)
                .collect(Collectors.toList());
    }

    /**
     *
     * @return the a list of String values corresponding to the user objects
     */

    public List<String> getRepository() {
        return filter(p -> true);
    }

    /**
     *
     * @param minimumNumberOfTransactions the lower bound for the Users' number of transactions
     * @return all of the users having more transactions than the given minimum amount
     */

    public List<String> filterByNumberOfTransactions(Integer minimumNumberOfTransactions) {
        return filter(p -> p.getNumberOfTransactions() > minimumNumberOfTransactions);
    }
}

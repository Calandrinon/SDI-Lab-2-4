package Controller;

import Exceptions.ValidationException;
import Model.User;
import Repository.InMemoryRepository;
import Repository.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class UserController implements IController{
    private final Repository<Integer, User> UserRepository;

    public UserController(Repository<Integer, User> UserRepository) {
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
     * @throws RuntimeException
     *              if the element has already been added to the list
     *
     */


    public void add(Integer id, String FirstName, String LastName, Integer NumberOfTransactions) throws ValidationException {
        User user = new User(FirstName, LastName, NumberOfTransactions);
        user.setId(id);
        this.UserRepository.save(user).ifPresent(e -> {throw new RuntimeException("element already in the list");});
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
     *
     * @param id removes from the repository the user having this id
     * @throws Exception
     *              if the element is not found inside the repository
     */

    public void remove(Integer id) throws Exception {
        this.UserRepository.delete(id).orElseThrow(() -> new Exception("no such user"));
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
        return filter(p -> p.getNumberOfTransactions() >= minimumNumberOfTransactions);
    }

    /**
     *
     * @param userID the id of the user you want to obtain
     * @return a User wrapped inside an Optional or an empty Ooptional
     */

     public Optional<User> exists(Integer userID) {
        return this.UserRepository.findOne(userID);
     }
}

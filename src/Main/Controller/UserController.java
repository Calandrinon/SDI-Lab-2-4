package Main.Controller;

import Main.Model.User;
import Main.Repository.InMemoryRepository;

import java.util.ArrayList;
import java.util.List;

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
     * @return the a list of String values corresponding to the user objects
     *
     */

    public List<String> getRepository() {
        List<String> list = new ArrayList<>();
        this.UserRepository.findAll().forEach(e -> list.add(e.toString()));
        return list;
    }
}

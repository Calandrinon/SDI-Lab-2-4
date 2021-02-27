package Controller;

import Model.User;
import Repository.InMemoryRepository;
import Repository.Repository;

import java.util.ArrayList;
import java.util.List;

public class UserController implements IController{
    private final InMemoryRepository<Integer, User> UserRepository;

    public UserController(InMemoryRepository<Integer, User> UserRepository) {
        this.UserRepository = UserRepository;
    }

    public void add(Integer id, String FirstName, String LastName, Integer NumberOfTransactions) throws Exception {
        User user = new User(FirstName, LastName, NumberOfTransactions);
        user.setId(id);
        this.UserRepository.save(user);
    }

    public void update(Integer id, String FirstName, String LastName, Integer NumberOfTransactions) throws Exception {
        User user = new User(FirstName, LastName, NumberOfTransactions);
        user.setId(id);
        this.UserRepository.update(user);
    }

    public void remove(Integer id) {
        this.UserRepository.delete(id);
    }

    public List<String> getRepository() {
        List<String> list = new ArrayList<>();
        this.UserRepository.findAll().forEach(e -> list.add(e.toString()));
        return list;
    }
}

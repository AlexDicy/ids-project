package it.unicam.cs.ids.manager;

import it.unicam.cs.ids.model.user.User;
import it.unicam.cs.ids.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserManager {
    private final UserRepository repository;

    public UserManager(UserRepository repository) {
        this.repository = repository;
    }

    public User getUser(String id) {
        return repository.findById(id).orElseThrow();
    }

    public List<User> getAll() {
        return repository.findAll();
    }
}

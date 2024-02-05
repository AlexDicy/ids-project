package it.unicam.cs.ids.manager;

import it.unicam.cs.ids.model.content.ContentType;
import it.unicam.cs.ids.model.user.User;
import it.unicam.cs.ids.model.user.UserRole;
import it.unicam.cs.ids.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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

    public void setRole(String userId, UserRole role) {
        User user = getUser(userId);
        user.setRole(role);
        repository.save(user);
    }

    public void addFavorite(String userId, ContentType type, String contentId) {
        User user = getUser(userId);
        user.addFavorite(type, contentId);
        repository.save(user);
    }

    public void removeFavorite(String userId, ContentType type, String contentId) {
        User user = getUser(userId);
        user.removeFavorite(type, contentId);
        repository.save(user);
    }

    public Map<ContentType, List<String>> getFavorites(String userId) {
        return getUser(userId).getFavorites();
    }
}

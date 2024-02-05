package it.unicam.cs.ids.controller;

import it.unicam.cs.ids.manager.UserManager;
import it.unicam.cs.ids.model.content.ContentType;
import it.unicam.cs.ids.model.user.User;
import it.unicam.cs.ids.model.user.UserRole;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/user")
public class UserController {
    private final UserManager manager;

    public UserController(UserManager manager) {
        this.manager = manager;
    }

    @GetMapping("/all")
    public List<User> getAll() {
        return manager.getAll();
    }

    @PostMapping("/setRole/{userId}/{role}")
    public void setRole(@PathVariable String userId, @PathVariable UserRole role) {
        manager.setRole(userId, role);
    }

    @PostMapping("/favorites/add/{userId}/{type}/{contentId}")
    public void addFavorite(@PathVariable String userId, @PathVariable ContentType type, @PathVariable String contentId) {
        manager.addFavorite(userId, type, contentId);
    }

    @PostMapping("/favorites/remove/{userId}/{type}/{contentId}")
    public void removeFavorite(@PathVariable String userId, @PathVariable ContentType type, @PathVariable String contentId) {
        manager.removeFavorite(userId, type, contentId);
    }

    @GetMapping("/favorites/{userId}")
    public Map<ContentType, List<String>> getFavorites(@PathVariable String userId) {
        return manager.getFavorites(userId);
    }
}

package it.unicam.cs.ids.manager;

import it.unicam.cs.ids.model.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserManager {
    private final List<User> userList = new ArrayList<>();

    public User getUser(String id) {
        Objects.requireNonNull(id, "Id is not valid");

        for (User u : userList) {
            if (u.getId().equals(id)) {
                return u;
            }
        }

        return null;
    }

    public List<User> getUsers() {
        return userList;
    }
}

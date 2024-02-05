package it.unicam.cs.ids.model.user;

import it.unicam.cs.ids.model.content.ContentType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Document(collection = "users")
public class User {
    @Id
    private String id;
    private String email;
    //@JsonIgnore
    private String password;
    @Setter
    private UserRole role;
    @Setter
    private Map<ContentType, List<String>> favorites;

    public User(String id, String email, String password, UserRole role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
        this.favorites = new HashMap<>();
    }

    public void addFavorite(ContentType type, String contentId) {
        favorites.computeIfAbsent(type, k -> new ArrayList<>());
        favorites.get(type).add(contentId);
    }

    public void removeFavorite(ContentType type, String contentId) {
        favorites.computeIfAbsent(type, k -> new ArrayList<>());
        favorites.get(type).remove(contentId);
    }
}

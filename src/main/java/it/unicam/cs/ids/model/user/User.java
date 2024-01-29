package it.unicam.cs.ids.model.user;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Document(collection = "users")
public class User {
    @Id
    private String id;
    private String email;
    private String password;
    private UserRole role;

    public User(String id, String email, String password, UserRole role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
    }
}

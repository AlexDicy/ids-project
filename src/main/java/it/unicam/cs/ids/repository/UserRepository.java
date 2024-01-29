package it.unicam.cs.ids.repository;

import it.unicam.cs.ids.model.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
}

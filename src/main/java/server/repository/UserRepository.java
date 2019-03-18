package server.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import server.model.User;

import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {

    User findFirstByUsernameAndPassword(String username, String password);

    User findFirstByUsername(String username);

    User findFirstByEmail(String email);

    int deleteUserByUsername(String username);

    List<User> findAll();

    List<User> findAllByAnonymous(boolean isAnonymous);
}

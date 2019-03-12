package server.model;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {

    User findFirstByUsernameAndPassword(String username, String password);

    User findFirstByUsername(String username);

    User findFirstByEmail(String email);

    int deleteUserByUsername(String username);

}

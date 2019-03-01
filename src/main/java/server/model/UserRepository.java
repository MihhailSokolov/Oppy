package server.model;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface UserRepository extends MongoRepository<User, String>{

     User findFirstByUsernameAndPassword(String username, String password);
     User findFirstByUsername(String username);
     User findFirstByEmail(String email);

}

package server.db;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.model.User;
import server.model.UserRepository;

// Class which will contain all the methods for querying the database
@Service
public class DbDataController {
    @Autowired
    UserRepository userRepository;

    // Check if provided user details are correct
    public boolean isUserAuthenticated(String username, String password) {
        return userRepository.findFirstByUsernameAndPassword(username, password) != null;
    }

    public boolean isUsernameAvailable(String username) {
        return userRepository.findFirstByUsername(username) == null;
    }

    public boolean isEmailAvailable(String email) {
        return userRepository.findFirstByEmail(email) == null;
    }


    /**
     * User creation method: checks whether user exists - if not: user is created.
     * @param username of user to be checked if there are no duplicates and registering
     * @param password of user for registering
     * @param email    of user to be checked for duplicates and for registering
     * @return an empty msg if all went well otherwise fill the msg with the error
     */
    public String createNewUser(String username, String password, String email) {
        String message = "";
        if (!isUsernameAvailable(username)) {
            message = "Username is already taken. Try another username.";
        } else if (!isEmailAvailable(email)) {
            message = "Email address is already registered.";
        } else {
            userRepository.save(new User(username, password, email, 0));
        }
        return message;
    }
}

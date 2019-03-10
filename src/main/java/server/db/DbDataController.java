package server.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.model.Action;
import server.model.ActionRepository;
import server.model.User;
import server.model.UserRepository;

import java.util.List;

// Class which will contain all the methods for querying the database
@Service
public class DbDataController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    ActionRepository actionRepository;

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

    public int getUserScore(String username) {
        return userRepository.findFirstByUsername(username).getScore();
    }

    public boolean deleteUser(String username) {
        return userRepository.deleteUserByUsername(username) == 1;
    }

    /**
     * Method for updating user's password in db.
     * @param username user's username
     * @param newpass user's new password
     * @return true is successful, false otherwise
     */
    public boolean updatePassword(String username, String newpass) {
        User userToUpdate = userRepository.findFirstByUsername(username);
        userToUpdate.setPassword(newpass);
        return userRepository.save(userToUpdate) != null;
    }

    /**
     * Method to get all the actions from db.
     * @return List of actions
     */
    public List<Action> getAllActions() {
        return actionRepository.findAll();
    }

    public boolean addAction(String actionName, String category, int points) {
        return actionRepository.save(new Action(actionName, category, points)) != null;
    }

    /**
     * Method to get number of points a certain action is worth.
     * @param actionName name of the action
     * @return number of points
     */
    public int getActionPoints(String actionName) {
        return actionRepository.findFirstByActionName(actionName).getPoints();
    }

    /**
     * Method to increase user's score by certain amount of points.
     * @param username user's username
     * @param points number of points to add to current user's score
     * @return true if successful, false otherwise
     */
    public boolean addToUserScore(String username, int points) {
        User user = userRepository.findFirstByUsername(username);
        user.setScore(user.getScore() + points);
        return userRepository.save(user) != null;
    }
}

package server.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.model.Action;
import server.model.User;
import server.repository.ActionRepository;
import server.repository.UserRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

import java.util.List;
import java.util.concurrent.TimeUnit;

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
            userRepository.save(new User(username, password, email, 0, new Date()));
        }
        return message;
    }

    /**
     * Method for getting the actual score of the user.
     * @param username user's username
     *      take the difference between the register date and current date and multiply that by 50.
     * @return the score - 50 times the days since creation of account with minimum value of 0
     */
    public int getUserScore(String username) {
        User user = userRepository.findFirstByUsername(username);
        int score = user.getScore();
        Date currentDate = new Date();
        Date registerDate = user.getRegisterDate();
        long diff = currentDate.getTime() - registerDate.getTime();
        int interval = Math.toIntExact(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
        int actualScore = score - 150 * interval;
        return actualScore;
    }

    public String getUserEmail(String username) {
        User user = userRepository.findFirstByUsername(username);
        return user.getEmail();
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
        if (userRepository.save(userToUpdate) == null) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Method to get all the actions from db.
     * @return List of actions
     */
    public List<Action> getAllActions() {
        return actionRepository.findAll();
    }

    /**
     * Method to add an action to the db (for prepping the db).
     * @param actionName name of action
     * @param category category name of action
     * @param points number of pts the action has
     * @return true if successful
     */
    public boolean addAction(String actionName, String category, int points) {
        if (actionRepository.save(new Action(actionName, category, points)) == null) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Method to get number of points a certain action is worth.
     * @param actionName name of the action
     * @return number of points
     */
    public int getActionPoints(String actionName) {
        Action action =  actionRepository.findFirstByActionName(actionName);
        if (action != null) {
            return action.getPoints();
        } else {
            return 0;
        }
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
        if (userRepository.save(user) == null) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Method to get Top 50 users by their score.
     * @return List of Users
     */
    public List<User> getTop50Users() {
        List<User> users = userRepository.findAllByAnonymous(false);
        List<User> top50Users = new ArrayList<>();
        int limit = 50;
        if (users.size() < 50) {
            limit = users.size();
        }
        for (int i = 0; i < limit; i++) {
            User user = users.get(i);
            user.setPassword(null);
            user.setEmail(null);
            user.setRegisterDate(null);
            user.setScore(getUserScore(user.getUsername()));
            top50Users.add(user);
        }
        top50Users.sort(new ScoreComparator());
        return top50Users;
    }

    private class ScoreComparator implements Comparator<User> {
        @Override
        public int compare(User user1, User user2) {
            return -Integer.compare(user1.getScore(), user2.getScore());
        }
    }

    /**
     * Method to change anonymous status in db.
     * @param username user's username
     * @param anonymous new anonymous status
     * @return true if successful, false otherwise
     */
    public boolean changeAnonymous(String username, boolean anonymous) {
        User userToUpdate = userRepository.findFirstByUsername(username);
        userToUpdate.setAnonymous(anonymous);
        if (userRepository.save(userToUpdate) == null) {
            return false;
        } else {
            return true;
        }
    }

     /** Method for updating user's password in db.
     * @param username user's username
     * @param newEmail user's new email
     * @return true is successful, false otherwise
     */
    public boolean updateEmail(String username, String newEmail) {
        User userToUpdate = userRepository.findFirstByUsername(username);
        userToUpdate.setEmail(newEmail);
        return userRepository.save(userToUpdate) != null;
    }

    /**
     * Method to reset users points to 0.
     * @param username user's username
     * @return true if successful, false otherwise
     */
    public boolean resetScore(String username, String pass) {
        User user = userRepository.findFirstByUsername(username);
        user.setScore(0);
        user.setRegisterDate(new Date());
        return userRepository.save(user) != null;
    }
}

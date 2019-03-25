package server.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.model.Action;
import server.model.Preset;
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
     *
     * @param user User to be registered
     * @return an empty msg if all went well otherwise fill the msg with the error
     */
    public String createNewUser(User user) {
        String message = "";
        if (!isUsernameAvailable(user.getUsername())) {
            message = "Username is already taken. Try another username.";
        } else if (!isEmailAvailable(user.getEmail())) {
            message = "Email address is already registered.";
        } else {
            user.setScore(0);
            user.setRegisterDate(new Date());
            userRepository.save(user);
        }
        return message;
    }

    /**
     * Method for getting the actual score of the user.
     *
     * @param username user's username
     *                 take the difference between the register date and current date and multiply that by 50.
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
     *
     * @param username user's username
     * @param newpass  user's new password
     * @return true is successful, false otherwise
     */
    public boolean updatePassword(String username, String newpass) {
        User userToUpdate = userRepository.findFirstByUsername(username);
        userToUpdate.setPassword(newpass);
        userRepository.save(userToUpdate);
        return true;
    }

    /**
     * Method to get all the actions from db.
     *
     * @return List of actions
     */
    public List<Action> getAllActions() {
        return actionRepository.findAll();
    }

    /**
     * Method to get number of points a certain action is worth.
     *
     * @param actionName name of the action
     * @return number of points
     */
    public int getActionPoints(String actionName) {
        Action action = actionRepository.findFirstByActionName(actionName);
        if (action != null) {
            return action.getPoints();
        } else {
            return 0;
        }
    }

    /**
     * Method to increase user's score by certain amount of points.
     *
     * @param username user's username
     * @param points   number of points to add to current user's score
     * @return true if successful, false otherwise
     */
    public boolean addToUserScore(String username, int points) {
        User user = userRepository.findFirstByUsername(username);
        user.setScore(user.getScore() + points);
        userRepository.save(user);
        return true;
    }

    /**
     * Method to get Top 50 users by their score.
     *
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
     *
     * @param username  user's username
     * @param anonymous new anonymous status
     * @return true if successful, false otherwise
     */
    public boolean changeAnonymous(String username, boolean anonymous) {
        User userToUpdate = userRepository.findFirstByUsername(username);
        userToUpdate.setAnonymous(anonymous);
        userRepository.save(userToUpdate);
        return true;
    }

    /**
     * Method for updating user's password in db.
     *
     * @param username user's username
     * @param newEmail user's new email
     * @return true is successful, false otherwise
     */
    public boolean updateEmail(String username, String newEmail) {
        User userToUpdate = userRepository.findFirstByUsername(username);
        userToUpdate.setEmail(newEmail);
        userRepository.save(userToUpdate);
        return true;
    }

    /**
     * Method to reset users points to 0.
     *
     * @param username user's username
     * @return true if successful, false otherwise
     */
    public boolean resetScore(String username) {
        User user = userRepository.findFirstByUsername(username);
        user.setScore(0);
        user.setRegisterDate(new Date());
        userRepository.save(user);
        return true;
    }

    public List<Preset> getPresets(String username) {
        return userRepository.findFirstByUsername(username).getPresets();
    }

    /**
     * Method to add new preset to the list of user's presets.
     * @param username user's username
     * @param preset a new preset to add
     * @return true if successful, false otherwise
     */
    public boolean addPresetToUser(String username, Preset preset) {
        User user = userRepository.findFirstByUsername(username);
        List<Preset> presetList = user.getPresets();
        presetList.add(preset);
        user.setPresets(presetList);
        userRepository.save(user);
        return true;
    }

    /**
     * Method to delete a preset from the list of user's presets.
     * @param username user's username
     * @param preset Preset to be deleted
     * @return true of successful, false otherwise
     */
    public boolean deletePreset(String username, Preset preset) {
        User user = userRepository.findFirstByUsername(username);
        List<Preset> presetList = user.getPresets();
        boolean deleted = presetList.remove(preset);
        user.setPresets(presetList);
        if (!deleted) {
            return false;
        }
        userRepository.save(user);
        return true;
    }

    /**
     * Method to execute provided preset.
     * @param username user's username
     * @param preset Preset to be executed
     * @return true if successfully executed, false otherwise
     */
    public boolean executePreset(String username, Preset preset) {
        List<Preset> presets = getPresets(username);
        int index = findIndexByPresetName(presets, preset.getName());
        if (index == -1) {
            return false;
        }
        int pointsToAdd = 0;
        for (String actionName : presets.get(index).getActionList()) {
            pointsToAdd += getActionPoints(actionName);
        }
        return addToUserScore(username, pointsToAdd);
    }

    /**
     * Private method to help find the index of the preset in the list only by name.
     * @param list List of presets
     * @param presetName preset name to be found
     * @return index of the preset or -1 if not found
     */
    private int findIndexByPresetName(List<Preset> list, String presetName) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getName().equals(presetName)) {
                return i;
            }
        }
        return -1;
    }

    public List<User> getFriends(String username) {
        return userRepository.findFirstByUsername(username).getFriends();
    }

    /**
     * Method to add a new friend to the user's friend list.
     * @param username user's username
     * @param friend new friend (User) to be added
     * @return true if successfully added, false otherwise
     */
    public boolean addNewFriend(String username, User friend) {
        User user = userRepository.findFirstByUsername(username);
        List<User> friends = user.getFriends();
        friends.add(friend);
        user.setFriends(friends);
        userRepository.save(user);
        return true;
    }

    /**
     * Method to delete a friend from user's list of friends.
     * @param username user's username
     * @param friend User to be deleted
     * @return true if successfully deleted, false otherwise
     */
    public boolean deleteFriend(String username, User friend) {
        User user = userRepository.findFirstByUsername(username);
        List<User> friends = user.getFriends();
        for (User user1 : friends) {
            if (user1.getUsername().equals(friend.getUsername())) {
                friends.remove(user1);
            }
        }
        user.setFriends(friends);
        userRepository.save(user);
        return true;
    }

    public int getYourPostionInList(String username) {
        List<User> users = userRepository.findAllByAnonymousOrUsernameOrderByScore(false, username);
        return findIndexByUsername(users, username);
    }

    /**
     * Private method to help find the index of the user in the list only by name.
     * @param list List of users
     * @param username username to be found
     * @return index of the user or -1 if not found
     */
    private int findIndexByUsername(List<User> list, String username) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getUsername().equals(username)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Method to get profile picture of the user from db.
     * @param username user's username
     * @return binary representation of profile picture as a String
     *          or empty string of couldn't find the user
     */
    public String getProfilePicture(String username) {
        User user = userRepository.findFirstByUsername(username);
        if (user == null) {
            return "";
        }
        return user.getProfilePicture();
    }

    /**
     * Method to change user's profile picture in db.
     * @param user User who wants to set profile picture
     * @return true if succeeded, false otherwise
     */
    public boolean setProfilePicture(User user) {
        String username = user.getUsername();
        String pic = user.getProfilePicture();
        user = userRepository.findFirstByUsername(username);
        if (user == null) {
            return false;
        }
        user.setProfilePicture(pic);
        userRepository.save(user);
        return true;
    }
}

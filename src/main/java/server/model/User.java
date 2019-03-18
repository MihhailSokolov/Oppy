package server.model;

import javafx.scene.image.Image;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@Document(collection = "user")
public class User {

    @Id
    private String id;
    private String username;
    private String password;
    private String email;
    private int score;
    private Date registerDate;
    private boolean anonymous;
    private boolean pushNotifications;
    private Image profilePicture;

    /**
     * Constructor for User object.
     *
     * @param username username of user
     * @param password pass of the user
     * @param email    email address of the user
     * @param score    score/pts of the user
     */
    public User(String username, String password, String email, int score, Date registerDate) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.score = score;
        this.registerDate = registerDate;
        this.anonymous = false;
        this.pushNotifications = true;
        this.profilePicture = null; //new Image("placeholder 100x100.png");
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public String getEmail() {
        return this.email;
    }

    public int getScore() {
        return this.score;
    }

    public boolean getAnonymous() {
        return  this.anonymous;
    }

    public boolean getPushNotifications() {
        return this.pushNotifications;
    }

    public Image getProfilePicture() {
        return profilePicture;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setAnonymous(boolean anonymous) {
        this.anonymous = anonymous;
    }

    public void setPushNotifications(boolean pushNotifications) {
        this.pushNotifications = pushNotifications;
    }

    public void setProfilePicture(Image profilePicture) {
        this.profilePicture = profilePicture;
    }

    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        return String.format(
                "User[username='%s', password='%s', email='%s', score='%d', registerDate='%s', "
                        + "anonymous='%b', pushNotifications='%b', profilePicture='%s']",
                username, password, email, score, dateFormat.format(registerDate), anonymous,
                pushNotifications, profilePicture);
    }

    @Override
    public boolean equals(Object other) {
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        User user = (User) other;
        return score == user.score
                && Objects.equals(username, user.username)
                && Objects.equals(password, user.password)
                && Objects.equals(email, user.email)
                && Objects.equals(registerDate, user.registerDate)
                && Objects.equals(anonymous, user.anonymous)
                && Objects.equals(pushNotifications, user.pushNotifications)
                && Objects.equals(profilePicture, user.profilePicture);
    }
}

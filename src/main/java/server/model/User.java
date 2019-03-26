package server.model;

import com.google.common.base.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private String profilePicture;
    private List<Preset> presets;
    private List<User> friends;

    /**
     * Constructor for User object.
     *
     * @param username username of user
     * @param password pass of the user
     * @param email    email address of the user
     * @param score    score/pts of the user
     */
    @JsonCreator
    public User(@JsonProperty("username") String username, @JsonProperty("password") String password,
                @JsonProperty("email") String email, @JsonProperty("score") int score,
                @JsonProperty("registerDate") Date registerDate) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.score = score;
        this.registerDate = registerDate;
        this.anonymous = false;
        this.profilePicture = "";
        this.presets = new ArrayList<>();
        this.friends = new ArrayList<>();
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

    public String getProfilePicture() {
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

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public List<Preset> getPresets() {
        return presets;
    }

    public void setPresets(List<Preset> presets) {
        this.presets = presets;
    }

    public List<User> getFriends() {
        return friends;
    }

    public void setFriends(List<User> friends) {
        this.friends = friends;
    }

    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        return "User["
                + "username='" + username + '\''
                + ", password='" + password + '\''
                + ", email='" + email + '\''
                + ", score=" + score
                + ", registerDate='" + dateFormat.format(registerDate) + '\''
                + ", anonymous=" + anonymous
                + ", profilePicture='" + profilePicture + '\''
                + ", presets=" + presets
                + ", friends=" + friends + ']';
    }

    @Override
    public boolean equals(Object other) {
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        User user = (User) other;
        return score == user.score
                && anonymous == user.anonymous
                && Objects.equal(username, user.username)
                && Objects.equal(password, user.password)
                && Objects.equal(email, user.email)
                && Objects.equal(registerDate, user.registerDate)
                && Objects.equal(profilePicture, user.profilePicture)
                && Objects.equal(presets, user.presets)
                && Objects.equal(friends, user.friends);
    }
}
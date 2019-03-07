package server.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "user")
public class User {

    @Id
    private String id;
    private String username;
    private String password;
    private String email;
    private int score;

    /**
     * Constructor for User object.
     * @param username username of user
     * @param password pass of the user
     * @param email email address of the user
     * @param score score/pts of the user
     */
    public User(String username, String password, String email, int score) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.score = score;
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

    @Override
    public String toString() {
        return String.format(
                "User[username=%s, password='%s', email='%s', score='%d']",
                username, password, email, score);
    }

}

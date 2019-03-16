package server.model;

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

    /**
     * Constructor for User object.
     * @param username username of user
     * @param password pass of the user
     * @param email email address of the user
     * @param score score/pts of the user
     */
    public User(String username, String password, String email, int score, Date registerDate) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.score = score;
        this.registerDate = registerDate;
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
                "User[username='%s', password='%s', email='%s', score='%d', registerDate='%s']",
                username, password, email, score, new SimpleDateFormat("dd.MM.yyyy").format(registerDate));
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        User user = (User) other;
        return score == user.score &&
                Objects.equals(username, user.username) &&
                Objects.equals(password, user.password) &&
                Objects.equals(email, user.email) &&
                Objects.equals(registerDate, user.registerDate);
    }
}

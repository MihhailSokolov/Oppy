package server.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "user")
public class User {

    @Id private String id;
    private String username;
    private String password;
    private String email;
    private int score;

    public User(String username, String password, String email, int score){
        this.username = username;
        this.password = password;
        this.email = email;
        this.score = score;
    }

    public String getUsername(){
        return this.username;
    }

    public String getPassword(){
        return this.password;
    }

    public String getEmail(){
        return this.email;
    }

    public int getScore(){
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

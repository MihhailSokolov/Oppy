package clientSide;

import com.google.common.hash.Hashing;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.nio.charset.StandardCharsets;

public class LoginHandler implements EventHandler<ActionEvent> {
    String username;
    String password;
    boolean rememberMe;

    public LoginHandler(String user, String pwd, boolean rem){
        this.username = user;
        this.password = pwd;
        this.rememberMe = rem;
    }

    @Override
    public String toString(){
        System.out.println(this.username);
        return "/Login?username=" + this.username + "&pass=" + Hashing.sha256().hashString(this.password, StandardCharsets.UTF_8).toString();
    }


    @Override
    public void handle(ActionEvent event) {
        System.out.println(this.toString());
        ClientController.sendLogin(this.toString());
    }
}

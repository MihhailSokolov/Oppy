package clientSide;

import com.google.common.hash.Hashing;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.nio.charset.StandardCharsets;

public class RegisterHandler implements EventHandler<ActionEvent> {
    String username;
    String email;
    String password;

    public RegisterHandler(String name, String E, String pass){
        this.username = name;
        this.email = E;
        this.password = pass;
    }

    @Override
    public String toString(){
        return "/register?username=" + this.username + "&pass=" + Hashing.sha256().hashString(this.password, StandardCharsets.UTF_8).toString() + "&email=" + this.email;
    }

    @Override
    public void handle(ActionEvent event) {
        ClientController.sendLogin(this.toString());
    }
}

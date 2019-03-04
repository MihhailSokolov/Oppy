package clientSide;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class ClientHandler implements EventHandler<ActionEvent> {

    Button obj;

    public ClientHandler(Button obj){
        this.obj = obj;
    }

    @Override
    public void handle(ActionEvent event) {
        if(this.obj.getText().equals("Login")){

        }
    }
}

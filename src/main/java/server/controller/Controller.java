package server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import server.db.DbDataController;
import server.model.Response;

@RestController
public class Controller {

    @Autowired DbDataController dbDataController;

    @RequestMapping("/check")
    public ResponseEntity<Response> response(
            @RequestParam(value = "msg", defaultValue = "Successful server response") String msg) {
        return ResponseEntity.ok().body(new Response("Server: " + msg));
    }

    @RequestMapping("/login")
    public ResponseEntity<Boolean> login(
            @RequestParam(value = "username") String username, @RequestParam(value = "pass") String pass){
        return ResponseEntity.ok().body(dbDataController.isUserAuthenticated(username, pass));
    }

    @RequestMapping("/nameavailable")
    public ResponseEntity<Boolean> isUsernameAvailable(@RequestParam(value = "username") String username){
        return ResponseEntity.ok().body(dbDataController.isUsernameAvailable(username));
    }

    @RequestMapping("/register")
    public ResponseEntity<String> register(@RequestParam(value = "username") String username,
            @RequestParam(value = "pass") String pass, @RequestParam(value = "email") String email){
        String msg = dbDataController.createNewUser(username, pass, email);
        if(msg.isEmpty()) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(500).body(msg);
        }
    }
}

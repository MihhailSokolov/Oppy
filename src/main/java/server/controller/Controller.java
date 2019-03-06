package server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import server.db.DbDataController;
import server.model.Response;

@RestController
public class Controller {

    @Autowired
    DbDataController dbDataController;

    @RequestMapping("/check")
    public ResponseEntity<Response> response(
            @RequestParam(value = "msg", defaultValue = "Successful server response") String msg) {
        return ResponseEntity.ok().body(new Response("Server: " + msg));
    }

    @RequestMapping("/login")
    public ResponseEntity<Boolean> login(
            @RequestParam(value = "username") String username,
            @RequestParam(value = "pass") String pass) {
        return ResponseEntity.ok().body(dbDataController.isUserAuthenticated(username, pass));
    }

    @RequestMapping("/nameavailable")
    public ResponseEntity<Boolean> isUsernameAvailable(
            @RequestParam(value = "username") String username) {
        return ResponseEntity.ok().body(dbDataController.isUsernameAvailable(username));
    }

    /**
     * Mapping for registering a user.
     * @param username of user to be checked if there are no duplicates and registering
     * @param pass     of user for registering
     * @param email    of user to be checked for duplicates and for registering
     * @return an empty msg if all went well otherwise fill the msg with the error
     */
    @RequestMapping("/register")
    public ResponseEntity<String> register(@RequestParam(value = "username") String username,
                                           @RequestParam(value = "pass") String pass,
                                           @RequestParam(value = "email") String email) {
        String msg = dbDataController.createNewUser(username, pass, email);
        if (msg.isEmpty()) {
            return ResponseEntity.ok().body("true");
        } else {
            return ResponseEntity.status(500).body(msg);
        }
    }

    @RequestMapping("/score")
    public ResponseEntity<Integer> getPoints(@RequestParam(value = "username") String username) {
        return ResponseEntity.ok().body(dbDataController.getUserScore(username));
    }

    @RequestMapping("/delete")
    public ResponseEntity<Boolean> delete(
            @RequestParam(value = "username") String username,
            @RequestParam(value = "pass") String pass) {
        if(dbDataController.isUserAuthenticated(username, pass))
            return ResponseEntity.ok().body(dbDataController.deleteUser(username));
        else
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
    }
}

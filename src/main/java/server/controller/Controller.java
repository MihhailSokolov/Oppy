package server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import server.db.DbDataController;
import server.model.Action;
import server.model.Response;

import java.util.List;

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
     *
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

    @RequestMapping("/email")
    public ResponseEntity<String> getEmail(@RequestParam(value = "username") String username) {
        return ResponseEntity.ok().body(dbDataController.getUserEmail(username));
    }

    /**
     * Delete user mapping.
     *
     * @param username to delete
     * @param pass     to confirm the action is requested by correct user.
     * @return returns status code.
     */
    @RequestMapping("/delete")
    public ResponseEntity<Boolean> delete(
            @RequestParam(value = "username") String username,
            @RequestParam(value = "pass") String pass) {
        if (dbDataController.isUserAuthenticated(username, pass)) {
            return ResponseEntity.ok().body(dbDataController.deleteUser(username));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
        }
    }

    /**
     * Method for updating user's password.
     * @param username username
     * @param pass old password
     * @param newpass new password
     * @return 'true' if successful and 'false' otherwise
     */
    @RequestMapping("/updatepass")
    public ResponseEntity<Boolean> updatepass(
            @RequestParam(value = "username") String username,
            @RequestParam(value = "pass") String pass,
            @RequestParam(value = "newpass") String newpass) {
        if (dbDataController.isUserAuthenticated(username, pass)) {
            return ResponseEntity.ok().body(dbDataController.updatePassword(username, newpass));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
        }
    }

    @RequestMapping("/addaction")
    public ResponseEntity<Boolean> addNewAction(
            @RequestParam(value = "name") String name,
            @RequestParam(value = "category") String category,
            @RequestParam(value = "points") String strPoints) {
        int points = Integer.parseInt(strPoints);
        return ResponseEntity.ok().body(dbDataController.addAction(name, category, points));
    }

    @RequestMapping("/actions")
    public ResponseEntity<List<Action>> getActions() {
        return ResponseEntity.ok().body(dbDataController.getAllActions());
    }

    @RequestMapping("/takeaction")
    public ResponseEntity<Boolean> takeAction(@RequestParam(value = "username") String username,
                                              @RequestParam(value = "action") String action) {
        int pointsToAdd = dbDataController.getActionPoints(action);
        return ResponseEntity.ok().body(dbDataController.addToUserScore(username, pointsToAdd));
    }

    /**
     * Method for taking multiple actions at once.
     * @param username user's username
     * @param actions json list of actions
     * @return true if successful, false otherwise
     */
    @RequestMapping("/takeactions")
    public ResponseEntity<Boolean> takeMultipleActions(@RequestParam("username") String username,
                                                       @RequestBody List<Action> actions) {
        int pointsToAdd = 0;
        for (Action action : actions) {
            if (action != null) {
                pointsToAdd += dbDataController.getActionPoints(action.getActionName());
            }
        }
        return ResponseEntity.ok().body(dbDataController.addToUserScore(username, pointsToAdd));
    }

    /**
     * Method for updating user's password.
     * @param username username
     * @param pass old password
     * @param newpass new password
     * @return 'true' if successful and 'false' otherwise
     */
    @RequestMapping("/updateEmail")
    public ResponseEntity<Boolean> updateEmail(
            @RequestParam(value = "username") String username,
            @RequestParam(value = "pass") String pass,
            @RequestParam(value = "newEmail") String newpass) {
        if (dbDataController.isUserAuthenticated(username, pass)) {
            return ResponseEntity.ok().body(dbDataController.updatePassword(username, newpass));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
        }
    }
}

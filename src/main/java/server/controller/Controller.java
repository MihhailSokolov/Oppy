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
import server.model.Preset;
import server.model.Response;
import server.model.User;

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

    /**
     * Mapping to login user.
     * @param user User object to login
     * @return Response object with message 'true' if user is successfully logged in, 'false' otherwise
     */
    @RequestMapping("/login")
    public ResponseEntity<Response> login(@RequestBody User user) {
        String name = user.getUsername();
        String pass = user.getPassword();
        return ResponseEntity.ok().body(new Response(dbDataController.isUserAuthenticated(name, pass)));
    }

    @RequestMapping("/nameavailable")
    public ResponseEntity<Response> isUsernameAvailable(
            @RequestParam(value = "username") String username) {
        return ResponseEntity.ok().body(new Response(dbDataController.isUsernameAvailable(username)));
    }

    /**
     * Mapping for registering a user.
     * @param user User object to be added to db
     * @return Response object with 'true' message if successful and explaining message if not
     */
    @RequestMapping("/register")
    public ResponseEntity<Response> register(@RequestBody User user) {
        String msg = dbDataController.createNewUser(user);
        if (msg.isEmpty()) {
            return ResponseEntity.ok().body(new Response("true"));
        } else {
            return ResponseEntity.ok().body(new Response(msg));
        }
    }

    @RequestMapping("/score")
    public ResponseEntity<Response> getPoints(@RequestParam(value = "username") String username) {
        return ResponseEntity.ok().body(new Response(dbDataController.getUserScore(username)));
    }

    @RequestMapping("/email")
    public ResponseEntity<Response> getEmail(@RequestParam(value = "username") String username) {
        return ResponseEntity.ok().body(new Response(dbDataController.getUserEmail(username)));
    }

    /**
     * Delete user mapping.
     * @param user User to be deleted
     * @return Response object with message 'true' if successful and 'false otherwise
     */
    @RequestMapping("/delete")
    public ResponseEntity<Response> delete(@RequestBody User user) {
        if (dbDataController.isUserAuthenticated(user.getUsername(), user.getPassword())) {
            return ResponseEntity.ok().body(new Response(dbDataController.deleteUser(user.getUsername())));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Response(false));
        }
    }

    /**
     * Method for updating user's password.
     * @param user User who wants to change password
     * @param newpass  new password
     * @return Response object with message'true' if successful and 'false' otherwise
     */
    @RequestMapping("/updatepass")
    public ResponseEntity<Response> updatepass(@RequestBody User user,
                                              @RequestParam(value = "newpass") String newpass) {
        String name = user.getUsername();
        String oldpass = user.getPassword();
        if (dbDataController.isUserAuthenticated(name, oldpass)) {
            return ResponseEntity.ok().body(new Response(dbDataController.updatePassword(name, newpass)));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Response(false));
        }
    }

    @RequestMapping("/actions")
    public ResponseEntity<List<Action>> getActions() {
        return ResponseEntity.ok().body(dbDataController.getAllActions());
    }

    @RequestMapping("/takeaction")
    public ResponseEntity<Response> takeAction(@RequestParam(value = "username") String username,
                                              @RequestBody Action action) {
        int pointsToAdd = dbDataController.getActionPoints(action.getActionName());
        return ResponseEntity.ok().body(new Response(dbDataController.addToUserScore(username, pointsToAdd)));
    }

    /**
     * Method for taking multiple actions at once.
     *
     * @param username user's username
     * @param actions  json list of actions
     * @return Response object with message 'true' if successful, 'false' otherwise
     */
    @RequestMapping("/takeactions")
    public ResponseEntity<Response> takeMultipleActions(@RequestParam("username") String username,
                                                       @RequestBody List<Action> actions) {
        int pointsToAdd = 0;
        for (Action action : actions) {
            pointsToAdd += dbDataController.getActionPoints(action.getActionName());
        }
        return ResponseEntity.ok().body(new Response(dbDataController.addToUserScore(username, pointsToAdd)));
    }

    /**
     * Method for updating user's email.
     * @param user User who wants to update email
     * @param newEmail new email
     * @return Response object with message 'true' if successful and 'false' otherwise
     */
    @RequestMapping("/updateEmail")
    public ResponseEntity<Response> updateEmail(@RequestBody User user,
            @RequestParam(value = "newEmail") String newEmail) {
        String name = user.getUsername();
        String pass = user.getPassword();
        if (dbDataController.isUserAuthenticated(name, pass)) {
            return ResponseEntity.ok().body(new Response(dbDataController.updateEmail(name, newEmail)));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Response(false));
        }
    }

    /**
     * Method for resetting users points to 0.
     * @param user User who wants to reset
     * @return Response object with message 'true' if successful and 'false' otherwise
     */
    @RequestMapping("/reset")
    public ResponseEntity<Response> resetScore(@RequestBody User user) {
        String name = user.getUsername();
        String pass = user.getPassword();
        if (dbDataController.isUserAuthenticated(name, pass)) {
            return ResponseEntity.ok().body(new Response(dbDataController.resetScore(name)));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Response(false));
        }
    }

    @RequestMapping("/top50")
    public ResponseEntity<List<User>> getTop50Users() {
        return ResponseEntity.ok().body(dbDataController.getTop50Users());
    }

    /**
     * Mapping for changing user's anonymous status.
     * @param user User who wants to change anonymous status
     * @param strAnonymous new anonymous status
     * @return Response object with message 'true' is successful, 'false' otherwise
     */
    @RequestMapping("/changeAnonymous")
    public ResponseEntity<Response> changeAnonymous(@RequestBody User user,
                                                   @RequestParam("anonymous") String strAnonymous) {
        boolean anonymous = Boolean.parseBoolean(strAnonymous);
        String name = user.getUsername();
        String pass = user.getPassword();
        if (dbDataController.isUserAuthenticated(name, pass)) {
            return ResponseEntity.ok().body(new Response(dbDataController.changeAnonymous(name, anonymous)));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Response(false));
        }
    }

    @RequestMapping("/presets")
    public ResponseEntity<List<Preset>> getPresets(@RequestParam("username") String username) {
        return ResponseEntity.ok().body(dbDataController.getPresets(username));
    }

    @RequestMapping("/addpreset")
    public ResponseEntity<Response> addPreset(@RequestParam("username") String username, @RequestBody Preset preset) {
        return ResponseEntity.ok().body(new Response(dbDataController.addPresetToUser(username, preset)));
    }

    @RequestMapping("/deletepreset")
    public ResponseEntity<Response> deletePreset(@RequestParam("username") String username,
                                                 @RequestBody Preset preset) {
        return ResponseEntity.ok().body(new Response(dbDataController.deletePreset(username, preset)));
    }

    @RequestMapping("/executepreset")
    public ResponseEntity<Response> executePreset(@RequestParam("username") String username,
                                                  @RequestBody Preset preset) {
        return ResponseEntity.ok().body(new Response(dbDataController.executePreset(username, preset)));
    }

    @RequestMapping("/friends")
    public ResponseEntity<List<User>> getFriends(@RequestParam("username") String username) {
        return ResponseEntity.ok().body(dbDataController.getFriends(username));
    }

    @RequestMapping("/addfriend")
    public ResponseEntity<Response> addFriend(@RequestParam("username") String username, @RequestBody User friend) {
        return ResponseEntity.ok().body(new Response(dbDataController.addNewFriend(username, friend)));
    }

    @RequestMapping("/deletefriend")
    public ResponseEntity<Response> deleteFriend(@RequestParam("username") String username, @RequestBody User friend) {
        return ResponseEntity.ok().body(new Response(dbDataController.deleteFriend(username, friend)));
    }

    @RequestMapping("/position")
    public ResponseEntity<Response> getYourPositions(@RequestParam("username") String username) {
        return ResponseEntity.ok().body(new Response(dbDataController.getYourPostionInList(username)));
    }

    @RequestMapping("/getprofilepic")
    public ResponseEntity<Response> getProfilePic(@RequestParam("username") String username) {
        return ResponseEntity.ok().body(new Response(dbDataController.getProfilePicture(username)));
    }

    @RequestMapping("/setprofilepic")
    public ResponseEntity<Response> setProfilePic(@RequestBody User user) {
        return ResponseEntity.ok().body(new Response(dbDataController.setProfilePicture(user)));
    }
}
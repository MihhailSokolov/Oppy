package clientside;

import com.google.common.hash.Hashing;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.image.Image;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import server.model.Action;
import server.model.Preset;
import server.model.User;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ClientController {
    private User user;
    private String baseUrl = "https://oppy-project.herokuapp.com/";
    private RestTemplate restTemplate = new RestTemplate();
    private List<Action> actionList = null;
    private ResponseEntity<String> responseEntity = null;
    private List<User> top50 = null;
    private ObjectMapper objectMapper = new ObjectMapper();

    public ClientController(User user) {
        this.user = user;
        user.setPassword(hash(user.getPassword()));
    }


    public ClientController() {
    }

    private enum Path {
        REGISTER {
            public String toString() {
                return "register";
            }
        }, AVAILABILITY {
            public String toString() {
                return "nameavailable?username=%s";
            }
        }, LOGIN {
            public String toString() {
                return "login";
            }
        }, SCORE {
            public String toString() {
                return "score?username=%s";
            }
        }, ACTIONS {
            public String toString() {
                return "actions";
            }
        }, TAKEACTION {
            public String toString() {
                return "takeaction?username=%s";
            }
        }, DELETE {
            public String toString() {
                return "delete";
            }
        }, UPDATEPASS {
            public String toString() {
                return "updatepass?newpass=%s";
            }
        }, EMAIL {
            public String toString() {
                return "email?username=%s";
            }
        }, TAKEACTIONS {
            public String toString() {
                return "takeactions?username=%s";
            }
        }, UPDATEEMAIL {
            public String toString() {
                return "updateEmail?newEmail=%s";
            }
        }, TOP50 {
            public String toString() {
                return "top50";
            }
        }, CHANGEANON {
            public String toString() {
                return "changeAnonymous?anonymous=%s";
            }
        }, RESET {
            public String toString() {
                return "reset";
            }
        }, ADDFRIEND {
            public String toString() {
                return "addfriend?username=%s";
            }
        }, GETFRIENDS {
            public String toString() {
                return "friends?username=%s";
            }
        }, DELETEFRIEND {
            public String toString() {
                return "deletefriend?username=%s";
            }
        }, ADDPRESET {
            public String toString() {
                return "addpreset?username=%s";
            }
        }, GETPRESETS {
            public String toString() {
                return "presets?username=%s";
            }
        }, DELETEPRESET {
            public String toString() {
                return "deletepreset?username=%s";
            }
        }, USERINFO {
            public String toString() {
                return "userinfo";
            }
        }, SETPROFILEPIC {
            public String toString() {
                return "setprofilepic";
            }
        }, GETPROFILEPIC {
            public String toString() {
                return "getprofilepic?username=%s";
            }
        }

    }

    /**
     * Fetches the profile picture base 64 string from the server.
     * @param username who the profile picture belongs to.
     * @return Image (profile picture).
     */
    public Image getProfilePic(String username) {
        responseEntity = this.getRequest(this.baseUrl + String.format(Path.GETPROFILEPIC.toString(), username));
        return ImageHandler.decodeToImg(new JSONObject(responseEntity.getBody()).getString("message"));
    }

    /**
     * Sends the base64 encoded profile picture string to the server.
     * @param img the img file to be encoded and sent.
     * @return String response message ("true"/"false").
     */
    public String updateProfilePic(File img) {
        String encodedStr = ImageHandler.getBase64Str(img);
        this.user.setProfilePicture(encodedStr);
        responseEntity = this.postRequest(this.baseUrl + Path.SETPROFILEPIC.toString(), user);
        return new JSONObject(responseEntity.getBody()).getString("message");
    }

    /**
     * Deletes the given preset from the user's presetList on the server.
     *
     * @param preset the preset to delete (only preset name is required, the rest can be null)
     * @return String response message ("true"/"false").
     */
    public String deletePreset(Preset preset) {
        responseEntity = this.postRequest(this.baseUrl
                + String.format(Path.DELETEPRESET.toString(), this.user.getUsername()), preset);
        return new JSONObject(responseEntity.getBody()).getString("message");
    }

    /**
     * Updates this.user's presetsList by downloading a User (preset) list from server and setting
     * the user's presets to the mentioned list.
     */
    public void updateUserPresets() throws IOException {
        responseEntity = this.getRequest(this.baseUrl
                + String.format(Path.GETPRESETS.toString(), this.user.getUsername()));
        this.user.setPresets(objectMapper.readValue(responseEntity.getBody(), new TypeReference<List<Preset>>() {
        }));
    }

    /**
     * Sends a post request to the server requesting to add a preset to a user's preset list.
     *
     * @param preset the preset (type: Preset) to be added.
     * @return String response message ("true"/"false").
     */
    public String addPreset(Preset preset) {
        responseEntity = this.postRequest(this.baseUrl
                + String.format(Path.ADDPRESET.toString(), this.user.getUsername()), preset);
        return new JSONObject(responseEntity.getBody()).getString("message");
    }

    /**
     * Updates this.user's friendlist by downloading a User (friend) list from server and setting
     * the user's friends to the mentioned list.
     */
    public void updateFriendList() throws IOException {
        responseEntity = this.getRequest(this.baseUrl
                + String.format(Path.GETFRIENDS.toString(), this.user.getUsername()));
        this.user.setFriends(objectMapper.readValue(responseEntity.getBody(), new TypeReference<List<User>>() {
        }));
    }

    /**
     * Sends a get request to server to add a friend to the this.user's friend list.
     *
     * @param friend the the friend (User type) to be added
     * @return String response message ("true"/"false").
     */
    public String addFriend(User friend) {
        responseEntity = this.postRequest(this.baseUrl
                + String.format(Path.ADDFRIEND.toString(), this.user.getUsername()), friend);
        return new JSONObject(responseEntity.getBody()).getString("message");
    }

    /**
     * Sends a post request to server to delete a friend from the user's friend list.
     *
     * @param friendToDelete (User) friend to delete.
     * @return String response message ("true"/"false").
     */
    public String deleteFriend(User friendToDelete) {
        responseEntity = this.postRequest(this.baseUrl
                + String.format(Path.DELETEFRIEND.toString(), this.user.getUsername()), friendToDelete);
        return new JSONObject(responseEntity.getBody()).getString("message");
    }

    /**
     * Sends a new user registration request to server.
     *
     * @return String response message ("true"/"false").
     */
    public String register() {
        responseEntity = this.postRequest(this.baseUrl + Path.REGISTER.toString(), user);
        return new JSONObject(responseEntity.getBody()).getString("message");
    }

    /**
     * Sends an "update user pass" request to the server.
     *
     * @param newPass the new password for the user.
     * @return String response message ("true"/"false").
     */
    public String updatePass(String newPass) {
        responseEntity = this.postRequest(this.baseUrl
                + String.format(Path.UPDATEPASS.toString(), hash(newPass)), user);
        return new JSONObject(responseEntity.getBody()).getString("message");
    }

    /**
     * Sends a "take action request" to the server.
     *
     * @param actionName action name to be sent.
     * @return String response msg ("true"/"false").
     */
    public String takeAction(String actionName) {
        responseEntity = this.postRequest(this.baseUrl + String.format(Path.TAKEACTION.toString(),
                user.getUsername()), new Action(actionName, "", 0));
        return new JSONObject(responseEntity.getBody()).getString("message");
    }

    /**
     * Sends a "get email request" to the server.
     *
     * @return String response msg containing user's email addy.
     */
    public String getEmail() {
        responseEntity = this.getRequest(this.baseUrl + String.format(Path.EMAIL.toString(), user.getUsername()));
        return new JSONObject(responseEntity.getBody()).getString("message");
    }

    /**
     * Returns the list of actions.
     *
     * @return actionList.
     */
    public List<Action> getActionList() {
        return this.actionList;
    }

    /**
     * Updates the list of actions by downloading the action list from the server.
     */
    public void updateActionList() {
        responseEntity = this.getRequest(this.baseUrl + String.format(Path.ACTIONS.toString()));
        try {
            actionList = objectMapper.readValue(responseEntity.getBody(), new TypeReference<List<Action>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sends a "delete acct request" to the server.
     *
     * @return String response msg ("true"/"false"), implying success or failure.
     */
    public String deleteAccount() {
        responseEntity = this.postRequest(this.baseUrl + Path.DELETE.toString(), user);
        return new JSONObject(responseEntity.getBody()).getString("message");
    }

    /**
     * Sends an "update email addy request" to the server.
     *
     * @param newEmail the new email addy.
     * @param pass     current password.
     * @return String response msg ("true"/"false").
     */
    public String updateEmail(String newEmail, String pass) {
        if (hash(pass).equals(this.user.getPassword())) {
            responseEntity = this.postRequest(this.baseUrl
                    + String.format(Path.UPDATEEMAIL.toString(), newEmail), user);
            String responseMsg = new JSONObject(responseEntity.getBody()).getString("message");
            return responseMsg;
        } else {
            return "false";
        }
    }

    /**
     * Sends a "reset request" to the server.
     *
     * @return String resposne msg ("true"/"false").
     */
    public String reset() {
        responseEntity = this.postRequest(this.baseUrl + Path.RESET, this.user);
        return new JSONObject(responseEntity.getBody()).getString("message");
    }

    /**
     * Sends a "login request" to the server.
     *
     * @return String response msg "true"/"false"
     */
    public String login() {
        responseEntity = this.postRequest(this.baseUrl + Path.LOGIN.toString(), user);
        return new JSONObject(responseEntity.getBody()).getString("message");
    }

    /**
     * Sends a request to server to get user's score.
     *
     * @return user's score contained in content body.
     */
    public String getScore() {
        responseEntity = this.getRequest(this.baseUrl + String.format(Path.SCORE.toString(), user.getUsername()));
        return new JSONObject(responseEntity.getBody()).getString("message");
    }

    /**
     * Sends a request to the server checking the availability of a username.
     *
     * @param username username to be checked.
     * @return availability "true"/"false" contained in response msg.
     */
    public String checkAvailability(String username) {
        responseEntity = this.getRequest(this.baseUrl + String.format(Path.AVAILABILITY.toString(), username));
        return new JSONObject(responseEntity.getBody()).getString("message");
    }

    /**
     * Post request method.
     *
     * @param url url to point at.
     * @param obj object to send.
     * @return ResponseEntity containing status code/body.
     */
    private ResponseEntity<String> postRequest(String url, Object obj) {
        return restTemplate.postForEntity(url, obj, String.class);
    }

    /**
     * Get request method.
     *
     * @param url to point at.
     * @return ResponseEntity containing status code/body.
     */
    private ResponseEntity<String> getRequest(String url) {
        return restTemplate.getForEntity(url, String.class);
    }

    /**
     * Method to subdivide list of actions according to given cat name.
     *
     * @param categoryName cat name to subdivide.
     * @return return list of actions from only that category.
     */
    public List<Action> getCategoryList(String categoryName) {
        List<Action> categoryList = new ArrayList<>();
        if (this.actionList != null) {
            for (Action act : actionList) {
                if (act.getCategory().equals(categoryName)) {
                    categoryList.add(act);
                }
            }
            return categoryList;
        }
        return null;
    }

    public User getUser() {
        return this.user;
    }

    public String hash(String pwd) {
        return Hashing.sha256().hashString(pwd, StandardCharsets.UTF_8).toString();
    }

    /**
     * Method to update the top50 arraylist with the users who have the most points.
     */
    public void updateTop50() {
        responseEntity = this.getRequest(this.baseUrl + String.format(Path.TOP50.toString()));
        try {
            top50 = objectMapper.readValue(responseEntity.getBody(), new TypeReference<List<User>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<User> getTop50() {
        return top50;
    }

    /**
     * Updates the this.user.
     */
    public void updateUser() {
        responseEntity = this.postRequest(this.baseUrl
                + Path.USERINFO.toString(), user);
        try {
            this.user = objectMapper.readValue(responseEntity.getBody(), User.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public void setActionList(List<Action> actionList) {
        this.actionList = actionList;
    }

    /**
     * Sends an "update pushNotifications request" to the server.
     *
     * @param trueOrFalse tells if anonymous should be set to true or false.
     */
    public String updateAnonymous(boolean trueOrFalse) {
        responseEntity = this.postRequest(this.baseUrl
                + String.format(Path.CHANGEANON.toString(), trueOrFalse), user);
        String responseMsg = new JSONObject(responseEntity.getBody()).getString("message");
        this.user.setAnonymous(trueOrFalse);
        return responseMsg;
    }


}

package clientside;

import com.google.common.hash.Hashing;
import com.google.gson.Gson;

import org.json.JSONObject;

import org.springframework.http.ResponseEntity;

import org.springframework.web.client.RestTemplate;

import server.model.Action;
import server.model.User;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClientController {
    private User user;
    private String baseUrl = "https://oppy-project.herokuapp.com/";
    private RestTemplate restTemplate = new RestTemplate();
    private List<Action> actionList = null;
    private ResponseEntity<String> responseEntity = null;
    private List<User> top50 = null;

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
        }

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
        String responseMsg = new JSONObject(responseEntity.getBody()).getString("message");
        if (responseMsg.equals("true")) {
            this.user.setPassword(hash(newPass));
        }
        return responseMsg;
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
        Gson gson = new Gson();
        actionList = Arrays.asList(gson.fromJson(responseEntity.getBody(), Action[].class));
    }

    /**
     * Sends a "delete acct request" to the server
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
            if (responseMsg.equals("true")) {
                this.user.setEmail(newEmail);
            }
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
        Gson gson = new Gson();
        top50 = Arrays.asList(gson.fromJson(responseEntity.getBody(), User[].class));

    }

    public List<User> getTop50() {
        return top50;
    }

    public void updateUser() {
        user.setScore(Integer.parseInt(this.getScore()));
    }


    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public void setActionList(List<Action> actionList) {
        this.actionList = actionList;
    }

}

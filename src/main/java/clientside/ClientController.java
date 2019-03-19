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
    private String baseUrl =  "https://oppy-project.herokuapp.com/";
    private RestTemplate restTemplate = new RestTemplate();
    private List<Action> actionList = null;
    ResponseEntity<String> responseEntity = null;

    public ClientController(User user){
        this.user = user;
        user.setPassword(hash(user.getPassword()));
    }

    public ClientController(){ }

    public enum Path{
        REGISTER{
            public String toString(){
                return "register";
            }
        }, AVAILABILITY{
            public String toString(){
                return "nameavailable?username=%s";
            }
        }, LOGIN{
            public String toString() {
                return "login";
            }
        }, SCORE{
            public String toString() {
                return "score?username=%s";
            }
        }, ACTIONS{
            public String toString(){
                return "actions";
            }
        }, TAKEACTION {
            public String toString(){
                return "takeaction?username=%s";
            }
        }, DELETE{
            public String toString(){
                return "delete";
            }
        }, UPDATEPASS{
            public String toString(){
                return "updatepass?newpass=%s";
            }
        }, EMAIL{
            public String toString(){
                return "email?username=%s";
            }
        }, TAKEACTIONS{
            public String toString(){
                return "takeactions?username=%s";
            }
        }, UPDATEEMAIL{
            public String toString(){
                return "updateEmail?newEmail=%s";
            }
        }, TOP50 {
            public String toString(){
                return "TOP50";
            }
        }, CHANGEANON{
            public String toString(){
                return "/changeAnonymous?anonymous=%s";
            }
        }

    }

    public String register(){
        if(this.user != null){
            responseEntity = this.postRequest(this.baseUrl + Path.REGISTER.toString(), user);
        }
        return new JSONObject(responseEntity.getBody()).getString("message");
    }

    public String takeAction(String actionName){
        if(this.user != null){
            responseEntity = this.postRequest(this.baseUrl + String.format(Path.TAKEACTION.toString(),
                    user.getUsername()), new Action(actionName, "", 0));
        }
        return new JSONObject(responseEntity.getBody()).getString("message");
    }

    public String getEmail(){
        responseEntity = this.getRequest(this.baseUrl + String.format(Path.EMAIL.toString()));
        return new JSONObject(responseEntity.getBody()).getString("message");
    }

    public List<Action> getActionList() {
        return this.actionList;
    }

    public void updateActionList(){
        responseEntity = this.getRequest(this.baseUrl + String.format(Path.ACTIONS.toString()));
        if(responseEntity.getBody() != null) {
            Gson gson = new Gson();
            actionList = Arrays.asList(gson.fromJson(responseEntity.getBody(), Action[].class));
        }
    }

    public String deleteAcount(){
        if(this.user != null){
            responseEntity = this.postRequest(this.baseUrl + Path.DELETE.toString(), user);
        }
        return new JSONObject(responseEntity.getBody()).getString("message");
    }

    public String updateEmail(String newEmail, String pass){
        if(this.user != null && hash(pass) == this.user.getPassword()) {
            responseEntity = this.postRequest(this.baseUrl + Path.UPDATEEMAIL.toString(), user);
        }
        return new JSONObject(responseEntity.getBody()).getString("message");
    }

    public String login(){
        if(this.user != null){
            responseEntity = this.postRequest(this.baseUrl + Path.LOGIN.toString(), user);
        }
        return new JSONObject(responseEntity.getBody()).getString("message");
    }

    public String getScore(){
        responseEntity = this.getRequest(this.baseUrl + String.format(Path.SCORE.toString(), user.getUsername()));
        return new JSONObject(responseEntity.getBody()).getString("message");
    }
    public String checkAvailability(String username){
        responseEntity = this.getRequest(this.baseUrl + String.format(Path.AVAILABILITY.toString(), username));
        return new JSONObject(responseEntity.getBody()).getString("message");
    }

    private ResponseEntity<String> postRequest(String url, Object obj){
        return restTemplate.postForEntity(url, obj, String.class);
    }

    private ResponseEntity<String> getRequest(String url){
        return restTemplate.getForEntity(url, String.class);
    }

    public List<Action> getCategoryList(String categoryName) {
        List<Action> categoryList = new ArrayList<>();
        if (this.actionList != null) {
            for (Action act : actionList) {
                if (act.getCategory().equals(categoryName)) {
                    categoryList.add(act);
                }
            }
        }
        return categoryList;
    }

    public User getUser(){
        return this.user;
    }

    private String hash(String pwd) {
        return Hashing.sha256().hashString(pwd, StandardCharsets.UTF_8).toString();
    }
}

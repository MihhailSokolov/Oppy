package clientside;

import org.springframework.web.client.RestTemplate;
import server.model.Action;

import java.util.Arrays;
import java.util.List;

public class ActionHandler {
    private  String username;
    private List<Action> actionList;
    private final String submitParams = "takeaction?username=%s&action=%s";
    private final String uri = "http://oppy-project.herokuapp.com/";
    private Action lastSentAction;

    public ActionHandler(String username) {
        this.username = username;
        this.lastSentAction = null;
        this.actionList = null;
    }

    public List<Action> getActionList() {
        return this.actionList;
    }

    public String submitAction(Action action){
        lastSentAction = action;
        RestTemplate restTemplate = new RestTemplate();
        System.out.println(this.uri +  String.format(submitParams, this.username, action.getActionName()));
        return restTemplate.getForObject(this.uri + String.format(submitParams, this.username, action.getActionName()), String.class);
    }

    public void updateActionList(){
        RestTemplate restTemplate = new RestTemplate();
        actionList = Arrays.asList(restTemplate.getForObject(this.uri + "actions", Action[].class));
    }

}

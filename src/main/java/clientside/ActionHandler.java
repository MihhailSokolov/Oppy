package clientside;

import org.springframework.web.client.RestTemplate;
import server.model.Action;

import java.util.Arrays;
import java.util.List;

public class ActionHandler {
    private String username;
    private List<Action> actionList;
    private final String submitParams = "takeaction?username=%s&action=%s";
    private final String uri = "http://oppy-project.herokuapp.com/";
    private RestTemplate restTemplate;

    /**
     * ActionHandler is responsible for:
     * submitting actions, updating action list.
     *
     * @param username stores the username for comm purposes. Preps RestTemplate.
     */
    public ActionHandler(String username) {
        this.username = username;
        this.actionList = null;
        restTemplate = new RestTemplate();
    }

    public List<Action> getActionList() {
        return this.actionList;
    }

    /**
     * Submits action to server.
     * @param action the action to be submitted.
     * @return response body.
     */
    public String submitAction(Action action) {
        return restTemplate.getForObject(this.uri
                + String.format(submitParams, this.username, action.getActionName()), String.class);
    }

    public void updateActionList() {
        actionList = Arrays.asList(restTemplate.getForObject(this.uri + "actions", Action[].class));
    }

}

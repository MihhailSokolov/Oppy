package clientside;

import org.springframework.web.client.RestTemplate;
import server.model.Action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ActionHandler {
    private String username;
    private List<Action> actionList;
    private final String submitParams = "takeaction?username=%s&action=%s";
    private String uri = "http://oppy-project.herokuapp.com/";
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
     * Submits the given action to the server.
     *
     * @param actionName the action name to be submitted.
     * @return response body.
     */
    public String submitAction(String actionName) {
        return restTemplate.getForObject(this.uri
                + String.format(submitParams, this.username, actionName), String.class);
    }

    public void updateActionList() {
        actionList = Arrays.asList(restTemplate.getForObject(this.uri + "actions", Action[].class));
    }

    /**
     * Subdivides and returns the action list in only the desired category.
     * @param categoryName The category name of the desired list.
     * @return the list of actions in the desired category
     */
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

    public void setUri(String uri) {
        this.uri = uri;
    }
}

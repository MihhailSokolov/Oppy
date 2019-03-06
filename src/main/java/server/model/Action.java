package server.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document(collection = "actions")
public class Action {

    @Id
    private String id;
    private String actionName;
    private String category;
    private int points;

    public Action(String actionName, String category, int points) {
        this.actionName = actionName;
        this.category = category;
        this.points = points;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    @Override
    public String toString() {
        return "Action{" +
                "actionName='" + actionName + '\'' +
                ", category='" + category + '\'' +
                ", points=" + points +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Action)) return false;
        Action action = (Action) o;
        return getPoints() == action.getPoints() &&
                Objects.equals(getActionName(), action.getActionName()) &&
                Objects.equals(getCategory(), action.getCategory());
    }
}

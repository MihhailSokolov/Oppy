package server.model;

public class Action {
    private String nameAction;
    private String category;
    private int points;

    public Action(String nameAction, String category, int points) {
        this.nameAction = nameAction;
        this.category = category;
        this.points = points;
    }

    public void setNameAction(String nameAction) {
        this.nameAction = nameAction;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getCategory() {
        return category;
    }

    public int getPoints() {
        return points;
    }

    public String getNameAction() {
        return nameAction;
    }
}

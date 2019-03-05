package UI;

public class AddActionObject {
    public String nameAction;
    public String category;
    public int points;
    public boolean selected;


    public AddActionObject(String nameAction, String category, int points){
        this.nameAction = nameAction;
        this.category =category;
        this.points = points;
        this.selected = false;
    }


}

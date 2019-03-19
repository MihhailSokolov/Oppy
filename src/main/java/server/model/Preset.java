package server.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Preset {

    private String name;
    private List<String> actionList;

    public Preset(String name) {
        this.name = name;
        actionList = new ArrayList<>();
    }

    public Preset(String name, List<String> actionList) {
        this.name = name;
        this.actionList = actionList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getActionList() {
        return actionList;
    }

    public void setActionList(List<String> actionList) {
        this.actionList = actionList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Preset preset = (Preset) o;
        return Objects.equals(name, preset.name) &&
                Objects.equals(actionList, preset.actionList);
    }
}

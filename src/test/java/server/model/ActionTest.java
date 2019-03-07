package server.model;

import org.junit.Test;
import static org.junit.Assert.*;

public class ActionTest {

    @Test
    public void constructorTest() {
        Action action = new Action("recycle bottle","recycling",10);
        assertNotNull(action);
    }

    @Test
    public void getActionName() {
        Action action = new Action("recycle bottle","recycling",10);
        assertEquals("recycle bottle", action.getActionName());
    }

    @Test
    public void getCategory() {
        Action action = new Action("recycle bottle","recycling",10);
        assertEquals("recycling", action.getCategory());
    }

    @Test
    public void getPoints() {
        Action action = new Action("recycle bottle","recycling",10);
        assertEquals(10, action.getPoints());
    }

    @Test
    public void setActionName() {
        Action action = new Action("recycle bottle","recycling",10);
        action.setActionName("recycle paper");
        assertEquals("recycle paper",action.getActionName());
    }

    @Test
    public void setCategory() {
        Action action = new Action("recycle bottle","recycling",10);
        action.setCategory("plastic");
        assertEquals("plastic", action.getCategory());
    }

    @Test
    public void setPoints() {
        Action action = new Action("recycle bottle","recycling",10);
        action.setPoints(20);
        assertEquals(20, action.getPoints());
    }
}

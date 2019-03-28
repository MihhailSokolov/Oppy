package server.model;

import org.junit.Test;

import java.util.Date;

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

    @Test
    public void testToString() {
        Action action = new Action("recycle bottle","recycling",10);
        assertEquals("Action[actionName='recycle bottle', category='recycling', points='10']", action.toString());
    }

    @SuppressWarnings("all")
    @Test
    public void testEquals() {
        Action action1 = new Action("recycle bottle","recycling",10);
        Action action2 = new Action("recycle bottle","recycling",10);
        Action action3 = new Action("vegetable meal","food",15);
        Action action4 = new Action("recycle bottle","food",10);
        Action action5 = new Action("vegetable meal","recycling",10);
        Action action6 = new Action("recycle bottle","recycling",15);
        assertTrue(action1.equals(action2));
        assertTrue(action1.equals(action1));
        assertFalse(action1.equals(action3));
        assertFalse(action1.equals(action4));
        assertFalse(action1.equals(action5));
        assertFalse(action1.equals(action6));
        assertFalse(action1.equals(null));
        assertFalse(action1.equals("String test"));
        assertFalse(action1.equals(new User("user", "pass", "mail", 10, new Date())));
    }
}

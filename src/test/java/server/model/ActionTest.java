package server.model;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class ActionTest {

    private Action action1;
    private Action action2;
    private Action action3;
    private Action action4;
    private Action action5;
    private Action action6;


    @Before
    public void setUp() {
        action1 = new Action("recycle bottle","recycling",10);
        action2 = new Action("recycle bottle","recycling",10);
        action3 = new Action("vegetable meal","food",15);
        action4 = new Action("recycle bottle","food",10);
        action5 = new Action("vegetable meal","recycling",10);
        action6 = new Action("recycle bottle","recycling",15);
    }

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

    @Test
    public void testEqualsSimilar() {
        assertEquals("An action should be equal to another action with the same values for fields.", action1, action2);
    }

    @Test
    public void testEqualsSelf() {
        assertEquals("An action should be equal to itself.", action1, action1);
    }

    @Test
    public void testEqualsCompletelyDifferent() {
        assertNotEquals("An action with different field values should not be equal.", action1, action3);
    }

    @Test
    public void testEqualsCategoryDifferent() {
        assertNotEquals("If an action is in a different category, it should not be equal.", action1, action4);
    }

    @Test
    public void testEqualsNameDifferent() {
        assertNotEquals("If an action has a different name, it should not be equal.", action1, action5);
    }

    @Test
    public void testEqualsPointsDifferent() {
        assertNotEquals("If an action has a different number of points, it should not be equal.", action1, action6);
    }

    @Test
    public void testEqualsNotNull() {
        assertNotEquals("An action should not be equal to null.", action1, null);
    }

    @Test
    public void testEqualsNotString() {
        assertNotEquals("An action should not be equal to a String.", action1, "String");
    }

    @Test
    public void testEqualsNotUser() {
        assertNotEquals("An action should not be equal to a User.", action1, new User("user", "pass", "mail", 10, new Date()));
    }

}

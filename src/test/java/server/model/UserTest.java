package server.model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class UserTest {

    private User user1;
    private User user2;
    private User user3;
    private User user4;
    private User user5;
    private User user6;
    private User user7;
    private User user8;
    private User user9;
    private User user10;
    private User user11;
    private User user12;

    @Before
    public void setUp(){
        user1 = new User("hugo","pass","test@gmail.com",100, new Date());
        user2 = new User("hugo","pass","test@gmail.com",100, new Date());
        user3 = new User("nothugo","differentpass","mail@gmail.com",200, new Date(0));
        user4 = new User("nothugo","pass","test@gmail.com",100, new Date());
        user5 = new User("hugo","differentpass","test@gmail.com",100, new Date());
        user6 = new User("hugo","pass","mail@gmail.com",100, new Date());
        user7 = new User("hugo","pass","test@gmail.com",200, new Date());
        user8 = new User("hugo","pass","test@gmail.com",100, new Date(0));
        user9 = new User("hugo","pass","test@gmail.com",100, new Date());
        user9.setAnonymous(true);
        user10 = new User("hugo","pass","test@gmail.com",100, new Date());
        user10.setProfilePicture("1010100101010010111110");
        user11 = new User("hugo","pass","test@gmail.com",100, new Date());
        user11.setFriends(Arrays.asList(user5, user6, user7));
        user12 = new User("hugo","pass","test@gmail.com",100, new Date());
        user12.setPresets(Arrays.asList(new Preset("preset1", new ArrayList<>()), new Preset("preset2", new ArrayList<>())));
    }

    @Test
    public void constructorTest() {
        assertNotNull(user1);
    }

    @Test
    public void getUsername() {
        assertEquals("hugo", user1.getUsername());
    }

    @Test
    public void getPassword() {
        assertEquals("pass", user1.getPassword());
    }

    @Test
    public void getEmail() {
        assertEquals("test@gmail.com", user1.getEmail());
    }

    @Test
    public void getScore() {
        assertEquals(100, user1.getScore());
    }

    @Test
    public void setUsername() {
        User u = new User("hugo","pass","test@gmail.com",100, new Date());
        u.setUsername("hugo2");
        assertEquals("hugo2",u.getUsername());
    }

    @Test
    public void setPassword() {
        User u = new User("hugo","pass","test@gmail.com",100, new Date());
        u.setPassword("pass2");
        assertEquals("pass2",u.getPassword());
    }

    @Test
    public void setEmail() {
        User u = new User("hugo","pass","test@gmail.com",100, new Date());
        u.setEmail("test2@gmail.com");
        assertEquals("test2@gmail.com",u.getEmail());
    }

    @Test
    public void setScore() {
        User u = new User("hugo","pass","test@gmail.com",100, new Date());
        u.setScore(200);
        assertEquals(200,u.getScore());
    }

    @Test
    public void setRegisterDate() {
        User u = new User("hugo","pass","test@gmail.com",100, new Date());
        u.setRegisterDate(new Date(123456789));
        assertEquals(new Date(123456789),u.getRegisterDate());
    }

    @Test
    public void getRegisterDate() {
        assertEquals(new Date(), user1.getRegisterDate());
    }

    @Test
    public void getPresets() {
        assertNotNull(user1.getPresets());
    }

    @Test
    public void setPresets() {
        User user = new User("hugo","pass","test@gmail.com",100, new Date());
        Preset preset = new Preset("myPreset", new ArrayList<>());
        List<Preset> presets = new ArrayList<>();
        presets.add(preset);
        user.setPresets(presets);
        assertEquals(presets, user.getPresets());
    }

    @Test
    public void testEqualsSimilar() {
        assertEquals("A User should be equal to another User with the same values for fields.", user1, user2);
    }

    @Test
    public void testEqualsSelf() {
        assertEquals("A User should be equal to itself.", user1, user1);
    }

    @Test
    public void testEqualsCompletelyDifferent() {
        assertNotEquals("A User with different field values should not be equal.", user1, user3);
    }

    @Test
    public void testEqualsNameDifferent() {
        assertNotEquals("If a User has a different name, it should not be equal.", user1, user4);
    }

    @Test
    public void testEqualsPasswordDifferent() {
        assertNotEquals("If a User has a different password, it should not be equal.", user1, user5);
    }

    @Test
    public void testEqualsEmailDifferent() {
        assertNotEquals("If a User has a different email, it should not be equal.", user1, user6);
    }

    @Test
    public void testEqualsScoreDifferent() {
        assertNotEquals("If a User has a different score, it should not be equal.", user1, user7);
    }

    @Test
    public void testEqualsDateDifferent() {
        assertNotEquals("If a User has a different creation date, it should not be equal.", user1, user8);
    }

    @Test
    public void testEqualsAnonDifferent() {
        assertNotEquals("If a User has a different anonymity state, it should not be equal.", user1, user9);
    }

    @Test
    public void testEqualsPicDifferent() {
        assertNotEquals("If a User has a different profile picture, it should not be equal.", user1, user10);
    }

    @Test
    public void testEqualsFriendsDifferent() {
        assertNotEquals("If a User has different friends, it should not be equal.", user1, user11);
    }

    @Test
    public void testEqualsPresetsDifferent() {
        assertNotEquals("If a User has a different presets, it should not be equal.", user1, user12);
    }

    @Test
    public void testEqualsNotNull() {
        assertNotEquals("A User should not be equal to null.", user1, null);
    }

    @Test
    public void testEqualsNotString() {
        assertNotEquals("A User should not be equal to a String.", user1, "String");
    }

    @Test
    public void testEqualsNotUser() {
        assertNotEquals("A User should not be equal to an Action.", user1, new Action("name", "category", 10));
    }
}
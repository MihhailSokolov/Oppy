package server.model;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class UserTest {

    @Test
    public void constructorTest() {
        User u = new User("hugo","pass","test@gmail.com",100, new Date());
        assertNotNull(u);
    }

    @Test
    public void getUsername() {
        User u = new User("hugo","pass","test@gmail.com",100, new Date());
        assertEquals("hugo",u.getUsername());
    }

    @Test
    public void getPassword() {
        User u = new User("hugo","pass","test@gmail.com",100, new Date());
        assertEquals("pass",u.getPassword());
    }

    @Test
    public void getEmail() {
        User u = new User("hugo","pass","test@gmail.com",100, new Date());
        assertEquals("test@gmail.com",u.getEmail());
    }

    @Test
    public void getScore() {
        User u = new User("hugo","pass","test@gmail.com",100, new Date());
        assertEquals(100,u.getScore());
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
        User u = new User("hugo","pass","test@gmail.com",100, new Date());
        assertEquals(new Date(), u.getRegisterDate());
    }

    @Test
    public void testToString() {
        User u = new User("hugo","pass","test@gmail.com",100, new Date());
        assertEquals("User[username='hugo', password='pass', email='test@gmail.com', score=100, registerDate='"
                + new SimpleDateFormat("dd.MM.yyyy").format(new Date()) + "', anonymous=false," +
                " profilePicture='', presets=[], friends=[]]", u.toString());
    }

    @SuppressWarnings("all")
    @Test
    public void testEquals() {
        User user1 = new User("hugo","pass","test@gmail.com",100, new Date());
        User user2 = new User("hugo","pass","test@gmail.com",100, new Date());
        User user3 = new User("nothugo","differentpass","mail@gmail.com",200, new Date());
        User user4 = new User("hugo","differentpass","test@gmail.com",100, new Date());
        User user5 = new User("hugo","pass","mail@gmail.com",100, new Date());
        User user6 = new User("hugo","pass","test@gmail.com",200, new Date());
        User user7 = new User("hugo","pass","test@gmail.com",100, new Date(0));
        User user8 = new User("nothugo","diffpass","test@gmail.com",100, new Date());
        User user9 = new User("nothugo","pass","nottest@gmail.com",100, new Date());
        User user10 = new User("nothugo","pass","test@gmail.com",100, new Date(0));
        User user11 = new User("hugo","pass","test@gmail.com",100, new Date());
        user11.setAnonymous(true);
        User user12 = new User("hugo","pass","test@gmail.com",100, new Date());
        user12.setProfilePicture("1010100101010010111110");
        User user13 = new User("hugo","pass","test@gmail.com",100, new Date());
        user13.setFriends(Arrays.asList(user5, user6, user7));
        User user14 = new User("hugo","pass","test@gmail.com",100, new Date());
        user14.setPresets(Arrays.asList(new Preset("preset1", new ArrayList<>()), new Preset("preset2", new ArrayList<>())));
        assertTrue(user1.equals(user2));
        assertTrue(user1.equals(user1));
        assertFalse(user1.equals(user3));
        assertFalse(user1.equals(user4));
        assertFalse(user1.equals(user5));
        assertFalse(user1.equals(user6));
        assertFalse(user1.equals(user7));
        assertFalse(user1.equals(user8));
        assertFalse(user1.equals(user9));
        assertFalse(user1.equals(user10));
        assertFalse(user1.equals(user11));
        assertFalse(user1.equals(user12));
        assertFalse(user1.equals(user13));
        assertFalse(user1.equals(user14));
        assertFalse(user1.equals(null));
        assertFalse(user1.equals("String test"));
        assertFalse(user1.equals(new Action("name", "category", 10)));
    }

    @Test
    public void getPresets() {
        User user = new User("hugo","pass","test@gmail.com",100, new Date());
        assertNotNull(user.getPresets());
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
}
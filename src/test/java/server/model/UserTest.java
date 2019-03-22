package server.model;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
                " pushNotifications=true, profilePicture=null, presets=[], friends=[]]", u.toString());
    }

    @Test
    public void testEquals() {
        User user1 = new User("hugo","pass","test@gmail.com",100, new Date());
        User user2 = new User("hugo","pass","test@gmail.com",100, new Date());
        User user3 = new User("nothugo","differentpass","mail@gmail.com",200, new Date());
        assertEquals(user1, user2);
        assertEquals(user1, user1);
        assertNotEquals(user1, user3);
        assertNotEquals(null, user1);
        assertNotEquals("String test", user1);
        assertNotEquals(new Action("name", "category", 10), user1);
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
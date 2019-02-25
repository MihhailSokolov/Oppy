package server.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {

    @Test
    public void constructorTest() {
        User u = new User("hugo","pass","test@gmail.com",100);
        assertNotNull(u);
    }

    @Test
    public void getUsername() {
        User u = new User("hugo","pass","test@gmail.com",100);
        assertEquals("hugo",u.getUsername());
    }

    @Test
    public void getPassword() {
        User u = new User("hugo","pass","test@gmail.com",100);
        assertEquals("pass",u.getPassword());
    }

    @Test
    public void getEmail() {
        User u = new User("hugo","pass","test@gmail.com",100);
        assertEquals("test@gmail.com",u.getEmail());
    }

    @Test
    public void getScore() {
        User u = new User("hugo","pass","test@gmail.com",100);
        assertEquals(100,u.getScore());
    }

    @Test
    public void setUsername() {
        User u = new User("hugo","pass","test@gmail.com",100);
        u.setUsername("hugo2");
        assertEquals("hugo2",u.getUsername());
    }

    @Test
    public void setPassword() {
        User u = new User("hugo","pass","test@gmail.com",100);
        u.setPassword("pass2");
        assertEquals("pass2",u.getPassword());
    }

    @Test
    public void setEmail() {
        User u = new User("hugo","pass","test@gmail.com",100);
        u.setEmail("test2@gmail.com");
        assertEquals("test2@gmail.com",u.getEmail());
    }

    @Test
    public void setScore() {
        User u = new User("hugo","pass","test@gmail.com",100);
        u.setScore(200);
        assertEquals(200,u.getScore());
    }
}
package clientSide;

import clientside.LoginHandler;
import org.junit.Test;

import static org.junit.Assert.*;

public class LoginHandlerTest {

    LoginHandler l = new LoginHandler("oppy123", "passwd", true);
    @Test
    public void constructorTestNEP(){
        LoginHandler local = new LoginHandler("oppy123", "passwd", true);
        assertNotNull(local);
    }

    @Test
    public void getUsernameTest(){
        assertEquals("oppy123", l.getUsername());
    }

    @Test
    public void getPasswordTest(){
        assertNotEquals("passwd", l.getPassword());
    }

    @Test
    public void toStringTest(){
        String result = l.toString();
        assertEquals("https://oppy-project.herokuapp.com/login?username=oppy123" +
                "&pass=0d6be69b264717f2dd33652e212b173104b4a647b7c11ae72e9885f11cd312fb",
                result);
    }

    @Test
    public void sendLoginTest(){
        
    }
}

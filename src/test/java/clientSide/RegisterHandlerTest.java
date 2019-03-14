package clientSide;

import clientside.RegisterHandler;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class RegisterHandlerTest {
    @Test
    public void constructorTestENP(){
        clientside.RegisterHandler r = new RegisterHandler("test", "test", "test");
        assertNotNull(r);
    }
}

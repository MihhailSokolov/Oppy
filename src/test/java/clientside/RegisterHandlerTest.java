package clientside;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

public class RegisterHandlerTest {

    @Test
    public void constructorTestENP(){
        RegisterHandler r = new RegisterHandler("test", "test", "test");
        assertNotNull(r);
    }

    @Test
    public void toStringTest(){
        RegisterHandler r = new RegisterHandler("oppy123", "oppy%40gmail.com", "passwd");
        String result = r.toString();
        assertEquals( "https://oppy-project.herokuapp.com/register?username=oppy123" +
                        "&pass=0d6be69b264717f2dd33652e212b173104b4a647b7c11ae72e9885f11cd312fb" +
                        "&email=oppy%40gmail.com",
                result);
    }

    @Test
    public void sendRegisterTest(){

    }

    @Test
    public void sendAvailabilityCheck(){

    }
}

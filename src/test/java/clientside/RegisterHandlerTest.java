package clientside;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static org.junit.Assert.assertEquals;

public class RegisterHandlerTest {

    RegisterHandler registerHandler;

    @Rule
    public WireMockRule wireMockRule = new WireMockRule();

    @Before
    public void setUp(){
        registerHandler = new RegisterHandler("somename", "someemail", "somepass");
    }

    @Test
    public void sendRegisterTest(){
        registerHandler.setUri("http://127.0.0.1:8080/");
        wireMockRule.stubFor(get("/"
                + String.format(registerHandler.getRegisterParams(), registerHandler.getUsername(), registerHandler.getPassword(), registerHandler.getEmail() ))
                .willReturn(ok("true")));
        assertEquals("true", registerHandler.sendRegister());
    }

    @Test public void sendAvailabilityCheckTest(){
        RegisterHandler rh = new RegisterHandler("somename");
        rh.setUri("http://127.0.0.1:8080/");
        wireMockRule.stubFor(get("/"
                + String.format(rh.getAvailabilityParams(), rh.getUsername()))
                .willReturn(ok("true")));
        assertEquals("true", rh.sendAvailabilityCheck());
    }

//    @Test
//    public void constructorTestENP(){
//        RegisterHandler r = new RegisterHandler("test", "test", "test");
//        assertNotNull(r);
//    }
//
//    @Test
//    public void toStringTest(){
//        RegisterHandler r = new RegisterHandler("oppy123", "oppy%40gmail.com", "passwd");
//        String result = r.toString();
//        assertEquals( "https://oppy-project.herokuapp.com/register?username=oppy123" +
//                        "&pass=0d6be69b264717f2dd33652e212b173104b4a647b7c11ae72e9885f11cd312fb" +
//                        "&email=oppy%40gmail.com",
//                result);
//    }
//
//    @Test
//    public void sendRegisterTest(){
//
//    }
//
//    @Test
//    public void sendAvailabilityCheck(){
//
//    }
}

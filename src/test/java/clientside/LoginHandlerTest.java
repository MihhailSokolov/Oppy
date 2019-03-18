package clientside;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;


import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static org.junit.Assert.assertEquals;

public class LoginHandlerTest {
    LoginHandler loginHandler;

    @Rule
    public WireMockRule wireMockRule = new WireMockRule();

    @Before
    public void setup() {
        loginHandler = new LoginHandler("test", "passwd", true);
    }

    @Test
    public void sendLoginTest() {
        loginHandler.setUri("http://127.0.0.1:8080/");
        wireMockRule.stubFor(get(String.format("/" + loginHandler.getLoginParams(), loginHandler.getUsername(), loginHandler.getPassword()))
                .willReturn(ok("true")));
        assertEquals("true", loginHandler.sendLogin());
    }
}

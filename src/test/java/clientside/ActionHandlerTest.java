package clientside;


import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static org.junit.Assert.*;
import org.springframework.web.client.RestTemplate;
import server.model.Action;

import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;


public class ActionHandlerTest {
     RestTemplate restTemplate;
     Action testAction;
     ActionHandler actionHandler;

    @Rule
    public WireMockRule wireMockRule = new WireMockRule();


    @Before
    public void setup(){
        restTemplate = new RestTemplate();
        testAction = new Action("vegetarian meal", "food", 20);
        actionHandler = new ActionHandler("Simba");
    }
    @Test
    public void submitActionTest() throws URISyntaxException {
        actionHandler.setUri("http://127.0.0.1:8080/");
        wireMockRule.stubFor(get("/takeaction?username=Simba&action="+ testAction.getActionName().replaceAll(" ", "%20"))
                .willReturn(ok("true")));
        assertEquals("true", actionHandler.submitAction(testAction));
    }

    @Test
    public void updateActionListTest(){
        actionHandler.setUri("http://oppy-project.herokuapp.com/");
        actionHandler.updateActionList();
        List<Action> expectedList = Arrays
                .asList(restTemplate.getForObject("http://oppy-project.herokuapp.com/actions", Action[].class));
        assertEquals(expectedList, actionHandler.getActionList());
    }
}
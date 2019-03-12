package clientside;


import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.web.client.RestTemplate;
import server.model.Action;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;


public class ActionHandlerTest {
    private RestTemplate restTemplate;
    private Action testAction;
    private ActionHandler actionHandler;

    @Before
    public void setup(){
        restTemplate = new RestTemplate();
        testAction = new Action("vegetarian meal", "food", 20);
        actionHandler = new ActionHandler("Simba");
    }
    @Test
    public void submitActionTest() throws URISyntaxException {
        String expected = restTemplate.getForObject(new URI("http://oppy-project.herokuapp.com/takeaction?username=Simba&action=vegetarian%20meal"), String.class);
        assertEquals(expected, actionHandler.submitAction(testAction));
    }

    @Test
    public void getActionList(){
        actionHandler.updateActionList();
        List<Action> expectedList = Arrays
                .asList(restTemplate.getForObject("http://oppy-project.herokuapp.com/actions", Action[].class));
                assertEquals(expectedList, actionHandler.getActionList());
    }
}
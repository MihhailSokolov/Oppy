package clientside;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;
import server.model.Action;
import server.model.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ClientControllerTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(); // no constr args = default port 8080

    User testUser;
    Action testAction;
    String testUserJson;
    String testActionJson;

    ClientController clientController;
    ObjectMapper objectMapper;
    RestTemplate restTemplate;

    final String trueResponse = "{\"message\" : \"true\"}";

    @Before
    public void setUp() throws Exception {
        testUser = new User("user", "pass", "email", 0, new Date());
        testAction = new Action("I had green thoughts", "", 0);
        clientController = new ClientController();
        clientController = new ClientController(testUser);
        clientController.setBaseUrl("http://localhost:8080/");
        objectMapper = new ObjectMapper();
        testUserJson = objectMapper.writeValueAsString(testUser);
        testActionJson = objectMapper.writeValueAsString(testAction);
        restTemplate = new RestTemplate();
    }

    @Test
    public void registerTest() {
        wireMockRule.stubFor(any(urlPathEqualTo("/register"))
                .withRequestBody(equalToJson(testUserJson))
                .willReturn(ok(trueResponse)));
        assertEquals("true", clientController.register());
    }

    @Test
    public void loginTest() {
        wireMockRule.stubFor(any(urlPathEqualTo("/login"))
                .withRequestBody(equalToJson(testUserJson))
                .willReturn(ok(trueResponse)));
        assertEquals("true", clientController.login());
    }

    @Test
    public void checkAvailabilityTest() {
        wireMockRule.stubFor(get(urlPathEqualTo("/nameavailable"))
                .withQueryParam("username", equalTo(testUser.getUsername()))
                .willReturn(ok(trueResponse)));
        assertEquals("true", clientController.checkAvailability(testUser.getUsername()));
    }

    @Test
    public void getScoreTest() {
        wireMockRule.stubFor(get(urlPathEqualTo("/score"))
                .withQueryParam("username", equalTo(testUser.getUsername()))
                .willReturn(ok(trueResponse)));
        assertEquals("true", clientController.getScore());
    }

    @Test
    public void getUpdateActionList() {
        clientController.setBaseUrl("http://oppy-project.herokuapp.com/");
        clientController.updateActionList();
        List<Action> expectedList = Arrays
                .asList(restTemplate.getForObject("http://oppy-project.herokuapp.com/actions", Action[].class));
        assertEquals(expectedList, clientController.getActionList());
        clientController.setBaseUrl("http://localhost:8080/");
    }

    @Test
    public void takeActionTest() {
        wireMockRule.stubFor(any(urlPathEqualTo("/takeaction"))
                .withQueryParam("username", equalTo(testUser.getUsername()))
                .withRequestBody(equalToJson(testActionJson))
                .willReturn(ok(trueResponse)));
        assertEquals("true", clientController.takeAction(testAction.getActionName()));
    }

    @Test
    public void deleteAccountTest() {
        wireMockRule.stubFor(any(urlPathEqualTo("/delete"))
                .withRequestBody(equalToJson(testUserJson))
                .willReturn(ok(trueResponse)));
        assertEquals("true", clientController.deleteAccount());
    }

    @Test
    public void updatePassTest() {
        wireMockRule.stubFor(any(urlPathEqualTo("/updatepass"))
                .withQueryParam("newpass", equalTo(clientController.hash("newpaws")))
                .withRequestBody(equalToJson(testUserJson))
                .willReturn(ok(trueResponse)));
        assertEquals("true", clientController.updatePass("newpaws"));
        assertTrue(testUser.getPassword().equals(clientController.hash("newpaws")));

        wireMockRule.stubFor(any(urlPathEqualTo("/updatepass"))
                .withQueryParam("newpass", equalTo(clientController.hash("newpaws")))
                .withRequestBody(equalToJson(testUserJson))
                .willReturn(ok(trueResponse)));
    }

    @Test
    public void getEmailTest() {
        wireMockRule.stubFor(get(urlPathEqualTo("/email"))
                .withQueryParam("username", equalTo(testUser.getUsername()))
                .willReturn(ok(trueResponse)));
        assertEquals("true", clientController.getEmail());
    }

    @Test
    public void updateEmailTest(){
        this.testUser = new User("user", "pass", "email", 0, new Date());
        wireMockRule.stubFor(any(urlPathEqualTo("/updateEmail"))
                .withQueryParam("newEmail", equalTo("ewmail"))
                .withRequestBody(equalToJson(testUserJson))
                .willReturn(ok(trueResponse)));
        assertEquals("true", clientController.updateEmail("ewmail", testUser.getPassword()));
        assertEquals("false", clientController.updateEmail("ewmail", "123456"));
        assertTrue(clientController.getUser().getEmail().equals("ewmail"));
    }

    @Test
    public void resetTest() {
        wireMockRule.stubFor(any(urlPathEqualTo("/reset"))
                .withRequestBody(equalToJson(testUserJson))
                .willReturn(ok(trueResponse)));
        assertEquals("true", clientController.reset());
    }

    @Test
    public void getCategoryListTest(){

        List<Action> actionList = new ArrayList<>();
        assertEquals(null, clientController.getCategoryList("5"));
        for(int i=0; i<10; i++){
            actionList.add(new Action("somename" + i, String.valueOf(i), i*100));
        }
        List<Action> cat5actionList = new ArrayList<>();
        Action cat5action = new Action("somename5", "5", 500);
        cat5actionList.add(cat5action);
        clientController.setActionList(actionList);
        assertEquals(cat5actionList, clientController.getCategoryList("5"));
    }

}
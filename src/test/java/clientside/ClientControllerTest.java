package clientside;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;
import server.model.Action;
import server.model.Preset;
import server.model.User;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.Assert.assertEquals;

public class ClientControllerTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(); // no constr args = default port 8080

    User testUser;
    User testFriend;
    Action testAction;
    Preset testPreset;
    List<User> testList;

    String testUserJson;
    String testActionJson;
    String testPresetJson;
    String testFriendJson;

    ClientController clientController;
    ObjectMapper objectMapper;
    RestTemplate restTemplate;

    final String trueResponse = "{\"message\" : \"true\"}";
    final File tuxFile = new File("src/main/resources/tux.png");

    @Before
    public void setUp() throws Exception {
        testUser = new User("user", "pass", "email", 0, new Date());
        testFriend = new User("Jesus", "", "", 0, new Date());
        testAction = new Action("I had green thoughts", "", 0);
        testPreset = new Preset("SomePreset", null);
        clientController = new ClientController();
        clientController = new ClientController(testUser);
        clientController.setBaseUrl("http://localhost:8080/");
        objectMapper = new ObjectMapper();
        testUserJson = objectMapper.writeValueAsString(testUser);
        testActionJson = objectMapper.writeValueAsString(testAction);
        testPresetJson = objectMapper.writeValueAsString(testPreset);
        testFriendJson = objectMapper.writeValueAsString(testFriend);
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
    public void getUpdateActionList() throws IOException {
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
    }

    @Test
    public void getEmailTest() {
        wireMockRule.stubFor(get(urlPathEqualTo("/email"))
                .withQueryParam("username", equalTo(testUser.getUsername()))
                .willReturn(ok(trueResponse)));
        assertEquals("true", clientController.getEmail());
    }

    @Test
    public void updateEmailTest() {
        this.testUser = new User("user", "pass", "email", 0, new Date());
        wireMockRule.stubFor(any(urlPathEqualTo("/updateEmail"))
                .withQueryParam("newEmail", equalTo("ewmail"))
                .withRequestBody(equalToJson(testUserJson))
                .willReturn(ok(trueResponse)));
        assertEquals("true", clientController.updateEmail("ewmail", testUser.getPassword()));
        assertEquals("false", clientController.updateEmail("ewmail", "123456"));
    }

    @Test
    public void resetTest() {
        wireMockRule.stubFor(any(urlPathEqualTo("/reset"))
                .withRequestBody(equalToJson(testUserJson))
                .willReturn(ok(trueResponse)));
        assertEquals("true", clientController.reset());
    }

    @Test
    public void getCategoryListTest() {

        List<Action> actionList = new ArrayList<>();
        assertEquals(null, clientController.getCategoryList("5"));
        for (int i = 0; i < 10; i++) {
            actionList.add(new Action("somename" + i, String.valueOf(i), i * 100));
        }
        List<Action> cat5actionList = new ArrayList<>();
        Action cat5action = new Action("somename5", "5", 500);
        cat5actionList.add(cat5action);
        clientController.setActionList(actionList);
        assertEquals(cat5actionList, clientController.getCategoryList("5"));
    }

    @Test
    public void updateFriendListTest() throws IOException {
        List<User> testFriendList = new ArrayList<>();
        testFriendList.add(new User("BillGates99", null, null, 0, new Date()));
        testFriendList.add(new User("ElonMusk24", null, null, 0, new Date()));
        String testFriendListJson = objectMapper.writeValueAsString(testFriendList);
        wireMockRule.stubFor(get(urlPathEqualTo("/friends"))
                .withQueryParam("username", equalTo(testUser.getUsername()))
                .willReturn(ok(testFriendListJson)));
        clientController.updateFriendList();
        assertEquals(testFriendList, testUser.getFriends());
    }

    @Test
    public void addFriendTest() {
        wireMockRule.stubFor(any(urlPathEqualTo("/addfriend"))
                .withQueryParam("username", equalTo(testUser.getUsername()))
                .withRequestBody(equalToJson(testFriendJson))
                .willReturn(ok(trueResponse)));
        assertEquals("true", clientController.addFriend(testFriend));
    }

    @Test
    public void deleteFriendTest() {
        wireMockRule.stubFor(any(urlPathEqualTo("/deletefriend"))
                .withQueryParam("username", equalTo(testUser.getUsername()))
                .withRequestBody(equalToJson(testFriendJson))
                .willReturn(ok(trueResponse)));
        assertEquals("true", clientController.deleteFriend(testFriend));
    }

    @Test
    public void updateTop50Test() throws IOException {
        List<User> testTop50 = new ArrayList<>();
        testTop50.add(new User("num1", null, null, 10, null));
        String testTop50Json = objectMapper.writeValueAsString(testTop50);
        wireMockRule.stubFor(get(urlPathEqualTo("/top50"))
                .willReturn(ok(testTop50Json)));
        clientController.updateTop50();
        assertEquals(testTop50, clientController.getTop50());
    }

    @Test
    public void addPresetTest() {
        wireMockRule.stubFor(any(urlPathEqualTo("/addpreset"))
                .withQueryParam("username", equalTo(testUser.getUsername()))
                .withRequestBody(equalToJson(testPresetJson))
                .willReturn(ok(trueResponse)));
        assertEquals("true", clientController.addPreset(testPreset));
    }

    @Test
    public void updateUserPresetsTest() throws IOException {
        List<Preset> presetList = new ArrayList<>();
        presetList.add(testPreset);
        String presetListJson = objectMapper.writeValueAsString(presetList);
        wireMockRule.stubFor(get(urlPathEqualTo("/presets"))
                .withQueryParam("username", equalTo(testUser.getUsername()))
                .willReturn(ok(presetListJson)));
        clientController.updateUserPresets();
        assertEquals(presetList, testUser.getPresets());
    }

    @Test
    public void deletePresetTest() {
        wireMockRule.stubFor(any(urlPathEqualTo("/deletepreset"))
                .withQueryParam("username", equalTo(testUser.getUsername()))
                .withRequestBody(equalToJson(testPresetJson))
                .willReturn(ok(trueResponse)));
        assertEquals("true", clientController.deletePreset(testPreset));
    }

    @Test
    public void updateAnonymousTest() {
        this.testUser = new User("user", "pass", "email", 0, new Date());
        wireMockRule.stubFor(any(urlPathEqualTo("/changeAnonymous"))
                .withQueryParam("anonymous", equalTo("true"))
                .withRequestBody(equalToJson(testUserJson))
                .willReturn(ok(trueResponse)));
        assertEquals("true", clientController.updateAnonymous(true));
        assertEquals(true, clientController.getUser().getAnonymous());
    }

    @Test
    public void updateUserTest() {
        wireMockRule.stubFor(any(urlPathEqualTo("/userinfo"))
                .withRequestBody(equalToJson(testUserJson))
                .willReturn(ok(testFriendJson)));
        clientController.updateUser();
        assertEquals(clientController.getUser(), testFriend);
    }

    @Test
    public void updateProfilePicTest() throws IOException {
        testUser.setProfilePicture(ImageHandler.getBase64Str(ImageIO.read(tuxFile)));
        wireMockRule.stubFor(any(urlPathEqualTo("/setprofilepic"))
                .withRequestBody(equalToJson(objectMapper.writeValueAsString(testUser)))
                .willReturn(ok(trueResponse)));
        assertEquals("true", clientController.updateProfilePic(ImageIO.read(tuxFile)));
    }

    @Test
    public void getProfilePicTest() throws IOException {
        String base64tux = ImageHandler.getBase64Str(ImageIO.read(tuxFile));
        BufferedImage tuxImg = ImageHandler.decodeToImg(base64tux);
        wireMockRule.stubFor(get(urlPathEqualTo("/getprofilepic"))
                .withQueryParam("username", equalTo(testUser.getUsername()))
                .willReturn(ok("{\"message\" : \"" + base64tux + "\"}")));
        BufferedImage imgFromServer = clientController.getProfilePic(testUser.getUsername());
        assertEquals(imgFromServer.getHeight(), tuxImg.getHeight());
        assertEquals(imgFromServer.getWidth(), tuxImg.getWidth());
        for (int x = 0; x < imgFromServer.getWidth(); x++) {
            for (int y = 0; y < imgFromServer.getHeight(); y++) {
                assertEquals(imgFromServer.getRGB(x, y), tuxImg.getRGB(x, y));
            }
        }
    }

    @Test
    public void getPositionTest() {
        wireMockRule.stubFor(get(urlPathEqualTo("/position"))
                .withQueryParam("username", equalTo(testUser.getUsername()))
                .willReturn(ok(trueResponse)));
        assertEquals("true", clientController.getPosition());
    }

    @Test
    public void top50RankTest() {
        List<User> test = new ArrayList<>();
        test.add(new User("BillGates99", null, null, 0, new Date()));
        clientController.top50Ranks(test);
        assertEquals("1", test.get(0).getEmail());
    }

}

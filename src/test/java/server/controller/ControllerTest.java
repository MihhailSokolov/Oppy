package server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import server.db.DbDataController;
import server.model.Action;
import server.repository.ActionRepository;
import server.model.User;
import server.repository.UserRepository;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ControllerTest {

    MockMvc mockMvc;

    User testUser;

    Action testAction;

    Action additionalTestAction;

    @Autowired
    Controller controller;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ActionRepository actionRepository;

    @Autowired
    DbDataController dbDataController;

    @Before
    public void setup() {
        this.mockMvc = standaloneSetup(this.controller).build();
        LocalDate nowDate = LocalDate.now().minusDays(1);
        Date date = Date.from(nowDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        testUser = new User("oppy123",
                "0d6be69b264717f2dd33652e212b173104b4a647b7c11ae72e9885f11cd312fb",
                "oppy%40gmail.com",
                42
                , date);
        if(userRepository.findFirstByUsername(testUser.getUsername())!=null)
            userRepository.delete(userRepository.findFirstByUsername(testUser.getUsername()));
        testAction = new Action("Recycle paper", "Recycling", 10);
        if(actionRepository.findFirstByActionName(testAction.getActionName()) != null)
            actionRepository.delete(actionRepository.findFirstByActionName(testAction.getActionName()));
        additionalTestAction = new Action("Go by bike", "Transportation", 20);
        if(actionRepository.findFirstByActionName(additionalTestAction.getActionName()) != null)
            actionRepository.delete(actionRepository.findFirstByActionName(additionalTestAction.getActionName()));
    }

    @Test
    public void actualPointsTest() {
        userRepository.save(testUser);
        int actualScore = dbDataController.getUserScore(testUser.getUsername());
        assertEquals(testUser.getScore() - 150, actualScore);
        userRepository.delete(testUser);
    }

    @Test
    public void checkTestWithoutMsg() throws Exception {
        mockMvc.perform(get("/check").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("Server: Successful server response")));
    }

    @Test
    public void checkScore() throws Exception {
        userRepository.save(testUser);
        String strScore = String.valueOf(dbDataController.getUserScore(testUser.getUsername()));
        mockMvc.perform(get("/score?username="+testUser.getUsername()))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.message", is(strScore)));
    }

    @Test
    public void checkTestWithMsg() throws Exception {
        mockMvc.perform(get("/check?msg=Hello").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("Server: Hello")));
    }

    @Test
    public void checkUsernameAvailable() throws Exception {
        // Two scenarios:
        // Username not in db...
        if(userRepository.findFirstByUsername(testUser.getUsername())!=null)
            userRepository.delete(userRepository.findFirstByUsername(testUser.getUsername()));
        mockMvc.perform(get(String.format("/nameavailable?username=%s", testUser.getUsername())))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.message", is("true")));

        // and username in db
        userRepository.save(testUser);
        mockMvc.perform(get("/nameavailable?username=" + testUser.getUsername()))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.message", is("false")));

        userRepository.delete(testUser);

    }

    @Test
    public void checkUserRegister() throws Exception {
        if(userRepository.findFirstByUsername(testUser.getUsername())!=null)
            userRepository.delete(userRepository.findFirstByUsername(testUser.getUsername()));
        ObjectMapper mapper = new ObjectMapper();
        String jsonBody = mapper.writeValueAsString(testUser);
        User wrongTestUser = new User(testUser.getUsername(), testUser.getPassword(), testUser.getEmail(),
                testUser.getScore(), testUser.getRegisterDate());
        wrongTestUser.setUsername("newUsernameWhichWasNotUsedYet");
        String wrongJsonBody = mapper.writeValueAsString(wrongTestUser);
        mockMvc.perform(get("/register").contentType(MediaType.APPLICATION_JSON).content(jsonBody))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.message", is("true")));
        mockMvc.perform(get("/register").contentType(MediaType.APPLICATION_JSON).content(jsonBody))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.message", is("Username is already taken. Try another username.")));
        mockMvc.perform(get("/register").contentType(MediaType.APPLICATION_JSON).content(wrongJsonBody))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.message", is("Email address is already registered.")));
        userRepository.delete(userRepository.findFirstByUsername(testUser.getUsername()));
    }

    @Test
    public void checkUserLogin() throws Exception {
        userRepository.save(testUser);
        ObjectMapper mapper = new ObjectMapper();
        String jsonBody = mapper.writeValueAsString(testUser);
        User wrongTestUser = new User(testUser.getUsername(), testUser.getPassword(), testUser.getEmail(),
                testUser.getScore(), testUser.getRegisterDate());
        wrongTestUser.setPassword("inc0rrectP@ssw0rd");
        String wrongJsonBody = mapper.writeValueAsString(wrongTestUser);
        // wrong pass
        mockMvc.perform(get("/login").contentType(MediaType.APPLICATION_JSON).content(wrongJsonBody))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.message", is("false")));
        // correct pass
        mockMvc.perform(get("/login").contentType(MediaType.APPLICATION_JSON).content(jsonBody))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.message", is("true")));
        userRepository.delete(testUser);
        // user does not exist
        mockMvc.perform(get("/login").contentType(MediaType.APPLICATION_JSON).content(jsonBody))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.message", is("false")));
    }

    @Test
    public void checkDelete() throws Exception {
        userRepository.save(testUser);
        ObjectMapper mapper = new ObjectMapper();
        String jsonBody = mapper.writeValueAsString(testUser);
        User wrongTestUser = new User(testUser.getUsername(), testUser.getPassword(), testUser.getEmail(),
                testUser.getScore(), testUser.getRegisterDate());
        wrongTestUser.setPassword("inc0rrectP@ssw0rd");
        String wrongJsonBody = mapper.writeValueAsString(wrongTestUser);
        // wrong pass
        mockMvc.perform(get("/delete").contentType(MediaType.APPLICATION_JSON).content(wrongJsonBody))
                .andExpect(status().isUnauthorized())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.message", is("false")));
        // correct pass
        mockMvc.perform(get("/delete").contentType(MediaType.APPLICATION_JSON).content(jsonBody))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.message", is("true")));
        // check user is really deleted
        mockMvc.perform(get("/login").contentType(MediaType.APPLICATION_JSON).content(jsonBody))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.message", is("false")));
    }

    @Test
    public void checkUpdatePass() throws Exception {
        userRepository.save(testUser);
        ObjectMapper mapper = new ObjectMapper();
        User wrongTestUser = new User(testUser.getUsername(), testUser.getPassword(), testUser.getEmail(),
                testUser.getScore(), testUser.getRegisterDate());
        wrongTestUser.setPassword("inc0rrectP@ssw0rd");
        String wrongJsonBody = mapper.writeValueAsString(wrongTestUser);
        String jsonBody = mapper.writeValueAsString(testUser);
        String newpass = "newpass";
        // wrong oldpass:
        mockMvc.perform(get(String.format("/updatepass?newpass=%s", newpass))
                .contentType(MediaType.APPLICATION_JSON).content(wrongJsonBody))
                .andExpect(status().isUnauthorized())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.message", is("false")));
        // correct oldpass :
        mockMvc.perform(get(String.format("/updatepass?newpass=%s", newpass))
                .contentType(MediaType.APPLICATION_JSON).content(jsonBody))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.message", is("true")));
        // check to see if pass has indeed been changed
        testUser.setPassword(newpass);
        jsonBody = mapper.writeValueAsString(testUser);
        mockMvc.perform(get("/login").contentType(MediaType.APPLICATION_JSON).content(jsonBody))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.message", is("true")));
        userRepository.delete(testUser);
    }

    @Test
    public void checkGetAllActions() throws Exception {
        actionRepository.save(testAction);
        mockMvc.perform(get("/actions"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"));
        actionRepository.delete(testAction);
    }

    @Test
    public void checkTakeAction() throws Exception {
        userRepository.save(testUser);
        actionRepository.save(testAction);
        int oldPoints = testUser.getScore();
        ObjectMapper mapper = new ObjectMapper();
        String jsonBody = mapper.writeValueAsString(testAction);
        mockMvc.perform(get(String.format("/takeaction?username=%s",
                testUser.getUsername())).contentType(MediaType.APPLICATION_JSON).content(jsonBody))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.message", is("true")));
        testUser = userRepository.findFirstByUsername(testUser.getUsername());
        assertEquals(oldPoints + testAction.getPoints(), testUser.getScore());
        userRepository.delete(testUser);
        actionRepository.delete(testAction);
    }

    @Test
    public void checkUserEmail() throws Exception {
        userRepository.save(testUser);
        mockMvc.perform(get("/email?username="+testUser.getUsername()))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.message", is(testUser.getEmail())));
        userRepository.delete(testUser);
    }

    @Test
    public void checkMultipleActions() throws Exception {
        userRepository.save(testUser);
        actionRepository.save(testAction);
        actionRepository.save(additionalTestAction);
        List<Action> actionList = new ArrayList<>();
        actionList.add(testAction);
        actionList.add(additionalTestAction);
        int oldPoints = testUser.getScore();
        ObjectMapper mapper = new ObjectMapper();
        String jsonBody = mapper.writeValueAsString(actionList);
        mockMvc.perform(get(String.format("/takeactions?username=%s",
                testUser.getUsername())).contentType(MediaType.APPLICATION_JSON).content(jsonBody))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.message", is("true")));
        testUser = userRepository.findFirstByUsername(testUser.getUsername());
        assertEquals(oldPoints + testAction.getPoints() + additionalTestAction.getPoints(), testUser.getScore());
        userRepository.delete(testUser);
        actionRepository.delete(testAction);
        actionRepository.delete(additionalTestAction);
    }

    @Test
    public void checkUpdateEmail() throws Exception {
        userRepository.save(testUser);
        ObjectMapper mapper = new ObjectMapper();
        User wrongTestUser = new User(testUser.getUsername(), testUser.getPassword(), testUser.getEmail(),
                testUser.getScore(), testUser.getRegisterDate());
        wrongTestUser.setPassword("inc0rrectP@ssw0rd");
        String wrongJsonBody = mapper.writeValueAsString(wrongTestUser);
        String jsonBody = mapper.writeValueAsString(testUser);
        String newEmail = "new@new.new";
        // wrong pass:
        mockMvc.perform(get(String.format("/updateEmail?newEmail=%s", newEmail))
                .contentType(MediaType.APPLICATION_JSON).content(wrongJsonBody))
                .andExpect(status().isUnauthorized())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.message", is("false")));
        // correct pass:
        mockMvc.perform(get(String.format("/updateEmail?newEmail=%s", newEmail))
                .contentType(MediaType.APPLICATION_JSON).content(jsonBody))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.message", is("true")));
        testUser = userRepository.findFirstByUsername(testUser.getUsername());
        // check to see if pass has indeed been changed
        assertEquals(newEmail, testUser.getEmail());
        userRepository.delete(testUser);
    }

    @Test
    public void checkResetPoints() throws Exception {
        userRepository.save(testUser);
        ObjectMapper mapper = new ObjectMapper();
        User wrongTestUser = new User(testUser.getUsername(), testUser.getPassword(), testUser.getEmail(),
                testUser.getScore(), testUser.getRegisterDate());
        wrongTestUser.setPassword("inc0rrectP@ssw0rd");
        String wrongJsonBody = mapper.writeValueAsString(wrongTestUser);
        String jsonBody = mapper.writeValueAsString(testUser);
        // wrong pass:
        mockMvc.perform(get("/reset").contentType(MediaType.APPLICATION_JSON).content(wrongJsonBody))
                .andExpect(status().isUnauthorized())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.message", is("false")));
        // correct pass:
        mockMvc.perform(get("/reset").contentType(MediaType.APPLICATION_JSON).content(jsonBody))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.message", is("true")));
        testUser = userRepository.findFirstByUsername(testUser.getUsername());
        // check to see if pass has indeed been changed
        assertEquals(0, testUser.getScore());
        userRepository.delete(testUser);
    }
    public void checkTop50Users() throws Exception {
        userRepository.save(testUser);
        mockMvc.perform(get("/top50"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"));
        userRepository.delete(testUser);
    }

    @Test
    public void checkChangeAnonymous() throws Exception {
        userRepository.save(testUser);
        ObjectMapper mapper = new ObjectMapper();
        User wrongTestUser = new User(testUser.getUsername(), testUser.getPassword(), testUser.getEmail(),
                testUser.getScore(), testUser.getRegisterDate());
        wrongTestUser.setPassword("inc0rrectP@ssw0rd");
        String wrongJsonBody = mapper.writeValueAsString(wrongTestUser);
        String jsonBody = mapper.writeValueAsString(testUser);
        String newAnonymous = "true";
        // wrong pass:
        mockMvc.perform(get(String.format("/changeAnonymous?anonymous=%s", newAnonymous))
                .contentType(MediaType.APPLICATION_JSON).content(wrongJsonBody))
                .andExpect(status().isUnauthorized())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.message", is("false")));
        // correct pass :
        mockMvc.perform(get(String.format("/changeAnonymous?anonymous=%s", newAnonymous))
                .contentType(MediaType.APPLICATION_JSON).content(jsonBody))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.message", is("true")));
        // check to see if anonymous has indeed been changed
        testUser = userRepository.findFirstByUsername(testUser.getUsername());
        assertEquals(newAnonymous, String.valueOf(testUser.getAnonymous()));
        userRepository.delete(testUser);
    }

    @Test
    public void checkGetPresets() throws Exception {
        //TODO
    }

    @Test
    public void checkAddPreset() throws Exception {
        //TODO
    }

    @Test
    public void checkDeletePreset() throws Exception {
        //TODO
    }

    @Test
    public void checkExecutePreset() throws Exception {
        //TODO
    }
}
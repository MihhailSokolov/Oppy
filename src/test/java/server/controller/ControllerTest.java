package server.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import server.model.User;
import server.model.UserRepository;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ControllerTest {

    MockMvc mockMvc;

    User testUser;

    @Autowired
    Controller controller;

    @Autowired
    UserRepository userRepository;

    @Before
    public void setup() {
        this.mockMvc = standaloneSetup(this.controller).build();
        testUser = new User("oppy123",
                "0d6be69b264717f2dd33652e212b173104b4a647b7c11ae72e9885f11cd312fb",
                "oppy%40gmail.com",
                42);
        if(userRepository.findFirstByUsername(testUser.getUsername())!=null)
            userRepository.delete(userRepository.findFirstByUsername(testUser.getUsername()));
    }

    @Test
    public void checkTestWithoutMsg() throws Exception {
        mockMvc.perform(get("/check").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("Server: Successful server response")));
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
                .andExpect(content().string("true"));

        // and username in db
        userRepository.save(testUser);
        mockMvc.perform(get("/nameavailable?username=" + testUser.getUsername()))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));

        userRepository.delete(testUser);

    }

    @Test
    public void checkUserRegister() throws Exception {
        if(userRepository.findFirstByUsername(testUser.getUsername())!=null)
            userRepository.delete(userRepository.findFirstByUsername(testUser.getUsername()));
        mockMvc.perform(get(String.format("/register?username=%s&pass=%s&email=%soppy%%40gmail.com",
                testUser.getUsername(), testUser.getPassword(), testUser.getEmail())))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));

        mockMvc.perform(get(String.format("/register?username=%s&pass=%s&email=%soppy%%40gmail.com",
                testUser.getUsername(), testUser.getPassword(), testUser.getEmail())))
                .andExpect(status().is(500));

        userRepository.delete(userRepository.findFirstByUsername(testUser.getUsername()));
    }

    @Test
    public void checkUserLogin() throws Exception {
        userRepository.save(testUser);
        mockMvc.perform(get(String.format("/login?username=%s&pass=%s", testUser.getUsername(), testUser.getPassword())))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
        userRepository.delete(testUser);
        mockMvc.perform(get(String.format("/login?username=%s&pass=%s", testUser.getUsername(), testUser.getPassword())))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));
    }

    @Test
    public void checkUserScore() throws Exception {
        userRepository.save(testUser);
        mockMvc.perform(get(String.format("/score?username=%s", testUser.getUsername())))
                .andExpect(status().isOk())
                .andExpect(content().string("42"));
        userRepository.delete(testUser);
    }
}
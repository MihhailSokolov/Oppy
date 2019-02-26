package server.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ControllerTest {

    MockMvc mockMvc;

    @Autowired
    Controller controller;

    @Before
    public void setup() {
        this.mockMvc = standaloneSetup(this.controller).build();
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
        mockMvc.perform(get("/nameavailable?username=oppy123"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    public void checkUserLogin() throws Exception {
        mockMvc.perform(get("/login?username=oppy123&pass=0d6be69b264717f2dd33652e212b173104b4a647b7c11ae72e9885f11cd312fb"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    public void checkUserRegister() throws Exception {
        mockMvc.perform(get("/register?username=oppy123"
                +"&pass=0d6be69b264717f2dd33652e212b173104b4a647b7c11ae72e9885f11cd312fb&email=oppy%40gmail.com"))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }
}

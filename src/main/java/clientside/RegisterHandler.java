package clientside;

import com.google.common.hash.Hashing;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;


public class RegisterHandler {
    String username;
    String email;
    String password;

    /**
     * Stores user's entered information from the login page.
     *
     * @param email email address of user
     * @param pass  password
     */
    public RegisterHandler(String name, String email, String pass) {
        this.username = name;
        this.email = email;
        this.password = Hashing.sha256().hashString(pass, StandardCharsets.UTF_8).toString();
    }

    public String sendRegister(){
        final String uri = this.toString();
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);
        return result;
    }

    @Override
    public String toString() {
        return String.format("http://oppy-project.herokuapp.com/register?username=%s&pass=%s&email=%s",
                this.username,
                this.password,
                this.email);
    }
}

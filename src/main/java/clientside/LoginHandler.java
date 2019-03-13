package clientside;

import com.google.common.hash.Hashing;

import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;

public class LoginHandler {
    String username;
    String password;
    boolean rememberMe;


    /**
     * Stores users entered information from the login page.
     *
     * @param user text entered in the username box
     * @param pwd  plain text entered in the password box
     * @param rem  rememberMe checkbox
     */
    public LoginHandler(String user, String pwd, boolean rem) {
        this.username = user;
        this.password = Hashing.sha256().hashString(pwd, StandardCharsets.UTF_8).toString();
        this.rememberMe = rem;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    /**
     * Method for sending login.
     * @return String response
     */
    public String sendLogin() {
        final String uri = this.toString();
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);
        return result;
    }

    @Override
    public String toString() {
        return "https://oppy-project.herokuapp.com/login?username=" + this.username + "&pass=" + this.password;
    }

}

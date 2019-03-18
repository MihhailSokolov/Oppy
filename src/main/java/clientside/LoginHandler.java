package clientside;

import com.google.common.hash.Hashing;

import org.springframework.web.client.RestTemplate;


import java.nio.charset.StandardCharsets;

public class LoginHandler {
    String username;
    String password;
    boolean rememberMe;
    private String uri;
    private String loginParams = "login?username=%s&pass=%s";


    /**
     * Stores users entered information from the login page.
     * @param user text entered in the username box
     * @param pwd  plain text entered in the password box
     * @param rem  rememberMe checkbox
     */
    public LoginHandler(String user, String pwd, boolean rem) {
        this.username = user;
        this.password = Hashing.sha256().hashString(pwd, StandardCharsets.UTF_8).toString();
        this.rememberMe = rem;
        this.uri = "https://oppy-project.herokuapp.com/";
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getLoginParams() {
        return loginParams;
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
        return this.uri +  String.format(loginParams, this.username, this.password);
    }

}

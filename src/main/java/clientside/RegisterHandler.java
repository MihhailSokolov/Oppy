package clientside;

import com.google.common.hash.Hashing;

import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;


public class RegisterHandler {
    String username;
    String email;
    String password;
    private String uri = "https://oppy-project.herokuapp.com/";
    private String registerParams = "register?username=%s&pass=%s&email=%s";
    private String availabilityParams = "nameavailable?username=%s";

    /**
     * Stores user's entered information from the register page.
     * @param name username of the user.
     * @param email email address of user
     * @param pass  password
     */
    public RegisterHandler(String name, String email, String pass) {
        this.username = name;
        this.email = email;
        this.password = Hashing.sha256().hashString(pass, StandardCharsets.UTF_8).toString();
    }

    /**
     * This constructor is used only for checking username availability.
     * @param username username of the user
     */
    public RegisterHandler(String username) {
        this.username = username;
    }

    /**
     * Send login request.
     * @return String response msg.
     */
    public String sendRegister() {
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(this.toString(), String.class);
        return result;
    }

    @Override
    public String toString() {
        return String.format(this.uri + String.format(registerParams,
                this.username,
                this.password,
                this.email));
    }

    /**
     * Send availability check.
     * @return String response (always true)
     */
    public String sendAvailabilityCheck() {
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(this.uri
                + String.format(availabilityParams, this.getUsername()), String.class);
        return result;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getRegisterParams() {
        return this.registerParams;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getAvailabilityParams() {
        return availabilityParams;
    }

}

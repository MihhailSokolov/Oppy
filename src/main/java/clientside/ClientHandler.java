package clientside;
import com.google.common.hash.Hashing;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import server.model.Response;
import server.model.User;

import java.nio.charset.StandardCharsets;

public class ClientHandler {
    private User user;
    private String baseUrl =  "http://localhost:8080/";
    private RestTemplate restTemplate = new RestTemplate();
    ResponseEntity<String> responseEntity = null;
//    private final String loginParams = "login?username=%s&pass=%s";
//    private final String registerParams = "register?username=%s&pass=%s&email=%s";
//    private final String availabilityParams = "nameavailable?username=%s";


    public ClientHandler(User user){
        this.user = user;
        user.setPassword(hash(user.getPassword()));
    }

    public ClientHandler(){ }

    public enum Path{
        REGISTER{
            public String toString(){
                return "register";
            }
        }, AVAILABILITY{
            public String toString(){
                return "nameavailable?username=%s";
            }
        }
    }

    public String register(){
        if(this.user != null){
            responseEntity = this.post(this.baseUrl + Path.REGISTER.toString(), user);
        }
        return new JSONObject(responseEntity.getBody()).getString("message");
    }

    public String checkAvailability(String username){
        responseEntity = this.get(this.baseUrl + String.format(Path.AVAILABILITY.toString(), username));
        return new JSONObject(responseEntity.getBody()).getString("message");
    }

    private ResponseEntity<String> post(String url, Object obj){
        return restTemplate.postForEntity(url, obj, String.class);
    }

    private ResponseEntity<String> get(String url){
        return restTemplate.getForEntity(url, String.class);
    }

    public static String hash(String pwd) {
        return Hashing.sha256().hashString(pwd, StandardCharsets.UTF_8).toString();
    }
}

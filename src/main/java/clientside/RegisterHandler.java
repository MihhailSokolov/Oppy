package clientside;

import com.google.common.hash.Hashing;

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
        this.password = pass;
    }

    @Override
    public String toString() {
        return String.format("/register?username=%s&pass=%s&email=%s",
                this.username,
                Hashing.sha256().hashString(this.password, StandardCharsets.UTF_8).toString(),
                this.email);
    }
}

package clientside;

import com.google.common.hash.Hashing;

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
        this.password = pwd;
        this.rememberMe = rem;
    }

    @Override
    public String toString() {
        return "/login?username=" + this.username + "&pass="
                + Hashing.sha256().hashString(this.password, StandardCharsets.UTF_8).toString();
    }
}

package clientside;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class is used to check the data coming from the registerPage before sending it.
 */
public class RegisterCheck {

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    /**
     * this method validates emails using regular expressions.
     * Source:https://stackoverflow.com/questions/8204680/java-regex-email/13013056#13013056
     * @param emailStr The String to be checked against the regular expression representing a valid email
     * @return returns true if a valid email has been provided in most cases
     */
    public static boolean validate(String emailStr) {

        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
        return matcher.find();
    }

    /**
     * This method checks whether a username has been given instead of just an empty string.
     * @param userName The username to be checked
     * @return returns true if a userName is longer than 0 chars
     */
    public static boolean checkUser(String userName) {

        if (userName.length() == 0) {
            return false;
        }
        return true;
    }

    /**
     * This method checks whether an email is both valid and not null.
     * @param email Email to be checked.
     * @return true if a valid email has been provided false otherwise.
     */
    public static boolean checkEmail(String email) {

        if (email.length() != 0 && (validate(email))) {
            return true;
        }
        return false;
    }

    /**
     * this method checks whether the two provided passwords are longer than 8 chars and are equal to each other.
     * @param passwd the first passwd to be checked.
     * @param confpasswd the second passwd to be checked.
     * @return true if both passwords are 8 or more long and are equal to each other, false otherwise.
     */
    public static boolean checkPassword(String passwd, String confpasswd) {

        if (passwd.length() >= 8 && confpasswd.length() >= 8 && passwd.equals(confpasswd)) {
            return true;
        }
        return false;
    }
}

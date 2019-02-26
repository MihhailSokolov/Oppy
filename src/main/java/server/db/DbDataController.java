package server.db;

// Class which will contain all the methods for querying the database
public class DbDataController {
    // Check if provided user details are correct
    public static boolean isUserAuthenticated(String username, String pass){
        // Yet to be implemented
        return true;
    }

    public static  boolean isUsernameAvailable(String username){
        // Yet to be implemented
        return true;
    }

    public static String createNewUser(String username, String password, String email){
        // Yet to be implemented
        String message = "";
        // If user was successfully registered, return empty message
        // Otherwise message contains explanation of error
        return message;
    }
}

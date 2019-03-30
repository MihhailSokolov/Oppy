package clientside;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Remember {
    /**
     * This class gives the functionaility where locally a username will be stored to ease loging in.
     *
     * @param userName The username to be saved for the next login.
     * @param filePath The filepath to where the file is where the name is written.
     * @throws IOException Since we're dealing with IO unfortunately I've had to add this exception.
     */
    public static void storeUser(String userName, String filePath) throws IOException {
        FileWriter write = new FileWriter(filePath, false);
        PrintWriter print = new PrintWriter(write);
        print.printf("%s", userName);
        print.close();
    }
}

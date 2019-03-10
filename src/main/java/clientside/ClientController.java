package clientside;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Class for client-side controller.
 */
public class ClientController {
    /**
     * Sends login request. Possibly deprecated class.
     * @param param login params
     */
    public static void sendLogin(String param) {
        System.out.println(param);
        String text = executeGet("http://oppy-project.herokuapp.com" + param, "");
        if (text.equals("true")) {
            //go to main page
        }
    }

    /**
     * Method for executing get requests.
     * @param targetUrl the URL of the target server
     * @param urlParameters URL parameters
     * @return String server response
     */
    public static String executeGet(String targetUrl, String urlParameters) {
        HttpURLConnection connection = null;

        try {
            //Create connection
            URL url = new URL(targetUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);

            //Send request
            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.close();

            //Get Response
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            StringBuffer response = new StringBuffer(); // or StringBuffer if Java version 5+
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\n');
            }
            rd.close();
            System.out.println(response.toString());
            return response.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }


}

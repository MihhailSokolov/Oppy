package clientside;


import javafx.scene.image.Image;
import org.apache.commons.io.FileUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;

public class ImageHandler {
    static String getBase64Str(File imgFile) {
        String encodedString = "";
        try {
            byte[] imgBytes = FileUtils.readFileToByteArray(imgFile);
            encodedString = Base64.getEncoder().encodeToString(imgBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return encodedString;
    }

    static Image decodeToImg(String encodedStr) {
        byte[] imgBytes = Base64.getDecoder().decode(encodedStr);
        return new Image(new ByteArrayInputStream(imgBytes));
    }
}

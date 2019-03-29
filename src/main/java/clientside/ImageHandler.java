package clientside;


import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import org.apache.commons.io.FileUtils;
import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;

public class ImageHandler {
    static String getBase64Str(Image img) {
//        File outputFile = new File("src/main/resources/pfp.png");
        BufferedImage bImage = SwingFXUtils.fromFXImage(img, null);
        Scalr.resize(bImage, 150);
        final ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ImageIO.write(bImage, "png", os);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Base64.getEncoder().encodeToString(os.toByteArray());

//        try {
//            ImageIO.write(bImage, "png", outputFile);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        String encodedString = "";
//        try {
//            byte[] imgBytes = FileUtils.readFileToByteArray(outputFile);
//            encodedString = Base64.getEncoder().encodeToString(imgBytes);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return encodedString;
    }

    static Image decodeToImg(String encodedStr) {
        byte[] imgBytes = Base64.getDecoder().decode(encodedStr);
        return new Image(new ByteArrayInputStream(imgBytes));
    }
}

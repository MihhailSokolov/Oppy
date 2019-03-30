package clientside;

import org.imgscalr.Scalr;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import javax.imageio.ImageIO;


public class ImageHandler {
    static String getBase64Str(BufferedImage binImage) {
        Scalr.resize(binImage, 150);
        final ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ImageIO.write(binImage, "png", os);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Base64.getEncoder().encodeToString(os.toByteArray());

    }

    static BufferedImage decodeToImg(String encodedStr) {
        byte[] imgBytes = Base64.getDecoder().decode(encodedStr);
        try {
            return ImageIO.read(new ByteArrayInputStream(imgBytes));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}

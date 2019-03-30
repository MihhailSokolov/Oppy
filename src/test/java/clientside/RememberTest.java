package clientside;

import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class RememberTest {
    @Test
    public void rememberUserTest() throws IOException {
        Remember.storeUser("testName", "testRememberFile.txt");
        File file = new File("testRememberFile.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String remUserName = br.readLine();
        Assert.assertEquals(remUserName, "testName");
    }
}

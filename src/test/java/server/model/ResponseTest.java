package server.model;

import org.junit.Test;
import static org.junit.Assert.*;

public class ResponseTest {
    @Test
    public void getMessageTest() {
        Response res = new Response("Hello");
        assertEquals("Hello", res.getMessage());
    }

    @Test
    public void getNullMessageTest() {
        Response res = new Response(null);
        assertNull(res.getMessage());
    }

    @Test
    public void constructorTest() {
        Response res = new Response("Hello");
        assertNotNull(res);
    }
}

package server.model;

import org.junit.Test;
import static org.junit.Assert.*;

public class ResponseTest {

    @Test
    public void constructorTest() {
        Response res = new Response("Hello");
        assertNotNull(res);
    }

    @Test
    public void getMessageTest() {
        Response res = new Response("Hello");
        assertEquals("Hello", res.getMessage());
    }

    @Test
    public void getIntMessageTest() {
        Response res = new Response(10);
        assertEquals("10", res.getMessage());
    }

    @Test
    public void getBoolMessageTest() {
        Response res = new Response(true);
        assertEquals("true", res.getMessage());
    }
}

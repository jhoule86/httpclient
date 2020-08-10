package org.jhoule.ws.client.http;

import org.junit.Test;
import static org.junit.Assert.*;

import java.net.URL;

import org.jhoule.ws.client.URLResource;

public class HttpClientTest {

    @Test
    public void pingGoogle()
    {
        int response = -1;
        HTTPClient client = null;
        try {
            URLResource resource = new URLResource(new URL("https://www.google.com"));
            client = new NativeHTTPClient();
            client.setResource(resource);
            client.open();
            response = client.doRequest(HTTPClient.METHOD.GET, null);

            byte[] payload = client.getLastResponsePayload();
            assertNotNull("payload should not be null", payload);
            assertTrue("Payload should not be empty", payload.length > 0);
        } catch (Exception e)
        {
            fail(e.getMessage());
        } finally
        {
            client.close();
        }
        assertEquals(200, response);
    }
    
}

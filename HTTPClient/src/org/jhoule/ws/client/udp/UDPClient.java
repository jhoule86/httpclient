package org.jhoule.ws.client.udp;

import org.jhoule.ws.client.ClientException;
import org.jhoule.ws.client.WSClient;

/**
 * Created by jhoule on 10/25/2014.
 */
public interface UDPClient extends WSClient{

    public void sendBytes(byte[] aRequestPayload) throws ClientException;

}

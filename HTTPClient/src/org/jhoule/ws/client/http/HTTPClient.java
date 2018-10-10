/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jhoule.ws.client.http;

import org.jhoule.ws.client.ClientException;
import org.jhoule.ws.client.WSClient;

import java.util.Map;

/**
 * @author jhoule
 */
public interface HTTPClient extends WSClient {

    public static enum METHOD
    {
        GET, POST, PUT, DELETE, HEAD, TRACE, CONNECT
    }

    public int getLastResponseCode();

    public byte[] getLastResponsePayload();

    public String getLastError();

    public boolean supportsMethod(METHOD aMethod);

//    public void setMethod(METHOD aMethod) throws ClientException;

//    public boolean open(METHOD aMethod) throws ClientException;

    public int doRequest(METHOD aMethod, byte[] aRequestPayload) throws ClientException;

    public int doRequest(METHOD aMethod, byte[] aRequestPayload, Map<String, String> aHeaders) throws ClientException;
}

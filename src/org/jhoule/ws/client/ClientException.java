package org.jhoule.ws.client;

/**
 * Created by jhoule on 10/25/2014.
 */
public class ClientException extends Exception {

    public ClientException(Throwable t)
    {
        super(t);
    }

    public ClientException(String s)
    {
        super(s);
    }
}

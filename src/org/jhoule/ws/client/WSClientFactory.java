/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhoule.ws.client;

import java.util.LinkedHashMap;
import java.util.Map;
import org.jhoule.ws.client.http.NativeHTTPClient;

/**
 *
 * @author jhoule
 */
public class WSClientFactory
{

    final static Map<Class, Class> DEFAULT_CLIENT_CLASSES;

    static
    {
        DEFAULT_CLIENT_CLASSES = new LinkedHashMap<Class, Class>();

        DEFAULT_CLIENT_CLASSES.put(URLResource.class, NativeHTTPClient.class);
    }

    public static WSClient getDefaultClient(RemoteResource aResourceInfo)
    {
        if (aResourceInfo == null)
        {
            return null;
        }

        Class resourceClass = aResourceInfo.getClass();
        Class clientClass = DEFAULT_CLIENT_CLASSES.get(resourceClass);
        while (clientClass == null && !(resourceClass == null || resourceClass.equals(Object.class)))
        {
            resourceClass = resourceClass.getSuperclass();
            clientClass = DEFAULT_CLIENT_CLASSES.get(resourceClass);
        }

        if (clientClass != null)
        {
            try
            {
                WSClient client =  (WSClient) clientClass.newInstance();
                client.setResource(aResourceInfo);
            } catch (Throwable t)
            {
                System.err.println("couldn't instantiate client");
            }
        }

        return null;
    }
}

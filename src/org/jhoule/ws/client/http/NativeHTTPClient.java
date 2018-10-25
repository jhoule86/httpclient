/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jhoule.ws.client.http;

import org.jhoule.ws.client.ClientException;
import org.jhoule.ws.client.RemoteResource;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author jhoule
 */
public class NativeHTTPClient extends HTTPClientImpl {

    private HttpURLConnection mConnection;

    private static final Set<METHOD> supportedMethods;

    static
    {
        supportedMethods = new HashSet<METHOD>();
        supportedMethods.add(METHOD.GET);
        supportedMethods.add(METHOD.POST);

        // TODO: fill this out as support is added/required
    }

    public NativeHTTPClient() {
        super();
    }

    public NativeHTTPClient(RemoteResource aResource) {
        super(aResource);
    }

    @Override
    public Set<METHOD> getSupportedMethods() {
        return supportedMethods;
    }

    @Override
    public boolean open() throws ClientException {

        if (mConnection != null) {
            System.err.println("Warning: previous connection was not explicitly closed");
            close();
        }

        if (mResourceDetails == null) {
            throw new IllegalStateException("Cannot open connection: resource details have not been specified");
        }

        URL url = mResourceDetails.getURL();
        if (url == null) {
            throw new IllegalStateException("Cannot open connection: resource details do not contain a URL");
        }

        try {
            URLConnection conn = url.openConnection();

            if (conn instanceof HttpURLConnection) {
                mConnection = (HttpURLConnection) conn;
                mConnection.setConnectTimeout(20000);
                mConnection.setReadTimeout(20000);

                return true;
            }

        } catch (Exception e)
        {
            throw new ClientException(e);
        }

        return false;
    }

    @Override
    public boolean close() {
        if (mConnection == null) {
            return false;
        }

        mConnection.disconnect();
        mConnection = null;
        return true;
    }

    @Override
    public boolean isConnected() {
        return (mConnection != null);
    }

    public int doRequest(METHOD aMethod, byte[] aRequestPayload, Map<String, String> aHeaders) throws ClientException
    {
        int res = -1;

        if (mConnection == null)
        {
            throw new IllegalStateException("Not connected!");
        }

        METHOD m = aMethod;
        if (m == null)
        {
            m = METHOD.GET;
        }

        InputStream err = null;
        InputStream inbound = null;

        try {

            if (aHeaders != null)
            {
                for (String key: aHeaders.keySet())
                {
                    mConnection.setRequestProperty(key, aHeaders.get(key));
                }
            }

            mConnection.setDoInput(true);
            mConnection.setRequestMethod(m.toString());

            byte[] request = aRequestPayload;
            if (request != null) {
                OutputStream outbound = null;
                try {
                    mConnection.setDoOutput(true);

                    outbound = mConnection.getOutputStream();
                    outbound.write(request);
                    outbound.flush();
                }
                finally
                {
                    if (outbound != null)
                    {
                        outbound.close();
                    }
                }
            }

            try
            {
                err = mConnection.getErrorStream();
                inbound = mConnection.getInputStream();
            }
            catch (Exception e)
            {
                System.err.println("stream problem:" + e.toString());
            }

            res = mConnection.getResponseCode();

            if (err != null && (err.available() > 0))
            {
                byte[] buff = new byte[255];
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                int amt = err.read(buff);
                while (amt > 0) {
                    bos.write(buff, 0, amt);
                    amt = err.read(buff);

                }

                err.close();
                mLastError = new String(bos.toByteArray());
            } else
            {
                mLastError = null;
            }

            if (inbound != null && inbound.available() > 0)
            {
                byte[] buff = new byte[255];
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                int amt = inbound.read(buff);
                while (amt > 0) {
                    bos.write(buff, 0, amt);
                    amt = inbound.read(buff);
                }

                mLastResponsePayLoad = bos.toByteArray();

                inbound.close();
            }

        } catch (Throwable t)
        {
            t.printStackTrace();
        }
        finally
        {
            try {
                if (inbound != null) {
                    inbound.close();
                }

                if (err != null) {
                    err.close();
                }
            }
            catch (IOException e)
            {
                System.err.println(e.toString());
            }
        }

        return res;
    }
}

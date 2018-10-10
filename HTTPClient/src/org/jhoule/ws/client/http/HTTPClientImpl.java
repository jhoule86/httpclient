/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jhoule.ws.client.http;

import org.jhoule.ws.client.ClientException;
import org.jhoule.ws.client.RemoteResource;
import org.jhoule.ws.client.URLResource;

import java.util.Set;

/**
 * @author jhoule
 */
public abstract class HTTPClientImpl implements HTTPClient {

    protected URLResource mResourceDetails;

    protected int mLastResponseCode;
    protected byte[] mLastResponsePayLoad;
    protected String mLastError;

    // protected METHOD mMethod = METHOD.GET;

    public HTTPClientImpl() {

    }

    public HTTPClientImpl(RemoteResource areRemoteResource) {
        this();
        setResource(areRemoteResource);
    }

    @Override
    public void setResource(RemoteResource aResource) {
        if (!(aResource instanceof URLResource)) {
            throw new IllegalArgumentException("HTTPClient only supports URL Resources");
        }

        mResourceDetails = (URLResource) aResource;
    }

    @Override
    public int getLastResponseCode()
    {
        return mLastResponseCode;
    }

    @Override
    public byte[] getLastResponsePayload() {
        return mLastResponsePayLoad;
    }

    @Override
    public String getLastError() {
        return mLastError;
    }

    abstract Set<METHOD> getSupportedMethods();

    @Override
    public boolean supportsMethod(METHOD aMethod)
    {
        Set set = getSupportedMethods();
        return (set != null && set.contains(aMethod));
    }

    @Override
    public int doRequest(METHOD aMethod, byte[] aRequestPayload) throws ClientException {
        return doRequest(aMethod, aRequestPayload, null);
    }

    //    @Ooverride
//    public void setMethod(METHOD aMethod) throws ClientException
//    {
//        if (! supportsMethod(aMethod))
//        {
//            throw new ClientException("The method" + aMethod.toString() + " is not supported.");
//        }
//
//        mMethod = aMethod;
//    }

}

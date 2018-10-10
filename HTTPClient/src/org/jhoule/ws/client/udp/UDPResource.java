/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jhoule.ws.client.udp;

import org.jhoule.ws.client.RemoteResource;

/**
 *
 * @author jhoule
 */
public class UDPResource implements RemoteResource
{
    private final String mHost;
    private final int mPort;

    public UDPResource()
    {
        this(null, -1);
    }

    public UDPResource(final String aHost, final int aPort)
    {
        this.mHost = aHost;
        this.mPort = aPort;
    }

    public  String getHost()
    {
        return mHost;
    }

    public int getPort()
    {
        return mPort;
    }
}

package org.jhoule.ws.client.udp;

import org.jhoule.ws.client.RemoteResource;
import org.jhoule.ws.client.URLResource;

/**
 * Created by jhoule on 10/25/2014.
 */
public abstract class UDPClientImpl implements UDPClient {

    protected UDPResource mResourceDetails;

    @Override
    public void setResource(RemoteResource aResource) {
        if (!(aResource instanceof UDPResource)) {
            throw new IllegalArgumentException("UDPClientImpl only supports UDP Resources");
        }

        mResourceDetails = (UDPResource) aResource;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jhoule.ws.client;

import java.net.URL;

/**
 *
 * @author jhoule
 */
public class URLResource implements RemoteResource
{
    private final URL mURL;

    public URLResource()
    {
        this.mURL = null;
    }
    
    public URLResource(final URL aURL)
    {
        this.mURL = aURL;
    }

    public URL getURL()
    {
        return mURL;
    }
    
}

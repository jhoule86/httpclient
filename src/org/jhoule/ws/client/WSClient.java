/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jhoule.ws.client;

/**
 * Web Services client interface
 * @author jhoule
 */
public interface WSClient
{
    public void setResource(RemoteResource aResource);
    public boolean open() throws ClientException;
    // public boolean open(RemoteResource aResourceDetails);
    
    public boolean close();
    
    public boolean isConnected();
}

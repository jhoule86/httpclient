package org.jhoule.ws.client.udp;

import org.jhoule.ws.client.ClientException;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.URL;

/**
 * Created by jhoule on 10/25/2014.
 */
public class UnicastUDPClient extends UDPClientImpl {

    DatagramSocket mSocket;

    @Override
    public boolean open() throws ClientException {
        try {
            mSocket = new DatagramSocket();
            String host = mResourceDetails.getHost();
            int port = mResourceDetails.getPort();
            InetAddress addr = InetAddress.getByName(host);

            mSocket.connect(addr, port);
        } catch (Throwable t) {
            throw new ClientException(t);
        }

        return (mSocket != null);
    }

    @Override
    public boolean close() {
        if (!isConnected()) {
            System.err.println("Attempted to close socket that was missing or already closed");
            return false;
        }

        mSocket.close();
        return true;
    }

    @Override
    public boolean isConnected() {
        return (mSocket != null && mSocket.isConnected());
    }

    @Override
    public void sendBytes(byte[] aRequestPayload) throws ClientException {

        if (! isConnected())
        {
            throw new ClientException(new IllegalStateException("Not connected!"));
        }

        if (aRequestPayload == null || aRequestPayload.length <= 0) {
            System.err.println("skipping request due to attempt to send empty payload");

        }

        try {
            DatagramPacket packet = new DatagramPacket(aRequestPayload, aRequestPayload.length);
            mSocket.send(packet);
        } catch (Throwable t) {
            throw new ClientException(t);
        }
    }
}

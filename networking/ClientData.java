package networking;

/*
 * Ethan Lanier
 * Class that holds data from the received packets to be used in the response
 */

import java.net.InetAddress;
public class ClientData {
    private int port;
    private InetAddress address;

    public ClientData(int port, InetAddress address) {
        this.port = port;
        this.address = address;
    }

    public int getPort() {
        return port;
    }

    public InetAddress getAddress() {
        return address;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setAddress(InetAddress address) {
        this.address = address;
    }
}
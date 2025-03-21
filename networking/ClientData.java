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
}
package networking;
import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.SocketException;
import java.io.IOException;


/*
 * Ethan Lanier
 * Server class that listens for a packet and prints
 * out if the packet was recieved
 */

public class Server {
    private DatagramSocket socket; //socket to recieve the packet
    private byte[] buffer = new byte[1024]; //buffer for the packet

    public Server(DatagramSocket socket){
        this.socket = socket;
    }

    //this should return the packet recieved
    public packet receive() {
        while(true) { //server will keep listening for packets until it is stopped
            try {
                DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);
                socket.receive(datagramPacket);

                packet packet = new packet(null, 0, null, null, datagramPacket);
                System.out.println("Packet recieved: " + packet); //hooray
                return packet;
            } catch (IOException e) {
                e.printStackTrace();
            }
        } 
    }

    public void send(DatagramPacket packet) { //respond to client
        while(true) {
            try {
                int port = packet.getPort();
                buffer = "nice".getBytes();
                InetAddress address = packet.getAddress();
                DatagramPacket response = new DatagramPacket(buffer, buffer.length, address, port);
                socket.send(response);
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    public static void main(String[] args) throws SocketException {
        DatagramSocket socket = new DatagramSocket(1234);
        Server server = new Server(socket);
        server.receive();
    }
}

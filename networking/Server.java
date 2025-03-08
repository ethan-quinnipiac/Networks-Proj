package networking;

import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.SocketException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/*
 * Ethan Lanier
 * Server class that listens for a packet and prints
 * out if the packet was received
 */

public class Server {
    private DatagramSocket socket; //socket to receive the packet
    private byte[] buffer = new byte[1024]; //buffer for the packet

    public Server(DatagramSocket socket) {
        this.socket = socket;
    }

    public void receive() {
        while (true) { //server will keep listening for packets until it is stopped
            try {
                System.out.println("...");

                DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);
                socket.receive(datagramPacket);

                //deserialize the HACPacket object from the received datagramPacket
                ByteArrayInputStream byteStream = new ByteArrayInputStream(datagramPacket.getData());
                ObjectInputStream objStream = new ObjectInputStream(byteStream);
                HACPacket receivedPacket = (HACPacket) objStream.readObject();
                System.out.println("Packet received: " + receivedPacket);

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void send(DatagramPacket packet) { //respond to client
        while (true) {
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
        DatagramSocket socket = new DatagramSocket(9876);
        Server server = new Server(socket);
        System.out.println("Server started");
        server.receive();
    }
}

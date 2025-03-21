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

                //print details of the packet
                System.out.println("Packet received: " + receivedPacket);
                System.out.println("Node number: " + receivedPacket.getNodeNumber());
                System.out.println("Sequence number: " + receivedPacket.getSequenceNumber());
                System.out.println("Files: " + receivedPacket.getFiles());
                System.out.println("Port #" + datagramPacket.getPort());
                System.out.println("-------------------");

                send(datagramPacket.getAddress(), datagramPacket.getPort());

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

    }

    public void send(InetAddress clientAddress, int clientPort) { //respond to client
        try {
            byte[] responseBuffer = new byte[1024]; //buffer for the response
            responseBuffer = "nice".getBytes();
            DatagramPacket response = new DatagramPacket(responseBuffer, responseBuffer.length, clientAddress, clientPort);
            socket.send(response);
            System.out.println("Response Sent...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws SocketException {
        DatagramSocket socket = new DatagramSocket(9876);
        Server server = new Server(socket);
        System.out.println("Server started");
        server.receive();
    }
}

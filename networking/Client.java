package networking;

import java.net.DatagramPacket;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.SocketException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;

/*
 * Ethan Lanier
 * Client class that sends a packet to the server
 */

public class Client {
    
    private DatagramSocket socket;
    private InetAddress address;
    private byte[] buffer = new byte[1024];

    public Client(DatagramSocket socket, InetAddress address){
        this.socket = socket;
        this.address = address;
    }

    public void send() { //send a packet to the server
        try {
            //create a packet object
            HACPacket packetToSend = new HACPacket(1, 1, new ArrayList<String>(Arrays.asList("file1", "file2", "file3")));
                
            //serialize the packet object to a byte array
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            ObjectOutputStream objStream = new ObjectOutputStream(byteStream);
            objStream.writeObject(packetToSend);
            objStream.flush();
            byte[] packetBytes = byteStream.toByteArray();
                
            //create a DatagramPacket with the serialized packet
            DatagramPacket datagramPacket = new DatagramPacket(packetBytes, packetBytes.length, address, 9876);
            socket.send(datagramPacket);
            System.out.println("Sent");
                
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void receive() {
        try {
            byte[] buffer = new byte[4096]; // Increase buffer size for larger packets
            DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);
            socket.receive(datagramPacket);

            // Deserialize the HACPacket
            HACPacket receivedPacket = deserializeHACPacket(datagramPacket.getData());
            System.out.println("Response from server:");
            System.out.println("Node Number: " + receivedPacket.getNodeNumber());
            System.out.println("Sequence Number: " + receivedPacket.getSequenceNumber());
            System.out.println("Files: " + receivedPacket.getFiles());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws SocketException, IOException {
        DatagramSocket socket = new DatagramSocket();
        InetAddress address = InetAddress.getByName("localhost");
        Client client = new Client(socket, address);

        System.out.println("client start");
        while(true) {
            client.send();
            client.receive();
        }
        
    }

    private static HACPacket deserializeHACPacket(byte[] data) {
            try {
            ByteArrayInputStream byteStream = new ByteArrayInputStream(data);
            ObjectInputStream objStream = new ObjectInputStream(byteStream);
            return (HACPacket) objStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}

/*
 * public void receive() throws IOException {
        try {
            System.out.println("Listening...");
            DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);
            socket.receive(datagramPacket);
            String fromServer = new String(datagramPacket.getData(), 0, datagramPacket.getLength());
            System.out.println("Server: " + fromServer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 */
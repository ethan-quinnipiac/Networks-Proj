package networking;

import java.net.DatagramPacket;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.SocketException;
import java.net.DatagramSocket;
import java.net.InetAddress;

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
        while(true) {
            try {
                //create a packet object
                packet packetToSend = new packet("1.0", 5, new String[]{"flag1", "flag2"}, null, null);
                
                //serialize the packet object to a byte array
                ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
                ObjectOutputStream objStream = new ObjectOutputStream(byteStream);
                objStream.writeObject(packetToSend);
                objStream.flush();
                byte[] packetBytes = byteStream.toByteArray();
                
                //create a DatagramPacket with the serialized packet
                DatagramPacket datagramPacket = new DatagramPacket(packetBytes, packetBytes.length, address, 9876);
                socket.send(datagramPacket);
                
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    public void receive() throws IOException {
        while(true) {
            try {
                DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);
                socket.receive(datagramPacket);
                String fromServer = new String(datagramPacket.getData(), 0, datagramPacket.getLength());
                System.out.println("Server: " + fromServer);
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    public static void main(String[] args) throws SocketException, IOException {
        DatagramSocket socket = new DatagramSocket();
        InetAddress address = InetAddress.getByName("localhost");
        Client client = new Client(socket, address);
        System.out.println("client start");
        client.send();
        client.receive();
    }
}

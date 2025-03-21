package networking;

import java.util.ArrayList;
import java.net.DatagramSocket;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.SocketException;

/*
 * Created by Kyle Macdonald
 * Main file that's NOT executable on the virtual machine. Will handle outputting all necessary data.
 * IMPORTANT!!!! RUN THE HACPROGRAM, THIS JUST HOLDS THE FUNCTIONS FOR EACH TYPE OF CONNECTION
 */

public class Establisher {

    //TODO SET FULL HOME DIR HERE
    static String homeDir = "";
    static int heartDelay = 15;


    //Establishes the current node as both a client and server
    //TODO NEEDS TO BE SETUP TO HAVE 5 OTHER SOCKETS, ONE FOR EACH NODE
    public static void peerToPeer(int nodeNum, ConfigHandler config){
        DirectoryGetter getDir = new DirectoryGetter(homeDir);
        ThreadHandler thread = new ThreadHandler();
        ArrayList<String> directoriesToSend = getDir.getDirectories();
        Heartbeat heart = new Heartbeat(heartDelay);

        ArrayList<DatagramSocket> nodeSockets = new ArrayList<>();

        try {
            //create 1 socket for each node
            for (int i = 1; i <= 5; i++) {
                if (i != nodeNum) { //skip the current node
                    InetAddress nodeAddress = config.getAddress(i);
                    int nodePort = config.getPortNumber(i);
                    DatagramSocket socket = new DatagramSocket();
                    nodeSockets.add(socket);
                    System.out.println("Socket created for Node " + i + " at " + nodeAddress + ":" + nodePort);
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }

        boolean running = true;
        int sequenceNumber = 0;

        while(running){
            try {
                //send HAC packet
                for (int i = 0; i < nodeSockets.size(); i++) {
                    DatagramSocket socket = nodeSockets.get(i);
                    InetAddress nodeAddress = config.getAddress(i + 1); // Node index starts from 1
                    int nodePort = config.getPortNumber(i + 1);

                    //create an HAC packet
                    HACPacket packetToSend = new HACPacket(nodeNum, sequenceNumber++, directoriesToSend);

                    //serialize the HAC packet
                 byte[] data = serializeHACPacket(packetToSend);

                    //create and send a DatagramPacket
                    DatagramPacket packet = new DatagramPacket(data, data.length, nodeAddress, nodePort);
                    socket.send(packet);
                    System.out.println("HACPacket sent to Node " + (i + 1));
                }

                //receive data from other nodes
                for (int i = 0; i < nodeSockets.size(); i++) {
                    DatagramSocket socket = nodeSockets.get(i);
                    byte[] buffer = new byte[4096]; // Increase buffer size for larger packets
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                    socket.receive(packet);

                    // Deserialize the HACPacket
                    HACPacket receivedPacket = deserializeHACPacket(packet.getData());
                    System.out.println("HACPacket received from Node " + (i + 1) + ":");
                    System.out.println("Node Number: " + receivedPacket.getNodeNumber());
                    System.out.println("Sequence Number: " + receivedPacket.getSequenceNumber());
                    System.out.println("Files: " + receivedPacket.getFiles());
                }

                heart.pulse();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //establishes the node as a client
    public static void Client(int nodeNum, ConfigHandler config){
        //sets up all the tools needed to run
        DirectoryGetter getDir = new DirectoryGetter(homeDir);
        ThreadHandler thread = new ThreadHandler();
        ArrayList<String> directoriesToSend = getDir.getDirectories();
        Heartbeat heart = new Heartbeat(heartDelay);
        int thisNode = nodeNum;

        //sets  up the socket for the client through the config file
        DatagramSocket socket;
        try {
            socket = new DatagramSocket(config.getPortNumber(1), config.getAddress(1));
            Client client = new Client(socket, config.getAddress(1));
        } catch (SocketException e) {
            e.printStackTrace();
        }
        
        //creates the data for the client
        ClientData clientData = new ClientData(config.getPortNumber(nodeNum), config.getAddress(nodeNum));

        //TODO CONVERT DIRECTORIES TO BYTESTREAM
        boolean running = true;
        while(running){
            //TODO receive packet
            //TODO send packet
            //TODO output data
            heart.pulse();
        }
    }

    //establishes the node as a server
    public static void Server(int nodeNum, ConfigHandler config){
        DirectoryGetter getDir = new DirectoryGetter(homeDir);
        ThreadHandler thread = new ThreadHandler();
        ArrayList<String> directoriesToSend = getDir.getDirectories();
        Heartbeat heart = new Heartbeat(heartDelay);
        
        try{
            DatagramSocket socket = new DatagramSocket(config.getPortNumber(1), config.getAddress(1));
            Server server = new Server(socket);
        }catch (SocketException e) {
            e.printStackTrace();
        }
        


        //TODO CONVERT DIRECTORIES TO BYTESTREAM
        boolean running = true;
        while(running){
            //TODO receive packet
            //TODO send packet
            //TODO output data
            heart.pulse();
        }
    }

    private static byte[] serializeHACPacket(HACPacket packet) {
        try {
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            ObjectOutputStream objStream = new ObjectOutputStream(byteStream);
            objStream.writeObject(packet);
            objStream.flush();
            return byteStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
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

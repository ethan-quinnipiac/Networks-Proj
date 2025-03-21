package networking;

import java.util.ArrayList;
import java.net.DatagramSocket;
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

        //TODO CONVERT DIRECTORIES TO BYTESTREAM
        boolean running = true;
        while(running){
            //TODO receive packet
            //TODO send packet
            //TODO output data
            heart.pulse();
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
}

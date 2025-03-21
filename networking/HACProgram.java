package networking;

import java.util.Scanner;
import java.util.ArrayList;

public class HACProgram {
    public static final ConfigHandler CONFIG_HANDLER = new ConfigHandler();

    public static void main(String[] args) {
        // Setup nodeNumber
        int thisNodeNumber;
        
        Scanner input = new Scanner(System.in);

        String currentInput;

        ArrayList<String> acceptedP2P = new ArrayList<>();
        acceptedP2P.add("P2P");
        acceptedP2P.add("P2p");
        acceptedP2P.add("p2P");
        acceptedP2P.add("p2p");

        ArrayList<String> acceptedCS = new ArrayList<>();
        acceptedCS.add("C-S");
        acceptedCS.add("C-s");
        acceptedCS.add("c-S");
        acceptedCS.add("c-s");
        acceptedCS.add("CS");
        acceptedCS.add("Cs");
        acceptedCS.add("cS");
        acceptedCS.add("cs");

        System.out.println("Please enter this node's ID# (1-" + CONFIG_HANDLER.getNumOfNodes() + "). If server node, please enter " + 
                                        ConfigHandler.SERVER_NODE_NUMBER + ":");
        thisNodeNumber = input.nextInt();
        while (thisNodeNumber > CONFIG_HANDLER.getNumOfNodes() || thisNodeNumber < 1) {
            System.out.println("ERROR: Node ID# out of range. Please enter an ID# from 1-" + CONFIG_HANDLER.getNumOfNodes() + ":");
            thisNodeNumber = input.nextInt();
        }
        input.nextLine();

        System.out.println("What mode would you like to run?");
        System.out.println("Type:   P2P     C-S");
        currentInput = input.nextLine();

        // Check that input matches p2p or c-s
        while (!(acceptedP2P.contains(currentInput) || acceptedCS.contains(currentInput))) {
            System.out.println("Unknown mode requested. Please type one of the following:");
            System.out.println("P2P     C-S");
            currentInput = input.nextLine();
        }

        if (acceptedP2P.contains(currentInput)) {
            // Close input and start p2p process
            input.close();
            System.out.println("P2P mode selected.");
            System.out.println("Beginning P2P program.");
            Establisher.peerToPeer(thisNodeNumber, CONFIG_HANDLER);
        }
        else {
            System.out.println("C-S mode selected.");
            // Non-client node
            if (thisNodeNumber != ConfigHandler.SERVER_NODE_NUMBER) {
                // Close input and start client process
                input.close();
                System.out.println("Client node detected.");
                System.out.println("Beginning client program.");
                Establisher.Client(thisNodeNumber, CONFIG_HANDLER);
            }
            else {
                // Close input and start server process
                input.close();
                System.out.println("Server node detected.");
                System.out.println("Beginning server program.");
                Establisher.Server(thisNodeNumber, CONFIG_HANDLER);
            }
        }
    }
}

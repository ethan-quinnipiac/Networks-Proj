package networking;

import java.util.Scanner;
import java.util.ArrayList;

public class HACProgram {
    
    public static void main(String[] args) {
        
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
            System.out.println("Beginning P2P protocol.");
            // TODO Fill in P2P behavior
        }
        else {
            ArrayList<String> acceptedClient = new ArrayList<>();
            acceptedClient.add("Client");
            acceptedClient.add("client");
            acceptedClient.add("C");
            acceptedClient.add("c");

            ArrayList<String> acceptedServer = new ArrayList<>();
            acceptedServer.add("Server");
            acceptedServer.add("server");
            acceptedServer.add("S");
            acceptedServer.add("s");

            System.out.println("C-S mode selected.");
            System.out.println("Client or server?");
            System.out.println("Type: Client     Server");
            currentInput = input.nextLine();

            // Check that input matches client or server
            while (!(acceptedClient.contains(currentInput) || acceptedServer.contains(currentInput))) {
                System.out.println("Unknown mode requested. Please type one of the following:");
                System.out.println("Client     Server");
                currentInput = input.nextLine();
            }

            if (acceptedClient.contains(currentInput)) {
                // Close input and start client process
                input.close();
                System.out.println("Client mode selected.");
                System.out.println("Beginning client protocol.");
                // TODO Fill in client behavior
            }
            else {
                // Close input and start server process
                input.close();
                System.out.println("Server mode selected.");
                System.out.println("Beginning server protocol.");
                // TODO Fill in server behavior
            }
        }
    }
}

package networking;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * Written by John Caceres
 * Class reads the config file upon creating an instance and stores socket
 * info for each node in two arrays.
 * Methods are provided to get the number of nodes, the InetAddress
 * associated with a node, and the port number associated with a node.
 */

public class ConfigHandler {
    public static final int SERVER_NODE_NUMBER = 1;
    private int numOfNodes;
    private int[] portNumbers;
    private InetAddress[] addresses;

    public ConfigHandler() {
        File configFile = new File("/Users/ethanlanier/Desktop/CSC340/Networks-Proj/networking/config.txt");

        try {
            Scanner reader = new Scanner(configFile);

            numOfNodes = reader.nextInt();
            portNumbers = new int[numOfNodes];
            addresses = new InetAddress[numOfNodes];

            for (int i  = 0; i < numOfNodes; i++) {
                portNumbers[i] = reader.nextInt();
                reader.nextLine();
                addresses[i] = InetAddress.getByName(reader.nextLine());
            }

            reader.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public int getNumOfNodes() {
        return numOfNodes;
    }

    // The following two methods can yield an ArrayIndexOutOfBoundsException
    // if nodeNumber > numOfNodes.
    public int getPortNumber(int nodeNumber) {
        return portNumbers[nodeNumber - 1];
    }

    public InetAddress getAddress(int nodeNumber) {
        return addresses[nodeNumber - 1];
    }

}
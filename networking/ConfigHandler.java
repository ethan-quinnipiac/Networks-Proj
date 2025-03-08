package networking;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ConfigHandler {
    private int numOfNodes;
    private int[] portNumbers;
    private InetAddress[] addresses;

    public ConfigHandler() {
        File configFile = new File(".\\networking\\config.txt");

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

    public int getPortNumber(int nodeNumber) {
        return portNumbers[nodeNumber - 1];
    }

    public InetAddress getAddress(int nodeNumber) {
        return addresses[nodeNumber - 1];
    }

}

package networking;

import java.io.Serializable;
import java.util.ArrayList;

public class HACPacket implements Serializable {
    private int nodeNumber;
    private int sequenceNumber;
    private ArrayList<String> files;

    // Create a new protocol packet to be converted to a byte[] and then sent over UDP.
    public HACPacket(int nodeNumber, int sequenceNumber, ArrayList<String> files) {
        this.nodeNumber = nodeNumber;
        this.sequenceNumber = sequenceNumber;
        this.files = new ArrayList<String>(files);
    }

    public int getNodeNumber() {
        return nodeNumber;
    }

    public int getSequenceNumber() {
        return sequenceNumber;
    }

    public ArrayList<String> getFiles() {
        return files;
    }

}
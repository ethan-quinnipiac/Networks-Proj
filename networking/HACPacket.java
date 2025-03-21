package networking;

import java.io.Serializable;
import java.util.ArrayList;

public class HACPacket implements Serializable {
    private String version;
    private int length;
    private int nodeNumber;
    private int sequenceNumber;
    private ArrayList<String> files;

    // Create a new protocol packet to be converted to a byte[] and then sent over UDP.
    public HACPacket(int nodeNumber, int sequenceNumber, ArrayList<String> files) {
        this.version = "1.0";
        this.length = 1024;
        this.nodeNumber = nodeNumber;
        this.sequenceNumber = sequenceNumber;
        this.files = new ArrayList<String>(files);
    }

    public HACPacket(String version, int length, int nodeNumber, int sequenceNumber, ArrayList<String> files) {
        this.version = version;
        this.length = length;
        this.nodeNumber = nodeNumber;
        this.sequenceNumber = sequenceNumber;
        this.files = new ArrayList<>(files);
    }

    public String getVersion() {
        return this.version;
    }

    public int getLength() {
        return this.length;
    }

    public int getNodeNumber() {
        return this.nodeNumber;
    }

    public int getSequenceNumber() {
        return this.sequenceNumber;
    }

    public ArrayList<String> getFiles() {
        return this.files;
    }

}
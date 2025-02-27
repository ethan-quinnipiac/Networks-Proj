package networking;
import java.net.Socket;
import java.net.DatagramPacket;

public class packet {
    //Data
    String version;
    int length;
    String[] flags;
    Socket destSocket;
    DatagramPacket datagramPacket;

    //Constructor
    public packet(String version, int length, String[] flags, Socket destSocket, DatagramPacket datagramPacket){
        this.version = version;
        this.length = length;
        this.flags = flags;
        this.destSocket = destSocket;
        this.datagramPacket = datagramPacket;
    }


    //Getters and setters
    public String getVersion(){
        return this.version;
    }
    public void setVersion(String version){
        this.version = version;
    }

    public int getLength(){
        return this.length;
    }
    public void setLength(int length){
        this.length = length;
    }
    
    public String[] getFlags(){
        return this.flags;
    }
    public void setFlags(String[] flags){
        this.flags = flags;
    }
}

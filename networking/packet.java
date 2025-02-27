package networking;
import java.net.Socket;

public class packet {
    String version;
    int length;
    String[] flags;
    Socket destSocket;
    public packet(String version, int length, String[] flags, Socket destSocket){
        this.version = version;
        this.length = length;
        this.flags = flags;
        this.destSocket = destSocket;
    }

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

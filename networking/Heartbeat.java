package networking;
import java.util.concurrent.TimeUnit;
/*
 * Created by Kyle Macdonald
 * Used to create the time delay for the pulse for sending out packets, create a heartbeat object with the delay for the value
 */

public class Heartbeat{
    long timeDelay;

    public Heartbeat(long delay){
        if(delay < 1 || delay > 30){
            System.out.println("Delay for heartbeat is recommended to be 1-30, defaulting to 15");
            this.timeDelay = 15;
        }else{
            this.timeDelay = delay;
        }
    }

    public void pulse(){
        System.out.println("next pulse in " + this.timeDelay + " seconds");
        try {
            TimeUnit.SECONDS.sleep(timeDelay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
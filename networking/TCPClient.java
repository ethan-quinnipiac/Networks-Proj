package networking;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
/**
 * 
 * @author cjaiswal
 *
 * 
 */
public class TCPClient 
{
    private Socket socket = null;
    private InputStream inStream = null;
    private OutputStream outStream = null;

    public TCPClient() 
    {
    	//create a socket to connect to localHost's (127.0.0.1) port 3339
        try 
        {
			socket = new Socket("localHost", 3339);
			System.out.println("Connected!");
		} 
        catch (UnknownHostException e) 
        {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        catch (IOException e) 
        {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    public void createSocket()
    {
        try 
        {
        	//fetch the streams
            inStream = socket.getInputStream();
            outStream = socket.getOutputStream();
            createReadThread();
            createWriteThread();
        } 
        catch (UnknownHostException u) 
        {
            u.printStackTrace();
        } 
        catch (IOException io) 
        {
            io.printStackTrace();
        }
    }

    public void createReadThread() 
    {
        Thread readThread = new Thread() 
        {
            public void run() 
            {
                while (socket.isConnected()) 
                {
                    try 
                    {
                        byte[] readBuffer = new byte[200];
                        int num = inStream.read(readBuffer);

                        if (num > 0) 
                        {
                            byte[] arrayBytes = new byte[num];
                            System.arraycopy(readBuffer, 0, arrayBytes, 0, num);
                            String recvedMessage = new String(arrayBytes, "UTF-8");
                            System.out.println("Received message :" + recvedMessage);
                        }
                        else 
                        {
                        	notifyAll();
                        }
                    }
                    catch (SocketException se)
                    {
                        System.exit(0);
                    }
                    catch (IOException i) 
                    {
                        i.printStackTrace();
                    }
                }
            }
        };
        readThread.setPriority(Thread.MAX_PRIORITY);
        readThread.start();
    }

    public void createWriteThread() 
    {
        Thread writeThread = new Thread() 
        {
            public void run() 
            {
                while (socket.isConnected()) 
                {
                	try 
                	{
                        BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
                        sleep(100);
                        String typedMessage = inputReader.readLine();
                        if (typedMessage != null && typedMessage.length() > 0) 
                        {
                            synchronized (socket) 
                            {
                                outStream.write(typedMessage.getBytes("UTF-8"));
                            }
                            sleep(100);
                        }
                        else
                        {
                        	notifyAll();
                        }
                    } 
                	catch (IOException i) 
                	{
                        i.printStackTrace();
                    } 
                	catch (InterruptedException ie) 
                	{
                        ie.printStackTrace();
                    }
                }
            }
        };
        writeThread.setPriority(Thread.MAX_PRIORITY);
        writeThread.start();
    }

    public static void main(String[] args) throws Exception 
    {
        TCPClient myChatClient = new TCPClient();
        myChatClient.createSocket();
        /*myChatClient.createReadThread();
�       myChatClient.createWriteThread();*/
    }
}

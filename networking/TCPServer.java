package networking;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
/**
 * 
 * @author cjaiswal
 *
 *  
 * 
 */
public class TCPServer 
{
    private ServerSocket serverSocket = null;
    private Socket socket = null;
    private InputStream inStream = null;
    private OutputStream outStream = null;

    public TCPServer() 
    {
    	//create a server listen socket at port 3339
        try 
        {
			serverSocket = new ServerSocket(3339);
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
            while (true) 
            {
            	//wait for a client to request to connect
            	socket = serverSocket.accept();
                
            	//fetch the streams for the connected client	
                inStream = socket.getInputStream();
                outStream = socket.getOutputStream();
                System.out.println("Connected");
                createReadThread();
                createWriteThread();
            }
        }
        catch (IOException io) 
        {
            io.printStackTrace();
        }
    }
    
    //Reading thread using anonymous class
    public void createReadThread() 
    {
        Thread readThread = new Thread() 
        {
            public void run() 
            {
            	//check socket connectivity
                while (socket.isConnected()) 
                {
                    try 
                    {
                        byte[] readBuffer = new byte[200];
                        //read the data from client
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

    //write thread using anonymous class
    public void createWriteThread() 
    {
        Thread writeThread = new Thread() 
        {
            public void run() 
            {
            	//check socket connectivity
                while (socket.isConnected()) 
                {
                    try 
                    {
                    	//Use BufferedReader or Scanner to read from console
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

    public static void main(String[] args)
    {
        TCPServer chatServer = new TCPServer();
        chatServer.createSocket();
    }
}

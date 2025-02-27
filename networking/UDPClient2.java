package networking;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;
/**
 * 
 * @author cjaiswal
 *
 *  
 * 
 */
public class UDPClient2 
{
    private DatagramSocket socket;
    private Scanner in = new Scanner(System.in);
    public UDPClient2() 
    {
    	//create a client socket with random port number chose by DatagramSocket
    	try 
    	{
			socket = new DatagramSocket();
		} 
    	catch (SocketException e) 
    	{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    public void createAndListenSocket() 
    {
        try 
        {
            char ch='y';
            
            //create socket for the destination/server
            InetAddress IPAddress = InetAddress.getByName("localhost");
            int serverPort = 9876;
            byte[] incomingData = new byte[1024];
            String sentence = "";
        	byte data[] = new byte[1024];

            do
            {
            	//construct the client packet & send it
            	System.out.println("Enter your message:");
            	sentence = in.nextLine();
            	data = sentence.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(data, data.length, IPAddress, serverPort);
                socket.send(sendPacket);
               
                //create packet and recieve the response from the server
                DatagramPacket incomingPacket = new DatagramPacket(incomingData, incomingData.length);
                socket.receive(incomingPacket);
                String response = new String(incomingPacket.getData());
                System.out.println("Response from server:" + response);
                System.out.println("Server Details:PORT " + incomingPacket.getPort()
                + ", IP Address: " + incomingPacket.getAddress());
                sendPacket = null; incomingPacket = null;
                System.out.println("Chat more? Y/N...");
                ch = in.nextLine().charAt(0);
            }while(ch=='y' || ch=='Y');
            
            //send THEEND message to server to terminate
            sentence = "THEEND";
            data = sentence.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(data, data.length, IPAddress, 9876);
            socket.send(sendPacket);
            socket.close();
        }
        catch (UnknownHostException e) 
        {
            e.printStackTrace();
        } 
        catch (SocketException e) 
        {
            e.printStackTrace();
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) 
    {
        UDPClient2 client = new UDPClient2();
        client.createAndListenSocket();
    }
}

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.*;
import java.util.ArrayList;

public class SendMessage {
	private static SendMessage sync = new SendMessage();
	private static int process_node = 0;
	private static String host;
	
	public static void generateTree()
	{
		try {
			host = InetAddress.getLocalHost().getHostName();
			int itr = 0;
			for (String hst : Tree.hostname) 
			{
				itr++;				
				if (hst.equalsIgnoreCase(host)) 
				{
					process_node = itr;
				}
				
			}
		} catch (UnknownHostException e)
		{
			System.out.println("Host name not found error");
			e.printStackTrace();
		}
		try 
		{
			Thread.sleep(2000);
		} 
		catch (InterruptedException e) 
		{		
			e.printStackTrace();
		}
		if(process_node== new BroadcastService().getRoot())
		{
			Tree.generateSpanningTree(process_node, process_node);	
		}
	}

	public static void doWait()
	{
		synchronized (sync) {
			try {
				sync.wait();
			} catch (InterruptedException e) {
				e.getMessage();
			}
		}
	}

	public static void doNotify() {
		synchronized (sync) {
			sync.notify();
		}
	}
	
	public static void sendMessage_broadcastMsg(int node, int port,int root,int random) 
	{			
		Socket socket;
		try {
			socket = new Socket(Tree.hostname.get(node-1), port);
			PrintWriter msg_send = new PrintWriter(
					socket.getOutputStream(), true);
			msg_send.println("BROADCAST_MSG from node,root : "+process_node + ", " + root + ", " + random);
         socket.close();

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	public static void sendMessage_broadcastAck(int node, int port, int root) 
	{
		Socket socket;
		try {
			socket = new Socket(Tree.hostname.get(node-1), port);
			PrintWriter msg_send = new PrintWriter(
					socket.getOutputStream(), true);
         msg_send.println("BROADCAST_ACK_MSG from node,root: "+process_node + ", " + root);			

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	public static void sendMessage_applicationMsg(int node, int port, int root) 
	{		
		Socket socket;
		try {
			socket = new Socket(Tree.hostname.get(node-1), port);
			PrintWriter msg_send = new PrintWriter(
					socket.getOutputStream(), true);
			msg_send.println("APP_MSG from node, root-node: "+process_node+", "+root);
			

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	public static void sendMessage_applicationAck(int node, int port) 
	{				
		Socket socket;
		try {
			socket = new Socket(Tree.hostname.get(node-1), port);
			PrintWriter msg_send = new PrintWriter(
					socket.getOutputStream(), true);			
			msg_send.println("APP_ACK from node: "+process_node);
			

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static int getNodeNumber()
	{
		return process_node;
	}
	public static String getHostName()
	{	
		return host;
	}	
}
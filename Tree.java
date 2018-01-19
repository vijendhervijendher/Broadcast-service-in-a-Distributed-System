import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;


public class Tree
{	
	public static void createNodes()
	{
		try {			 	
			
         bufferedReader = new BufferedReader(new FileReader("/home/011/v/vx/vxp161830/CS6378/Project1/config.txt")); 
			String line;
         while ((line = bufferedReader.readLine()) != null) 
			{
				if(line.startsWith("#")){
					continue;
               }
				String[] words = line.split("\t");
            if(words.length == 1){
               noOfBroadcast = Integer.parseInt(words[0].replaceAll("\\s",""));
               continue;
            }
            String configNeighbour = words[2];
				int port = Integer.parseInt(words[1]);
            String host = words[0];	
				ports.add(port);
            configNeighbours.add(configNeighbour);
            hostname.add(host + ".utdallas.edu");	

			}			
			for(int i=0;i<hostname.size();i++)
			{				
				if(hostname.get(i).trim().equals(InetAddress.getLocalHost().getHostName()))
				{
					hosts.add(hostname.get(i));					
					ReceiveMessage.listenPort = ports.get(i);
               int j = i + 1;
               System.out.println(hostname.get(i).substring(0,hostname.get(i).indexOf(".")) + " is listening on " + ports.get(i));
					Thread listener = new Thread(new ReceiveMessage());
					listener.start();
				}
			}
			
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public static void generateSpanningTree(int node, int root)
	{
			String[] parts = configNeighbours.get(node-1).split(" ");
			for(int j=0;j<parts.length;j++)
			{
				int index= Integer.parseInt(parts[j]);
				SendMessage.sendMessage_applicationMsg(index, ports.get(index-1), root);
			}
	}
	public static void sendBroadcast(int node,int root,int broadcastParentNode,int sentRandom)
	{        
            int random = 0;       
            if(broadcastParentNode == 0)
            {
               random = (int)(Math.random()*100);
               sum = sum + random;
            }
            else
            {
               random = sentRandom;
            }
            for(int neighbour: neighbours)
            {
               if(neighbour != broadcastParentNode)
               {
                  SendMessage.sendMessage_broadcastMsg(neighbour, getPort(neighbour),root,random);
               }
            } 
	}
	
   public static void printChildren()
	{       
		System.out.println("Children of " + SendMessage.getHostName().substring(0,SendMessage.getHostName().indexOf(".")) + "  " );
		for(int child: children)
		{
			System.out.print("  " + hostname.get(child - 1).substring(0,hostname.get(child - 1).indexOf(".")) +"  ");
		}
      System.out.println();			
	}
   
   public static void printNeighbours()
	{
      System.out.println("Neighbours of " + SendMessage.getHostName().substring(0,SendMessage.getHostName().indexOf(".")) + "  ");
		for(int neighbour: neighbours)
		{
			System.out.print("  " + hostname.get(neighbour - 1).substring(0,hostname.get(neighbour - 1).indexOf(".")) + "  ");
		}
      System.out.println();
	}
	
   public static int getPort(int node)
	{	
		return ports.get(node-1);
	}
   
   static BufferedReader bufferedReader = null;
	static ArrayList<String> hosts = new ArrayList<String>();
	public static ArrayList<String> hostname = new ArrayList<String>();
	static ArrayList<Integer> ports = new ArrayList<Integer>();
	static ArrayList<String> configNeighbours = new ArrayList<String>();
	static ArrayList<Integer> children = new ArrayList<Integer>();
	static ArrayList<Integer> neighbours = new ArrayList<Integer>();
	static int noOfBroadcast = 0;
   static int sum = 0;  
}

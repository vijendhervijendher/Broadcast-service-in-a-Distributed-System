import java.net.*;
import java.util.ArrayList;
import java.io.*;

public class ReceiveMessage extends Thread
{
	   
	public static void doWait() {
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

	@Override
	public void run() 
	{
		try (ServerSocket serverSocket = new ServerSocket(listenPort);) 
		{
			
			ReceiveMessage.doNotify();
			while (bool) 
			{
				try 
				{
					
					Socket socket = serverSocket.accept();					
					BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));	
					String message = reader.readLine();
					if(message.contains("BROADCAST_MSG"))
					{	   
                     String[] part = message.split(":");
						   String[] part2 = part[1].replaceAll("\\s","").split(",");
						   int senderNode = Integer.parseInt(part2[0]);
						   int rootNode = Integer.parseInt(part2[1]);
                     int random = Integer.parseInt(part2[2]);
                     if(broadCastParentNode[rootNode] < 0 && rootNode != SendMessage.getNodeNumber())
                     {
                        Tree.sum += random;
                        broadCastParentNode[rootNode] = senderNode;
                        System.out.println("  " + Tree.hostname.get(senderNode - 1).substring(0,Tree.hostname.get(senderNode - 1).indexOf(".")) + " sent Broadcast to " + Tree.hostname.get(SendMessage.getNodeNumber() - 1).substring(0,Tree.hostname.get(SendMessage.getNodeNumber() - 1).indexOf(".")) + " with initiator " + Tree.hostname.get(rootNode - 1).substring(0,Tree.hostname.get(rootNode - 1).indexOf(".")) + "  ");
                        if(Tree.neighbours.size()==1)
                        {
                           SendMessage.sendMessage_broadcastAck(senderNode, Tree.getPort(senderNode),rootNode);
                           broadCastParentNode[rootNode] = -1;
                           acknowledgeCounter[rootNode] = 0;
                        }
                        else
                        {
                           Tree.sendBroadcast(SendMessage.getNodeNumber(),rootNode,broadCastParentNode[rootNode],random);      
                        }
                        
                     }					
																					
					}
					else if(message.contains("BROADCAST_ACK_MSG"))
					{  
                  String[] part = message.split(":");
					   String[] part2 = part[1].replaceAll("\\s","").split(",");
					   int senderNode = Integer.parseInt(part2[0]);
					   int rootNode = Integer.parseInt(part2[1]);
                  System.out.println(" Sent broadcast ack. from node  "+ Tree.hostname.get(senderNode - 1).substring(0,Tree.hostname.get(senderNode - 1).indexOf(".")) + " to " + Tree.hostname.get(SendMessage.getNodeNumber() - 1).substring(0,Tree.hostname.get(SendMessage.getNodeNumber() - 1).indexOf(".")) + " with initiator " + Tree.hostname.get(rootNode - 1).substring(0,Tree.hostname.get(rootNode - 1).indexOf(".")));
                  if(rootNode == SendMessage.getNodeNumber())
                  {
                     acknowledgeCounter[rootNode]++;
                     if(acknowledgeCounter[rootNode] == Tree.neighbours.size())
                     {  
                           acknowledgeCounter[rootNode] = 0;
                           System.out.println("  Broadcast operation completed for " + Tree.hostname.get(rootNode - 1).substring(0,Tree.hostname.get(rootNode - 1).indexOf(".")) + "   ");
                           
                           if(Tree.noOfBroadcast>1)
    								{
    									Tree.noOfBroadcast--;
    									System.out.println("  " + Tree.noOfBroadcast+" time(s) broadcast remaining for " + Tree.hostname.get(rootNode - 1).substring(0,Tree.hostname.get(rootNode - 1).indexOf(".")) + "  ");
    									
                              try 
    									{  
                                 double random = Math.random();
		                           double x = Math.log(1 - random)/(-1);
                                 int timeGap = (int) Math.ceil(x*1000);
    										Thread.sleep(timeGap);
    									} 
    									catch (InterruptedException e) 
    									{		
    										e.printStackTrace();
    									}
    									new BroadcastService().broadcast();									
    								}
                       }
                  } 
                  else
                  {
                     acknowledgeCounter[rootNode]++;
                     if(acknowledgeCounter[rootNode] == Tree.neighbours.size() - 1)
                     {  
                        SendMessage.sendMessage_broadcastAck(broadCastParentNode[rootNode], Tree.getPort(broadCastParentNode[rootNode]),rootNode);
                        acknowledgeCounter[rootNode] = 0;
                        broadCastParentNode[rootNode] = -1;
                     }
                     
                  }                 
					}
					else if(message.contains("APP_MSG"))
					{
						String[] part = message.split(":");
						String[] part2 = part[1].replaceAll("\\s","").split(",");
						int senderNode = Integer.parseInt(part2[0]);
						int rootNode = Integer.parseInt(part2[1]);
						if(parentNode<0 && !(rootNode==SendMessage.getNodeNumber()))
						{
                     Tree.neighbours.add(senderNode);
							parentNode = senderNode;							
							SendMessage.sendMessage_applicationAck(senderNode, Tree.getPort(senderNode));
							Tree.generateSpanningTree(SendMessage.getNodeNumber(), rootNode);
							
						}
					}
					else if(message.contains("APP_ACK"))
					{
						String[] part = message.split(":");
						int senderNode = Integer.parseInt(part[1].replaceAll("\\s",""));
						Tree.children.add(senderNode);
	    				Tree.neighbours.add(senderNode);					
					}
					reader.close();				
				} 
				
				catch (IOException e) 
				{
					System.out.println(e.getMessage());
				}
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	   
      double sum = 0;
      private static int[] broadCastParentNode = new int[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
      private static final boolean bool = true;
      public static int listenPort;
      String line;
      int[] acknowledgeCounter = new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
      private static ReceiveMessage sync = new ReceiveMessage();
      private static int parentNode = -1;
      
      public static int getParentNode(){
         return parentNode;
      }

}

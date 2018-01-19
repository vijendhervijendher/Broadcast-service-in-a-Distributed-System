import java.util.Scanner;

import javax.naming.InitialContext;


public class BroadcastService 
{	  
  	public static void main(String[] args) 
	{
		Tree.createNodes();
	
		SendMessage.generateTree();
		try 
		{
			Thread.sleep(8000);
		} 
		catch (InterruptedException e) 
		{		
			e.printStackTrace();
		}

      Tree.printChildren();
		Tree.printNeighbours();
		try 
		{
			Thread.sleep(2000);
		} 
		catch (InterruptedException e) 
		{		
			e.printStackTrace();
		}

		new BroadcastService().broadcast();
      try
      {
         Thread.sleep(30000);
      }
      catch (InterruptedException e) 
		{		
			e.printStackTrace();
		}
      System.out.println(" Total sum of node " + Tree.hostname.get(SendMessage.getNodeNumber() - 1).substring(0,Tree.hostname.get(SendMessage.getNodeNumber() - 1).indexOf(".")) + " is " + Tree.sum);		
	}
	
	public void broadcast()
	{  
          Tree.sendBroadcast(SendMessage.getNodeNumber(),SendMessage.getNodeNumber(),0,0);	
	}
	
	private int root = 1;
   private int broadcastInitiator = 2;
	
   
	public void setRoot(int root)
	{
		this.root = root;
	}
   
   public int getRoot() 
	{
		return root;
	}
   
    public int getBroadcastInitiator(){
       return broadcastInitiator;
    }
    
    public void setBroadcastInitiator(int broadcastInitator){
       this.broadcastInitiator = broadcastInitiator;
    }
}
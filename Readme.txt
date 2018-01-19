# Broadcast after building a Spanning Tree 

Assuming a distributed system in which nodes are arranged in a certain topology (specified in a configuration file). 
Built a spanning tree using a distributed algorithm.
Once the spanning tree construction completes, each node should know which subset of its neighbors are also its tree neighbours. 
Using the spanning tree constructed above implemented a broadcast service that allows any node to send a message to all nodes in the system. 
The broadcast service informs the source node of the completion of the broadcast operation. 
Assumption is made that only one broadcast operation can occur at any time.




-	The zip file contains all the required .java files to compile and run the program.


-	It also contains the script files to launch and clean-up the process.


-	Along with all this files a configuration file “config.txt” is there to help the program build the tree which later be used to start the broadcast service.


To run:


a.	Keep all the file in a folder “CS6378/Project1” under default location that is $HOME.
 In my case it is (absolute location): /home/011/v/vx/vxp161830

b.      Rename file input in Tree.java to $HOME as per the user.
c.	Compile the main program BroadcastService.java





d.	Now run the launcher - launcher.sh



e.	It reads all the parameters from the configuration file. Inside which it is also specified, how many times the nodes will broadcast messages. 

f.	Once it runs the specified times, use the clean-up script to kill the processes initiated by you and are still running in background.

        
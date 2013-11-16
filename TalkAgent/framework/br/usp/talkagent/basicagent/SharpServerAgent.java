package br.usp.talkagent.basicagent;

/**
* Basic functionality to a multi thread agent which may receive multiple incoming requests (sockets)
*/

import java.util.*;
import java.net.*;


public class SharpServerAgent extends Thread
{
	
  protected fipaos.ont.fipa.fipaman.AgentID agentid;
  protected java.net.ServerSocket server;
  protected java.util.Vector sentMessages;
  protected java.util.Vector recievedMessages;
  protected AmsClient amsclient;
  private int running_clients;
  private int port;
  private boolean running;

public SharpServerAgent() 
{
	super();
	this.running_clients=0;
	try
	{
		amsclient = new AmsClient();	
	}
	catch(Exception e)
	{}
}


public SharpServerAgent(boolean connect) 
{
	super();	
}


public void Connect()
{
	this.amsclient.Connect();
}

/**
* DisRegister agent from AMS Server 
*/ 
public void disRegister() 
{
	amsclient.disRegister(this.agentid);
}

/**
* Register Agent into AMS - Agent Managment Service
*/
public void register() 
{
	amsclient.register(this.agentid);
}

/**
 */
public void run() 
{}


/**
 * @param IP java.lang.String
 * @param port int
 */
public void setAMS(String IP, int port) 
{
	amsclient.setAMS(IP,port);
}


/**
* Set Agent ID
*/
public void setId(String name, String server, String port) 
{
	LinkedList l1; 
	
	this.agentid = new fipaos.ont.fipa.fipaman.AgentID();
	agentid.setName(name);
	l1 = new LinkedList();
	l1.add(server);
	l1.add(port);		
	agentid.setAddresses(l1);
}


/**
* Creates a new Server Socket which listen to new incoming requests.
*/
protected void startConnection(int port, int maxConnections)
{
	try
	{
		server = new ServerSocket(port, maxConnections);

	}
	catch (Exception e)
	{
		System.err.println("ERROR: Failed to Create Server Socket ==");
		e.printStackTrace();
	}
}

	
	
/**
* Uses FIPA-OS algorithm to create a new ConversationID
*/
public String createConversationID()
{

	//initialise local variables
	String conversationID = null;
	String stringOfTime = null;
	String _agent_name = agentid.getName();
	
	//intrement count instance variable by 1
	this.running_clients++;

	//build conversationID by concatenating agentguid to current time plus count
	long currentTime = System.currentTimeMillis();
	stringOfTime = "" + currentTime;

	//concat current_time
	conversationID = _agent_name + stringOfTime + running_clients;
	return conversationID;

}


/**
 * Returns the port.
 * @return int
 */
public int getPort() {
	return port;
}

/**
 * Returns the running.
 * @return boolean
 */
public boolean isRunning() {
	return running;
}

/**
 * Sets the port.
 * @param port The port to set
 */
public void setPort(int port) {
	this.port = port;
}

/**
 * Sets the running.
 * @param running The running to set
 */
public void setRunning(boolean running) {
	this.running = running;
}

}
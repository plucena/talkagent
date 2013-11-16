package br.usp.semanticagent.dispatcher;

/**
 * Insert the type's description here.
 * Creation date: (14/06/02 17:47:52)
 * @author: 
 */

import java.util.*;
import java.net.*;
import br.usp.talkagent.basicagent.*;


public class DispatcherAgent extends SharpServerAgent 
{
private boolean running;
private int port;

/**
* Dispatcher constructor comment.
*/

public DispatcherAgent() 
{
	super();	
	try {
		sentMessages = new Vector(10);
		recievedMessages = new Vector(10);	
	}	 
	catch (Exception e) 
	{
		e.printStackTrace();
	}
}


/**
// create server that listens to new incoming messages
// which are dispatched to a separate Dispatcher Thread
*/

 
public void run() 
{
	this.running = true;	
	try 
	{	    
		while(running)
	    {
		   //System.err.println("- Dispatcher Agent is waiting for new messages...");   		 
		   Dispatcher dispatcher = new Dispatcher(server.accept(),agentid,amsclient);
		   dispatcher.execute();
	    } 
	}
	catch (Exception e) 
	{
		e.printStackTrace();
	}
}


/**
 * Insert the method's description here.
 * Creation date: (31/07/02 15:34:39)
 */
public void init()
{
	try
	{
		this.port = 5045;
		this.setId("sharp_dispatcher_agent",InetAddress.getLocalHost().getHostAddress(),"5045");
	    this.startConnection(this.port, 100);
		System.err.println("- Starting Dispatcher Agent on " + InetAddress.getLocalHost().getHostAddress() + ":" + this.port); 
	}
	catch (Exception e)
	{
		e.printStackTrace();
	}
}

}
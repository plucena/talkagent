package br.usp.talkagent.ucl.converter.agent;

/**
 * Insert the type's description here.
 * Creation date: (19/06/02 16:33:22)
 * @author: 
 */

import java.net.InetAddress;
import java.util.Vector;
import br.usp.talkagent.basicagent.AmsClient;
import br.usp.talkagent.basicagent.SharpServerAgent;
import br.usp.talkagent.util.PrefReader;
import java.util.*;

public class UclAgent extends SharpServerAgent 
{
/**
Reads a NL sentence, including performatives, convert it to UCL
and send it to Dispatcher agent
**/
	private String io_file;	
	private int port;	
	private boolean running;	
	private String tt_host;
	private Router router;	
	private Vector runningClients;
	
public void run()
{
	this.running = true;	
	try
	{			    
	    while(running)
	    {  
		   // Create new conversation id
		   String conversationID = this.createConversationID();
		   if(amsclient==null)
		   		System.err.println("ERROR@UCL-CONVERTER -- NULL AMSCLIENT ");
		   this.runningClients.addElement(new UclClient(server.accept(),conversationID,tt_host,io_file,amsclient));		   
	    }   		   
	}
	catch (Exception e) 
	{		
		e.printStackTrace();
	}
}
	
/**
* UclAgent constructor comment.
*/
public UclAgent() {
	super();
	try {
		this.runningClients = new Vector(10);
		PrefReader pref = new PrefReader();
		this.tt_host = pref.getTTHost();
		this.io_file = "../db/AAout.xml";
		this.port = 5044;	
	}	 
	catch (Exception e) 
	{
		e.printStackTrace();
	}
}

/**
* Insert the method's description here.
* Creation date: (31/07/02 11:50:34)
*/        

public void init()
{
	try
	{
		this.port = 5044;
		this.startConnection(this.port, 100);
		this.setId("sharp_ucl_converter",InetAddress.getLocalHost().getHostAddress(),"5044");
		this.amsclient.register(this.agentid);
		System.err.println("------------------------------------------------------------");
		System.err.println("- Starting UCL-Converter Agent on " + InetAddress.getLocalHost().getHostAddress() + ":" + this.port);
		System.err.println("------------------------------------------------------------");
		this.router = new Router(runningClients,this.amsclient);
		this.router.start();	
	}
	catch (Exception e)
	{
		e.printStackTrace();
	}
}

}
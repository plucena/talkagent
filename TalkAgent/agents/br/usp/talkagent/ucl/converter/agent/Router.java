package br.usp.talkagent.ucl.converter.agent;

import java.io.BufferedInputStream;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Vector;
import br.usp.talkagent.aclmessages.ACLReader;
import br.usp.talkagent.basicagent.AmsClient;
import br.usp.talkagent.basicagent.SharpServerAgent;
import br.usp.talkagent.sentence.*;
import fipaos.ont.fipa.ACL;

/**
 * @author: 
 */
public class Router extends SharpServerAgent 
{
	private Socket connection;
	private fipaos.ont.fipa.ACL msg;
	private int port;
	private boolean running;
	private java.util.Vector runningClients;


/**
 */
public Router(Vector Clients, AmsClient ams) 
{
	super();
	try
	{		
		this.runningClients = Clients;
		this.port = 5042;
		this.startConnection(this.port, 100);
		this.setId("sharp_ucl_converter_router",InetAddress.getLocalHost().getHostAddress(),"5042");
		this.amsclient.register(this.agentid);
		System.err.println("- Starting UCL Agent Router on " + InetAddress.getLocalHost().getHostAddress() + ":" + this.port);

	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
}


/**
 */
public ACL getMsg() 
{
	return msg;
}


/**
 * @return java.lang.String
 * @param conn java.net.Socket
 */
public void route(ACL msg)
{
	int i;
	
	ACLReader aclreader = new ACLReader(msg);
	UclClient client;
	String cv_id = aclreader.getConversationID();
		
	for(i=0;i<runningClients.size();i++)
	{
		// search vector for client whose conversation_id == recieved message
		client = (UclClient) runningClients.elementAt(i);
		if(cv_id.equalsIgnoreCase(client.getCID()))
		{
			System.err.println("DEBUG@UCL.Router - Client Agent Found Ready to sent it incoming message ");
			String message = ((Message) aclreader.getMsgContent()).printHTML();
			client.sendDataToClient(message); 
		}
	}
	
}


/**
 */
public void run() 
{	
	this.running = true;
		
	try
	{			    
	    while(running)
	    {
		 	connection = server.accept();
		 	System.err.println("DEBUG@UCL.Router - New message recieved  ");					   
		 	// read message		 	
		    ACL aclmsg;
	    	ObjectInputStream input;
			input = new ObjectInputStream(new BufferedInputStream(connection.getInputStream()));
			aclmsg = ((fipaos.ont.fipa.ACL)input.readObject());

		    // search vector client whose conversation_id == recieved_message
		    this.route(aclmsg);
		    
		    
		    // pass message content to client, desblocking it
		   
	    }
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
}

}

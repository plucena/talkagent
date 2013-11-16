package br.usp.semanticagent.dispatcher;

/**
* Thread Class that treats incoming messages
*/

import java.net.*;
import java.io.*;
import fipaos.ont.fipa.fipaman.*;
import java.util.*;
import br.usp.talkagent.basicagent.*;
import br.usp.talkagent.aclmessages.*;
import fipaos.ont.fipa.ACL;


public class Dispatcher extends SharpAgent 
{

private Vector FipaPerformatives;


/**
 * Add a new association between a given agent and a performative 
 * @param PerformativeName java.lang.String
 * @param AgentName java.lang.String
 */ 
public void addAssociation(String PerformativeName, String AgentName) 
{
Performative aux;
AgentID agent;
	
	aux = this.getPerformative(PerformativeName);
	if(aux!=null)
	{
	// System.err.println("@DEBUG: Performative found. OK!");
	try
	{
		// System.err.println("@DEBUG: Searching Agent " + AgentName);
		agent = this.amsclient.getAgentID(AgentName);
		if(agent!=null)
		{
	 	   // System.err.println("@DEBUG: Agent found. OK!");
		   aux.addAssociation(agent);
		}
	}
	catch(Exception e)
	{		
		e.printStackTrace();
	}	
	}
	else
	System.err.println("@ERROR: Could not find performative " + PerformativeName);
}

/**
* Registers a new performative to Dispatcher
*/ 
public void addPerformative(String PerformativeName) 
{
	this.FipaPerformatives.addElement(new Performative(PerformativeName));
}

 
/***
* Dispatch: Based on Performative Type and Content, dispatch msg to
* knowledge manager or action manger or other agent associated to peformative.
*/
public void execute()
{
	ACL aclmsg;
	String messagePerformative;
	Performative perf;
	AgentID DispatchTo;
	Vector AssociatedAgents;

	try
	{  
		aclmsg = this.readMsg();
		
		// check message content
		// System.err.println("Parsing message");
		if (aclmsg != null)
		{
			messagePerformative = aclmsg.getPerformative();
			System.err.println("DEBUG@DISPTCHER - New message recieved " + messagePerformative);
			// For now, it will disptach message to first agent associated with performative.
			// futher analysis can be done, in a next version.
			if (messagePerformative != null)
			{
				perf = this.getPerformative(messagePerformative);
				AssociatedAgents = perf.getAssociatedAgents();
				if (AssociatedAgents != null)
				{
					DispatchTo = (AgentID) AssociatedAgents.elementAt(0);
					aclmsg.setReceiverAID(DispatchTo);
					addressParser addr = new addressParser(DispatchTo.getAddresses());	
					System.err.println("DEBUG@DISPATCHER - Redirecting recieved message to " + DispatchTo.getName() + "@" + addr.IP + ":" + addr.port);
					
					this.dispatchMessage(aclmsg);					
				}
				else
					System.err.println("ERROR@DISPATCHER: There is no agent associated to " + messagePerformative + " performative"); 
			}
		}
		else
			System.err.println("ERROR@DISPATCHER: Could not forward message. Error reading Message == "); 

	}
	catch (Exception e)
	{
		e.printStackTrace();
	}

}
 

/**
* Search for a specific Performative @ FipaPerformative Vector
* Returns performative if found. 
* @return br.usp.icmc.java.sharp.dispatcher.Performative
* @param performative_name java.lang.String
*/
private Performative getPerformative(String performative_name)
{
	int i = 0;
	boolean found = false;

	while ((i < FipaPerformatives.size()) && !found)
	{
		if (((Performative) FipaPerformatives.elementAt(i)).getAclPerformative().equalsIgnoreCase(performative_name))
		{
			found = true;
			return ((Performative) FipaPerformatives.elementAt(i));
		}
	i++;
	}
	System.err.println("ERROR@DISPATCHER - Performatiive: " + performative_name + " not found");
	return null;
	
}



/**
 */
protected void init()
{
	try
	{
		this.FipaPerformatives = new Vector(10);
		this.loadFipaTypes();
		this.setPerformativesDefaultAssociation();		
	}
	catch (Exception e) 
	{
		e.printStackTrace();
	}
}


/**
 * List Association between agents and performatives.
 */ 
public void listAssociations() 
{
	
}



/**
 * Load FIPA Performatives Used on Agent to Agent Communication
 */ 
private void loadFipaTypes()
{
	this.FipaPerformatives.addElement(new Performative("ACL_REQUEST"));  	// Request Action
	this.FipaPerformatives.addElement(new Performative("ACL_QUERYREF")); 	// Query KB
	this.FipaPerformatives.addElement(new Performative("ACL_QUERYIF")); 	// Query KB
	this.FipaPerformatives.addElement(new Performative("ACL_INFORM"));   	// Add Information to KB
	this.FipaPerformatives.addElement(new Performative("ACL_AGREE"));    	// Agree
	this.FipaPerformatives.addElement(new Performative("ACL_CANCEL"));  	// Cancel
	this.FipaPerformatives.addElement(new Performative("ACL_CONFIRM"));  	// Confirm
	this.FipaPerformatives.addElement(new Performative("ACL_DISCONFIRM"));	// Disconfirm
	this.FipaPerformatives.addElement(new Performative("ACL_REFUSE"));      // Refuse
}



/**
 * Creates default association between agents and performatives used by dispatcher to route
 * messages between agents. 
 */
private void setPerformativesDefaultAssociation() 
{
	this.addAssociation("ACL_QUERYREF","sharp_knowledge_manager");
	this.addAssociation("ACL_QUERYIF","sharp_knowledge_manager");
	this.addAssociation("ACL_INFORM","sharp_knowledge_manager"); 
	//this.addAssociation("ACL_REQUEST","action_manager");
}


public Dispatcher(Socket socket, fipaos.ont.fipa.fipaman.AgentID agtid, AmsClient ams)
{
	super();
	this.connection = socket;
	this.amsclient = ams;
	this.agentid = agtid;
	this.init();

}

/**
 * Dispatch Message - Creates a socket and* sends a message to a given agent
 */
private void dispatchMessage(ACL msg) 
{
	Socket socket;
	ObjectOutputStream output;
	AgentID sendto;  
    
	try
	{
		sendto = msg.getReceiverAID();
		addressParser addr = new addressParser(sendto);
		addr.parse();
		socket = new Socket(addr.IP, addr.port);
		output = new ObjectOutputStream(socket.getOutputStream());
		//System.err.println("Writing -- Socket connected to port: " +  connection.getLocalPort());
		output.writeObject(msg);
		output.flush();
		socket.close();
	}
	catch (Exception e)
	{
		System.err.println("ERROR@DISPATCHER -  Error writing to socket! ");
		e.printStackTrace();
	}

	
}

}
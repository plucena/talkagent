package br.usp.talkagent.ams;

import java.net.Socket;
import java.util.Vector;
import br.usp.talkagent.aclmessages.ACLReader;
import br.usp.talkagent.aclmessages.ACLWriter;
import br.usp.talkagent.aclmessages.addressParser;
import br.usp.talkagent.basicagent.SharpAgent;
import fipaos.ont.fipa.ACL;
import fipaos.ont.fipa.fipaman.AgentID;
import java.io.*;

public class AMS extends SharpAgent
{

private java.util.Vector AgentDB;
private PrintWriter output;

/** 
 * AMS constructor comment.
 */
public AMS(Socket socket, Vector AgentsDB, AgentID agentid, PrintWriter LogFile) 
{		
	super();
	this.connection = socket;
	this.AgentDB = AgentsDB;
	this.agentid = agentid;
	this.output = LogFile;
	this.manageRequest();
}

private int manageRequest()
{
	try
	{
	String Performative;
	// read message from connection
	ACLReader aclreader = new ACLReader(this.readMsg());
	Performative = aclreader.getPerformative();
	String action = (String) aclreader.getMsgContent();	
	String aux = "New message: " +  action + "\n";	    
    this.output.write(aux);
	this.output.flush();

	if (Performative.equalsIgnoreCase("ACL_REQUEST"))
	{

		if (action.equalsIgnoreCase("Register"))
		{
			this.register(aclreader.getSender());
			return 0;
		}
		if (action.equalsIgnoreCase("DisRegister"))
		{			
			this.disRegister(aclreader.getSender());
			return 0;
		}
		if (action.equalsIgnoreCase("BroadCast"))
		{
			this.broadcast(aclreader.aclmsg);
			return 0;
		}
		if (action.equalsIgnoreCase("List"))
		{
			this.list(aclreader.getSender());
			return 0;
		}
		System.err.println("WARNING@AMS - Unknown ACL_REQUEST: " + action);
	}
	else
		if (Performative.equalsIgnoreCase("ACL_QUERYREF"))
		{
			if (action.startsWith("getAddress"))
			{
				this.getAddr(aclreader.aclmsg);
				return 0;
			}
		}
		else
			System.err.println("WARNING@AMS - Unknown ACL_QUERYREF " + action);
	}
	catch(Exception e)
	{
		System.err.println("ERROR@AMS - " + e.getMessage());
	}
	return -1;
}




private void broadcast(ACL Agent) 
{
// must implement it!
  System.err.println(Agent.toString());
}


private void disRegister(AgentID agent) 
{
	while(AgentDB.contains(agent))
		AgentDB.remove(agent);
}



private AgentID getAddr(ACL msg)
{

	AgentID request_agt = msg.getSenderAID();
	String UnknowAgentName = ((String) msg.getContentObject()).substring(12);
	AgentID aux;
	AgentID UnknowAgent;
	int i;
	boolean found = false;	
	
	if (request_agt != null)
	{
		//System.err.println("@DEBUG -- Searching Agent: " + UnknowAgentName);
		try
		{
			// Search AgentDB to find agent;
			i = 0;
			while ((i < AgentDB.size()) && (!found))
			{
				aux = (AgentID) AgentDB.elementAt(i);
				if (aux.getName().equalsIgnoreCase(UnknowAgentName))
				{
					UnknowAgent = aux;
					found = true;
					ACLWriter aclwriter = new ACLWriter("ACL_INFORM", UnknowAgent, agentid, request_agt); 
					this.sendMsg(aclwriter.aclmsg);					
					return aux;
				}
				i++;
			}

			// Send Message to reciever with AgentID of the Agent
			if (!found)			
			{
				System.err.println("WARNING@AMS - Agent Not Found: " + UnknowAgentName);
				ACLWriter aclwriter = new ACLWriter("ACL_INFORM", "Agent Address Not Found", agentid, request_agt);  
				this.sendMsg(aclwriter.aclmsg);
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	else
	{		
		System.err.println("ERROR@AMS: Agent Requesting Service Has Null Address");
		return null;
	}
	// agent not found
	System.err.println("WARNING@AMS: Agent Not Found: " +  UnknowAgentName);
	return null;
}





/** 
 * Register. Adds AgentID to AgentDB.  AgentID contains name, string
 */
private void register(AgentID agent)
{

	if (agent != null)
	{
		try
		{
			addressParser addr = new addressParser((java.util.List)agent.getAddresses());
			String aux = "*AMS* " + addr.IP + ":" + addr.port + " - " + agent.getName() +  "\n";
			if(output!=null)
			{
				this.output.write(aux);
				this.output.flush();
			}
			else
				System.err.println("ERROR@AMS - Could not write to log file");
			AgentDB.addElement(agent);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	else
		System.err.println("Error: Agent is trying to register with null address");

}


public void list(AgentID agent)
{		
	if (agent != null)
	{
		ACLWriter writer = new ACLWriter("ACL_INFORM",this.AgentDB,this.agentid,agent);
		this.sendMsg(writer.aclmsg);		
	}
	else
		System.err.println("Error: Agent is trying to register with null address");
}



}

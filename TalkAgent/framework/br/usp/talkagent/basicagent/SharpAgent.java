package br.usp.talkagent.basicagent;

import java.util.*;
import java.io.*;
import fipaos.ont.fipa.fipaman.*;
import fipaos.ont.fipa.*;
import java.net.*;


public class SharpAgent extends Thread 
{

protected AgentID agentid;
protected boolean running;
protected Socket connection;
protected Socket outputSocket;
protected AmsClient amsclient;
protected String conversationID;

/**
 * SharpAgent constructor comment.
 */
public SharpAgent() 
{
	super();
}


/**
 * SharpAgent constructor comment.
 */
public SharpAgent(AgentID agentid) 
{
	super();
	this.agentid = agentid;
}

/**
 * SharpAgent constructor comment.
 */
public SharpAgent(AgentID agentid, AmsClient amsclient) 
{
	super();
	this.agentid = agentid;
	this.amsclient = amsclient;
}


/**
 */
protected void init() 
{
}


/***
* Read an ACL Message from current connection.
* Returns ACL message.
*/
protected ACL readMsg() 
{
ACL aclmsg;
ObjectInputStream input;

try
{	
	//System.err.println("Reading -- Socket connected to port: " +  connection.getLocalPort());
	input = new ObjectInputStream(new BufferedInputStream(connection.getInputStream()));
	aclmsg = ((fipaos.ont.fipa.ACL)input.readObject());
	return aclmsg;
}
catch(Exception e)
{
	System.err.println("AGENT INTERNAL ERROR - " + e.getMessage());
}

return null;
}


/**
* Send an ACL Message through current conncetion.
*/
public void sendMsg(ACL msg)
{
	ObjectOutputStream output;

	try
	{
		if(connection!=null)
		{
			output = new ObjectOutputStream(connection.getOutputStream());
			output.writeObject(msg);
		}
		else
		{
			throw new Exception("Socket is not connected");
		}
	}
	catch (Exception e)
	{
		System.err.println("== Error on writing to socket == ");
		e.printStackTrace();
	}

}


/**
* Set Agent Identity
*/
protected void setId(AgentID agentid) 
{
	
	this.agentid = agentid;
}


/**
* Set Agent Identity
*/
protected void setId(String name, String server, String port) 
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
 * @return fipaos.ont.fipa.fipaman.AgentID
 * @param AgentName java.lang.String
 */
public AgentID getAgentID(String AgentName) 
{
	return amsclient.getAgentID(AgentName);
}


}
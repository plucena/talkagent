package br.usp.talkagent.aclmessages;

// **
// ACLReader allows to extract contents from an ACL Message
// *

import java.net.*;
import java.io.*;
import fipaos.ont.fipa.fipaman.*;
import fipaos.ont.fipa.*;

public class ACLReader 
{

public ObjectInputStream reader;
private java.lang.String performative;
public java.net.Socket socket;
public fipaos.ont.fipa.ACL aclmsg;
	
/**
 */
public ACLReader(ACL msg) 
{
	super();
	this.aclmsg = msg;
}

/**
 */
public ACLReader(Socket sock) 
{
	super();	
	this.socket = sock;
	try
	{
	reader = new ObjectInputStream( new BufferedInputStream(socket.getInputStream()));
	aclmsg = ((fipaos.ont.fipa.ACL)reader.readObject());
	this.performative = aclmsg.getPerformative();
	} 
	catch(Exception e)
	{
		System.err.println(e.toString());
	}	

}


/**
 * @return java.lang.String
 */
public String getConversationID() 
{
	return aclmsg.getConversationID();
}


/**
 */
public Object getMsgContent() 
{
	try
	{
		return (aclmsg.getContentObject());	
	}
	catch(Exception e)
	{
		System.out.println(e.toString());	    
	}
	return null;
}


/**
 * @return java.lang.String
 */
public String getPerformative() 
{
	return aclmsg.getPerformative();
}


/**
 */
public AgentID getReciever() 
{
	return aclmsg.getReceiverAID(); 
}


/**
 * @return fipaos.ont.fipa.fipaman.AgentID
 */
public AgentID getSender() 
{
	return aclmsg.getSenderAID();
}

}

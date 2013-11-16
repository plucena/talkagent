package br.usp.talkagent.basicagent;

/**
 * @author: Percival Lucena
 */

import fipaos.ont.fipa.*;
import fipaos.ont.fipa.fipaman.*;
import java.net.*;
import br.usp.talkagent.aclmessages.*;
import br.usp.talkagent.util.*;
import java.io.*;
import java.util.Vector;

public class AmsClient
{
	
public fipaos.ont.fipa.fipaman.AgentID amsServer;
public addressParser amsAddr;
protected fipaos.ont.fipa.fipaman.AgentID agentid;
private String amsaddr;
private Socket connection;
private String configfile;
private boolean cfg;
private boolean param;
private boolean connected;

/**
*/

public AmsClient()
{
		super();
		this.cfg=false;
		this.param=false;
		this.Connect();
}


public AmsClient(String configfile)
{
		super();
		this.cfg=true;
		this.param=false;
		this.configfile = configfile;				
		this.Connect();
}


public AmsClient(String host, int Port)
{
		super();
		// connect
		try
		{	
			this.cfg=false;		
			this.param=true;
			amsAddr = new addressParser(host, Port);
			this.Connect();
		}
		catch(Exception e)
		{e.printStackTrace();}
}


public void Connect()
{
PrefReader prefs;

	try
		{
			if(this.param)
			{				
				this.connection	 = new Socket(amsAddr.IP, amsAddr.port);
				if(this.connection!=null)
					this.connected = true;
			}
			else
			{
				if(this.cfg)
				{
					 prefs = new PrefReader(configfile);
				}
				else
				{
					 prefs = new PrefReader();
				}
				this.amsaddr = prefs.getAmsClient();		
				amsAddr = new addressParser(amsaddr, 5040);
				this.connection	 = new Socket(amsAddr.IP, amsAddr.port);
				if(this.connection!=null)
					this.connected = true;
			}
		}	
		catch(Exception e)
		{
			e.printStackTrace();
		}
}

public void disConnect()
{
	try
	{
		this.connection.close();
		this.connected = false;
	}
	catch(Exception e)
	{ 
		e.printStackTrace(); 
	}
}

/**
 Set AMS Server address
*/
public void setAMS(String ip, int port)
{
		amsAddr.IP = ip;
		amsAddr.port = port;
}


/**
*/
public void disRegister(AgentID agentid) 
{
	ACLWriter aclwriter =  new ACLWriter("ACL_REQUEST", "Disregister", agentid, amsServer);
	// this.sendMsgAMS(aclwriter.aclmsg);
	this.sendMsg(aclwriter.aclmsg);
	System.err.println("disregistering agent from AMS");
}


/**
 * @return fipaos.ont.fipa.fipaman.AgentID
 * @param AgentName java.lang.String
 */ 
public AgentID getAgentID(String AgentName)
{
	try
	{		
	// send message request agentID from agentName
	
		ACLWriter aclwriter = new ACLWriter("ACL_QUERYREF","getAddress: " + AgentName, agentid, amsServer); 
		this.Connect();
		this.sendMsg(aclwriter.aclmsg);
		// get content from recieved message
		ACLReader aclreader = new ACLReader(this.readMsg());
		return (AgentID) aclreader.getMsgContent();	
	}
	catch (Exception e)
	{
		e.printStackTrace();
	}
	return null;
}


public void register(AgentID agentid) 
{
	this.agentid = agentid;
	ACLWriter aclwriter =  new ACLWriter("ACL_REQUEST", "Register", agentid, amsServer);
	this.sendMsg(aclwriter.aclmsg);
	//this.sendMsgAMS(aclwriter.aclmsg);
	//System.err.println("Registering agent to AMS");
}


public Vector listAgents()
{
Vector agents;
	
	ACLWriter aclwriter =  new ACLWriter("ACL_REQUEST", "List", agentid, amsServer);
	this.Connect();
	this.sendMsg(aclwriter.aclmsg);
	ACLReader aclr = new ACLReader(this.readMsg());
	agents = (Vector) aclr.getMsgContent();
	return agents;	
}


public AgentID getSelfID()
{
	return this.agentid;
}

/**
 * Returns the amsAddr.
 * @return addressParser
 */
public String getAmsAddr() 
{
	return amsaddr;
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
	input = new ObjectInputStream(new BufferedInputStream(connection.getInputStream()));
	aclmsg = ((fipaos.ont.fipa.ACL)input.readObject());
	return aclmsg;
}
catch(Exception e)
{
	e.printStackTrace();
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
		//this.Connect();
		if(connection!=null)
		{
			output = new ObjectOutputStream(connection.getOutputStream());
			output.writeObject(msg);		
		}
		else
		{
			
			System.err.println("Socket is not connected");
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
 * Returns the connected.
 * @return boolean
 */
public boolean isConnected() {
	return connected;
}

}
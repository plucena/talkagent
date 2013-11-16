package br.usp.talkagent.basicagent;

/**
 * @author Administrator
 */

import java.net.Socket;
import java.io.BufferedInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import fipaos.ont.fipa.ACL;
import fipaos.ont.fipa.fipaman.AgentID;
import br.usp.talkagent.aclmessages.ACLReader;
import br.usp.talkagent.aclmessages.ACLWriter;
import br.usp.talkagent.aclmessages.addressParser;
import br.usp.talkagent.basicagent.AmsClient;
import br.usp.talkagent.aclmessages.Address;

public class AgtConnector 
{

private AmsClient amsclient;
private String recieverName;
private AgentID reciever;
private AgentID sender;
private ACLWriter aclwriter;
private addressParser addr;
private Socket con;
private ObjectOutputStream output;
private ACL aclmsg;
private ObjectInputStream input;
private ACLReader aclreader;

	
public AgtConnector(AmsClient ams, AgentID Sender, String Reciever)
{
	super();
	this.amsclient = ams;
	this.sender = Sender;	
	this.recieverName = Reciever;
	reciever = this.amsclient.getAgentID(Reciever);
}


public AgtConnector(AmsClient ams, AgentID Sender, AgentID Reciever)
{
	super();
	this.amsclient = ams;
	this.sender = Sender;	 
	reciever = Reciever;;
}


public void Connect()
{
	try
	{
		addr = new  addressParser(reciever);
		addr.parse();
		con = new Socket(addr.IP,addr.port);
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
		con.close();
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}	
}



public void SendString(String perf, String msg)
{
	try
	{
		aclwriter = new  ACLWriter(perf, msg, sender, reciever);
		output = new ObjectOutputStream(con.getOutputStream());
		output.writeObject(aclwriter.aclmsg);
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}	
}


public void SendObject(String perf, Object obj)
{
try
	{
		aclwriter = new  ACLWriter(perf, obj, sender, reciever);
		output = new ObjectOutputStream(con.getOutputStream());
		output.writeObject(aclwriter.aclmsg);
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
}


public void sendMessage(ACL msg)
{
boolean ok=false;
try
	{
	    if(msg!=null)
	    if(msg.getContentObject()!=null)
	    {
			output = new ObjectOutputStream(con.getOutputStream());
			output.writeObject(msg);
			ok=true;
	    }
	    if(!ok)
	    	System.err.println("ERROR@CONNECTOR - MESSAGE HAS NULL CONTENTS");
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
}	
	

public String ReadString()
{
	try
	{
		input = new ObjectInputStream(new BufferedInputStream(con.getInputStream()));
		aclmsg = ((fipaos.ont.fipa.ACL)input.readObject());
		aclreader = new ACLReader(aclmsg);
		return (String) aclreader.getMsgContent();
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	return null;
}


public Object ReadObject()
{
	try
	{
		input = new ObjectInputStream(new BufferedInputStream(con.getInputStream()));
		aclmsg = ((fipaos.ont.fipa.ACL)input.readObject());
		aclreader = new ACLReader(aclmsg);
		return aclreader.getMsgContent();
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	return null;		
}


public ACL ReadMessage()
{
	try
	{
		input = new ObjectInputStream(new BufferedInputStream(con.getInputStream()));
		aclmsg = ((fipaos.ont.fipa.ACL)input.readObject());
		return aclreader.aclmsg;
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	return null;		
}



public AgentID getMsgSender()
{
	try
	{	
		if(aclreader!=null)
			return aclreader.getSender();
			
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	return null;
}


public String getMsgPerformative()
{
	try
	{	
		if(aclreader!=null)
			return aclreader.getPerformative();
			
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	return null;
}


public String getMsgConversationID()
{
	try
	{	
		if(aclreader!=null)
			return aclreader.getConversationID();
			
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	return null;
}


public Address getRecieverAddr()
{
	addressParser addrp = new addressParser(this.reciever);
	addrp.parse();	
	Address addr = new Address(addrp.IP,addrp.port);
	return addr;
} 

/**
 * Returns the amsclient.
 * @return AmsClient
 */
public AmsClient getAmsclient() {
	return amsclient;
}

}

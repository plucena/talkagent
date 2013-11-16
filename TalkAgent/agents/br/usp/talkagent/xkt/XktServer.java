package br.usp.talkagent.xkt;

import br.usp.talkagent.basicagent.*;
import br.usp.talkagent.ontology.tt.ttOzoneTreeKB;
import br.usp.talkagent.util.*;
import java.net.*;

public class XktServer extends SharpServerAgent
{
	public int port;
	public boolean running;
	public ttOzoneTreeKB kb;
	private java.lang.String kb_file;		
	private String ttServerIP;
	private String ozoneServer;

/**
 */
public XktServer() 
{
	super();
	PrefReader pref = new PrefReader();
	this.ttServerIP = pref.getTTHost();
	this.ozoneServer = pref.getOzoneServer();
	// sets default knowledge base
	this.kb_file = "../db/kb.xml";	 
}		



/**
 */
protected void init() 
{
boolean restoreTTKB=true;
	
 try
 {
	this.port = 5049;
	this.startConnection(this.port, 100);
	kb = new ttOzoneTreeKB(this.ttServerIP,this.ozoneServer); 
	
	// check if knowledge base exists
    PrefReader pr = new PrefReader();
    restoreTTKB = pr.isRestore_tt_ontology();

	if (restoreTTKB) 
	{
		// imports a new KB from TT into Ozone
		System.err.println("WARNING@XKT - Restoring KB from TT Server");
		kb.Restore();
	}
	else
	{
		// loads Ozone TT KB;
		System.err.println("MESSAGE@XKT - Loading Ozone TTKB");		
		kb.Load();
	}
	this.setId("sharp_xkt_kb",InetAddress.getLocalHost().getHostAddress(),"5049");
	System.err.println("------------------------------------------------------------");
	System.err.println("- Starting XKT-KB Server on " + InetAddress.getLocalHost().getHostAddress() + ":" + this.port);
	System.err.println("------------------------------------------------------------");
 }
 catch(Exception e)
 {
	 e.printStackTrace();
 } 
}


/**
 */
public void run()
{
	this.running = true;
	try
	{
		while (running)
		{  
			XktConnector xkt = new  XktConnector(server.accept(), agentid, amsclient, kb);
			xkt.execute();
		}
	}
	catch (Exception e)
	{
		e.printStackTrace();
	}
}


/**
 */
public void setTTServer() 
{
}


/**
 * @param kb_file java.lang.String
 */ 
public void setKB(String kb_file) 
{
	this.kb_file = kb_file;
}


}
package br.usp.talkagent.ams;

import br.usp.talkagent.basicagent.*;
import java.util.*;
import java.net.*;
import java.io.*;

public class AmsManager extends SharpServerAgent
{
	public Vector AgentsDB;
	private boolean running;
	private int port;
		
/**
 * AmsManager constructor comment.
 */
public AmsManager() 
{
	super(false);
	// init
	this.running = true;
	this.port = 5040;
	this.AgentsDB = new Vector(10);
	
	try 
	{		
	    this.startConnection(this.port,100);	    
		System.err.println("- Starting Agent Managment Service (AMS) on " + InetAddress.getLocalHost().getHostAddress() + ":" + this.port); 
		PrintWriter out = new PrintWriter(new FileWriter(".ams.log"));
			
		// should create new treads for each program it starts...						
	    while(running)
	    {
		   new AMS(server.accept(),AgentsDB,this.agentid,out);		   
	    } 
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
}
}




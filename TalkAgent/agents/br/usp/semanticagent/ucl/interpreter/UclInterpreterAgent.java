package br.usp.semanticagent.ucl.interpreter;

/**
 * @author: Percival Lucena 
 */

import java.net.InetAddress;
import br.usp.talkagent.basicagent.SharpServerAgent;


public class UclInterpreterAgent extends SharpServerAgent 
{
	
private int port;	
public boolean running;


/**
 */
public UclInterpreterAgent() 
{
	super();
}


/**
 */
public void init()
{
	try
	{
		this.running = true;
		this.port = 5046;
		this.startConnection(this.port, 100);
		this.setId("sharp_ucl_interpreter",InetAddress.getLocalHost().getHostAddress(),"5046"); 
		System.err.println("------------------------------------------------------------");
		System.err.println("- Starting UCL-Interpreter Agent on " + InetAddress.getLocalHost().getHostAddress() + ":" + this.port); 
		System.err.println("------------------------------------------------------------");
	}
	catch (Exception e) 
	{
		System.err.println("@ERROR - Error starting Knowledge Manager Agent ");
	    e.printStackTrace();
	}
}


/**
 */
public void run()
{
	try
	{
		while (running)
		{   /*
			System.err.println("\n=============================================");
			System.err.println(" Knowledge Manager Agent Started");
			System.err.println("\n=============================================");*/
			UclInterpreter xktconnect = new UclInterpreter(server.accept(), agentid, amsclient); 
			xktconnect.execute();
		}
	}
	catch (Exception e)
	{
		System.err.println("@ERROR: Error starting clients. ");
		e.printStackTrace();
		
	}

}}
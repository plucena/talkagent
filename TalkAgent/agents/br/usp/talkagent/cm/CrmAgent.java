package br.usp.talkagent.cm;

/**
 * @author Percival Lucena
 */
import java.net.InetAddress;
import java.net.UnknownHostException;
import br.usp.talkagent.basicagent.*;
import br.usp.talkagent.util.*;
import br.usp.talkagent.cm.db.*;
import br.usp.talkagent.cm.io.*;

public class CrmAgent extends SharpServerAgent 
{
private boolean running;
private int port;
private String OzoneServer;
private CRDB crdb;
private CIndexImpl cindex;
private ComponetsFtpServer ftpserver;
	
	public CrmAgent()
	{
		super(false);
		try {
			
			this.running = true;
			this.port = 5050;
			this.setId("sharp_component_manager",InetAddress.getLocalHost().getHostAddress(),"5050");
			this.amsclient = new AmsClient();
			this.amsclient.register(this.agentid);				
			System.err.println("------------------------------------------------------------");
			System.err.println("- Starting Component Manager Agent on " + InetAddress.getLocalHost().getHostAddress() + ":" + this.port);
			System.err.println("------------------------------------------------------------");				
		} 
		catch (UnknownHostException e)
		{
			e.printStackTrace();
		}		
	}
	
	
	public void run(boolean reset_db)
	{
		this.running = true;
		PrefReader pref = new PrefReader();
		this.OzoneServer = pref.getOzoneServer();
		this.ftpserver = new ComponetsFtpServer(); 		 
		
		this.startConnection(this.port, 100);		
		try
		{
			while (running)
			{  
				ComponentManager cpt = new  ComponentManager(server.accept(), this.agentid, this.amsclient, this.OzoneServer,reset_db);				
			}		
		}
		catch(Exception e)
		{
			e.printStackTrace(); 
		}	
	}



}

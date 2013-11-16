package br.usp.talkagent.useragent;
import java.io.BufferedInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Vector;
import br.usp.talkagent.aclmessages.ACLReader;
import br.usp.talkagent.aclmessages.ACLWriter;
import br.usp.talkagent.aclmessages.addressParser;
import br.usp.talkagent.basicagent.AmsClient;
import br.usp.talkagent.basicagent.SharpAgent;
import fipaos.ont.fipa.ACL;
import fipaos.ont.fipa.fipaman.AgentID;
   

 
class UclAgtConnector extends SharpAgent
{
	public java.util.Vector UnderstoodOptions;
	private static ACLReader aclreader;
	private String performative;
	private String msgContent;
	public boolean error;
	public String emsg;
	private AgentID UCLAgent; 
	private fipaos.ont.fipa.ACL msg;	
	private ObjectOutputStream output;	
	public boolean sentmessage;
	public boolean ka_ok;	
	public boolean ucl_ok;


/**
 * UclAgtConnector constructor comment.
 */
public UclAgtConnector() throws Exception {
	super();
	ucl_ok = false;
	error = false;
	sentmessage = false;
	//options_recieved = false; 
	amsclient = new AmsClient("../platform/db/config.xml");
	try
	{
		this.init();
	}
	catch (Exception e)
	{
		error=true;
		throw e;
	}
}


/**
* Init User Agent
* Creation date: (16/07/02 20:11:44)
*/  
protected void init()
{
addressParser addr;

	try
	{
		// set agent id and register it in AMS Service
		this.setId("sharp_usr_agent",InetAddress.getLocalHost().getHostAddress(),"5043");
		this.amsclient.register(this.agentid); 
		
		// get UCL AGent Adress and connect to it.
		UCLAgent = 	amsclient.getAgentID("sharp_ucl_agent");
		
		if (UCLAgent!=null)
		{	
			addr = new addressParser(UCLAgent);
			addr.parse();		
			this.connection = new Socket(addr.IP, addr.port);
		}
		else
		{
			error = true;
			throw new Exception("== Error: UCL Agent not found " + amsclient.getAmsAddr());			
		}		
		// output = new ObjectOutputStream(socket.getOutputStream());
	}
	catch(Exception e)
	{
	   error=true;	
	   e.printStackTrace();
	}
}

/**
*
*/

public void sendMessage(String Msg, String Performative) throws Exception
{
ACLWriter aclwriter;

	try
	{
	    this.sentmessage = false;	
	    this.performative = Performative;
	    this.msgContent = Msg;
		
		if ((this.performative==null) || (this.msgContent==null))
			throw new Exception("== Error: Trying to send message with null content or performative == ");

		this.setId("sharp_usr_agent",InetAddress.getLocalHost().getHostAddress(),"5043");

		UCLAgent = 	amsclient.getAgentID("sharp_ucl_agent");
		if(UCLAgent != null)
		{	
			aclwriter = new ACLWriter(performative,msgContent,agentid,UCLAgent);
		}
		else
		{
			error=true;
			throw new Exception("<BR><BR><BR> == Error: Could not find UCL Agent == " + amsclient.getAmsAddr() + "<BR><BR><BR>"); 		 
		}	

	      if(aclwriter.aclmsg == null)
	      {
		  	error=true;
		  	throw new Exception("== Error Constructing ACL Message. Its contents are null! == ");
	      }	
		
		// send data to UCL Agent;
		output = new ObjectOutputStream(this.connection.getOutputStream());
		output.writeObject(aclwriter.aclmsg);
		output.flush();
		sentmessage = true;
	}
	catch (Exception e)
	{
		error=true;
		throw e;
	}
}



public void readUclOptions() throws Exception
{
	ACL aclmsg;
	ObjectInputStream input;
	ACLReader aclreader;

	try
	{
		// recieve Understanding Options
		input = new ObjectInputStream(new BufferedInputStream(this.connection.getInputStream())); 
		aclmsg = ((fipaos.ont.fipa.ACL) input.readObject());
		aclreader = new br.usp.talkagent.aclmessages.ACLReader(aclmsg);
		UnderstoodOptions = (Vector) aclreader.getMsgContent();
		ucl_ok = true;
	}
	catch (Exception e)
	{
		ucl_ok = true;
		error = true;
		e.printStackTrace();
		throw e; 
	}

}


/**
 * Insert the method's description here.
 * Creation date: (22/08/02 14:34:04)
 * @return java.lang.String
 */
public String getResult() {
	ACL aclmsg;
	ObjectInputStream input;
	ACLReader aclreader;

	try
	{
		// recieve Understanding Options			
		input = new ObjectInputStream(new BufferedInputStream(this.connection.getInputStream())); 
		aclmsg = ((fipaos.ont.fipa.ACL) input.readObject());
		aclreader = new br.usp.talkagent.aclmessages.ACLReader(aclmsg);
		return (String) aclreader.getMsgContent();
	}
	catch(Exception e)
	{
	    e.printStackTrace();
	}	
	
	return null;
}

public AmsClient getAMS()
{
	return this.amsclient;
}
}
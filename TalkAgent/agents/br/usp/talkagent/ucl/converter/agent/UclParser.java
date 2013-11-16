package br.usp.talkagent.ucl.converter.agent;

/**
Basic Agent Ancestor of Sharp Server Agents.
Server Agents listen to requests of a specific services
and create especialist agents to satisfy request
*/

import java.net.Socket;
import java.util.Vector;
import br.usp.talkagent.aclmessages.ACLWriter;
import br.usp.talkagent.basicagent.AgtConnector;
import br.usp.talkagent.basicagent.AmsClient;
import br.usp.talkagent.basicagent.SharpAgent;
import br.usp.talkagent.ucl.converter.parser.SentenceParser2;
import br.usp.talkagent.ucl.reader.XMLFileReader;
import br.usp.talkagent.aclmessages.*;
import fipaos.ont.fipa.fipaman.AgentID;


public class UclParser extends SharpAgent 
{
	private XMLFileReader xmlreader;
	private String Performative;	
	private String tt_host;
	private SentenceParser2 parser;	
	private String io_file;
	private boolean gotResponse;	
	private String Response;


/**
* Get Command Sentence from user Agent through agent-toagent default connection
*/
private void getDataFromUsrAgent() throws Exception 
{
String Sentence;
Vector Options = new Vector();
int i;

	// recieve Sentence from User Agent, using agent connection.
	ACLReader aclreader = new ACLReader(this.readMsg());
	Sentence = (String) aclreader.getMsgContent();
	Performative = aclreader.getPerformative();
	System.err.println("DEBUG@UCL - Starting new job " + aclreader.getConversationID());	

	// Sewntence Parser Object creates a connection to a TT Host
	// and allows to convert a NL Sentence to UCL writing output 
	// to an XML file. configure xml temp. file output	
	try
	{
		parser = new SentenceParser2(tt_host);
		// get sentemce possible meanings;
		parser.Understand(Sentence, this.io_file);
		for(i=0;i<parser.Options.size();i++)
		{
			if(((String) parser.Options.elementAt(i)).length()>0)
			{
				if(((String) parser.Options.elementAt(i)).indexOf("X-bar") <= 0)
					Options.addElement(parser.Options.elementAt(i));
			}
		}
	}
	catch (Exception e)
	{
		throw e;
	}
	
    // Send Sentence Meaning Options,to User Agent
    AgentID usrAgent = amsclient.getAgentID("sharp_usr_agent");			
    ACLWriter aclwriter = new ACLWriter("ACL_QUERYREF",Options,this.agentid,usrAgent);
    this.sendMsg(aclwriter.aclmsg);
    
	if ((Options == null) || (Options.size() == 0) )
	{
		this.connection.close();
		throw new Exception("ERROR@UCL - Sentence not unserstood. Should stop this request now now"); 

	}

}


/**
 */
private void makeUCL(String Option)
{	
	parser.generateUCL(Option, this.io_file);
	xmlreader = new XMLFileReader(); 

	// create UCLDoc
	xmlreader.read(this.io_file);
	xmlreader.generateUclDoc();

	if (xmlreader.ucl == null)
		System.err.println("ERROR@UCL: UCL was not generated properly");
}


/**
 */
public void parse()
{
	try
	{
		// get Data from User Agent and process it. (Creates UCL Content)
		getDataFromUsrAgent();
		// get correct meaning from User Agent
		String opt = getOptionFromUsrAgent();
		//
		makeUCL(opt);
		// Send resulting UCL content, and FIPA-ACL context to UCL Interpreter Agent
		sendResultstoUclInterpreter();
	}
	catch (Exception e)
	{
		try
		{
			e.printStackTrace();
			connection.close();
		}
		catch (Exception e1)
		{
		}
	}
}


/***
* Create new ACL Message with UCL enconded content, and send it to UCL Interpreter Agent
*/
private void sendResultstoUclInterpreter() 
{

	try
   	{  		
   		AgtConnector interp = new AgtConnector(this.amsclient,this.agentid, "sharp_ucl_interpreter"); 
		interp.Connect();		
		ACLWriter aclwriter = new ACLWriter(this.Performative,xmlreader.ucl,agentid,amsclient.getAgentID("sharp_ucl_interpreter"));
		aclwriter.setConversationID(this.conversationID); 
		interp.sendMessage(aclwriter.aclmsg);
		System.out.println("DEBUG@UCL - Message Sent to UCL Interpreter Agent: ");
		interp.disConnect();
	}

	catch (Exception e)
	{
		System.err.println("=== Error@UCL ");
		e.printStackTrace();
		System.out.println("==========================");
	}
}


/**
 */
public void setIOFile(String filename) 
{
	this.io_file = filename;
}


/**
 */
public void setTTHost(String host) 
{
	this.tt_host = host;
}


/** 
 * Recieves the  correct meaning of the sentence from User Agent
*/
private String getOptionFromUsrAgent() throws Exception
{
String SelectedOption;

	ACLReader aclreader = new ACLReader(this.readMsg());	
	SelectedOption = (String) aclreader.getMsgContent();
	 
	if(aclreader.aclmsg==null)
		throw new Exception("Error@UCL: Read message is null == ");
	
	System.err.println("DEBUG@UCL:Selected Option: " + SelectedOption);	
	System.err.println("DEBUG@UCL User Agent's Perfomative: " + aclreader.getPerformative());
	System.err.println("DEBUG@UCL User Agent's MsgContent: " + aclreader.getMsgContent());
	
	if (SelectedOption == null)
		throw new Exception("ERROR@UCL - Could not read Option from User Agent == ");	
	 
	return SelectedOption;
}	



/**
 */
public UclParser(Socket socket, String tthost, AmsClient amsc, String ConversationID) 
{
	super();
	this.gotResponse = false;
	this.amsclient = amsc;
	this.agentid = amsclient.getSelfID();
	connection = socket;
	tt_host = tthost;
	this.conversationID = ConversationID;
}



/**
 * This may cause deadlock. Should check it carefully!!!
 */
private String getResult() 
{
	while(!gotResponse)
	{
	} // sits waiting
	return Response;
}


/**
 * @param response java.lang.String
 */
public void sendResponse(String response) 
{
	this.Response = response;
	this.gotResponse = true;
    AgentID usrAgent = amsclient.getAgentID("sharp_usr_agent");			
	ACLWriter aclwriter = new ACLWriter("ACL_INFORM", response, this.agentid, usrAgent); 
	this.sendMsg(aclwriter.aclmsg);	
	System.err.println("DEBUG@UCL Client has recieved response " + response);
}


}
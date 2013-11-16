package br.usp.semanticagent.ucl.interpreter;

import java.io.ObjectOutputStream;
import java.net.Socket;
import fipaos.ont.fipa.fipaman.*;
import fipaos.ont.fipa.ACL;
import br.usp.talkagent.aclmessages.ACLReader;
import br.usp.talkagent.aclmessages.ACLWriter;
import br.usp.talkagent.aclmessages.addressParser;
import br.usp.talkagent.basicagent.AmsClient;
import br.usp.talkagent.basicagent.SharpAgent;
import br.usp.talkagent.ucl.UCLDoc;
import fipaos.ont.fipa.fipaman.AgentID;
import br.usp.semanticagent.ucl.scripts.*;
import br.usp.talkagent.sentence.*;
import br.usp.talkagent.kb.*;
import br.usp.semanticagent.ucl.interpreter.action.*;

/** 
 */
public class UclInterpreter extends SharpAgent 
{ 


private XktClient xktc;

/**
 */
public UclInterpreter(Socket socket, AgentID id, AmsClient ams)
{
	super();
	this.connection = socket;
	this.amsclient = ams;
	this.agentid = id;	
	this.xktc = new XktClient(this.amsclient, this.agentid);
}


/**
 */
public void execute()
{
	ACL aclmsg;
	UCLDoc ucl;
    String Performative;
	try
	{
		aclmsg = this.readMsg();
		System.err.println("DEBUG@UCL-INETERPRETER - New message recieved");
		if (aclmsg != null)
		{
			ACLReader aclreader = new ACLReader(aclmsg);
			ucl = (UCLDoc) aclreader.getMsgContent();
			Performative = aclreader.getPerformative();
			System.err.println("performative: " + Performative);
			ucl.print();
			Message msg = this.interpretUCL(Performative,ucl);
			this.sendResultsToUclAgent(aclreader.aclmsg,msg);
		}
		else
			System.err.println("ERROR@UCL-INTERPRETER - Recieved message has null contents");
	}
	catch (Exception e)
	{
		e.printStackTrace();
	}
}


/**
 */
private Message interpretUCL(String Performative, UCLDoc ucldoc) throws Exception
{

	if(Performative.equalsIgnoreCase("ACL_QUERYREF"))
		return this.DoQuery(ucldoc);				
		
	if(Performative.equalsIgnoreCase("ACL_QUERYIF"))
		return this.DoInference(ucldoc);
			
	if(Performative.equalsIgnoreCase("ACL_INFORM"))
		return this.DoInsert(ucldoc);
	
	if(Performative.equalsIgnoreCase("ACL_REQUEST"))
	{
		return this.DoExecute(ucldoc);
	}
		
	System.err.println("ERROR@UCL-INTERPRETER: Unknown Performative " + Performative );
	return null;
}



private Message DoQuery(UCLDoc ucldoc)
{
	try
	{	
		// Query Definition - what
		if(ucldoc.find("object-interrogative-pronoun")!=null)
		{
			ScriptWhat scp = new ScriptWhat(xktc,ucldoc);
 			return scp.run(); 
		}

		// Query Definition - who
		if(ucldoc.find("human-interrogative-pronoun")!=null)	
		{	 
			ScriptWho scp = new ScriptWho(xktc,ucldoc);
 			return scp.run(); 				
		}
	
		// Query ListElements
		if(ucldoc.find("program-output")!=null)	
		{	 
			ScriptList scp = new ScriptList(xktc,ucldoc);
 			return scp.run(); 				
		}
		System.err.println("WARNING@UCL-INTERPRETER: Unknown query type.");	
		ucldoc.getConcepts().print();
		Message msg = new Message();
		return msg;
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	return null;
}



private Message DoInsert(UCLDoc ucldoc)
{
	if(ucldoc.find("isa")!=null)	
	{	
		ScriptInsert scp = new ScriptInsert(xktc,ucldoc);
		return scp.run();		
	}
	
	if(ucldoc.find("standard-copula")!=null)
	{
		ScriptInsert2 scp = new ScriptInsert2(xktc,ucldoc); 
		return scp.run();			
	}	
	System.err.println("WARNING@UCL-INTERPRETER: Couldn'ṫ parse insertion command:");	
	ucldoc.getConcepts().print();
	Message msg = new Message();
	return msg; 

}


private Message DoInference(UCLDoc ucldoc)
{
	System.err.println("DEBUG@UCL-INTERPRETER: Starting simple inference" );
	if(ucldoc.find("isa")!=null)	
	{	
		ScriptIsDef scp = new ScriptIsDef(xktc,ucldoc);
		return scp.run();		
	}
	
	if(ucldoc.find("standard-copula")!=null)
	{
		ScriptIsDef2 scp = new ScriptIsDef2(xktc,ucldoc); 
		return scp.run();			
	}	
	System.err.println("WARNING@UCL-INTERPRETER: Couldn'ṫ start inference:");	
	ucldoc.getConcepts().print();
	Message msg = new Message();
	return msg;
}


private Message DoExecute(UCLDoc ucldoc)
{
	ucldoc.print();
	ScriptAction em = new ScriptAction(this.agentid,this.amsclient,ucldoc);	
	String result = em.execute();
	if(result.length()==0)
		result="Sorry. Could not satisfy your request. No action associated.";	
	Message msg = new Message();
	Sentence st = new Sentence();
	Element el = new Element("other", result);
	st.addElement(el);
	msg.addSentence(st);
	return msg;
}

/**
 */
private void sendResultsToUclAgent(ACL incomingmsg, Object msgtosnd)
{
	ACLWriter aclwriter;	
	String ConversationID;
	AgentID uclAgent;

	try
	{
		// create message to be sent
		ACLReader aclreader = new ACLReader(incomingmsg);
		String Performative = "ACL_INFORM";
		ConversationID = aclreader.getConversationID();
		uclAgent = amsclient.getAgentID("sharp_ucl_converter_router");
		aclwriter =  new ACLWriter(ConversationID, Performative, msgtosnd, this.agentid, uclAgent); 	
		// send message to UCLServer
		addressParser addr = new addressParser(uclAgent);
		addr.parse();
		Socket xktcon = new Socket(addr.IP, addr.port);
		ObjectOutputStream output;
		output = new ObjectOutputStream(xktcon.getOutputStream());
		output.writeObject(aclwriter.aclmsg);
	}
	catch (Exception e)
	{
		e.printStackTrace();
	}
}


}
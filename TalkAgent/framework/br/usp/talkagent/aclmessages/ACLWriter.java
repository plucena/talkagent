package br.usp.talkagent.aclmessages;

/**
 * Insert the type's description here.
 * Creation date: (18/06/02 12:04:00)
 * @author: 
 */

import fipaos.ont.fipa.fipaman.*;
import br.usp.talkagent.ucl.*;

 
public class ACLWriter 
{

public fipaos.ont.fipa.ACL aclmsg;	
/**
Creates an ACLMessage containing a UCLDoc to be streamed
to another agent (knowledge agent, action agent)
*/


public ACLWriter(String Performative, UCLDoc content, AgentID sender, AgentID reciever) 
{
	super();
	this.aclmsg = new fipaos.ont.fipa.ACL();	
//	aclmsg.setContentEncoding();
	aclmsg.setSenderAID(sender);
	aclmsg.setReceiverAID(reciever);
	aclmsg.setContentObject(content);	
	aclmsg.setLanguage("UCL");
	aclmsg.setOntology("TT_Default");
	aclmsg.setPerformative(Performative);
/*    
	aclmsg.setConversationID(java.lang.String conv_id)				
	aclmsg.setProtocol(java.lang.String protocol); 
	
*/ 
	
	 
/* OPTIONAL
	
 void setInReplyTo(java.lang.String in_reply_to) 			
	
 void setReceiverAIDs(java.util.List aids) 
			
 void setReplyByUTC(UTCContainer utc) 
			
 void setReplyToAIDs(java.util.List aids) 
			
 void setReplyWith(java.lang.String reply_with) 
			
 void setSenderAID(AgentID aid) 
			
*/

	
}
/**
Creates an ACLMessage containing a STRING to be streamed
to another agent
*/


public ACLWriter(String Performative, Object content, AgentID sender, AgentID reciever) 
{
	super();
	this.aclmsg = new fipaos.ont.fipa.ACL();	
//	aclmsg.setContentEncoding();
	aclmsg.setSenderAID(sender);
	aclmsg.setReceiverAID(reciever);
	aclmsg.setContentObject(content);	
	aclmsg.setLanguage("UCL");
	aclmsg.setOntology("TT_Default");
	aclmsg.setPerformative(Performative);
/*    
	aclmsg.setConversationID(java.lang.String conv_id)				
	aclmsg.setProtocol(java.lang.String protocol); 
	
*/ 	 
/* OPTIONAL	
 void setInReplyTo(java.lang.String in_reply_to) 				
 void setReceiverAIDs(java.util.List aids) 			
 void setReplyByUTC(UTCContainer utc) 			
 void setReplyToAIDs(java.util.List aids) 			
 void setReplyWith(java.lang.String reply_with) 		 			
*/	
}


/**
Creates an ACLMessage containing a STRING to be streamed to another agent
*/
public ACLWriter(String Performative, String content, AgentID sender, AgentID reciever) {
	super();
	this.aclmsg = new fipaos.ont.fipa.ACL();	
//	aclmsg.setContentEncoding();
	aclmsg.setSenderAID(sender);
	aclmsg.setReceiverAID(reciever);
	aclmsg.setContentObject(content);	
	aclmsg.setLanguage("UCL");
	aclmsg.setOntology("TT_Default");
	aclmsg.setPerformative(Performative);

/*    
	aclmsg.setConversationID(java.lang.String conv_id)				
	aclmsg.setProtocol(java.lang.String protocol); 
*/ 	 
/* OPTIONAL	
 void setInReplyTo(java.lang.String in_reply_to) 				
 void setReceiverAIDs(java.util.List aids) 			
 void setReplyByUTC(UTCContainer utc) 			
 void setReplyToAIDs(java.util.List aids) 			
 void setReplyWith(java.lang.String reply_with) 			
 void setSenderAID(AgentID aid) 			
*/	
}


/**
* Creates an ACLMessage containing a STRING to be streamed to another agent
*/
public ACLWriter(String ConversationID, String Performative, String content, AgentID sender, AgentID reciever) 
{
	super();
	this.aclmsg = new fipaos.ont.fipa.ACL();	
//	aclmsg.setContentEncoding();
	aclmsg.setSenderAID(sender);
	aclmsg.setReceiverAID(reciever);
	aclmsg.setContentObject(content);	
	aclmsg.setLanguage("UCL");
	aclmsg.setOntology("TT_Default");	
	aclmsg.setPerformative(Performative);
	aclmsg.setConversationID(ConversationID);
	
/*    
	aclmsg.setConversationID(java.lang.String conv_id)				
	aclmsg.setProtocol(java.lang.String protocol); 	
*/ 
		 
/* OPTIONAL	
 void setInReplyTo(java.lang.String in_reply_to) 				
 void setReceiverAIDs(java.util.List aids) 			
 void setReplyByUTC(UTCContainer utc) 			
 void setReplyToAIDs(java.util.List aids) 			
 void setReplyWith(java.lang.String reply_with) 			
 void setSenderAID(AgentID aid) 			
*/	
}



/**
* Creates an ACLMessage containing an Object STRING to be streamed to another agent
*/
public ACLWriter(String ConversationID, String Performative, Object content, AgentID sender, AgentID reciever) 
{
	super();
	this.aclmsg = new fipaos.ont.fipa.ACL();	
//	aclmsg.setContentEncoding();
	aclmsg.setSenderAID(sender);
	aclmsg.setReceiverAID(reciever);
	aclmsg.setContentObject(content);	
	aclmsg.setLanguage("UCL");
	aclmsg.setOntology("TT_Default");	
	aclmsg.setPerformative(Performative);
	aclmsg.setConversationID(ConversationID);
	
/*    
	aclmsg.setConversationID(java.lang.String conv_id)				
	aclmsg.setProtocol(java.lang.String protocol); 	
*/ 
		 
/* OPTIONAL	
 void setInReplyTo(java.lang.String in_reply_to) 				
 void setReceiverAIDs(java.util.List aids) 			
 void setReplyByUTC(UTCContainer utc) 			
 void setReplyToAIDs(java.util.List aids) 			
 void setReplyWith(java.lang.String reply_with) 			
 void setSenderAID(AgentID aid) 			
*/	
}



/**
 */
public void setConversationID(String conversation) 
{
	if(aclmsg!=null)
	aclmsg.setConversationID(conversation);
}


}
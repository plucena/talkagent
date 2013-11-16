package br.usp.talkagent.xkt;

import java.net.Socket;
import java.util.StringTokenizer;
import java.util.Vector;
import br.usp.talkagent.aclmessages.ACLWriter;
import br.usp.talkagent.basicagent.AmsClient;
import br.usp.talkagent.basicagent.SharpAgent;
import br.usp.talkagent.kb.objects.ozConcept;
import br.usp.talkagent.ontology.tt.ttOzoneTreeKB;
import br.usp.talkagent.util.Boolean;
import fipaos.ont.fipa.ACL;
import fipaos.ont.fipa.fipaman.AgentID;

public class XktConnector extends SharpAgent 
{

private ttOzoneTreeKB tree;


/**
 * @param ttServer_ip java.lang.String
 */
public XktConnector(Socket socket, fipaos.ont.fipa.fipaman.AgentID id, AmsClient ams, ttOzoneTreeKB tree)
{
	super();
	this.connection = socket;
	this.amsclient = ams;
	this.agentid = id;
	this.tree = tree;
	this.init();
}


/**
 * Recieves requests for service throw  
 * ACL Messages Service include manipulating the Knowledge Base
 * Actions descriptors in XKT
 */
public void execute()
{

	ACL aclmsg;
	String messagePerformative;
	String request;	
	
	try
	{
		aclmsg = this.readMsg();		
		AgentID replyto = aclmsg.getSenderAID();	
		messagePerformative = aclmsg.getPerformative();
		request = (String) aclmsg.getContentObject();
		System.err.println("DEBUG@XKT-Server - New message recieved " + request );

		if(messagePerformative.equalsIgnoreCase("ACL_QUERYREF"))
		{
			DoQuery(request,replyto);
		} 
		if(messagePerformative.equalsIgnoreCase("ACL_INFORM"))		
		{
			DoInsert(request);
		}		
		if(messagePerformative.equalsIgnoreCase("ACL_QUERYIF"))
		{
			DoInference(request,replyto);
		}
		if(messagePerformative.equalsIgnoreCase("ACL_UPDATE"))
		{
			DoUpdate(request,replyto);
		}

	}
	catch (Exception e)
	{
		e.printStackTrace();
	}
}


public void DoQuery(String request, AgentID replyto)
{
	try
	{
		if(request.startsWith("Concept:"))
	   	{
		  	String concept = request.substring(8);
		  	ozConcept caux = getConcept(concept); 
		  	if(caux==null)
			 	 System.err.println("DEBUG@XKT - Couldn't find " + concept);
		  	// create acl message
		  	// sends it to message sender
		  	ACLWriter aclwriter = new ACLWriter("ACL_INFORM",caux,agentid,replyto);
		  	this.sendMsg(aclwriter.aclmsg);  
		}		  	
		
		if(request.startsWith("Parent:"))
		{
		    String concept = request.substring(7);
		  	ozConcept caux = parentConcept(concept); 
		  	if(caux==null)		  	
		  	 	 System.err.println("DEBUG@XKT - Couldn't find parent from " + concept);
			// create acl message
			// sends it to message sender		
		  	ACLWriter aclwriter = new ACLWriter("ACL_INFORM",caux,agentid,replyto);
		  	this.sendMsg(aclwriter.aclmsg);
		  					    
		}
	    
	    if(request.startsWith("Siblings:"))
	    {
		   	String concept = request.substring(9);
		  	Vector siblings = siblingConcepts(concept); 
		  	if(siblings==null)
			 	 System.err.println("DEBUG@XKT - Couldn't find any sibling from " + concept);

		  	// create acl message
		  	// sends it to message sender
		  	ACLWriter aclwriter = new ACLWriter("ACL_INFORM",siblings,agentid,replyto);
		  	this.sendMsg(aclwriter.aclmsg);					 	 
		 }
		 		   
		if(request.startsWith("Path:"))
	    {
		   	String concept = request.substring(5);
		  	Vector path = getAncestors(concept); 
		  	if(path==null)
			 	 System.err.println("DEBUG@XKT - Couldn't find concept " + concept);

		  	// create acl message
		  	// sends it to message sender
		  	ACLWriter aclwriter = new ACLWriter("ACL_INFORM",path,agentid,replyto);
		  	this.sendMsg(aclwriter.aclmsg);					 	 
		 }
		 
		 // direct children only
		 if(request.startsWith("Children:"))
		 {
		    	String concept = request.substring(9);
		    	if(concept.startsWith("ONTOLOGY_INDEX"))
		    	{
		    		Vector children = this.getIndex();
				  	if(children==null)
					 	 System.err.println("ERROR@XKT - Couldn't get Ontology Index " + concept);		    		
		    		ACLWriter aclwriter = new ACLWriter("ACL_IFORM",children,agentid,replyto);
			  		this.sendMsg(aclwriter.aclmsg);
		    	}     
		    	else
		    	{
		    		Vector children = directChildren(concept);		    
   				  	if(children==null)
						 	 System.err.println("DEBUG@XKT - " + concept + " has no children");
				  	ACLWriter aclwriter = new ACLWriter("ACL_IFORM",children,agentid,replyto);
			  		this.sendMsg(aclwriter.aclmsg);
		    	}		    			    	
		 }
		 
		 // Get Numver of Children (Direct and Indirect) of a given concept
 		 if(request.startsWith("NumberOfChildren:"))
		 {
		    	String concept = request.substring(17);
		    	String nc = getNumberOfChildren(concept);
	    		ACLWriter aclwriter = new ACLWriter("ACL_IFORM",nc,agentid,replyto);
		  		this.sendMsg(aclwriter.aclmsg);
		 }
		 
		 // Get Numver of Children (Direct and Indirect) of a given concept
 		 if(request.startsWith("DirectChildrenTotal:"))
		 {
		    	String concept = request.substring(20);
		    	String nc = getNumberOfDirectChildren(concept);
	    		ACLWriter aclwriter = new ACLWriter("ACL_IFORM",nc,agentid,replyto);
		  		this.sendMsg(aclwriter.aclmsg);
		 }
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
}



public void DoInsert(String request)
{
	if(request.startsWith("Insert:"))
	{
		String params = request.substring(7);
		StringTokenizer st = new StringTokenizer(params, ",", true);
		String concept = st.nextToken();
		st.nextToken();
		String parent = st.nextToken();
		tree.insertConcept(concept,parent);
		System.err.println("DEBUG@XKT: Insert(" + concept + "," + parent +")");
	}
}


public void DoUpdate(String request, AgentID replyto)
{
	// rename concept
	if(request.startsWith("Rename:"))
	{
		String params = request.substring(7);
		StringTokenizer st = new StringTokenizer(params, ",", true);
		String concept = st.nextToken();
		st.nextToken();
		String newname = st.nextToken();
		tree.renameConcept(concept,newname);
	}
	if(request.startsWith("AddAssertion:"))
	{
	}	
	if(request.startsWith("Delete:"))
	{
		String concept = request.substring(7);
		tree.delete(concept);
	}
	if(request.startsWith("Associate:"))
	{
		try
		{
			String params = request.substring(10);
			StringTokenizer st = new StringTokenizer(params, ",", true);
			String concept = st.nextToken();
			st.nextToken();
			String bean = st.nextToken();
			st.nextToken();
			String method = st.nextToken();
			st.nextToken();
			String snparam = st.nextToken();
			int nparam =  Integer.parseInt(snparam);
			String param=null;	
			st.nextToken();		
			if(st.hasMoreElements())			
				param = st.nextToken();
			tree.Associate(concept,bean,method,param, nparam);
		}	
		catch(Exception e)
		{
			e.printStackTrace();
		}		 
	}
	if(request.startsWith("Dissociate:"))
	{
		String params = request.substring(11);
		StringTokenizer st = new StringTokenizer(params, ",", true);
		String concept = st.nextToken();
		st.nextToken();
		String bean = st.nextToken();
		st.nextToken();
		String method = st.nextToken();
		st.nextToken();
		String snparam = st.nextToken();
		int nparam =  Integer.parseInt(snparam);
		String param=null;	
		st.nextToken();		
		if(st.hasMoreElements())
		{
			param = st.nextToken();
			if((param!=null) && (param.equalsIgnoreCase("null")))
				param = null;
		}
		tree.Dissociate(concept,bean,method,param,nparam);
	}
}


public void DoInference(String request, AgentID replyto)
{
	if(request.startsWith("IsDef:"))
	{
		String params = request.substring(6);
		StringTokenizer st = new StringTokenizer(params, ",", true);
		String concept = st.nextToken();
		st.nextToken();
		String ancestor = st.nextToken();
		System.err.println("DEBUG@XKT - IsDef(" + concept + "," + ancestor + ")" );
		Boolean resp = tree.isA(concept,ancestor);
		ACLWriter aclwriter = new ACLWriter("ACL_INFORM",resp,agentid,replyto);
		this.sendMsg(aclwriter.aclmsg);							
	}
	// System.err.println("DEBUG@XKT - Start inference " + request);	
}



/**
 *  Search for a specific concept in KB and return a memory object reference.
 */ 
protected ozConcept getConcept(String Concept) 
{	
	return tree.getConcept(Concept);
}

/***
 * Returns all the ancestors of a given concept  */
protected Vector getAncestors(String Concept)
{
	return tree.getAncestors(Concept);
}



/**
 *  Search for a specific concept in KB and return it.
 */ 
protected ozConcept findConcept(String Concept) 
{
	
	return tree.findConcept(Concept);
}


/**
* Return a list containig all parents of a specific concept 
*/
public ozConcept parentConcept(String Concept)
{
	return tree.findParent(Concept);
}


/**
*/
public Vector siblingConcepts(String Concept) 
{
	return tree.findSiblings(Concept);
}


/**
 */
public Vector directChildren(String Concept)
{
	return tree.findDirectChildren(Concept);
}


public Vector getIndex()
{
	return tree.getIndex();
}


public String getNumberOfChildren(String concept)
{

int value = tree.getNumberOfChildren(concept); 
String aux = Integer.toString(value);	
//aux += ":" + Integer.toString(tree.computeNumberOfChildren(concept));
return aux;
}


public String getNumberOfDirectChildren(String concept)
{
	return Integer.toString(tree.getNumberOfDirectChildren(concept));
}

}
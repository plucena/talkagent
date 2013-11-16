package br.usp.talkagent.cm;

/**
 * @Percival Lucena 
 */

import java.util.*;
import br.usp.talkagent.basicagent.*;
import br.usp.talkagent.cm.db.*;
import java.net.*;
import fipaos.ont.fipa.fipaman.AgentID;
import fipaos.ont.fipa.ACL;
import br.usp.talkagent.aclmessages.*; 
import br.usp.talkagent.component.*;
import br.usp.talkagent.kb.*;

public class ComponentManager extends SharpAgent 
{

private CRDB crdb;
private XktClient xktc;	
	
public ComponentManager (Socket socket, fipaos.ont.fipa.fipaman.AgentID id, AmsClient ams, String OzoneServer, boolean reset_db)
{	
	this.connection = socket;
	this.amsclient = ams;
	this.agentid = id;
	this.xktc = new XktClient(ams, agentid);
	this.crdb = new CRDB(OzoneServer,this.xktc);
	if(reset_db)
		crdb.initDB();					
	this.serveRequest();	
}


public String serveRequest()
{	
	ACL aclmsg;
	String messagePerformative;
	String request;	
	
	try
	{
		aclmsg = this.readMsg();		
		AgentID sender = aclmsg.getSenderAID();	
		messagePerformative = aclmsg.getPerformative();
		request = (String) aclmsg.getContentObject();		
		System.err.println("DEBUG@CM - New message recieved " + request );
		this.manageRequest(request,sender);
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}	
	return null;	
}


private void manageRequest(String request, AgentID sender)
{
	if(request.startsWith("ListComponents"))
	{
		Vector response = this.getComponentList();	
		ACLWriter acl = new ACLWriter("ACL_INFORM",response,this.agentid,sender);		 
		this.sendMsg(acl.aclmsg);
	}
	if(request.startsWith("AddComponent"))	
	{
		this.addComponent(request);
		
	}
	if(request.startsWith("Associate"))	
	{
		this.Associate(request);
		
	}
	if(request.startsWith("Dissociate"))
	{
		this.Dissociate(request);
	}
	if(request.startsWith("Delete"))
	{
		this.DeleteBean(request);
	}
	if(request.startsWith("Execute"))
	{
		String response = this.ExecuteComponentMethod(request);
		ACLWriter acl = new ACLWriter("ACL_INFORM",response,this.agentid,sender);		 
		this.sendMsg(acl.aclmsg);
	}	
}


private void addComponent(String request)
{
	String params = request.substring(13);
	String componentfile = params;
	this.crdb.insertComponent(componentfile);
}



private void Associate(String request)
{
	String params = request.substring(9);
	StringTokenizer st = new StringTokenizer(params, ",", true);
	String concept = st.nextToken();
	st.nextToken();
	String bean = st.nextToken();
	st.nextToken();
	String method = st.nextToken();	
	String param=null;			
	if(st.hasMoreElements())
	{
		st.nextToken();	
		if(st.hasMoreElements())		
			param = st.nextToken();
	}	
	crdb.Associate(concept,bean,method,param);
}



private void Dissociate(String request)
{
	String params = request.substring(10);
	StringTokenizer st = new StringTokenizer(params, ",", true);
	String concept = st.nextToken();
	st.nextToken();
	String bean = st.nextToken();
	st.nextToken();
	String method = st.nextToken();	
	String param=null;			
	if(st.hasMoreElements())
	{
		st.nextToken();	
		if(st.hasMoreElements())		
			param = st.nextToken();
	}	
	crdb.Dissociate(concept,bean,method,param);
}


private CompLink getComponentStructure(String componentname)
{	
	return null;
}


private void DeleteBean(String request)
{
	String component = request.substring(16);
	System.err.println("DEBUG@CM - Should delete " + component);
	ComponentImpl cp = this.crdb.getComponent(component);
	if(cp!=null)
	{			
		this.crdb.deleteComponent(cp);
	}
	else
	{
		System.err.println("ERROR@CM - Could'nt find component " + component);
	}
}


public String ExecuteComponentMethod(String request)
{
	String param;
	String params = request.substring(8);
	StringTokenizer st = new StringTokenizer(params, ",", true);	
	String componentName=st.nextToken();
	st.nextToken();
	String MethodName=st.nextToken();
	Vector parametervalues=new Vector(1);
	while(st.hasMoreElements())
	{
		st.nextToken();
		param=st.nextToken();
		//if(!param.equalsIgnoreCase("null"));
		parametervalues.add(param);
	}			
	return this.crdb.ExecuteComponentMethod(componentName, MethodName,parametervalues);
}


private Vector getComponentList()
{
	int i,j,k;	
	Vector response = crdb.listComponents();
	//System.err.println("DEBUG@CM - Sending Component Info - " + response.size() + " components");
	
	for(i=0;i<response.size();i++)
	{
		CompLink cl = ((Component)(response.elementAt(i))).getComponentInfo();
		if(cl!=null)
		{
			System.err.println(" - " + cl.getName());
			for(j=0;j<cl.getNumeberofMethods();j++)
			{
				if(cl.getMethod(j).getAssociatedConcepts().size()>0)
					//System.err.println(cl.getMethod(j).getName() + ":" +  cl.getMethod(j).getAssociatedConcepts().toString());
				for(k=0;k<cl.getMethod(j).getParametersNumber();k++)
				{
					if(cl.getMethod(j).getParameter(k).getAssociatedConcepts().size() >0)
					;//System.err.println(cl.getMethod(j).getName() + ":" + cl.getMethod(j).getParameter(k).getAssociatedConcepts().toString());
				}
			}
		}	
		else
			System.err.print(" - null ");
	}
		
	System.err.println();
	return response;
}

}

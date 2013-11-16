package br.usp.talkagent.aclmessages;


import java.util.*;
 
public class AgentUID {

	public java.lang.String agentName;
	public fipaos.ont.fipa.fipaman.AgentID agentid;
	

/**
 * AgentAddr constructor comment.
 */
public AgentUID() {
	super();
}


protected void setId(String name, String server, String port) 
{
	LinkedList l1;
	
	this.agentid = new fipaos.ont.fipa.fipaman.AgentID();
	agentid.setName(name);
	l1 = new LinkedList();
	l1.add(server);
	l1.add(port);		
	agentid.setAddresses(l1);	
}
}

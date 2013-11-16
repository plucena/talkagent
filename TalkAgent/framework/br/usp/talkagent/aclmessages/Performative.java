package br.usp.talkagent.aclmessages;

import fipaos.ont.fipa.fipaman.*;

/***
* Represents a FIPA Performative used in agent to agent communication.
* Store associations between agents and the specific performative represented.
*/

import java.util.*;

public class Performative {
	private String AclPerformative;
	private Vector Dispatchto;
	

public Performative() {
	super();
	Dispatchto = new Vector(5);		
}

public Performative(String AclPerformativeValue)
{
	super();
	this.AclPerformative = AclPerformativeValue;

}

/**
 * Associate an agent to a FIPA communicative performative 
 * @param agent fipaos.ont.fipa.fipaman.AgentID
 */
public void addAssociation(AgentID agent)
{
	
	if(Dispatchto == null)
	   Dispatchto = new Vector(5);
	
	if (agent != null)
	{
		Dispatchto.addElement(agent);
	}
	else
		System.err.println("@ERROR - Tring to associate agent with null value to performative " + this.AclPerformative); 
}


/**
 * @return java.lang.String
 */
public String getAclPerformative() 
{
	return this.AclPerformative;
}


/**
 * Get Agents that are Associated to a performative. 
 * Return a vector containing associated agents.
 * @return java.util.Vector
 */
public Vector getAssociatedAgents() 
{
	return Dispatchto;
}


/**
 * Check if a given agent is associated to this performative
 * @return boolean
 * @param agent fipaos.ont.fipa.fipaman.AgentID
 */
public boolean isAssociated(AgentID agent) 
{
 int i=0;
 boolean found = false;
 
 	
	while((i<Dispatchto.size()) && (!found))
	{
		if( ((AgentID)Dispatchto.elementAt(i)).getName() ==  agent.getName())
		   found=true;     
		i++;
		
	}
	return found;
}


/**
 */
public void setAclPerformative(String AclPerformativeType) 
{
	this.AclPerformative = AclPerformativeType;
		
}

}

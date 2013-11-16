package br.usp.talkagent.aclmessages;

/**
 * Insert the type's description here.
 * Creation date: (20/06/02 16:22:01)
 * @author: 
 */

import fipaos.ont.fipa.fipaman.*;
 
import java.util.*;public class addressParser {
	
	public int port;
	public String IP;
	private fipaos.ont.fipa.fipaman.AgentID agent;
/**
 * addressParser constructor comment.
 */

public addressParser(AgentID addr)
{
	super();
	if (addr != null)
	{
		this.agent = addr;
	}
	else
		System.err.println("== Warning: trying to parse null address ==");
}
/**
 * Insert the method's description here.
 * Creation date: (20/06/02 16:50:58)
 */
public void parse()
{
	java.util.List auxl;

	if (IP == null)
	{
		auxl = agent.getAddresses();
		this.IP = (String) auxl.get(0);
		this.port = Integer.parseInt((String) auxl.get(1));
	}

}
/**
 * addressParser constructor comment.
 */



public addressParser(String ip, int port) {
	super();
	this.IP = ip;
	this.port = port;
}/**
 * addressParser constructor comment.
 */

public addressParser(java.util.List list)
{
	super();
	Iterator itr = list.iterator();
	if (itr.hasNext())
	{
		this.IP = (String) itr.next();
		if (itr.hasNext())
			this.port = Integer.parseInt((String) itr.next());
		//((Integer) ).intValue();
		else
			System.err.println("=== Adress Parser: Error geting port number");
	}
	else
		System.err.println("== Warning: trying to parse a null address");

}}
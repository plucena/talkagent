package br.usp.semanticagent.ucl.scripts;

/**
 * @author Percival Lucena
 * 
 */

import br.usp.talkagent.component.*;
import br.usp.talkagent.kb.*;
import fipaos.ont.fipa.fipaman.*;
import br.usp.talkagent.ucl.UCLDoc;
import br.usp.semanticagent.ucl.interpreter.action.Action;
import br.usp.semanticagent.ucl.interpreter.action.ActionParser;
import br.usp.semanticagent.ucl.interpreter.action.Association;
import br.usp.semanticagent.ucl.interpreter.action.AssociationParser;
import br.usp.talkagent.basicagent.*;
import java.util.*;


public class ScriptAction 
{

private XktClient xktc;
private CRclient crc;
private UCLDoc ucldoc;
private Action act;
private AssociationParser assocParser;
private Vector Associations;

public ScriptAction(AgentID agentid, AmsClient amsclient,UCLDoc ucldoc)
{
	this.xktc = new XktClient(amsclient, agentid);
	this.crc = new CRclient(amsclient, agentid);
	this.ucldoc = ucldoc;
}


public String execute()
{
String response="<BR>";
Association assoc;
Vector params;
	
	// analyse ucl message structure
	ActionParser parser = new ActionParser(this.ucldoc);	
	this.act = parser.parse();
	
	
	// retrieve components associated to ucl concepts
	this.assocParser = new AssociationParser(this.ucldoc, this.xktc);
	this.assocParser.parse();
	Associations = this.assocParser.getAssociations();
	
	if((Associations!=null)&& (act!=null))
	{
		for(int i=0;i<Associations.size();i++)
		{
			assoc = (Association) Associations.elementAt(i);
			// method associated to verb object
			if(act.getActionObjects().contains(assoc.getConcept()))
			{
				System.err.println("Method associated to verb object");
				params=new Vector(1);
				for(int j=0;j<act.getActionObjects().size();j++)
					params.add(act.getActionObjects().elementAt(j));				
				params.remove(assoc.getConcept());
				if(assoc.getAssociatedComponents()!=null)
				{
					for(int j=0;j<assoc.getAssociatedComponents().size();j++)
					{
						CElement cel = (CElement)assoc.getAssociatedComponents().elementAt(j);
						System.err.println("Method " + cel.getMethodName() + " parameters:" + cel.getNumberofparam());
						// no params specified, send null values ...
						if ((params.size()==0) && (cel.getNumberofparam()>0))						
						{
							params=new Vector(cel.getNumberofparam());
							for(int k=0;k<cel.getNumberofparam();k++)
								params.add(null);
						}
						// in case we have more arguments that we need
						// (it's necessary a better policy to match parameters @ get Action Objects!)
						if(params.size()>cel.getNumberofparam())
						{
							for(int l=0;l<(params.size()-cel.getNumberofparam());l++)
								params.removeElementAt(params.size()-l-1);
						}						
						response += callMethod(cel,params);						
					}				
				}						
			}
			else
			// method associated directly to a verb			
			if(act.getAction().equalsIgnoreCase(assoc.getConcept()))
			{	
				System.err.println("Method associated to verb");			
				params = act.getActionObjects();				
				if(assoc.getAssociatedComponents()!=null)
				{
					for(int j=0;j<assoc.getAssociatedComponents().size();j++)
					{
						CElement cel = (CElement)assoc.getAssociatedComponents().elementAt(j);
						System.err.println("Method " + cel.getMethodName() + " parameters:" + cel.getNumberofparam());						
						// no params specified, send null values ...
						if ((params.size()==0)&&(cel.getNumberofparam()>0))						
						{
							params=new Vector(cel.getNumberofparam());
							for(int k=0;k<cel.getNumberofparam();k++)
								params.add(null);
						}
						// in case we have more arguments that we need
						// (it's necessary a better policy to match parameters @ get Action Objects!)
						if(params.size()>cel.getNumberofparam())
						{
							for(int l=0;l<(params.size()-cel.getNumberofparam());l++)
								params.removeElementAt(params.size()-l-1);
						}
						response += callMethod(cel,params);
					}
				}										
			}				
		}
	}
		
	response += DebugInfo(); 
	return response;
}



public String DebugInfo()
{
	String response = "<BR><BR><HR>Debug Info <BR> ";	
	response += act.toHTML();
	response += assocParser.toHTML();
	return response;
}


private String callMethod(CElement cel, Vector args)
{ 
	// send message to CRM Agent		
	//return this.crc.runMethod(cel.getComponent(),cel.getMethod(),args,cel.getParamName());
	if(args==null)
		args = new Vector(1);
	return this.crc.runMethod(cel.getComponent(),cel.getMethod(),args);	 
}


}
package br.usp.talkagent.kb;

/**
* @author Percival Lucena
*/

import fipaos.ont.fipa.fipaman.AgentID;
import br.usp.talkagent.basicagent.AgtConnector;
import br.usp.talkagent.basicagent.*;
import br.usp.talkagent.util.Boolean;
import java.util.*;
import br.usp.talkagent.kb.objects.*;
import br.usp.talkagent.component.*;

public class XktClient extends AgtConnector 
{

public XktClient(AmsClient ams, AgentID Sender)
{	
	super(ams, Sender, "sharp_xkt_kb");
}

public Vector getIndex()
{
Vector index;
	try
	{
		String rcp  = "Children:" + "ONTOLOGY_INDEX";
		this.Connect();
		this.SendString("ACL_QUERYREF",rcp);
		index = (Vector) this.ReadObject();		
		this.disConnect(); 	
		return index;
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	return null;
}	


public ozConcept getConcept(String concept)
{
	try
	{
		String rcp  = "Concept:" +  concept;
		this.Connect();
		this.SendString("ACL_QUERYREF",rcp);
		ozConcept response = (ozConcept) this.ReadObject();		
		this.disConnect(); 	
		return response;
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	return null;
}


public Vector getPath(String concept)
{
	try
	{
		String rcp  = "Path:" +  concept;
		this.Connect();
		this.SendString("ACL_QUERYREF",rcp);
		Vector response = (Vector) this.ReadObject();		
		this.disConnect(); 	
		return response;
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	return null;
}


public String getParent(String concept)
{
ozConcept aux = null;

	try
	{
		aux = (ozConcept) this.getConceptParent(concept);
		System.err.println(aux.toString());
		if(aux!=null)
			return aux.getID();
		else
		{
			System.err.println("ERROR@UCL-INTERPRETER. Couldn retrieve " + concept + " parent");
			return null;
		}	
	}
	catch(Exception e)
	{
		System.err.println(aux.toString());
		e.printStackTrace();
		return null;
	}

}


public ozConcept getConceptParent(String concept)
{
	try
	{
		System.err.println("DEBUG@UCL-INTERPRETER - Searching for " + concept +   " parent's"); 
		String rcp ="Parent:" +  concept;
		this.Connect();
 		this.SendString("ACL_QUERYREF",rcp);
		ozConcept response = (ozConcept) this.ReadObject();
		if (response!=null)
			System.err.println("DEBUG@UCL-INTERPRETER - Parent found: " + response.getID());
		this.disConnect(); 	
		return  response;
	}	
	catch(Exception e)
	{
		e.printStackTrace();
	}		
return null;
}


public Vector getConceptSiblings(String concept)
{
	try
	{
		String rcp  = "Siblings:" +  concept;
		this.Connect();
 		this.SendString("ACL_QUERYREF",rcp);
		Vector response = (Vector) this.ReadObject();
        this.disConnect(); 
		return response;			
	}
	catch(Exception e)
	{
		e.printStackTrace();	
	}
	return null;	
}



public Vector getConceptChildren(String concept)
{
	try
	{
		String rcp  = "Children:" +  concept;
		this.Connect();
 		this.SendString("ACL_QUERYREF",rcp);
		Vector children = (Vector) this.ReadObject();
		this.disConnect();
		return children;
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	return null;
}


public int getNumberOfDirectChildren(String concept)
{
	try
	{
		String rcp  = "DirectChildrenTotal:" +  concept;
		this.Connect();
 		this.SendString("ACL_QUERYREF",rcp);
		String nodc = (String) this.ReadObject();
		this.disConnect();
		if(nodc!=null)
			return Integer.parseInt(nodc);
		else
			return 0;
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	return 0;
}



public String getChildren(String concept)
{
	String response = "";
	Vector children = this.getConceptChildren(concept);  		
	if(children!=null)
	{
		int j;				
		for(j=0;j<children.size();j++)
			response += ((ozConcept) children.elementAt(j)).getID() + ", ";			
	}	 
	return response;
}


public Vector getChildrenVector(String concept)
{
	Vector vc = this.getConceptChildren(concept);
	if(vc!=null)
	{
		List noDups = new Vector();
		Object obj = null;
		for(Iterator itr = vc.iterator(); itr.hasNext();)
		{
 	 		obj = itr.next();
  			if(!noDups.contains(obj))
  			{
    			noDups.add(obj);
  			}
 		} 		
 		return (Vector) noDups;
	}
	return null;	
}


public String getSiblings(String concept)
{
	String response="";
	Vector siblings = this.getConceptSiblings(concept);
	
	if(siblings!=null)
	{
		int j;			
		for(j=0;j<siblings.size()-1;j++)
			if(((ozConcept) siblings.elementAt(j)).getID().equalsIgnoreCase(concept))
			{
			}
			else
				response += ((ozConcept) siblings.elementAt(j)).getID() + ", ";		
		response += ((ozConcept) siblings.elementAt(j)).getID() + ". ";		
	 }
	 return response;
}

public Vector getSiblingsVector(String concept)
{
	return this.getConceptSiblings(concept);
}


public boolean findconcept(String concept)
{
	if(this.getConcept(concept)!=null)	
		return true;
	else
		return false;
}


public void insertconcept(String concept, String category)
{
	String rcp  = "Insert:" +  concept + "," + category;
	this.Connect();
	this.SendString("ACL_INFORM",rcp);		
	this.disConnect(); 	
}


public Boolean isDef(String concept, String category)
{
	String rcp  = "IsDef:" +  concept + "," + category;
	this.Connect();
	this.SendString("ACL_QUERYIF",rcp);
	Boolean resp = (Boolean) this.ReadObject();	
	this.disConnect();		
	return resp;	
}

public String getNumberOfChildren(String concept)
{
	try
	{
		String rcp  = "NumberOfChildren:" +  concept;
		this.Connect();
 		this.SendString("ACL_QUERYREF",rcp);
		String nc = (String) this.ReadObject();
		this.disConnect();
		return nc;
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	return null;
}


public void updateConcept(String concept, String newname)
{
	String rcp  = "Rename:" +  concept + "," + newname;
	this.Connect();
	this.SendString("ACL_UPDATE",rcp);
	this.disConnect();		
}


public void deleteConcept(String concept)
{
	String rcp  = "Delete:" +  concept;
	this.Connect();
	this.SendString("ACL_UPDATE",rcp);
	this.disConnect();		
}

public void AssociateConceptToComponent(CElement cel)
{
	String rcp  = "Associate:" +  cel.getConcept() + "," + cel.getComponent()  + "," + cel.getMethod() +  "," + cel.getNumberofparam() +  "," + cel.getParam();
	this.Connect();
	this.SendString("ACL_UPDATE",rcp);
	this.disConnect();
}


public void DissociateComponent(CElement cel)
{
	String rcp  = "Dissociate:" + cel.getConcept() + "," + cel.getComponent()  + "," + cel.getMethod() +  "," + cel.getNumberofparam() +  "," + cel.getParam();
	this.Connect();
	this.SendString("ACL_UPDATE",rcp);
	this.disConnect();
}


}
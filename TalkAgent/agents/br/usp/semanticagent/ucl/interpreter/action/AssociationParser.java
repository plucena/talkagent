package br.usp.semanticagent.ucl.interpreter.action;

/**
 * @author Percival Lucena
 * 
 */

import br.usp.talkagent.ucl.*;
import br.usp.talkagent.util.*;
import java.util.*;
import br.usp.talkagent.kb.*;
import br.usp.talkagent.kb.objects.ozConcept;

public class AssociationParser 
{

private UCLDoc ucldoc;
private Vector concepts;
private XktClient xktc;
private Vector Associations;

	
	/**
	 * Constructor for Associations.
	 */
	public AssociationParser(UCLDoc ucldoc, XktClient xktc) 
	{
		super();
		this.ucldoc = ucldoc;
		this.xktc = xktc;
		this.concepts = new Vector(1);
		this.Associations = new Vector(1);
	}

	public void parse()
	{	
		Association assoc;		
		GenList clist = ucldoc.getConcepts();		
		int cnumber = clist.getSize();
 
		// for each concept in ucldoc, verifies if there's a componet associated. 
		for(int i=0; i<cnumber; i++)
		{
		UclConcept ucpt = (UclConcept) clist.elementAt(i);
		concepts.add(ucpt.getName());
		ozConcept ozcpt = xktc.getConcept(ucpt.getName());
		if(ozcpt!=null)
		{
			Vector clinks = ozcpt.getComponetLinks(); 	
			if((clinks!=null)&&(clinks.size()>0))
				assoc = new Association(ucpt.getName(),clinks);
			else
				assoc = new Association(ucpt.getName());
							
			this.Associations.add(assoc);		}
		else 
			System.err.println("WARNING@UCL-INTERPRETER - couldn find concept " + ucpt.getName());	
		}
	}
	
	
	
	public Vector getAssociations()
	{
		return this.Associations;
	}
	
	
	public Vector getConcepts()
	{
		return this.concepts;
	}
		
	
	public String toHTML()
	{
	int i;
	
		String response= "<BR>Associations: <BR>";
		for(i=0;i<this.Associations.size();i++)
		{
			response += ((Association) this.Associations.elementAt(i)).toHTML();			
		}
		return response;				
	}
	
	

}

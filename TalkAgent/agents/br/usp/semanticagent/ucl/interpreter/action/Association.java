package br.usp.semanticagent.ucl.interpreter.action;

/**
 * @author Percival Lucena
 * 
 */

import java.util.*;
import br.usp.talkagent.component.*;

public class Association 
{

private String concept;
private Vector AssociatedComponents;

	/**
	 * Constructor for Association.
	 */
	public Association(String conceptName) 
	{
		super();
		this.concept = conceptName;		
	}
	
	public Association(String conceptName, Vector Associations)
	{
		super();
		this.concept = conceptName;
		this.AssociatedComponents = Associations;
	}
	

	public void addAssociation(String component, String method, String param, String type)
	{
		CElement cel = new CElement();
		cel.setComponent(component);
		cel.setMethod(method);
		cel.setParam(param);
		cel.setType(type);
		this.AssociatedComponents.add(cel);
	}	

	public void addAssociation(CElement cel)
	{
		this.AssociatedComponents.add(cel);
	}


	public String toHTML()
	{
		String response = this.concept + ": ";
		if(AssociatedComponents!=null)
		{	
			for(int i=0;i<this.AssociatedComponents.size();i++)
			{
				response += ((CElement) this.AssociatedComponents.elementAt(i)).getComponent() + ":";
				response += ((CElement) this.AssociatedComponents.elementAt(i)).getMethod() + ":";
				response += ((CElement) this.AssociatedComponents.elementAt(i)).getParam() + "<BR>";
			}
		}	
		response+= "<BR>";
		return response;
	}


/**
 * Returns the associatedComponents.
 * @return Vector
 */
public Vector getAssociatedComponents() {
	return AssociatedComponents;
}

/**
 * Returns the concept.
 * @return String
 */
public String getConcept() {
	return concept;
}

/**
 * Sets the associatedComponents.
 * @param associatedComponents The associatedComponents to set
 */
public void setAssociatedComponents(Vector associatedComponents) {
	AssociatedComponents = associatedComponents;
}

/**
 * Sets the concept.
 * @param concept The concept to set
 */
public void setConcept(String concept) {
	this.concept = concept;
}

}

package br.usp.talkagent.component;

/**
 * @author Percival Lucena 
 */

import java.util.*;
import java.io.*;
import br.usp.talkagent.component.*;
//import org.ozoneDB.OzoneObject; 

public class CMethod  implements Serializable 
{
	private String name;
	private Vector parameters;
	private String returntype;
	private String permission;
	private Vector AssociatedConcepts;
		
	public CMethod()
	{
		this.parameters = new Vector(1);
		this.AssociatedConcepts =new Vector(1); 
	}	
	
	public void addParameter(CParameter property)
	{
		this.parameters.add(property);
	}	
	
	public CParameter getParameter(int count)
	{
		return (CParameter) this.parameters.elementAt(count);
	}
	
	public CParameter getParameter(String name)
	{
		int i;
		for(i=0;i<this.parameters.size();i++)
		{
			CParameter param = (CParameter) this.parameters.elementAt(i);
			if(param.getName().equalsIgnoreCase(name))
				return param;			
		}
		return null;
	}
	
	public int getParametersNumber()
	{
		return this.parameters.size();
	}
		
	/**
	 * Returns the name.
	 * @return String
	 */
	public String getName() {
		return name;
	}

	
	/**
	 * Returns the permission.
	 * @return String
	 */
	public String getPermission() {
		return permission;
	}

	/**
	 * Returns the returntype.
	 * @return String
	 */
	public String getReturntype() {
		return returntype;
	}
		

	public String toString()
	{
	String aux;
	int i;
	
		aux = "public ";
		aux += returntype;		 					 	 		 			 
		aux += " " + name + "(";
		for(i=0;i<parameters.size()-1;i++)
			aux+= parameters.elementAt(i).toString() + ", ";	
	    if(parameters.size()>0)
	    	aux+= parameters.elementAt(parameters.size()-1).toString();
		aux +=")";			
		return aux;	
	}
	
	public void associateConcept(String concept)
	{
		this.AssociatedConcepts.add(concept);
	}
	
	public void dissociateConcept(String concept)
	{
		this.AssociatedConcepts.remove(concept);
	}
	/**
	 * Sets the returntype.
	 * @param returntype The returntype to set
	 */
	public void setReturntype(String returntype) {
		this.returntype = returntype;
	}

	/**
	 * Sets the permission.
	 * @param permission The permission to set
	 */
	public void setPermission(String permission) {
		this.permission = permission;
	}

	/**
	 * Sets the name.
	 * @param name The name to set
	 */
	public void setName(String name) 
	{
		this.name = name;
	}

	/**
	 * Returns the associatedConcepts.
	 * @return Vector
	 */
	public Vector getAssociatedConcepts() {
		return AssociatedConcepts;
	}


	public int getNumberOfAssociatedConcepts()
	{
		return AssociatedConcepts.size();
	}
	
	
	public String getID()
	{
	String id = null;
	
		StringTokenizer st = new StringTokenizer(this.name, " ", true);
		while(st.hasMoreElements())	
		{
			id = st.nextToken();
		}
	return id;
	}
}

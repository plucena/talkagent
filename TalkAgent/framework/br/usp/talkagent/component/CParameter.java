package br.usp.talkagent.component;

/**
 * @author Percival Lucena
 * 
 */
import java.io.*;
import java.util.*; 
//import org.ozoneDB.OzoneObject; 

public class CParameter implements Serializable
{
	
	private String type;
	private Object value;
	private String name;
	private Vector AssociatedConcepts;
	
	public CParameter()
	{
		this.AssociatedConcepts = new Vector(1);
	}
	
	public CParameter(String type, String value)
	{
		this.type = type;
		this.value = value;
		this.AssociatedConcepts = new Vector(1);
	}

	/**
	 * Returns the type.
	 * @return String
	 */
	public String getType() {
		return type;
	}

	/**
	 * Returns the value.
	 * @return String
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * Sets the type.
	 * @param type The type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Sets the value.
	 * @param value The value to set
	 */
	public void setValue(Object value) {
		this.value = value;
	}
	
	/**
	 * Returns the name.
	 * @return String
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 * @param name The name to set
	 */
	public void setName(String name) {
		this.name = name;
	}


	public String toString()
	{
		return this.type + " " + this.name;
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

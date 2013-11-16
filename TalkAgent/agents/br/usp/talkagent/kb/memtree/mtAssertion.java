package br.usp.talkagent.kb.memtree;

/**
 * @author Percival Lucena
 * This class represents a container for all assertions of a given concept??
 */

import br.usp.talkagent.kb.*;

public class mtAssertion implements Assertion  
{

private String type;
private Relation value;

public mtAssertion(String type, Relation value)
{
	super();
	this.type = type;
	this.value = value;	
}


public mtAssertion()
{
	super();
//his.values = new Vector();
}


/**
 * Returns the values.
 * @return Vector
 */
public Relation getvalue() 
{
	return value;
}



/**
 * Add new Assertion Value
 * @param values The values to set
 */
public void setValue(Relation aste) 
{
	this.value = (Relation) aste; 
}


/**
 * Returns the type.
 * @return String
 */
public String getType() 
{
	return type;
}


/**
 * Sets the type.
 * @param type The type to set
 */
public void setType(String type) {
	this.type = type;
}


public String print()
{
String aux;
	
	aux = this.type;
	aux += this.value.toString();
	return aux;	
}


}

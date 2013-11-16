package br.usp.talkagent.kb;


/**
 * @author Percival Lucena
 */

import br.usp.talkagent.kb.*;
import java.io.*;

public class Relation implements Serializable
{

private String type;
private Object value;


public Relation(String type, Object value) 
{
	this.type = type;
	this.value = value;
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
 * Returns the value.
 * @return Object
 */
public Object getValue() 
{
	return value;
}


/**
 * Sets the type.
 * @param type The type to set
 */
public void setType(String type) 
{
	this.type = type;
}


/**
 * Sets the value.
 * @param value The value to set
 */
public void setValue(Object value) 
{
	this.value = value;
}


public String toString()
{
 	String aux = type + ":" +  value.toString();
 	return aux;	  
}

}

package br.usp.talkagent.util;

/**
 * @author Percival
 */

import java.io.*;

public class Boolean implements Serializable 
{

boolean value;


public Boolean()
{
	super();
}

public Boolean(boolean value)
{
	super();
	this.value = value;
}

/**
 * Returns the value.
 * @return boolean
 */
public boolean getValue() 
{
	return value;
}

/**
 * Sets the value.
 * @param value The value to set
 */
public void setValue(boolean value) 
{
	this.value = value;
}

public String toString()
{
	if (this.value == false)
		return "false";
	else
		return "true";
}

}

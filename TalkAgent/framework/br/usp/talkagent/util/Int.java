package br.usp.talkagent.util;

/**
 * @author Percival
 */

import java.io.*;

public class Int implements Serializable 
{

int value;

public Int()
{
	super();
}

public Int(int value)
{
	super();
	this.value = value;
}

/**
 * Returns the value.
 * @return int
 */
public int getValue() {
	return value;
}


/**
 * Sets the value.
 * @param value The value to set
 */
public void setValue(int value) {
	this.value = value;
}

}

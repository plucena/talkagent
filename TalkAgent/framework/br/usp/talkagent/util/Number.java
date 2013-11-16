package br.usp.talkagent.util;

/**
 * @author Percival Lucena
 */

import java.util.Vector;
import com.signiform.tt.*;
import java.io.*;

public class Number implements Serializable
{

String unit;
float value;


public Number()
{
	super();
}

public Number(Vector number)
{
	try
	{
		if(number.size()==3)
		{
			this.value = ((Double) number.elementAt(1)).floatValue();
			this.unit = (String) number.elementAt(2);
		}
	}
	catch(Exception e)
	{
		System.err.println("---------------------------------------------");
		System.err.println("Unknown number:");
		System.err.println(TT.objectToPrettyString(number));		
		System.err.println("---------------------------------------------");		
	}
}

public Number(float value, String unit)
{
	this.value = value;
	this.unit = unit;
}

public String toString()
{
	String aux = value 	+ ":" + unit;
	return aux;
}

/**
 * Returns the unit.
 * @return String
 */
public String getUnit() 
{
	return unit;
}


/**
 * Returns the value.
 * @return float
 */
public float getValue() 
{
	return value;
}


/**
 * Sets the unit.
 * @param unit The unit to set
 */
public void setUnit(String unit) 
{
	this.unit = unit;
}


/**
 * Sets the value.
 * @param value The value to set
 */
public void setValue(float value) 
{
	this.value = value;
}


}

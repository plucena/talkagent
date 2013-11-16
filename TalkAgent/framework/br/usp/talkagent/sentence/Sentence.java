package br.usp.talkagent.sentence;

/**
 * @author Percival Lucena
 */

import java.util.*;
import java.io.*;
import com.signiform.tt.*;

public class Sentence implements Serializable
{

private Vector Elements;
private int breaks;
private boolean HR;
private String type;

public Sentence()
{
	super();
	Elements = new Vector(1);
	breaks=1;
}

public void addElement(Element elem)
{
	this.Elements.addElement(elem);
}

public Element getElemenetAt(int pos)
{
	return (Element) this.Elements.elementAt(pos);
}

public void setElementAt(int pos, Element value)
{
 	this.Elements.setElementAt(value,pos);
}

public void addBreak()
{
	this.breaks++;
}

public void addBreak(int value)
{
	this.breaks+=value;
}

public void setNoBreaks(boolean value)
{
	if(value==true)
	{
		this.breaks+=0;
	}
}

public String printHTML()
{
int i=0;
String sentence="";
Element el;
int size = Elements.size();
String Assertion="";

if(type!=null)
{
	if(type.equalsIgnoreCase("assertion"))
	{
		do
    	{	
			el = (Element) this.Elements.elementAt(i);		
    		Assertion += el.getValue() +  " ";
    		i++;
    	} while(i<size); 
		TTEnglishConcept ttec = new TTEnglishConcept(Assertion);
		sentence = ttec.parse();		
	}
}
else
{	
	if(size>0)
	{
		do
    	{	
			el = (Element) this.Elements.elementAt(i);		
    		sentence += el.printHTML() +  " ";
    		i++;
    	} while(i<size); 
	}
}    


if(!this.HR)
{
    for(i=0;i<this.breaks;i++)   
    	sentence+= "<BR>";
}
  	else
  		sentence+= "<HR>";	

return sentence;

}



/**
 * Returns the hR.
 * @return boolean
 */
public boolean isHR() {
	return HR;
}

/**
 * Sets the hR.
 * @param hR The hR to set
 */
public void setHR(boolean hR) 
{
	HR = hR;
}

/**
 * Returns the type.
 * @return String
 */
public String getType() {
	return type;
}

/**
 * Sets the type.
 * @param type The type to set
 */
public void setType(String type) {
	this.type = type;
}

}

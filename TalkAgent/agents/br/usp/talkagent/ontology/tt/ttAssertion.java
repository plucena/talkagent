/**
 * @author Percival Lucena 
 */

package br.usp.talkagent.ontology.tt;

import java.util.Enumeration;
import java.util.Vector;
import com.signiform.tt.*;
import br.usp.talkagent.kb.objects.*;
import br.usp.talkagent.util.PrefReader;

public class ttAssertion 
{

private Vector TTassertions; // asserttions TT Style
private Vector Assertions; // ozAssertions
private String concept;
private int size;
	
	

public ttAssertion(String Concept)
{
	super();
	this.concept = Concept;
	this.TTassertions = new Vector(1);	
}
	

public Vector getAssertions()
{
Vector assertion;
int i;

	assertion =  getTTAssertion(this.concept);
	for(i=0;i<assertion.size();i++)
		this.TTassertions.addElement(assertion.elementAt(i));		
	assertion = getTTAssertion("? "+ this.concept);
	for(i=0;i<assertion.size();i++)
		this.TTassertions.addElement(assertion.elementAt(i));		
	assertion = getTTAssertion("? ? "+ this.concept);
		for(i=0;i<assertion.size();i++)
		this.TTassertions.addElement(assertion.elementAt(i));		
	if(assertion!=null)
	{
		this.Assertions = this.parseAssertions(); 	 
		this.size = this.Assertions.size();
		return this.Assertions;
	}	
	this.size = 0;
	return null;
}


/*
 * Get a specific assertion
 */
public Object getAssertion(int element)
{
	return this.Assertions.elementAt(element);	
}



public int size()
{
	return this.size;	
}

/**
 *  Return a Vector of Objects Assertion
 */
private Vector getTTAssertion(String Concept)
{
	TTConnection tt;
	Vector assertions;
	
	try
	{	
		PrefReader pref = new PrefReader();
		String ttServer_ip = pref.getTTHost();	
		String host = ttServer_ip;
		tt=null;
		TTConnector ttc = new TTConnector(host);
		tt = ttc.connect(); 
		assertions = tt.retrieve(-1,-1,-1,"exact",Concept);
		tt.close();
		return assertions;
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	return null;			
}


/**
 *  Return a Vector of Objects Assertion
 */
private Vector parseAssertions()
{
	Vector Assertions = new Vector(1);
	ttAssertionParser parser;
	
	for (Enumeration e = TTassertions.elements(); e.hasMoreElements(); ) 
	{
		parser = new ttAssertionParser((Vector) e.nextElement(),this.concept);		
		ozAssertion asst = parser.getAssertion();
		if(asst!=null)
		{
     		Assertions.addElement(asst);
		}
    }	
    return Assertions;
} 

 
public int ttrawsize()
{
	return this.TTassertions.size();
}


public void printTTAssertions()
{
	for (Enumeration e = this.TTassertions.elements(); e.hasMoreElements(); ) 
	{
     	System.out.println(TT.objectToPrettyString(e.nextElement()));
    }	
} 




}
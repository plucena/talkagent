package br.usp.talkagent.ontology.tt;

/**
 * @author Percival Lucena
 */

import java.util.Vector;

import br.usp.talkagent.kb.Relation;
import br.usp.talkagent.kb.objects.*;
import br.usp.talkagent.util.Number;

public class ttAssertionParser 
{

private Vector ttassertion;
private String concept;
private ozAssertion assertion; 


// concept related to assertion;
public ttAssertionParser(Vector TTassertion, String Concept)
{
	this.ttassertion = TTassertion;
	this.concept = Concept;
	this.assertion = null;
	this.parseAssertion();
} 



private void parseAssertion()
{
	switch (ttassertion.size())
	{
		case 2:
		{
		this.isAssertion();
		break;		
		}	
		
		
		case 3:
		{
				
			
		if( ttassertion.elementAt(1) instanceof String )
		{ 
			// [ATRIBNAME,CONCEPT,VALUE]	
			String e1 = (String) ttassertion.elementAt(1);
			if(e1.equalsIgnoreCase(this.concept))			
				 this.atribAssertion(); 					
		}
		
		if( ttassertion.elementAt(2) instanceof String)
		{
			// [RELATNAME, RELAT, CONCEPT]
			if(((String) ttassertion.elementAt(2)).equalsIgnoreCase(this.concept))
			{
				this.relatAssertion();
			}
		}
		break;
		} // end case 3		
	} // end switch	
}



private ozAssertion isAssertion()
{	
int i;

	this.assertion = new ozAssertionImpl();
	assertion.setType("is");
	for(i=0;i<2;i++)
	{
		if(((String) ttassertion.elementAt(i)).equalsIgnoreCase(this.concept))
		{;}
		else
		{
			Relation asse;
			asse = new Relation("concept",ttassertion.elementAt(i));
			assertion.setValue(asse);
		}
	}	
	return assertion;
}




private ozAssertion atribAssertion()
{
// [ATRIBNAME,CONCEPT,VALUE]	
boolean knownassert=false;

if(ttassertion.elementAt(0) instanceof String)		 	
{
	this.assertion = new ozAssertionImpl();
	assertion.setType("atrib");
	if(ttassertion.elementAt(2) instanceof String)
	{
		knownassert=true;
		Relation el;
		el = new Relation((String)ttassertion.elementAt(1),ttassertion.elementAt(2));
		assertion.setValue(el); 
	}
	// can be a number, a stringm, a number-string 
	if(ttassertion.elementAt(2) instanceof Vector)
	{
		if( ((Vector) ttassertion.elementAt(2)).elementAt(0) instanceof String)
		{
		String type = (String) (((Vector) ttassertion.elementAt(2)).elementAt(0));
		
		if(type.equalsIgnoreCase("NUMBER"))
		{
			knownassert=true;
			Number nb = new Number((Vector) ttassertion.elementAt(2));
		 	Relation el;
			el = new Relation((String)ttassertion.elementAt(0), nb);
	 		assertion.setValue(el); 					
		}	
		if(type.equalsIgnoreCase("STRING"))
		{
			knownassert=true;
			String value = (String) (((Vector) ttassertion.elementAt(2)).elementAt(1)); 						
			Relation el;
			el = new Relation((String)ttassertion.elementAt(0), value);
			assertion.setValue(el);	
		}
		
		if(!knownassert)
		{ /*
		System.err.println("-------------------------------");		
		System.err.println("Unknown assertion: " + type);
		System.err.println(TT.objectToPrettyString(((Vector) ttassertion.elementAt(2))));
		System.err.println("-------------------------------");
		*/
		}
	 }
	}		 						
}			 	
return assertion;	
}




private ozAssertion relatAssertion()
{

if(ttassertion.elementAt(0) instanceof String)		 	
{
	if(ttassertion.elementAt(1) instanceof String)
	{
		this.assertion = new ozAssertionImpl();		 	
		assertion.setType("relat");
		Relation el;
		el = new Relation((String) ttassertion.elementAt(0),ttassertion.elementAt(1));
		assertion.setValue(el);		
	}		 	
}	
return assertion;	
}



/**
 * Returns the concept.
 * @return String
 */
public String getConcept() 
{
	return this.concept;
}


/**
 * Sets the concept.
 * @param concept The concept to set
 */
public void setConcept(String concept) 
{
	this.concept = concept;
}


/**
 * Returns the assertion.
 * @return Assertion
 */
public ozAssertion getAssertion() {
	return assertion;
}


/**
 * Sets the assertion.
 * @param assertion The assertion to set
 */
public void setAssertion(ozAssertion assertion) 
{
	this.assertion = assertion;
}

}

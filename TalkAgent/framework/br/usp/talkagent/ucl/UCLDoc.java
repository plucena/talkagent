package br.usp.talkagent.ucl;

/* An UCL Document is a set of
* UClRelations
* UClConcepts
*/

import java.io.Serializable;
import java.util.Vector;
import br.usp.talkagent.util.GenList;
import br.usp.talkagent.util.ListData;

public class UCLDoc implements Serializable 
{

public GenList Concepts;
public Vector Relations;	
public boolean isBroken;

/**
 */
public UCLDoc()
 {
	super();
	Concepts = new  br.usp.talkagent.util.GenList();
	Relations = new Vector(10);
	isBroken = false;
	// vector may be initialized with any # of elements
	// it grows as needed.
}


/**
 */ 
public GenList getConcepts() 
{
	return this.Concepts;
}


public void setConcepts(GenList concepts) 
{
	this.Concepts = concepts;
}


/**
 * Sets the relations.
 * @param relations The relations to set
 */
public void setRelations(Vector relations) 
{
	this.Relations = relations;
}


/**
 * @return java.util.Vector
 */
public Vector getRelations() 
{
	return Relations;
}


public Object findConncept(String concept)
{
	return Concepts.find(concept);
}


public Object find(String concept)
{
int i;
int size = Concepts.getSize();
ListData elem;
String aux;

// System.err.println("Debug: Searching ucldoc for '" + concept + "' +(" + size + ")");
for(i=0;i<size;i++)
{
	elem =  (ListData) Concepts.elementAt(i);
	aux = (String) elem.getValue();
	// System.err.print(aux + ",");
	if (aux.indexOf(concept) >= 0)
	{
		// System.err.println(concept + " found");
		return elem;
	}
}
	// System.err.println("\n" + concept + " not found");
	return null;	
}

/**
 *  Returns a relation that has a given concept in its tail.
 */
public UCLRelation findRelationWithConcept(String concept)
{

Vector Elements;
UCLRelation relat=null;
int i=0;
boolean found=false;

while( (i<Relations.size()) && (!found) )
{
	relat = (UCLRelation) Relations.elementAt(i);
	Elements = relat.getElements();
	if(Elements.elementAt(0) instanceof UclConcept)
	{
		if((((UclConcept)Elements.elementAt(0)).getName()).equalsIgnoreCase(concept))
			found = true;		
	}
	if(Elements.elementAt(1) instanceof UclConcept)
	{
		if((((UclConcept)Elements.elementAt(1)).getName()).equalsIgnoreCase(concept))	
			found = true;		
	}
	i++;   
}
	return relat;
}


/**
* Find a relation with has a given relation on its tail
*/ 
public UCLRelation findRelationWithRelation(String relatid) 
{
int i=0;
boolean found = false;
UCLRelation relat=null;
Vector Elements;

while((i<Relations.size()) && !found)
{
	relat = (UCLRelation) Relations.elementAt(i);
	Elements = relat.getElements();
	
	if(Elements.elementAt(0) instanceof UCLRelation)
	{
		if((((UCLRelation)Elements.elementAt(0)).getID()).equalsIgnoreCase(relatid))
			found = true;
	}
	
	if(Elements.elementAt(1) instanceof UCLRelation)
	{
		if((((UCLRelation)Elements.elementAt(1)).getID()).equalsIgnoreCase(relatid))	
			found = true;
	}
	i++;
}
  return relat;
}



public void print()
{
	int i;
	UclConcept caux;
	UCLRelation relat;
	String e1, e2;

  	if(this.isBroken==false)
  	{
	// print concepts	
	System.err.println("-- UCL Message: --");
	System.err.println("-- Concepts:");
	for(i=0;i<Concepts.getSize();i++)
	{
		caux = (UclConcept) Concepts.elementAt(i);
		if(caux!=null)
		{	
			System.err.println("*: " + caux.getValue() + " - " + caux.getName());
		}
	}	
	// print relations		
	if(Relations!=null)	
	{
	System.err.println("-- Relations (" + Relations.size() + ")");		
	for(i=0;i<Relations.size();i++)
	{		
		relat = (UCLRelation) Relations.elementAt(i);		
		if(relat!=null)
		{
			Vector Elements = relat.getElements();
			if(Elements!=null)
			{
				if(Elements.elementAt(0) instanceof UclConcept) 		
			  		e1 = ((UclConcept) Elements.elementAt(0)).getName();
				else 
					  e1 = ((UCLRelation) Elements.elementAt(0)).getID();
			  	
				if(Elements.elementAt(1) instanceof UclConcept)
			  		e2 = ((UclConcept) Elements.elementAt(1)).getName();
				else 
			  		e2 = ((UCLRelation) Elements.elementAt(1)).getID();
			  	
				System.err.println("*: " + relat.getID() + "-" + relat.getType() + ":" + e1 + "," + e2);
			}
			else 
				System.err.println("Error: null element in relation");
		}	
	}
	}
	else
		System.err.println("Error inerpreting UCL Document:  no relations found");
  	}
	else 
		System.err.println("ERROR -- Broken UCLDoc -- ");	
	System.err.println("");	
}


}
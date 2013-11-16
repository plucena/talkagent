package br.usp.talkagent.kb.objects;

/**
 * @author Percival Lucena
 */

import java.util.*;

import br.usp.talkagent.kb.Concept;

public class ozConceptIndex 
{

LinkedHashSet set;
Vector Concepts;

public ozConceptIndex(Vector Concepts) 
{
int i;
String cpt;

	this.Concepts = Concepts;
	set = new LinkedHashSet();
	for(i=0;i<Concepts.size();i++)
	{	
		cpt = ((ozConcept) Concepts.elementAt(i)).getID();
		if(!contains(cpt))	
			set.add(cpt);		
	}	
}


public String elementAt(int pos)
{
int i;	
String aux="";

	Iterator it = set.iterator();
	for(i=0;i<=pos;i++)	
	{
		aux = (String)it.next();
	}
	return  aux;	
}


public int size()
{
	return set.size();
}


private boolean contains(String concept)
{
	boolean ct = false;
	int i;
	String aux;
	String cpt = concept.trim();


	for(i=0;i<set.size();i++)
	{
		aux = elementAt(i).trim();
		if(aux.equalsIgnoreCase(cpt))
		  	ct=true;		
	}
	return ct;
}



}

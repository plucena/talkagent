package br.usp.semanticagent.ucl.scripts;

/**
 * @author Administrator 
 */

import br.usp.talkagent.sentence.Message;
import br.usp.talkagent.ucl.UCLDoc;
import br.usp.talkagent.kb.objects.*;
//import br.usp.talkagent.kb.ozone.db.*;
import br.usp.talkagent.sentence.*;
import java.util.*;
import br.usp.talkagent.kb.*;

public class Script 
{
protected XktClient xktc;
protected UCLDoc ucldoc;


public Script(XktClient con, UCLDoc ucldoc)
{
	super();
	this.ucldoc = ucldoc;
	this.xktc = con;	
}
 
	
public Message run() throws Exception
{
	return null;
}


protected Sentence printChildren(String concept, ozConceptIndex chld)
{
	Element el;
	Sentence st;
	int j;
	
	st = new Sentence();
    el = new Element("concept", concept);
    el.setBold(true);   
 	st.addElement(el);
 	el = new Element("unknown", ":");
 	st.addElement(el); 	
	
	if(chld!=null)
	{	
		for(j=0;j<chld.size();j++)
		{
			el = new Element("concept", (chld.elementAt(j)));		 
			st.addElement(el);
			el = new Element("unknown",",");					
			st.addElement(el);			
		}
	return st;		
	}    	 
	return null;
}



protected Sentence printChildren(Sentence st, String concept, ozConceptIndex chld)
{
	Element el;
	int j;
	
	if(chld!=null)
	{	
		for(j=0;j<chld.size();j++)
		{
			el = new Element("concept", (chld.elementAt(j)));		 
			st.addElement(el);
			el = new Element("unknown",",");					
			st.addElement(el);			
		}
	 return st;		
	}
	return null;    	 
}


protected Vector AllChildren(Vector concepts, Message msg, int level)
{
int i;
String schild;
Vector grandchildren;
int aux;

	for(i=0;i<concepts.size();i++)
	{
	   schild = (((ozConcept)concepts.elementAt(i)).getID());	
       grandchildren = this.RecursiveChildren(schild,msg,level);
       if(grandchildren!=null)
       {
       	   aux = level+1;
       	   this.AllChildren(grandchildren,msg,aux);
       }      	
	}   	
	return null;
}


private Vector RecursiveChildren(String concept, Message msg, int level)
{
Vector grandchildren;
Sentence st;
Element el;

	grandchildren = xktc.getChildrenVector(concept);				
	ozConceptIndex gdchildren = new ozConceptIndex(grandchildren);	
	st = this.printChildren(concept,gdchildren);
	if(level==0)
	{
		Sentence sthr = new Sentence();	
		el = new Element("unknown","");
		sthr.addElement(el);		
		sthr.setHR(true);
		msg.addSentence(sthr);
	}
	msg.addSentence(st); 
	return grandchildren;   
}



}

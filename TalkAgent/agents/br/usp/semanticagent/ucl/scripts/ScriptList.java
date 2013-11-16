package br.usp.semanticagent.ucl.scripts;


import br.usp.talkagent.ucl.*;
import br.usp.talkagent.kb.*;
import br.usp.semanticagent.ucl.interpreter.*;
import br.usp.talkagent.sentence.Element;
import br.usp.talkagent.sentence.Message;
import br.usp.talkagent.sentence.Sentence;
import java.util.*;
import br.usp.talkagent.kb.objects.*;

/**
 * @author Percival Lucena
 * 
 * Agent, list objectX 
 */


public class ScriptList extends Script 
{

public ScriptList(XktClient con, UCLDoc ucldoc)
{
	super(con,ucldoc);	
}


public Message run() throws Exception
{
String finalresponse;
String child;
Vector children;
Message msg = new Message();
Sentence st;
Element el;
int i;
String conceptToList;

	conceptToList = conceptToList();

	// get children	
	children = xktc.getChildrenVector(conceptToList);
	ozConceptIndex chld  = new ozConceptIndex(children);

	// print concept to list
	st = printChildren(conceptToList,chld);
	st.setHR(true);
    msg.addSentence(st);    
		
    // print recusrsive tree
    AllChildren(children,msg,0);
     
	return msg;
}





private String conceptToList() throws Exception
{
UCLRelation relat, relat2;
UclConcept define;
String relatid;
String conceptToList="";

	// find relationx which contains "program-output" in tail
	relat = ucldoc.findRelationWithConcept("program-output");
	if(relat!=null)
		relatid = relat.getID();	
	else
		throw new Exception("Could not find concept tp list");
		
	// find relationy which is directed related to relationx such as relationy=relationx,agent
	relat2 = ucldoc.findRelationWithRelation(relatid);
	
	// warning maybe another relation (recursive)	
	// if relationy=relationx,Z then Z is the concept which should be list
	if(relat2.getComplementaryElement(relat) instanceof UclConcept)
	{
		define = (UclConcept) relat2.getComplementaryElement(relat);
		conceptToList = define.getName();
	}
	
	return conceptToList;		
}


}

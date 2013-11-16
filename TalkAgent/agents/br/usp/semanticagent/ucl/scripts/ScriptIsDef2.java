package br.usp.semanticagent.ucl.scripts;


import br.usp.talkagent.ucl.*;
import br.usp.talkagent.ucl.UCLRelation;
import br.usp.talkagent.ucl.UclConcept;
import br.usp.talkagent.util.Boolean;
import br.usp.talkagent.sentence.Element;
import br.usp.talkagent.sentence.Message;
import br.usp.talkagent.sentence.Sentence;
import br.usp.talkagent.kb.*;


/**
 * @author Percival Lucena
 * Validates Definition 
 */



public class ScriptIsDef2 extends Script 
{

public ScriptIsDef2(XktClient con, UCLDoc ucldoc)
{
	super(con, ucldoc);
}


public Message run()
{
	System.err.println("Debug: Starting IsDef2 Script");
	String response;
	UclConcept concept, category, stdc;	
	UCLRelation relat,relat2;
	String relatid, conceptname, categoryname;
	
	// find concept
	stdc = (UclConcept) ucldoc.find("standard-copula");
	relat = (UCLRelation) ucldoc.findRelationWithConcept("standard-copula");
	concept = (UclConcept) relat.getComplementaryElement(stdc);
	conceptname = concept.getName();
	// find category
	relatid = relat.getID();	
	relat2 = ucldoc.findRelationWithRelation(relatid);
	category = (UclConcept) relat2.getComplementaryElement((UCLRelation) relat);	
	categoryname = category.getName();
	System.err.println("Debug: validate " + conceptname + categoryname);
	
	Boolean resp = xktc.isDef(conceptname,categoryname);	
	response =  resp.toString();
	Message msg =  new Message();
	Sentence st = new Sentence();
	Element el = new Element("unknown",response);
	el.setBold(true);
	st.addElement(el);
	msg.addSentence(st);
	System.err.println("Response: " + response);
	return msg;
}


}

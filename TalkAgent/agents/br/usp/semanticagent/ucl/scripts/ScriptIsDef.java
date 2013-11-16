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
 */

/**
 * Validates Definition
 * Is potato a vegetable?
 */

public class ScriptIsDef extends Script 
{

public ScriptIsDef(XktClient con, UCLDoc ucldoc)
{
	super(con, ucldoc);
}


public Message run()
{
	String response;
	UclConcept concept, category, isa;	
	UCLRelation relat,relat2;
	String relatid, conceptname, categoryname;
	
	// find concept
	System.err.println("Debug: Starting IsDef2 Script");
	isa = (UclConcept) ucldoc.find("isa");
	relat = (UCLRelation) ucldoc.findRelationWithConcept("isa");
	concept = (UclConcept) relat.getComplementaryElement(isa);
	conceptname = concept.getName();
	// find category
	relatid = relat.getID();	
	relat2 = ucldoc.findRelationWithRelation(relatid);
	category = (UclConcept) relat2.getComplementaryElement((UCLRelation) relat);	
	categoryname = category.getName();
	
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


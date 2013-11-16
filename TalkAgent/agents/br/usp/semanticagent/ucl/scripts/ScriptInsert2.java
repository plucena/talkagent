package br.usp.semanticagent.ucl.scripts;


import br.usp.talkagent.ucl.*;
import br.usp.talkagent.sentence.Element;
import br.usp.talkagent.sentence.Message;
import br.usp.talkagent.sentence.Sentence;
import br.usp.talkagent.kb.*;


/**
 * @author Percival Lucena
 * 
 *  Insert new information on Knowledge Base
 */


public class ScriptInsert2 extends Script 
{

public ScriptInsert2(XktClient con, UCLDoc ucldoc)
{
	super(con, ucldoc);
}

/**
 * STANDARD-COPULA
 */

public Message run()
{
	UclConcept concept, category, stdc;	
	UCLRelation relat,relat2;
	String relatid, conceptname, categoryname;
	Message msg = new Message();
	Sentence st = new Sentence();
	Element el;


	// find concept
	System.err.println("Starting 'Insert2'  Script");
	stdc = (UclConcept) ucldoc.find("standard-copula");
	relat = (UCLRelation) ucldoc.findRelationWithConcept("standard-copula");
	concept = (UclConcept) relat.getComplementaryElement(stdc);
	conceptname = concept.getName();
	el = new Element("concept", conceptname); 
	el.setBold(true);
	st.addElement(el);
	el = new Element("unknown", "inserted in"); 	
	st.addElement(el);
	System.err.println(st.printHTML());
	
	// find category	
	relatid = relat.getID();	
	relat2 = ucldoc.findRelationWithRelation(relatid);
	category = (UclConcept) relat2.getComplementaryElement((UCLRelation) relat);
	categoryname =	category.getName();
	el = new Element("concept", categoryname); 
	el.setBold(true);
	st.addElement(el);
	el = new Element("unknown", "category"); 	
	st.addElement(el);
	msg.addSentence(st);
	System.err.println(st.printHTML());
	
	xktc.insertconcept(conceptname,categoryname);
		
	return msg;	
}


}

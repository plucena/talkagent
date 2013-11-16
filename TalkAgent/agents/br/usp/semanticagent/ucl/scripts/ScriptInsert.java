package br.usp.semanticagent.ucl.scripts;


import br.usp.talkagent.ucl.*;
import br.usp.talkagent.ucl.UCLRelation;
import br.usp.talkagent.ucl.UclConcept;
import br.usp.talkagent.sentence.Element;
import br.usp.talkagent.sentence.Message;
import br.usp.talkagent.sentence.Sentence;
import br.usp.talkagent.kb.*;

/**
 * 
 * @author Percival Lucena
 *
 * Insert new information on Knowledge Base */

public class ScriptInsert extends Script 
{


public ScriptInsert(XktClient con, UCLDoc ucldoc)
{
	super(con, ucldoc);
}


/**
 * IS A
 */
public Message run()
{
	UclConcept concept, category, isa;	
	UCLRelation relat,relat2;
	String relatid, conceptname, categoryname;
	Message msg = new Message();
	Sentence st = new Sentence();
	Element el;
	
	// find concept
	System.err.println("Starting 'Insert'  Script");
	isa = (UclConcept) ucldoc.find("isa");
	relat = (UCLRelation) ucldoc.findRelationWithConcept("isa");
	concept = (UclConcept) relat.getComplementaryElement(isa);
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
	categoryname = category.getName();
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

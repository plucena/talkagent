package br.usp.semanticagent.ucl.scripts;


import java.util.Vector;
import br.usp.talkagent.kb.objects.*;
import br.usp.talkagent.sentence.Element;
import br.usp.talkagent.sentence.Message;
import br.usp.talkagent.sentence.Sentence;
import br.usp.talkagent.ucl.UCLDoc;
import br.usp.talkagent.ucl.UCLRelation;
import br.usp.talkagent.ucl.UclConcept;
import br.usp.talkagent.kb.objects.*;
import br.usp.talkagent.kb.*;


/**
 * @author Percival Lucena
 *
 * E.g. use - What is "Object X"
 */


public class ScriptWhat extends Script
{

public ScriptWhat(XktClient con, UCLDoc ucldoc)
{
	super(con, ucldoc);	
}

public Message run() throws Exception
{
	UCLRelation relat,relat2;
	UclConcept define;
	int i;
	String relatid;
	String conceptToDefine = "";	
	Message msg;
	Sentence st;
	Element el;
	
	
	System.err.println("DEBUG@UCL-INTERPRETER - running script'what' ");
	// find concept who needs definition in relation:
	// get id relation with name object-interrogative-pronoun
	relat = ucldoc.findRelationWithConcept("object-interrogative-pronoun");
	if(relat!=null)
		relatid = relat.getID();	
	else
		throw new Exception("Could not found relation containing 'what'");

	// find relation associted to id
	// get other concept associate with id
	relat2 = ucldoc.findRelationWithRelation(relatid);
	// warning maybe another relation (recursive)
	if( relat2.getComplementaryElement(relat) instanceof UclConcept)
	{
		define = (UclConcept) relat2.getComplementaryElement(relat);
		conceptToDefine = define.getName();
	}
	else
	{
		throw new Exception("Could not found concept to be defined");
	}
	
	
	System.err.println("DEBUG@KM: Request Define Concept: " + conceptToDefine);
	// query knowlege repository to wonder if concept is found
	try
	{	
		ozConcept cpt = xktc.getConcept(conceptToDefine);	
		if(cpt != null)
		{
			// ask xkt for parent
			msg = new Message();
			st = new Sentence();
			el = new Element("concept", conceptToDefine);
			st.addElement(el);			
			String parent = xktc.getParent(conceptToDefine);
			if(parent!=null)			
			{
				el = new Element("unknown","is a kind of");
				st.addElement(el);			
				el = new Element("concept", parent);
				st.addElement(el);
				st.addBreak();
				msg.addSentence(st);
			
				// ask xkt for siblings
				st = new Sentence();
				el = new Element("unknown","Other");
				el.setItalic(true);
				st.addElement(el);
				el = new Element("concept", parent);		 
				el.setItalic(true);
				st.addElement(el);
				el = new Element("unknown","include:");
				el.setItalic(true);
				st.addElement(el);
				Vector siblings = xktc.getSiblingsVector(parent);
				if(siblings!=null)
				{
					ozConceptIndex sb = new ozConceptIndex(siblings);				
					st = this.printChildren(st,conceptToDefine,sb);
				}
				st.addBreak();
				msg.addSentence(st);			 		
			}
			else
			{
				el = new Element("unknown","is an ontology root concept (no parent)");
				st.addElement(el);
				st.addBreak();			
				msg.addSentence(st);
			}								 
	 		
			// ask xkt for children
			st = new Sentence();
			el = new Element("unknown","Other");
			el.setItalic(true);
			st.addElement(el);
			el = new Element("concept", conceptToDefine);		 
			el.setItalic(true);
			st.addElement(el);
			el = new Element("unknown","include:");
			el.setItalic(true);
			st.addElement(el);
        	Vector children = xktc.getChildrenVector(conceptToDefine);
        	ozConceptIndex chld = new ozConceptIndex(children);				
		    st = this.printChildren(st,conceptToDefine,chld); 
		    st.addBreak();      	
			msg.addSentence(st);	
        	
        	// concept assertions
        	st = new Sentence();
			el = new Element("unknown","Known facts about");
			el.setItalic(true);
			st.addElement(el);
			el = new Element("concept", conceptToDefine);		 
			el.setItalic(true);
			st.addElement(el);
			el = new Element("unknown","include:");
			el.setItalic(true);
			st.addElement(el);    	        	
			msg.addSentence(st);
			ozConcept memcpt = this.xktc.getConcept(conceptToDefine);				
        	Vector assertions = memcpt.getAssertions();
     		int na = assertions.size();	

			for(i=0;i<na;i++)
			{
				ozAssertion ozA = (ozAssertion) assertions.elementAt(i);
				if(ozA != null)
				{ 						 
					//if(ozA.getType().equalsIgnoreCase("relat"))						
					//{
					st = new Sentence();
					el = new Element("concept", conceptToDefine);
					st.addElement(el);  					 		 
					Relation rl =ozA.getvalue();
					if(rl!=null)
					{
						el = new Element("concept", rl.getType());
						st.addElement(el);
						el = new Element("concept", rl.getValue().toString()); 					
						st.addElement(el);
					}
// 					st.setType("assertion"); 		  
					msg.addSentence(st);					
					//}					
				}								
			}		
        	
			return msg;
		}
		else
		{
			msg = new Message();
			st = new Sentence();
			String error = "Could not find concept " + conceptToDefine;
			el = new Element("unnknown", error);
			st.addElement(el);
			msg.addSentence(st);
			return msg;
		}
	}
	catch (Exception e)
	{	
		e.printStackTrace();
	}
	
	return null;
}


}

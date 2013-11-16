package br.usp.semanticagent.ucl.scripts;


import br.usp.talkagent.ucl.*;
import br.usp.semanticagent.ucl.interpreter.*;
import br.usp.talkagent.sentence.Element;
import br.usp.talkagent.sentence.Message;
import br.usp.talkagent.sentence.Sentence;
import br.usp.talkagent.kb.*;


/**
 * @author Percival Lucena
*
* E.g. use - Who is "Person X"?
*/

public class ScriptWho extends Script
{

public ScriptWho(XktClient con, UCLDoc ucldoc)
{
	super(con,ucldoc);
}

public Message run() throws Exception
{
	
UCLRelation relat, relat2;
UclConcept define;
String relatid;
String conceptToDefine;
String finalresponse;

// find concept who needs definition in relation:
// get id relation with name object-interrogative-pronoun
relat = ucldoc.findRelationWithConcept("human-interrogative-pronoun");
if(relat!=null)
	relatid = relat.getID();	
else
	throw new Exception("Could not found relation containing 'who'");
// find relation associted to id
// get other concept associate with id
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
	throw new Exception("Could not found person name in the sentence");

System.err.println("DEBUG@XKT: Request get info from person: " + conceptToDefine);

Message msg = new Message();
finalresponse = "Should return info from: " + conceptToDefine;
return msg;

// query knowlege repository to wonder if concept is found

// get parents, simblings and relations associated 

// send answer to user agent.
}

}

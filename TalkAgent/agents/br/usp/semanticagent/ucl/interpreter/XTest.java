package br.usp.semanticagent.ucl.interpreter;

/**
 * @author: Percival Lucena 
 */

public class XTest 
{

/**
 * XTest constructor comment.
 */
public XTest() {
	super();
}


/**
 * @param args java.lang.String[]
 * @exception java.lang.Exception The exception description.
 */
public static void main(String args[]) throws java.lang.Exception 
{

	UclInterpreterAgent knowledgeAgent = new UclInterpreterAgent();
	knowledgeAgent.init();
	knowledgeAgent.register();
	knowledgeAgent.run();
	System.err.println(args[0]);
}

}
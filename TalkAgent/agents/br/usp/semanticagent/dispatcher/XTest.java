package br.usp.semanticagent.dispatcher;

/**
 * Insert the type's description here.
 * Creation date: (19/06/02 16:46:13)
 * @author: 
 */
public class XTest {
/**
 * XTest constructor comment.
 */
public XTest() {
	super();
}
/**
 * Insert the method's description here.
 * Creation date: (19/06/02 16:47:00)
 * @param args java.lang.String[]
 * @exception java.lang.Exception The exception description.
 */
public static void main(String[] args) throws java.lang.Exception 
{
	DispatcherAgent dispatcher = new DispatcherAgent();
	dispatcher.init();
	dispatcher.register();
	dispatcher.run();
	System.err.println(args[0]);
}

}
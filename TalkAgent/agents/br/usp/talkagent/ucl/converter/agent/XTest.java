package br.usp.talkagent.ucl.converter.agent;



public class XTest {

 
public static void main(	String args[])
{
	UclAgent uclagent = new UclAgent();
	uclagent.init();
	uclagent.register();	
	uclagent.run();
	System.err.println(args[0]);
}
/**
 * UclLanguageTest constructor comment.
 */
public XTest() 
{
	super();
}

}
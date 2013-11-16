package br.usp.talkagent.sentence;

/**
 * @author root
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class XTest 
{

public static void main(String args[]) throws Exception
{

TTEnglishConcept ec = new TTEnglishConcept("pomme");
String meaning = ec.parse();
System.out.println("pomme == " + meaning);
ec = new TTEnglishConcept("beer");
meaning = ec.parse();
System.out.println("beer == " + meaning);
ec = new TTEnglishConcept("animal");
meaning = ec.parse();
System.out.println("animal == " + meaning);

}


}

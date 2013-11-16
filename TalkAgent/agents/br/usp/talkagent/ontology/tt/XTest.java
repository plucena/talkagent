package br.usp.talkagent.ontology.tt;

/**
 * @author Administrator
 */

import br.usp.talkagent.kb.memtree.mtAssertion;

public class XTest 
{

public static void main(String args[]) throws Exception
{	
	String Concept;	
	ttAssertion ttassertions;
	mtAssertion assertobj;
	int i,size;
	
	if(args.length>0)
		System.err.println(args[0]);	
	
	Concept = "human";

	ttassertions = new ttAssertion(Concept);	
	// Assertions is a vector of Assertion objects	
	System.err.println("---------------------------------");
  	System.err.println(ttassertions.ttrawsize() + " Assertions:");
	ttassertions.printTTAssertions();
	System.err.println("---------------------------------");
	

    size = ttassertions.size();
   	System.err.println(size + " Assertions:");
    for(i=0;i<size;i++)
    {
    	System.err.println("(" + i + ") ---------------------------------");
    	assertobj = (mtAssertion) ttassertions.getAssertion(i);
    	assertobj.print();    	
    }
    System.out.println("");
}

}

package br.usp.talkagent.kb.ozone.db;

/**
 * @author Percival Lucena
 */

import br.usp.talkagent.kb.Relation;
import br.usp.talkagent.kb.objects.ozAssertion;
import br.usp.talkagent.kb.objects.ozAssertionImpl;
import br.usp.talkagent.kb.objects.ozConcept;
import br.usp.talkagent.kb.objects.ozConceptImpl;
import br.usp.talkagent.util.PrefReader;
import java.util.Vector;

public class ozXTest 
{

public static void main(String args[]) throws Exception
{
 ozConceptImpl con;
 ozConcept ocroot, ocroot2;
 ozKB kb;
 PrefReader pr;
 String OzoneServer;
 int i;
  
	pr = new PrefReader();	
	OzoneServer = pr.getOzoneServer();
	kb = new ozKB(OzoneServer); 
	if(args.length>0)
		System.err.println(args[0]);
		 
	System.out.println("Testando Ozone KB");

	
	/* Inserindo Mamiferos */	
	con = new ozConceptImpl("Mamiferos"); 	 		
	ocroot = kb.insertRoot(con);
	
	// macaco, baleia, cachorro, leao 	
   //-----------------------------------------;
	con = new ozConceptImpl("Macaco");  
		//macaco is a primata
		ozAssertionImpl assertion = new ozAssertionImpl();
		assertion.setType("is");
		Relation asse;
		asse = new Relation("concept","primata");
		assertion.setValue(asse); 		
		con.addAssertion_update((ozAssertion) assertion);
		ocroot2 = kb.insert(con,ocroot); 
		   		
	// no children
	con = new ozConceptImpl("Macaco Prego"); 		
	kb.insert(con,ocroot2); 
	
	// no children	
	con = new ozConceptImpl("Mico Leao"); 	   		
	kb.insert(con,ocroot2);
	
	
	//-----------------------------------------
	con = new ozConceptImpl("Baleia");
	ocroot2 = kb.insert(con,ocroot);
	
	// no children
	con = new ozConceptImpl("Baleia Orca"); 	
	kb.insert(con,ocroot2);
	
	con = new ozConceptImpl("Baleia do Sea World"); 	
	kb.insert(con,ocroot2);
	
	
	
	//-----------------------------------------
    // no children
	con = new ozConceptImpl("Leao"); 	
	ocroot2 = kb.insert(con,ocroot);


 
	 //-----------------------
	/* Inserindo Repteis */
	//-----------------------

	con = new ozConceptImpl("Repteis"); 
	ocroot =  kb.insertRoot(con);

	
	con = new ozConceptImpl("Lagarto"); 	
	kb.insert(con,ocroot);   
	
	con = new ozConceptImpl("Cobra"); 	
	kb.insert(con,ocroot);        
	
	
		
  	/* Inserindo Aves */ 	
	con = new ozConceptImpl("Aves"); 
	ocroot = kb.insertRoot(con);
	 
	 
	/* Inserindo Dinossauros */ 	 	
	con = new ozConceptImpl("Dinossauros"); 
	ocroot = kb.insertRoot(con);

	
	/* Inserindo Peixes */	
	con = new ozConceptImpl("Peixes"); 
	ocroot = kb.insertRoot(con);



	/* Search and print */
	con = 	new ozConceptImpl("Macaco");
	System.out.println("Searching " + con.getID());
	ozConcept ozc = (ozConcept) kb.FindConcept(con);
	if(ozc!=null)
	{
		System.out.println("Concept found: " + ozc.print());
		System.out.println("Parent: " + (kb.FindParent(ozc)).getID());
		System.out.println("Direct Children: ");
		Vector children = kb.FindDirectChildren(ozc);
		for(i=0;i<children.size();i++)
			System.out.println("-" + ((ozConcept)children.elementAt(i)).getID() );		
		System.out.println("Siblings: ");
		children = kb.FindSiblings(ozc);
		for(i=0;i<children.size();i++)
			System.out.println("-" + ((ozConcept)children.elementAt(i)).getID() );		
	}
	else
	System.out.println("Concept not found");

	System.out.println("Printing KBTree \n");
	kb.print();
}




}

package br.usp.talkagent.ontology.tt;

import java.io.Serializable;
import java.util.Vector;
import br.usp.talkagent.kb.Concept;
import br.usp.talkagent.kb.memtree.mtConcept;
import br.usp.talkagent.kb.memtree.mtKB;
import br.usp.talkagent.util.Boolean;
import br.usp.talkagent.util.ListNode;
import com.signiform.tt.TTConnection;


public class ttMemTreeKB implements Serializable 
{

private TTConnection tt;
private mtKB ttmtKB;
private String ttServer_ip;

/**
 * XTestMenu constructor comment.
 */
public ttMemTreeKB(String ttServer_ip)
{
	this.ttServer_ip = ttServer_ip;
}
 

/**
 * Return number of concepts
 */ 
public int numberOfConcepts()
{
	return ttmtKB.getNumberOfConcepts();
}


public int numberOfAssertions()
{
	return ttmtKB.getNumberOfAssertions();
}


/**
 * Loads Knowledge Base From Thought Treasure. This methood is called if no
 * other knowledge base is found @ /db/kb.kb
 */
public void Load()
{
	Vector vparents;
	Vector vchildren;
	mtConcept con;
	int i;
			
	try
 	{
	 	ttmtKB = new mtKB();	 	
		tt = new TTConnection(this.ttServer_ip);
				
		vparents = tt.children("concept");
		System.out.println("----------- Loading Thought Treasure KB");
		for(i=0;i<vparents.size();i++)		
 	    {						
			con = new mtConcept((String) (vparents.elementAt(i))); 	   		
			ttmtKB.insertRoot(con);
			vchildren = tt.children((String) (vparents.elementAt(i)));
	 		// test if there are children
	 		if(vchildren.size()>0)
 	        {	 	     
	 		   LoadChildren((String) (vparents.elementAt(i)),(Concept) con);	 		  
 	        }
 	    }
 	    System.out.println("----------- " + "TTKB Loaded");
	 	System.out.println("----------- " + this.numberOfConcepts() + " concepts");
	 	System.out.println("----------- " + this.numberOfAssertions() +  " assertions");
 	tt.close();
 	 
 }
 catch(Exception e)
 {
  e.printStackTrace(System.out);
 } 
}



private void LoadChildren(String conceptName, Concept parent)
{

	int i;
	Vector vparent;
	Vector vchildren;
	mtConcept con;
	ttAssertion ttassertions;

	if (conceptName.length() > 0)
	{
		try 
		{
			vparent = tt.children(conceptName);

			for (i = 0; i < vparent.size(); i++)
			{
				con = new mtConcept((String) (vparent.elementAt(i)));
				ttassertions = new ttAssertion((String) (vparent.elementAt(i)));
				con.setAssetions(ttassertions.getAssertions());	
				this.ttmtKB.insert((Concept)con,parent);

				// testchildren
				vchildren = tt.children((String) vparent.elementAt(i));
				// recursive call to children
				if (vchildren.size() > 0)
				{				
					// THE 2ND PARAM SHOULDM'T IT BE "CON" INSTEAD OF "PARENT"???????
					LoadChildren((String)(vparent.elementAt(i)),(Concept)parent);
					
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace(System.out);
		}
	}
}





public mtConcept findConcept(String Concept) 
{
mtConcept caux; 

	caux = new mtConcept(Concept);
	return (mtConcept) ttmtKB.FindConcept(caux);
}
		

/** 
 */
public Vector findSiblings(String Concept) 
{
mtConcept caux; 

	caux = new mtConcept(Concept);
	return ttmtKB.FindSiblings(caux);
}



/**
 */
public mtConcept findParent(String sconcept) 
{
	mtConcept caux; 

	caux = new mtConcept(sconcept);
	return (mtConcept) ttmtKB.FindParent(caux);
}


/**
*/
public Vector findDirectChildren(String sconcept)
{
	if(sconcept!=null)
	{
		mtConcept caux = new mtConcept(sconcept);	
		return ttmtKB.FindDirectChildren(caux);
	}
	else
		System.err.println("ERROR - Searching children from a null concept: " + sconcept);
	return null;
}	


public void insertConcept(String concept, String parent)
{
	mtConcept cconcept = new mtConcept(concept);
	mtConcept cparent = new mtConcept(parent);
	this.ttmtKB.insert(cconcept,cparent);	 	
}


public Boolean isA(String concept, String ancestor)
{
	mtConcept cconcept = new mtConcept(concept);
	mtConcept cancestor = new mtConcept(ancestor);
	Boolean result = this.ttmtKB.isA(cconcept,cancestor);	
	return result;
}




}
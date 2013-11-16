package br.usp.talkagent.kb.memtree;

/**
 * Knowledge Base Class
 * WARNING:
 * memtree.KB keeps all data in memory and does not store its changes
 * it needs lots of RAM as KB grows...
 */

import br.usp.talkagent.kb.*;
import java.util.Vector;
import java.io.*;
import br.usp.talkagent.util.GenList;
import br.usp.talkagent.util.Boolean;


public class mtKB implements KnowledgeBase, Serializable 
{

mtMemTree tree;
int numberOfConcepts;
int numberOfAssertions;


public mtKB() 
{
	tree = new mtMemTree();
	numberOfConcepts = 0;
	numberOfAssertions = 0;
}


public void insertRoot(Concept sConcept)
{
  this.tree.insertRoot(sConcept);
  this.numberOfConcepts++;
  this.numberOfAssertions += sConcept.getNumberOfAssertions();
}


public void insert(Concept concept, Concept parent)
{
	this.tree.insert(concept,parent);
	this.numberOfConcepts++;
	this.numberOfAssertions += concept.getNumberOfAssertions();
}


/***
*/
public Concept FindConcept(Concept sConcept)
{
  return tree.getConcept(sConcept);	
}



public Concept FindParent(Concept sconcept)
{
	return tree.FindParent(sconcept);
}



/**
 * Returns a Vector containing all siblings of a giving concept;
 */ 
public Vector FindSiblings(Concept sconcept) 
{
int i;
br.usp.talkagent.util.GenList siblings;
Vector vsiblings = new Vector(1);
	
	siblings = tree.getSiblings(sconcept);
	// siblings are on list this.current node,
	// so all that's need is to convert it to a Vector
	for(i=0;i<siblings.getSize();i++)
	{
		if(siblings.elementAt(i) instanceof mtConcept)
	    	vsiblings.addElement(siblings.elementAt(i));
	}
	return vsiblings;
}




public Vector FindDirectChildren(Concept sconcept)
{	
	int i = 0;
	boolean fnd = false;
   	Vector vct = new Vector(1); 
   	mtConcept caux;
   	GenList children;
   	   	   	
 	System.err.println("DEBUG -- Searching children from " + sconcept.getID());
 	GenList ls = tree.getSiblings(sconcept);
	if(ls!=null)
	{		
		ls.moveFirst();
	    while((i<ls.getSize())&&(!fnd))
		{
			caux = (mtConcept) ls.elementAt(i);
	    	if(  (caux.getID()).equalsIgnoreCase(sconcept.getID()) )
	    	{
	    		fnd=true;
	    	}
	    	else
	    	{
				ls.moveNext();
				i++;		
	    	}
		}	
		if(fnd)
		{
			if(ls.getCurrNode().children!=null)
			{
				children = ls.getCurrNode().children;
				System.err.println("DEBUG@XKT: " + children.getSize() + " children found");	
				for(i=0; i<children.getSize();i++)
				{
					vct.add(children.elementAt(i));		
				}
			}
		}		
	}
    return vct;
}


public Boolean isA(Concept con, Concept Ancestor)
{
boolean found=false;
boolean hasended=false;
mtConcept previousRelative;

	this.FindConcept(con);
//	this.currentNode = this.found;
    previousRelative = (mtConcept) this.FindParent(con);

	while( (!found) && (!hasended) )
	{		
		if(previousRelative.isEqual((mtConcept) Ancestor))
			found=true;
		else
		{
			try
			{
				previousRelative = (mtConcept) this.FindParent(previousRelative);
				if(previousRelative == null)
					hasended=true;	
			}
			catch(Exception e)
			{
				hasended=true;	
			}		
		}	
	}	
	return new Boolean(found);
}



public void print()
{
	this.tree.Print();
}


/**
* Return the number of elements of the KB Tree
*/       
public int size() 
{
	return 0;
}


/**
 * Returns the numberOfConcepts.
 * @return int
 */
public int getNumberOfConcepts() 
{
	return numberOfConcepts;
}


/**
 * Returns the numberOfAssertions.
 * @return int
 */
public int getNumberOfAssertions() 
{
	return numberOfAssertions;
}


}
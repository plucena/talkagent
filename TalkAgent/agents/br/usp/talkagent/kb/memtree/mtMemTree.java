package br.usp.talkagent.kb.memtree;

/**
 * @author Percival Lucena
 */

import br.usp.talkagent.kb.Concept;
import br.usp.talkagent.util.GenList;
import br.usp.talkagent.util.ListData;
import br.usp.talkagent.util.ListNode;

public class mtMemTree 
{


public GenList Root;
public GenList currentNode;
public GenList Elems;
public GenList found;
public int currentLevel;

	
public mtMemTree()
{
	super();
	currentLevel=1;
	Elems = new br.usp.talkagent.util.GenList();
	currentNode = Elems;
}


public void Print()
{
 int i;
 mtConcept caux;
 Elems.moveFirst();
	
 for (i=0; i<this.Elems.getSize();i++)
 {
   // print current element
   caux =  (mtConcept)(this.Elems.getCurrNode().data);
   System.out.print("*");
   System.out.println(caux.getID());
   // print subitems recursive
   if(this.Elems.getCurrNode().children != null)
	   PrintTree(this.Elems.getCurrNode().children,1);
   this.Elems.moveNext();  
  }
}


private void PrintTree(br.usp.talkagent.util.GenList pt, int sLevel)
{
	int i,j,aux;
	mtConcept caux;

	pt.moveFirst();

	for(i=0;i<pt.getSize();i++)
	{

	  for (j=0; j<sLevel; j++)
		System.out.print("-");

  	  caux = (mtConcept)(pt.getCurrNode().data);	
  	  System.out.println(caux.getID());
  	   if(pt.getCurrNode().children != null)
  	   {
		  aux=sLevel+1;
	  	  PrintTree(pt.getCurrNode().children,aux);
  	   }
  	   pt.moveNext(); 
	}
}


public boolean insert(Concept concept, Concept parent)
{
	this.getConcept(parent);
	this.currentNode = this.found;
	this.MoveDown();	
 	this.insert(this.currentNode, (mtConcept)concept);
	// return true;
return false;	
}


public boolean insertRoot(Concept concept)
{
 	this.insert(Root, (mtConcept)concept);
	// return true;
return false;	
}


private void insert(GenList listpt, mtConcept value)
{
	// test tree level 	
	// insert in root
	if (listpt == Root) 
	{	
		Elems.insert((ListData) value);		
	} 
	else
	{
	   listpt.insert((ListData) value);
	}
	
}

public Concept getConcept(Concept sConcept)
{
	int i;
	mtConcept caux, caux2; 

	Elems.moveFirst();

	for (i = 0; i < this.Elems.getSize(); i++)
	{
		// test current element
		caux = (mtConcept) (this.Elems.getCurrNode().data);
		if (caux.isEqual((mtConcept)sConcept))
		{
			this.found = this.Elems;
			return (caux);
		}
		else
		{
			// recursive search subitems 
			if (this.Elems.getCurrNode().children != null)
			{
				caux2 = searchTree(this.Elems.getCurrNode().children, (mtConcept)sConcept);
				if (caux2 != null)
					return (caux2);
			}
			this.Elems.moveNext();
		}
	}

	// concept not found
	return null;
}



public GenList getSiblings(Concept sConcept)
{
	int i;
	mtConcept caux, caux2; 

	Elems.moveFirst();

	for (i = 0; i < this.Elems.getSize(); i++)
	{
		// test current element
		caux = (mtConcept) (this.Elems.getCurrNode().data);
		if (caux.isEqual((mtConcept)sConcept))
		{ 
			return (this.Elems);
		}
		else
		{
			// recursive search subitems 
			if (this.Elems.getCurrNode().children != null)
			{
				caux2 = searchTree(this.Elems.getCurrNode().children, (mtConcept)sConcept);
				if (caux2 != null)
					return (this.found);
			}
			this.Elems.moveNext();
		}
	}

	// concept not found
	return null;
}


/**
* Private methood. Recusivelly search tree.
*/
private mtConcept searchTree(GenList pt, mtConcept concept)
{

int i;
mtConcept caux, caux2;

	pt.moveFirst();

	for (i = 0; i < pt.getSize(); i++)
	{
		// stop condition
		caux = (mtConcept) (pt.getCurrNode().data);		
		if (caux.isEqual(concept))
		{
			this.found = pt;					
	        return caux;
		}
	    else // recursive search
			{
				if (pt.getCurrNode().children != null)
				{
					caux2 = searchTree(pt.getCurrNode().children, concept);
					if(caux2!=null)
					{
						return caux2;
					}
				}                
				pt.moveNext(); 
			}
	}
	return null;
}



public Concept FindParent(Concept sconcept)
{
	int i;
	mtConcept sfound, caux;
	br.usp.talkagent.util.GenList cnode;
	br.usp.talkagent.util.GenList parentnode;
	ListNode uncle;

	System.err.println("--- [Parent Search] Searching concept " + sconcept.getID()); 

	cnode = this.getSiblings(sconcept);
	sfound =  (mtConcept) this.getConcept(sconcept);
	
	if (cnode != null)
	{		
		parentnode = cnode.getParent();
		//System.err.println("DEBUG@XKT Geting parent node.. ");
		// test all elements of parent node, for imediate children equals the one found
		if (parentnode != null)
		{
			parentnode.moveFirst();
			//System.err.println("DEBUG@XKT: Searching concept uncles... ");
			for (i = 0; i < parentnode.getSize(); i++)
			{
				uncle = parentnode.getCurrNode();				
				caux = null;
				try
				{
					if (uncle.children != null)
						if (uncle.children.find(sfound.getID()) instanceof mtConcept)
							caux = (mtConcept) uncle.children.find(sfound.getID());
				}
				catch (Exception e)
				{
					System.err.println("ERROR@XKT - Internal Error - Error accessing parent node content");
				}

				if (caux != null)
				{
					//System.err.println("DEBUG@XKT: Parent Found " + caux.getID());
					if (caux.getID().equalsIgnoreCase(sfound.getID()))
					{
						System.err.println("DEBUG@XKT: Parent Concept Found: " + ((mtConcept) uncle.data).getID());
						// this uncle is the parent, because concept is his son!
						return (mtConcept) uncle.data;
					}
				}
				parentnode.moveNext();
			}
		}
		else
			System.err.println("WARNING: Could not get parent node from concept");		
	}
	return null;
}



//*****************************************************
// INTERNAL METHOODS
//*****************************************************

private void MoveDown()
{

if (this.currentNode.getCurrNode().children == null)
{
	this.currentNode.getCurrNode().children = new br.usp.talkagent.util.GenList();
	this.currentNode.getCurrNode().children.setParent(this.currentNode);	
}
	  
	currentNode = currentNode.getCurrNode().children;
	currentLevel++;
}


// left pointer
private void MoveLeft()
{
}


private void MoveRight()
{
//  if (pt != Root)
	 this.currentNode.moveNext();  
}
// up pointer one level in Tree


private void MoveUp()
{	
	currentNode = currentNode.getParent();
}







}
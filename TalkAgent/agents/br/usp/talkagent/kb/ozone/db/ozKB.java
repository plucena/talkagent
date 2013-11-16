package br.usp.talkagent.kb.ozone.db;

/**
 * @author Percival Lucena
 */

import br.usp.talkagent.kb.objects.ozAssertion;
import br.usp.talkagent.kb.objects.ozConcept;
import br.usp.talkagent.kb.objects.ozConceptImpl;
import br.usp.talkagent.util.Boolean;
import br.usp.talkagent.component.*;
import java.util.Vector;

public class ozKB 
{

ozOODB oz;


public ozKB(String server)
{
	super();
	oz=null;
	oz = new ozOODB(server);	
}


	
public ozConcept insertRoot(ozConcept sConcept)
{
ozConcept cpt;
	
	try
	{		
		cpt = (ozConcept) oz.addConcept(sConcept);
		if(cpt!=null)		
			oz.addCategory(sConcept); 		
		else // concept probabilly alread exists
			cpt = oz.getConcept(sConcept.getID()); 
			
		return cpt; 		
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	return null;
}


public ozConcept insert(ozConcept concept, ozConcept parent)
{	
	ozConcept cpt;		
	cpt =(ozConcept) oz.getConcept(concept.getID());
	
	if(cpt==null)
	{		
		return oz.addConcept(concept,parent);		
	}
	else
	{
		if(parent==null)
			System.err.println("-- Error: Could not insert " + concept.getID() + ". Parent not found!");
		else
			if(cpt!=null)
			{
				System.err.println("Warning: Concept " + concept.getID() + " already exists");
				return cpt;
			}
	}
	return null;
}

public void delete(ozConcept concept)
{
	ozConcept parent;
	parent = this.FindParent(concept);
	// handle db corruption, concept not found but parent still points to it
	if(parent==null)
	{
		parent = oz.getConceptWith(concept);			
	}	
	if(parent!=null)
	{	
		this.oz.delete(concept,parent);
	}
	else
		System.err.println("ERROR@XKT-KB: UNable to find parent from concept " + concept.getID() + " - InnerSearch  has failed");
}


public void print()
{
	System.err.println("Printing Ozone KB");
	oz.print();
}


public int getNumberOfConcepts()
{
	return this.oz.getNumberOfConcepts();
}

public int getNumberOfAssertions()
{
	return this.oz.getNumberOfAssertions();
}


public ozConcept FindConcept(ozConcept sConcept)
{	
	return oz.getConcept(sConcept.getID());
}

/***
 *  Returns a memory instance of a OzoneDB concept
 */
public ozConcept getConcept(ozConcept sConcept)
{
	int i, size;
	Vector Assertions = new Vector(1);
	ozAssertion assertion;
	Vector concepts = new Vector(1);
	
	ozConcept ozcpt = oz.getConcept(sConcept.getID());
	if(ozcpt!=null)
	{
	ozConceptImpl memcpt = new ozConceptImpl();
	memcpt.setID_update(ozcpt.getID());
	size = ozcpt.getAssertions().size();
	for(i=0;i<size;i++)
	{
		assertion = (ozAssertion) ozcpt.getAssertions().elementAt(i);
		Assertions.addElement(assertion);
	}
	memcpt.setAssetions_update(Assertions);	
	memcpt.setNumberOfChildren_update(ozcpt.getNumberOfChildren());
	memcpt.setNumberOfDirectChildren_update(ozcpt.getNumberOfDirectChildren());
	memcpt.setCompontLinks_update(ozcpt.getComponetLinks());
    return memcpt;
	}
	else
	return null;
}


public ozConcept getOzConcept(ozConcept sConcept)
{	
	ozConcept ozcpt = oz.getConcept(sConcept.getID());
	
	return ozcpt;
}


public void renameConcept(ozConcept concept, ozConcept newname)
{	
	oz.renameConcept(concept,newname);	
}



/***
* Return  memomory instance of a given concept's parent.
*/
public ozConcept FindParent(ozConcept ctf)
{
	ozConcept cpt; 
    ozConceptImpl prn,prn2;
    ozConcept parent=null;
    
    System.out.println("");  
    System.out.println("DEBUG@XKT-KB: Find Parent") ;   
	cpt = this.FindConcept(ctf);
	
	if(cpt!=null)
	{
		System.err.println("concept " + cpt.getID()) ;
		if(cpt.getParentID()!=null)
		{
		prn = new ozConceptImpl(cpt.getParentID());	
		parent = this.FindConcept(prn);
		}		
	    if(parent!=null)
	    {
   			System.err.println("parent found " + parent.getID()) ;
   			System.err.println("----------------------------------------");
   			// should convert result to memory object, other wise, other agent
   			// would need to access ozone, which somehow does NOT work
   			prn2 = new ozConceptImpl();
   			prn2.setAssetions_update(parent.getAssertions());
   			prn2.setID_update(parent.getID());
   			prn2.setParent_update(parent.getParentID());
	    	return prn2;
	    }
	    else 
	    {
	    	if(ctf!=null)
	    	{
		    	System.err.println("Warning - Could not find parent from concept " + ctf.getID()) ;
		    	System.err.println("----------------------------------------");
		    return null;
	    	}
	    }
	}
	else
	{
		if(ctf!=null)
		{
			System.err.println("Error searching from parent: Could'nt find concept " + ctf.getID()) ;
			System.err.println("----------------------------------------");
		}
	}
	return null;
}



/***
 * Return memory instances of a given concept� children 
 */ 
public Vector FindDirectChildren(ozConcept ctf)
{
	int i;
	ozConcept prn;
	Vector children, result;
	result = new Vector(1);
	String smbg;
	ozConcept smblg;
	
	prn = this.FindConcept(ctf);
	if(prn!=null)
	{
		children = prn.getChildrenVector();
		for(i=0;i<children.size();i++)	
		{				
			smbg = (String)children.elementAt(i);
			if(smbg!=null)
			{			
				smblg = new ozConceptImpl(smbg);
				ozConceptImpl caux = new ozConceptImpl();
	   			caux.setAssetions_update(smblg.getAssertions());
	   			caux.setID_update(smblg.getID());
   				caux.setParent_update(smblg.getParentID());	
   				caux.setNumberOfDirectChildren_update(smblg.getNumberOfDirectChildren());			
				result.addElement(caux);
			}
		}
	return result;	
	}	
	return null;
}


public Vector getAncestors(ozConcept ctf)
{
Vector response, path;
ozConcept curEl;
int i, size;

	response = new Vector(1);
	curEl =  ctf;

	System.err.println("DEBUG@XKT - Retrieving path " + ctf.getID());
	while(curEl != null)
	{
		System.err.println(":" + curEl.getID());
		response.add(curEl.getID());
		curEl = this.FindParent(curEl);	
	}
	path = new Vector(1);
	// invert response vector;
	size = 	response.size();
	for(i=0;i<size;i++)	
	{
		path.add(response.elementAt(size-i-1));
	}
	return path;	
}

public Vector FindSiblings(ozConcept ctf)
{
	if(ctf!=null)
	{		
	    ozConcept prn = this.FindParent(ctf);
    	if(prn!=null)
	    {    	
    		return this.FindDirectChildren(prn);
	    }
	}
	return null;
}



public Boolean isA(ozConcept con, ozConcept Ancestor)
{
boolean found=false;
boolean hasended=false;
ozConcept previousRelative;

	this.FindConcept(con);
//	this.currentNode = this.found;
    previousRelative = (ozConcept) this.FindParent(con);

	while( (!found) && (!hasended) )
	{		
		if(previousRelative.getID().indexOf(Ancestor.getID())>=0)
			found=true;
		else
		{
			try
			{
				previousRelative = (ozConcept) this.FindParent(previousRelative);
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



//-----------------------------------------------------------------------------

public int getNumberofChildren(ozConcept ctf)
{
	ozConcept cpt = this.FindConcept(ctf);
	if(cpt!=null)
		return cpt.getNumberOfChildren();
	else
		return -1;
}


//-----------------------------------------------------------------------------
// Associate a concept to a Component

public void Associate(CElement cel)
{
	
	ozConcept cpt =(ozConcept) oz.getConcept(cel.getConcept());
	cpt.addComponentLink_update(cel);	
}


//-----------------------------------------------------------------------------
// Disssociate a concept from a Component

public void Dissociate(CElement cel)
{
	ozConcept cpt =(ozConcept) oz.getConcept(cel.getConcept());
	if(cpt!=null)
	{
		Vector cels = cpt.getComponetLinks();
		int i =0;
		boolean found =false;
		CElement cpnt = null;
		CElement el;
		String scomponent = cel.getComponent().trim();				
		while((i<cels.size())&& (!found))
		{
			el = (CElement) cels.elementAt(i);
			String sel = el.getComponent().trim();
			
			if(sel.equalsIgnoreCase(scomponent))
			{
				if(el.getMethodName().equalsIgnoreCase(cel.getMethodName()))
				{				 
					if((el.getParam()==null)|| (el.getParam().equalsIgnoreCase("null")))
					{
						cpnt = el;	
						found = true;									
					}
					else
					{
						if(el.getParamName().equalsIgnoreCase(cel.getParamName()))
						{
							found=true;
							cpnt = el;
						}					
					}
				}
			}	
			i++;
		}
		if(cpnt!=null)
		{
			cels.remove(cpnt);
			cpt.setCompontLinks_update(cels);
			System.err.println("Debug@XKT-KB - Component link " + cel.getConcept() + " -> " + cel.getComponent() + " removed");
		}
		else
		{
			System.err.println("Debug@XKT-KB - Couldn't find link " + cel.getConcept() + " -> " + cel.getComponent());
		}
	}
	else
	 	System.err.println("Debug@XKT-KB - Couldn't find  concept " + cel.getConcept());
}



/***----------------------------------------------------------------------------
 * Compute 	TOTAL Number of Children, including ALL descendants
 * @param ctf
 * @return int
 */

public int computeNumberofChildren(ozConcept ctf)
{
int i, count=0;
ozConcept chd; 	

 	Vector chld = FindDirectChildren(ctf);

    if(chld!=null) 
    {
		for(i=0;i<chld.size();i++)
		{
			count ++;
			chd = (ozConcept) chld.elementAt(i);
			count +=this.getNumberChildren(chd);	
		}
		return count;
    }
    else
    	return 0;
}

private int getNumberChildren(ozConcept ctf)
{
int i;
ozConcept chd;
int count=0;
   	
	Vector chld = FindDirectChildren(ctf);

	for(i=0;i<chld.size();i++)
	{
		count++;
		chd = (ozConcept) chld.elementAt(i);
		Vector gc = this.FindDirectChildren(chd);
		if((gc!=null) && (gc.size()>0))
		{		    	
			count += getNumberChildren(chd);
		}
	}
	return count;	
}


//-----------------------------------------------------------------------------
// Direct Children Only, Uses Info Stored in Node

public int getNumberOfDirectChildren(ozConcept ctf)
{
	ozConcept cpt;
	
	cpt = this.FindConcept(ctf);
	return cpt.getNumberOfDirectChildren();	
}

//-----------------------------------------------------------------------------


public Vector getIndex()
{
int i;
ozConceptImpl el;
ozConcept ozel;

	Vector cat = oz.getCategories();
	Vector cmem = new Vector(1);
	for(i=0; i<cat.size();i++)
	{
		ozel = (ozConcept) cat.elementAt(i);
		el = new ozConceptImpl();		
		el.setAssetions_update(ozel.getAssertions());
		el.setID_update(ozel.getID());
		cmem.addElement(el);
	}
	return cmem;
}




//-------------------------------------------------------------------------
// Build Statistics Tree Storing Info on Nodes

public void BTT()
{
int i;
ozConcept cp, root;

	int nc = this.getIndex().size();
	for(i=0;i<nc;i++)
	{		
		cp = (ozConcept) this.getIndex().elementAt(i);
		root = this.FindConcept(cp);		
		this.btt(root,1);
		// set my number of children
		root = (ozConcept) this.getOzConcept(cp);
		root.setNumberOfChildren_update(this.computeNumberofChildren(root)); 			
	}	
}


private void btt(ozConcept cpt, int sLevel)
{
	int k,aux;
	ozConcept root;	

    Vector ls = cpt.getChildrenVector();
    
    if(ls!=null)  	  
	if(ls.size()>0)
	{
		aux=sLevel+1;
		//System.err.println(cpt.getID() + " has " + ls.size() + " children");		
		for(k=0;k<ls.size();k++)		
		{	
			ozConcept cpt2 = new ozConceptImpl((String)ls.elementAt(k));			
			root = (ozConcept) this.getOzConcept(cpt2);
			root.setNumberOfChildren_update(this.computeNumberofChildren(root)); 			
	  		this.btt(root,aux);	  		
		}
  	}
}



}




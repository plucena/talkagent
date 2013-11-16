package br.usp.talkagent.kb.ozone.db;

/**
 * @author Percival Lucena  
 * Store some objects on Ozone
 */

import org.ozoneDB.ExternalDatabase;
import java.util.Vector;
import br.usp.talkagent.kb.objects.*;
import java.io.*;

public class ozOODB 
{
    
private ExternalDatabase db;
private ozCategories ConceptRootIndex;
private int noc;
private int noa;
private PrintWriter output;
private String msg;


public ozOODB(String server)
{
	try 
	{	
		db=null;	
		// create and open a new database connection
		output = new PrintWriter(new FileWriter(".kb.log"));
		while(db==null)
		{
			try
			{
				db = ExternalDatabase.openDatabase( "ozonedb:remote://" + server + ":3333" );	
			}
			catch(Exception e)
			{ db=null;}
		}		
		msg = "Connected to Ozone OODBMS";
		this.log(msg);
    	db.reloadClasses();
    	// test if ConceptRootIndex exists, if it is absent, so create it
    	ConceptRootIndex = (ozCategories) db.objectForName("CRIndex");
    	if(ConceptRootIndex!=null)
    	{;}	
    	else
    	{
    		 ConceptRootIndex = (ozCategories) (db.createObject(ozCategoriesImpl.class.getName(), 0,"CRIndex" ));
    	}
    	this.printCategories();    	    	
	}         
    catch(Exception e)
    {
    	e.printStackTrace();
    }
}


public void close() throws Exception
{
	db.close();
}



public ozConcept addConcept(String id, String name) throws Exception 
{
ozConcept concept;
	
 	concept =  (ozConcept) this.getConcept(id);
	if(concept != null)
	{
		msg = "Warning: Concept " + concept.getID() + " already exists";
		this.log(msg);
	}
	else
	{
		msg = "( " + this.getNumberOfConcepts() + ") - [Insert]: " + id + "," + name;
		this.log(msg);
		// the return value is Concept_proxy, which implements the Concept-interface
		ozConcept cpt = (ozConcept) (db.createObject(ozConceptImpl.class.getName(),0,name));		
		if(cpt!=null)		
		{	
			cpt.setID_update(name);				
			this.ConceptRootIndex.adjustCount();
		}		
		return cpt;			
	}	
	return null;
}



public ozConcept addConcept(ozConcept concept)
{
ozConcept cptchk;	
	try	
	{
		cptchk = (ozConcept) this.getConcept(concept.getID());
		if(cptchk != null)
		{
			msg = "Warning: Concept " + concept.getID() + " already exists";
			this.log(msg);
		}
		else
	    {	    	
 			// SET OZCONCEPT ASSERTIONS AND NODC
			ozConcept cpt =  (ozConcept) this.addConcept(concept.getID(),concept.getID());
			cpt.setNumberOfDirectChildren_update(concept.getNumberOfDirectChildren());
			if(concept.getNumberOfAssertions()>0)
			{
				cpt.setAssetions_update(concept.getAssertions());
				msg = "Assertions: " + concept.getAssertions().size();				
				this.log(msg);
				this.ConceptRootIndex.setNumberOfAssertions(this.getNumberOfAssertions()+concept.getAssertions().size());
			}		
			return cpt;
		}
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	return null;		
}


public ozConcept addConcept(ozConcept concept,ozConcept parent)
{
ozConcept cptchk;
ozConcept prn;
	
	try
	{
		cptchk = (ozConcept) this.getConcept(concept.getID());
		if(cptchk != null)
		{			
			msg = "Warning: Concept " + concept.getID() + " already exists";
			this.log(msg);
		}
		else
	    {
			ozConcept cpt = (ozConcept) this.addConcept(concept);
			// SET OZCONCEPT PARENT
			if(cpt!=null)
			{
				cpt.setParent_update(parent.getID());											
				prn = this.getConcept(parent.getID());
				prn.addChild_update(concept.getID());
				prn.setNumberOfDirectChildren_update(parent.getNumberOfDirectChildren()+1);				
			}
			return cpt;
	    }	
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	return null;
}



public void printConcept(String id) throws Exception 
{
	try
	{ 
		ozConcept concept =  (ozConcept)(db.objectForName(id));
		if (concept != null) 
		{
			System.out.println( "Concept: " + concept.getID());
		}
    	else 
    	{
        	System.out.println( "- KB - Concept not found" );
   		 }
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	    
}


public void deleteConcept(String id) throws Exception 
{
	ozConcept concept = (ozConcept)(db.objectForName(id));
    if (concept != null) 
    {
    	db.deleteObject( concept );
    } 
    else 
    {
     	System.out.println( "- KB - Concept not found." );
    }
}



public ozConcept getConcept(String conceptName)
{	
	try
	{
		ozConcept me;
		me = (ozConcept) db.objectForName(conceptName);				
		return me;
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}	
	return null;
}


public void addCategory(ozConcept category)
{
	msg = "Inserting new category " + category.getID();
	this.log(msg);
	ConceptRootIndex.addCategory(category);
}

public Vector getCategories()
{
	return this.ConceptRootIndex.getCategories();
}



public void printCategories()
{
	
	int nc = ConceptRootIndex.getCategories().size();
	int i;
	Vector cat;	
	cat = ConceptRootIndex.getCategories();
	// this.Statistics();
	System.err.println("");
	System.err.println("----------------------------------");
	System.err.println("Categories: (" + nc + ")");	
	for(i=0;i<cat.size();i++)
	System.err.println("*" + ((ozConcept) cat.elementAt(i)).getID());			
	System.err.println("Index Information");
	System.err.println("Concepts: " + ConceptRootIndex.getNumberOfConcepts());
	System.err.println("Assertions" + ConceptRootIndex.getNumberOfAssertions());
	System.err.println("----------------------------------");	
	
}



private void Statistics()
{
int i;
ozConcept cp, root;

	int nc = ConceptRootIndex.getCategories().size();
	for(i=0;i<nc;i++)
	{		
		cp = (ozConcept) ConceptRootIndex.getCategories().elementAt(i);
		noc++;
		noa += cp.getNumberOfAssertions();
		root = this.getConcept(cp.getID());
		this.statElems(root,1);
	}	
}
       
       
private void statElems(ozConcept cpt, int sLevel)
{
	int k,aux;
	ozConcept root;	

	noc++;
	noa= cpt.getNumberOfAssertions();
	
    Vector ls = cpt.getChildrenVector();
    
    if(ls!=null)  	  
	if(ls.size()>0)
	{
		aux=sLevel+1;
		//System.err.println(cpt.getID() + " has " + ls.size() + " children");		
		for(k=0;k<ls.size();k++)		
		{				
			root = (ozConcept) this.getConcept((String)ls.elementAt(k)); 			
	  		this.statElems(root,aux);	  		
		}
  	}
}


       

public void print()
{
	int nc = ConceptRootIndex.getCategories().size();
	int i;
	ozConcept cp, root;
	
	for(i=0;i<nc;i++)
	{
		cp = (ozConcept) ConceptRootIndex.getCategories().elementAt(i);
		root = this.getConcept(cp.getID());
		this.printCat(root,1);
	}
}


private void printCat(ozConcept cpt, int sLevel)
{
	int i,j,k,aux;
	ozConcept root;
	Vector prnt;

	for (j=0; j<sLevel; j++)
		System.out.print("-");	  
	prnt = cpt.print();
	for(i=0;i<prnt.size();i++)
		System.out.println((String)prnt.elementAt(i));
	System.err.println("______________");	
    
    Vector ls = cpt.getChildrenVector();
    
    if(ls!=null)  	  
	if(ls.size()>0)
	{
		aux=sLevel+1;
		//System.err.println(cpt.getID() + " has " + ls.size() + " children");		
		for(k=0;k<ls.size();k++)
		{				
			root = (ozConcept) this.getConcept((String)ls.elementAt(k)); 			
	  		this.printCat(root,aux);	  		
		}
  	}
}


public int getNumberOfConcepts()
{
	return this.ConceptRootIndex.getNumberOfConcepts();
}


public int getNumberOfAssertions()
{
	return  this.ConceptRootIndex.getNumberOfAssertions();
}

public void renameConcept(ozConcept concept, ozConcept newname)
{
	try
	{
		ozConcept cpt;
		cpt = (ozConcept) db.objectForName(concept.getID());
		cpt.setID_update(newname.getID());
		db.nameObject(cpt, newname.getID());		
		ozConcept parent; 
		String pid = cpt.getParentID(); 
		parent = (ozConcept) db.objectForName(pid);
		Vector children = parent.getChildrenVector();
		int pos = children.indexOf(concept.getID());
		children.setElementAt(newname.getID(),pos);
		parent.setChildren_update(children);								
	}
	catch(Exception e)
	{ 
		e.printStackTrace(); 
	}
}


public void delete(ozConcept concept, ozConcept parentc)
{ 
	try
	{
		ozConcept prn;
		prn = (ozConcept) db.objectForName(parentc.getID());		
		Vector children = prn.getChildrenVector();
		children.remove(concept.getID());
		prn.setChildren_update(children);
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
}

// Search non-addressable childrens inside concepts
// {solves possible database corruption caused by user}
public ozConcept getConceptWith(ozConcept cpt)
{
	String children = cpt.getID();
	int i;
	ozConcept root,cp,result;
	
	int nc = ConceptRootIndex.getCategories().size();
	for(i=0;i<nc;i++)
	{		
		cp = (ozConcept) ConceptRootIndex.getCategories().elementAt(i);	
		root = this.getConcept(cp.getID());
		if(!root.getChildrenVector().contains(children))
		{
			result = this.searchInnerChild(root,children);
			if (result!=null)
				return result;
		}
		else
			return root;	
	}
	return null;	
}


private ozConcept searchInnerChild(ozConcept cpt, String children)
{
	int k;
	ozConcept root,result;	
	
    Vector ls = cpt.getChildrenVector();
    
    if(ls!=null)  	  
	if(ls.size()>0)
	{
		//System.err.println(cpt.getID() + " has " + ls.size() + " children");		
		for(k=0;k<ls.size();k++)		
		{				
			root = (ozConcept) this.getConcept((String)ls.elementAt(k));
			Vector vchild = root.getChildrenVector();			 			
	  		if(!vchild.contains(children))
	  		{
	  			result = this.searchInnerChild(root,children);	  		
	  			if (result!= null)
	  				return result;
	  		}
	  		else
	  			return root;
		}
  	}
  	return null;
}


private void log(String value)
{
  this.output.write(value +  "\n");	
  System.out.println(value);
}


}
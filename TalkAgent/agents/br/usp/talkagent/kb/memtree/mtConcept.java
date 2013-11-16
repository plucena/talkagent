package br.usp.talkagent.kb.memtree;


import br.usp.talkagent.util.GenList;
import br.usp.talkagent.util.ListData;
import br.usp.talkagent.kb.*;
import br.usp.talkagent.util.List;
import java.util.Vector;


public class mtConcept implements Concept, ListData 
{

private String name; 
private Vector assertions;
private GenList children; 
private mtConcept parent;
// Lista de Objetos Complink
// subconcepts list



public void addAssertion(Assertion ast)
{
	this.assertions.addElement(ast);	
}

		
/**
 * Returns the assertions.
 * @return Vector
 */
public Vector getAssertions() 
{
	return assertions;
}



public void setAssetions(Vector ast)
{
	this.assertions = ast;
}


public int getNumberOfAssertions()
{
	return this.assertions.size();
}

// construtor
public mtConcept() 
{
	super();
	this.assertions = new Vector();
}


public mtConcept(String name)
{
	super();
	this.name = name;
	this.assertions = new Vector(); 
}


public List getChildren()
{
    return this.children; 
}


public void addChild(Concept child)
{
	this.children.insert((ListData)child);
}



//------------------------------------------------------------------------------
// ListData INTERFACE IMPLEMENTATION
//------------------------------------------------------------------------------

/**
*/
public String getID() {
	return this.name;
}


/**
 */
public void setID(String ID) 
{
	this.name = ID;
}



public Object getValue()
{
	return this.name;
}


public boolean isEqual(ListData value)
{
	if (this.name.equalsIgnoreCase(((mtConcept)value).name))
		return true;
	else
	return false;
}


public boolean isGreater(ListData value)
{
mtConcept cvalue;

  cvalue = (mtConcept)value;	
  // compare name strings	
  java.text.Collator myCollator = java.text.Collator.getInstance(java.util.Locale.US);
  myCollator.setStrength(java.text.Collator.PRIMARY);
  if(myCollator.compare(this.name,cvalue.name) > 0 ) 
	return true;
  else
	 return false;
}


public boolean isSmaller(ListData value)
{
mtConcept cvalue;

  cvalue = (mtConcept)value;	
  // compare name strings	
  java.text.Collator myCollator = java.text.Collator.getInstance(java.util.Locale.US);
  myCollator.setStrength(java.text.Collator.PRIMARY);
  if(myCollator.compare(this.name,cvalue.name) < 0 ) 
	return true;
  else
	 return false;
}


/**
 * Returns the parent.
 * @return mtConcept
 */
public Concept getParent() 
{
	return parent;
}


/**
 * Sets the parent.
 * @param parent The parent to set
 */
public void setParent(Concept parent) 
{
	this.parent = (mtConcept) parent;
}


}
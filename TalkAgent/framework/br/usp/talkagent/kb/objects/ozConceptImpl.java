package br.usp.talkagent.kb.objects;


import org.ozoneDB.OzoneObject;
import java.util.Vector;
import br.usp.talkagent.kb.*;
import java.util.Vector;
import java.util.HashSet;
import br.usp.talkagent.component.*;
import java.util.*;

public class ozConceptImpl extends OzoneObject implements ozConcept
{

private String name;
private Vector assertions;
private Vector compLinks;
private HashSet children; 
private String parentID;
private int noa;
private int nodc;
private int noc;

// Lista de Objetos Complink
// subconcepts list

public ozConceptImpl()
{
	this.assertions = new Vector(1);
	this.compLinks = new Vector(1);
	this.children = new HashSet();	
}


public ozConceptImpl(String name)
{
	this.name = name;
	this.assertions = new Vector(); 
	this.children = new HashSet();	
}


/**
 * Sets the parent.
 * @param parent The parent to set
 */
public void setParent_update(String parentID) 
{
	this.parentID = parentID;
}


/**
 * Returns the parent.
 * @return ConceptImpl
 */
public String getParentID() 
{

	return this.parentID;
}

		
/**
 * Returns the assertions.
 * @return Vector
 */
public Vector getAssertions() 
{
	return assertions;
}


public void addAssertion_update(br.usp.talkagent.kb.Assertion ast)
{
	this.assertions.addElement(ast);
	this.noa++;	
}


public void setAssetions_update(Vector ast)
{
	this.assertions = ast;
	this.noa = ast.size();	
}


public int getNumberOfAssertions()
{
	return this.noa;
}


public Vector getChildrenVector()
{
	Vector children = new Vector(1);
	Iterator iterator = this.children.iterator();

	while(iterator.hasNext())
		children.addElement(iterator.next());

	return children;
}


public void addChild_update(String childID)
{
	this.children.add(childID);	
}


/**
*/
public String getID() 
{
	return this.name;
}


/**
 */
public void setID_update(String ID) 
{
	this.name = ID;
}


public Vector print()
{
int i;
Vector aux = new Vector(1);

	aux.addElement(this.name);
	for(i=0;i<this.assertions.size();i++)
		aux.addElement(((Assertion) this.assertions.elementAt(i)).print() );
	return aux;
}

/**
 * Returns the noc.
 * @return int
 */
public int getNumberOfChildren() {
	return noc;
}

/**
 * Returns the nodc.
 * @return int
 */
public int getNumberOfDirectChildren() {
	return nodc;
}

/**
 * Sets the noc.
 * @param noc The noc to set
 */
public void setNumberOfChildren_update(int noc) 
{
	this.noc = noc;
}

/**
 * Sets the nodc.
 * @param nodc The nodc to set
 */
public void setNumberOfDirectChildren_update(int nodc) 
{
	this.nodc = nodc;
}


public void setChildren_update(Vector children)
{
int i, size;
	
	this.children = new HashSet();	
	for(i=0;i<children.size();i++)
	{
		this.children.add((String)children.elementAt(i)); 
	}
}


public void addComponentLink_update(CElement cel)
{
	this.compLinks.add(cel);	
}

public Vector getComponetLinks()
{
	return this.compLinks;
}

public void setCompontLinks_update(Vector cels)
{
	this.compLinks = cels;
}

public void removeCompLink(CElement cel)
{
	this.compLinks.remove(cel);
}


}
package br.usp.talkagent.kb.ozone.db;

/**
 * @author Percival Lucena 
 */

import java.util.*;
import br.usp.talkagent.kb.*;
import br.usp.talkagent.kb.objects.ozConcept;

import org.ozoneDB.OzoneObject;

public class ozCategoriesImpl extends OzoneObject implements ozCategories
{

public Vector index;
private int numberOfConcepts;
private int numberOfAssertions;
private int OID;


public ozCategoriesImpl()
{
	this.index = new Vector(1);
}


public Vector getCategories()
{
	return index;
}


public void addCategory(ozConcept concept)
{
	this.index.addElement(concept);
}

/**
 * Returns the numberOfAssertions.
 * @return int
 */
public int getNumberOfAssertions() 
{
	return numberOfAssertions;
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
 * Sets the numberOfAssertions.
 * @param numberOfAssertions The numberOfAssertions to set
 */
public void setNumberOfAssertions(int numberOfAssertions) 
{
	this.numberOfAssertions = numberOfAssertions;
}

/**
 * Sets the numberOfConcepts.
 * @param numberOfConcepts The numberOfConcepts to set
 */
public void setNumberOfConcepts(int numberOfConcepts) 
{
	this.numberOfConcepts = numberOfConcepts;
}

public int makeOID()
{
	OID++;
	return OID;
}


public void adjustCount()
{
	this.numberOfConcepts++;
}


public void adjustAssertions(int noa)
{
	this.numberOfAssertions+=noa;
}



}
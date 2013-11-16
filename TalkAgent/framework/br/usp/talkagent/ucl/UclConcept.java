package br.usp.talkagent.ucl;

import br.usp.talkagent.util.*;

import java.io.*;


public class UclConcept implements ListData, Serializable 
{
	String id;
	String name;
	String parent;

/** 
 */
public UclConcept() 
{
	super();
}


/**
 * Get concept name. 
 */
public String getName() 
{
	return name;
}


/**
 * @param name java.lang.String
 */
public void setName(String name) 
{
	this.name = name;
}




//--------------------------------------------------------------------
// ListData INTERFACE
//--------------------------------------------------------------------

/**
 * Get concept id
 */
public String getID() 
{
	return id;
}


/**
 * @param ID java.lang.String
 */
public void setID(String ID) 
{
	this.id = ID;
}



public boolean isEqual(ListData value)
{
	if (this.name.equalsIgnoreCase(((UclConcept) value).getName()) )
		return true;
	else
		return false;
}


public boolean isGreater(ListData value)
{
UclConcept cvalue;

  cvalue = (UclConcept)value;	
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
UclConcept cvalue;

  cvalue = (UclConcept)value;	
  // compare name strings	
  java.text.Collator myCollator = java.text.Collator.getInstance(java.util.Locale.US);
  myCollator.setStrength(java.text.Collator.PRIMARY);
  if(myCollator.compare(this.name,cvalue.name) < 0 ) 
	return true;
  else
	 return false;
}


public Object getValue()
{
	return this.name;
}






}
package br.usp.talkagent.ucl;

import java.util.*;
import java.io.*;


public class UCLRelation implements Serializable  
{

String type; // UCL Relation Type, ICL, AGT, etc...
private Object Element1;
private Object Element2; 
private String e1;
private String e2;
private boolean hasNullValues;
private String id;


/**
 * UCLRelation = Name :- (UCLRelation|UclDoc,UCLRelation|UclDoc), attributes
 */

public UCLRelation() 
{
	super();
}

/**
 * @return java.util.Vector
 */
public java.util.Vector getElements() 
{
Vector aux = new Vector(5);

	aux.addElement(Element1);	
	aux.addElement(Element2);
	return aux;
}


/**
 */
public String getID() 
{
	return this.id;	
}


/**
 * @return java.lang.String
 */
public String getType() {
	return type;
}

public boolean isEqual(UCLRelation R2)
{
 if(R2.getID().equalsIgnoreCase(this.id))
	 return true;
  else
  	return false;	 	
}


/**
 * Get brother elemnt from relation
 */ 
public Object getComplementaryElement(Object Ex)
{
boolean ExIsRelation;

if(Ex instanceof UCLRelation)
	ExIsRelation=true;
else
	ExIsRelation=false;
	
if(ExIsRelation) 
{
	if(Element1 instanceof UCLRelation)
	{
     	if( ((UCLRelation)Element1).isEqual((UCLRelation)Ex) )
     	{    
    		return Element2;
     	}
	}
    else 
   	if(Element2 instanceof UCLRelation)
 	{ 
    	if( ((UCLRelation)Element2).isEqual((UCLRelation)Ex))	
	    		return Element1;
   	} 
}

else // Ex should be a Concept instead
{
	if(Element1 instanceof UclConcept)
	{
		if( ((UclConcept)Element1).equals(Ex) )
     	{    
    		return Element2;
     	}
	}
	else
	if(Element2 instanceof UclConcept)
	{
		if( ((UclConcept)Element2).equals(Ex) )
     	{    
    		return Element1;
     	}	
	}
}	

// given element does not belong to relation
return null;	
}		



/**
 * Returns the e1.
 * @return String
 */
public String getE1() {
	return e1;
}

/**
 * Returns the e2.
 * @return String
 */
public String getE2() {
	return e2;
}

/**
 * Returns the element1.
 * @return Object
 */
public Object getElement1() {
	return Element1;
}

/**
 * Returns the element2.
 * @return Object
 */
public Object getElement2() {
	return Element2;
}

/**
 * Returns the hasNullValues.
 * @return boolean
 */
public boolean HasNullValues() 
{
	return hasNullValues;
}

/**
 * Returns the id.
 * @return String
 */
public String getId() {
	return id;
}

/**
 * Sets the e1.
 * @param e1 The e1 to set
 */
public void setE1(String e1) {
	this.e1 = e1;
}

/**
 * Sets the e2.
 * @param e2 The e2 to set
 */
public void setE2(String e2) {
	this.e2 = e2;
}

/**
 * Sets the element1.
 * @param element1 The element1 to set
 */
public void setElement1(Object element1) {
	Element1 = element1;
}

/**
 * Sets the element2.
 * @param element2 The element2 to set
 */
public void setElement2(Object element2) {
	Element2 = element2;
}

/**
 * Sets the hasNullValues.
 * @param hasNullValues The hasNullValues to set
 */
public void setHasNullValues(boolean hasNullValues) {
	this.hasNullValues = hasNullValues;
}

/**
 * Sets the id.
 * @param id The id to set
 */
public void setId(String id) {
	this.id = id;
}

/**
 * Sets the type.
 * @param type The type to set
 */
public void setType(String type) {
	this.type = type;
}

}
package br.usp.talkagent.util;

/**
 * @author Percival Lucena
 */

import java.util.Vector;
import java.io.*;


public class VectorList implements List, Serializable 
{

private int length;
private Vector dataset;
private VectorList parent;

public VectorList()
{
	this.dataset = new Vector(1);
}

public void insert(Object data)
{
	this.dataset.addElement(data);
	this.length++;
}

public int getSize()
{ 
	return  this.dataset.size();
}

public Object elementAt(int pos)
{
	return this.dataset.elementAt(pos);
}

public Object find(String value)
{
	int i=0;
	ListData elem;
	boolean found = false;

	while( (i<this.dataset.size()) && (!found))
	{
		elem = (ListData) this.dataset.elementAt(i);
		if (elem.getID().indexOf(value)>0)
			return elem;
		i++;
	}		
	return null;	
} 


public VectorList getParent() 
{
	return parent;
}

public void setParent(VectorList parent) 
{
	this.parent = parent;
}


public void print() 
{
int i;
ListData elem;

	System.out.println("\nPrinting List (" + length + ")");
	for(i=0;i<this.dataset.size();i++)	
	{
		elem = (ListData) this.dataset.elementAt(i);
		System.err.println(elem.getValue());
	}	
}



}
package br.usp.talkagent.kb.objects;

/**
 * @author Percival Lucena
 * 
 * Alternative implementation of List Interface Base on Java Objects
 * Designed for ozOODB ADT
 */

import java.io.*;
import java.util.Vector;
import br.usp.talkagent.util.List;
import br.usp.talkagent.util.ListNode;
import br.usp.talkagent.kb.*;
import org.ozoneDB.OzoneObject;


public class ozChildrenListImpl extends OzoneObject implements ozChildrenList
{

private Vector list;


public ozChildrenListImpl()
{
	this.list = new Vector(1);
}

public Object elementAt(int pos)
{
	return list.elementAt(pos);
}

public void insert(Concept concept)
{
	list.addElement(concept);
}

public boolean delete(ListNode no)
{
	return false;
}

public Object find(String value)
{
int i;
ozConcept cpt;

	for(i=0;i<list.size();i++)
	{
		cpt = (ozConcept) list.elementAt(i);
		if(cpt.getID().equalsIgnoreCase(value))
			return cpt;			
	}	
	return null;
}
 
public int getSize()
{
	return this.list.size();
}

}

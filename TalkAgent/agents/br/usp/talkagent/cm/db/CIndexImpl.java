package br.usp.talkagent.cm.db;

import java.util.*;
import org.ozoneDB.OzoneObject;



public class CIndexImpl extends OzoneObject implements CIndex 
{
	
	private Vector CIndex;
	
	public CIndexImpl()
	{		
	}
	
	public void init_update()
	{
		this.CIndex = new Vector(1);
	}
	
	
	public void addComponent_update(String name)
	{
		this.CIndex.add(name);
	}
	
	
	public void removeComponent_update(String name)	
	{
		this.CIndex.remove(name);
	}

	public Vector getElements()
	{
		return this.CIndex;
	}
	
	public int getElementsNumber()
	{
		return this.CIndex.size();
	}
	
	
	public String getElement(int index)
	{
		return (String) this.CIndex.get(index);
	}
	
	public boolean containsElement(String name)
	{
		return this.CIndex.contains(name);
	}
	
	
}

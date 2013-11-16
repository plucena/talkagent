package br.usp.talkagent.util;

import java.io.*;

/**
 * @author Percival Lucena
 * 
 * Implementation of List Interface from Scratch using Generalized Lists ADT
 * Designed for MemTree ADT
 */


public class GenList implements List, Serializable 
{

private int length;
private ListNode first;
private ListNode currNode;
private GenList parent;

//----------------------------------------------------------------------------------
// LIST INTERFACE IMPL
//----------------------------------------------------------------------------------

// construtor
public GenList() 
{
	super();
	currNode=first;
	length = 0;
	first = null;	
}

public void insert(Object data)
{
	ListNode node	= new ListNode();
	node.data = (ListData) data;
	this.insert(node);	
}


private void insert(ListNode no)
{
	ListNode aux;
	
	switch (length) 
	{
		case 0: 
			// empty list
			first = no; // insert as 1st element 
			no.next = null;
			length++;
			currNode = no;
			break;
	case 1: 
		// 1 element
		if (no.data.isSmaller(first.data)) 
		{
			no.next = first;      
			first = no;
			currNode = no;
			length++; 
		}
		else 
		{
			first.next = no;
			no.next = null;
			currNode = no;
			length++; 
		}
		break;

	default:
		// more than 1 element	   
		aux = first;	
		// search for thr right position to insert
		while ( (aux.next != null) && ( (aux.next.data.isSmaller(no.data)) || (aux.next.data.isEqual(no.data)) )   )
			aux = aux.next;			
		// aux points to the first element smaler than the node
		// aux.next contains the first element greater than the node
		if(aux!=first)
		{
			no.next = aux.next;
			aux.next = no;
	  		currNode = no;
	  		length++; 
	  	}
		else
		{
	  		if(no.data.isSmaller(first.data))
	  		{
	    		no.next = first;
	    		first = no;
	    		currNode = no;
	    		length++;
	   		}
	  		else
	  		{
				no.next = first.next;
				first.next = no;
				length++;		
	  		} 
	 	}    
		break;
	} // end switch
}




public boolean delete(ListNode no)
{
	// Returns true if delete successful, else returns false
	ListNode aux;
	if (length == 0) // empty list
		return false; 
		
	// search the element to be deleted
	aux = first;
	// se for o 1o item tem que deixar a lista vazia
	if (aux.data.isEqual(no.data))
	{
  		length--;
		if (length==0)
			first = null;
	 	 else
			first = first.next;	
		return true;
	}
	else 
	{
  		while(!(aux.next.data.isEqual(no.data)))
  		{
  			aux = aux.next;
  		}
  		if (!(aux.next.data.isEqual(no.data)))
	 		return false;
  		else 
  		{// encontrou o elemento. aux apta pra o elemento anterior ao que
		// desejo excluir
			aux.next = aux.next.next; 
			// funciona tanto para  qualquer elemento do 2o incluindo o fim (null)
			return true;
  		}
	}
}


public int getSize()
{ 
	return length; 
}


/**
 * Returns data from List Current Element.
 */
public Object elementAt(int pos)
{
	ListNode laux;
	int i;

	laux = first;

	for (i = 0; i < pos; i++)
	{
	if (laux.next!=null)
		laux = laux.next;
	else
		return null;
	}
		return laux.data;	
}



/** 
 * @return java.lang.Object
 * @param value java.lang.String
 */
public Object find(String value) 
{
	ListNode pt = new ListNode();
	pt = this.first;
	while (pt!=null)
	{		
		if (pt.data.getID().equalsIgnoreCase(value))
		{
		   return pt.data;
		}
		else
		   pt = pt.next;  
	}	
	return null;
}



//----------------------------------------------------------------------------------
// METHOODS SPECIFIC TO SHARP.UTIL.LIST
//----------------------------------------------------------------------------------

public ListNode getCurrNode()
{
	return this.currNode;
}


public ListNode moveNext()
{
	currNode = currNode.next;
	return currNode;
}

public void moveFirst()
{
	this.currNode = this.first;
}


/**
 * Returns the parent.
 * @return ListImpl
 */
public GenList getParent() {
	return parent;
}

/**
 * Sets the parent.
 * @param parent The parent to set
 */
public void setParent(GenList parent) {
	this.parent = parent;
}



public void print() 
{
	ListNode aux;
	System.out.println("\nPrinting List (" + length + ")");
	if(length != 0)
	{
   		aux = first;
   		System.out.print(aux.data.getValue() + " ,");
	   	while(aux.next != null)
    	{
	   		aux = aux.next;
	   		System.out.print(aux.data.getValue() + " ,");
    	} 
		System.out.println("\n");
	}
	else 
	{ 
		System.out.println("Empty list"); 
	}
}


}
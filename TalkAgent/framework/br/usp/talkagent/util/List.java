package br.usp.talkagent.util;

/**
 * @author Percival Lucena
 * Interdaface to the new  ADT list
 */

public interface List 
{

public Object elementAt(int pos);
public void insert(Object data); /*update*/
public Object find(String value); 
public int getSize();

}

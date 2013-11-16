package br.usp.talkagent.kb.objects;

/**
 * @author Percival Lucena
 *
 *  Ozone OODB to Relat which is a element of Assertion Object
 */


public interface ozRelat 
{

public String getType(); 
public Object getValue(); 
public void setType(String type); /*update*/
public void setValue(Object value); /*update*/
public String toString();


}

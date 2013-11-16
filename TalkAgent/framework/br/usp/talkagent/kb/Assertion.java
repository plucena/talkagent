package br.usp.talkagent.kb;

/**
 * @author Percival Lucena
 *
 * Assertions are relations between concepts of a knowledge base
 */

public interface Assertion 
{
	
public String getType(); 
public void setType(String type);
public Relation getvalue(); 
public void setValue(Relation aste);
public String print();
}

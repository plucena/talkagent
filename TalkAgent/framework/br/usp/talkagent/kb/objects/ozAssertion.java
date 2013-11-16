package br.usp.talkagent.kb.objects;

/**
 * @author Percival Lucena
 * 
 */

import br.usp.talkagent.kb.*;

public interface ozAssertion extends Assertion
{

public Relation getvalue() ;
public void setValue(Relation aste); /*update*/
public String getType();
public void setType(String type);  /*update*/
public String print();
  
}

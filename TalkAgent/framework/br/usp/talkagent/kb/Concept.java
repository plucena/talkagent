package br.usp.talkagent.kb;

/**
 * @author Administrator
 *
 * A Knowledge Base is a Ontology composed by concepts and 
 * assertions hierarchically disposed.
 */ 

import java.util.Vector;
import br.usp.talkagent.util.List;
  
public interface Concept 
{
	public void addAssertion(Assertion ast);
	public Vector getAssertions(); 
	public void setAssetions(Vector ast);
	public int getNumberOfAssertions();
	public String getID();
	public void setID(String ID); 
	public void addChild(Concept child);
	public List getChildren();
	public Concept getParent();
	public void setParent(Concept parent);
}

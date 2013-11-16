package br.usp.talkagent.kb.objects;

/**
 * @author Percival Lucena
 * 
 *  Concept Interface
 */

import org.ozoneDB.OzoneRemote;
import java.util.Vector;
import br.usp.talkagent.kb.Assertion;
import br.usp.talkagent.component.CElement;


public interface ozConcept extends OzoneRemote
{

public void addChild_update(String childID);
public int getNumberOfDirectChildren();
public void setNumberOfDirectChildren_update(int nodc); 
public int getNumberOfChildren();
public void setNumberOfChildren_update(int noc); 
public Vector getChildrenVector();
public void setChildren_update(Vector children);
public String getID();
public void setID_update(String ID);
public Vector getAssertions(); 
public void addAssertion_update(Assertion ast);
public void setAssetions_update(Vector ast); 
public String getParentID(); 
public void setParent_update(String parentID);
public int getNumberOfAssertions();
public Vector print();
public void addComponentLink_update(CElement cel);
public Vector getComponetLinks();
public void setCompontLinks_update(Vector cels);
public void removeCompLink(CElement cel);
}

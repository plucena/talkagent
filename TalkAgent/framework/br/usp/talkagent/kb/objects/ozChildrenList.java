package br.usp.talkagent.kb.objects;

/**
 * @author Percival Lucena
 * Interdaface to the new  ADT list
 */

import br.usp.talkagent.util.List;
import org.ozoneDB.OzoneRemote;
import br.usp.talkagent.kb.*;

public interface ozChildrenList extends OzoneRemote
{

public Object elementAt(int pos);
public void insert(Concept concept); /*update*/
public Object find(String value); 
public int getSize();

}

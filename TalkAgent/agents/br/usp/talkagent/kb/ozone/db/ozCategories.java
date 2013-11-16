package br.usp.talkagent.kb.ozone.db;

/**
 * @author Percival Lucena
 * 
 * A category is a root concept that work as an entry point to traverse a 
 * collection of objects and so can be directly accessed.
 */

import java.util.*;
import br.usp.talkagent.kb.objects.*;

import org.ozoneDB.OzoneRemote;

public interface ozCategories extends OzoneRemote
{

public Vector getCategories();
public void addCategory(ozConcept concept);  /*update*/	
public int getNumberOfConcepts();
public void setNumberOfConcepts(int nc); /*update*/
public int getNumberOfAssertions();
public void setNumberOfAssertions(int na); /*update*/
public int makeOID(); /*update*/
public void adjustCount(); /*update*/
public void adjustAssertions(int noa); /*update*/
}

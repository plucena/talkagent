package br.usp.talkagent.kb;

/**
 * @author Percival Lucena
 * Knowledge Base Interface
 * Current Implementations include memtree.KB and ozone.KB
 * memtree.KB keeps all data in memory and does not store its changes
 * ozone.KB supports persistence and should be used in most cases.
 */

import java.util.Vector;

public interface KnowledgeBase 
{

public void 	 insertRoot(Concept concept);
public void 	 insert(Concept concept, Concept parent);
public Concept  FindConcept(Concept sConcept);
public Vector   FindDirectChildren(Concept sconcept);
public Concept  FindParent(Concept sconcept);
public Vector   FindSiblings(Concept sconcept); 

}

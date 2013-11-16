package br.usp.talkagent.ontology.tt;

/**
 * @author Percival Lucena
 */

import br.usp.talkagent.kb.objects.ozConcept;
import br.usp.talkagent.kb.objects.ozConceptImpl;
import br.usp.talkagent.kb.ozone.db.ozKB;
import br.usp.talkagent.util.Boolean;
import br.usp.talkagent.component.*;
import com.signiform.tt.*;
import br.usp.talkagent.util.*;
import java.util.Vector;

public class ttOzoneTreeKB 
{
	
public TTConnection tt;
private String ttServer_ip;
private String ozoneServer_ip;
private ozKB ttKB;

public ttOzoneTreeKB(String ttServer_ip, String ozoneServer_ip)
{
	this.ttServer_ip = ttServer_ip;
	this.ozoneServer_ip = ozoneServer_ip;
}


/**
 * Return number of concepts
 */ 
public int numberOfConcepts()
{
	return ttKB.getNumberOfConcepts();	
}


public int numberOfAssertions()
{
	return ttKB.getNumberOfAssertions();
}


public void Load()
{
	ttKB = new ozKB(this.ozoneServer_ip);
	PrefReader pr = new	PrefReader();	
	if(pr.isBuildST())
	{
		System.err.println("DEBUG@XKT-KB - Building Statistics Tree");
		ttKB.BTT();
	}
}


public void Restore()
{
	Vector vparents;
	Vector vchildren;
	ozConceptImpl con;
	ozConcept ocroot;
	int i;	

	try
 	{
	 	ttKB = new ozKB(this.ozoneServer_ip);	
	 	tt=null;
		String host = ttServer_ip;
		TTConnector ttc = new TTConnector(host);
		tt = ttc.connect();
				
		vparents = tt.children("concept");
		System.out.println("----------- RESETING KB - Loading Thought Treasure KB");
		for(i=0;i<vparents.size();i++)		
 	    {						
			con = new ozConceptImpl((String) (vparents.elementAt(i))); 	   		
			ocroot = ttKB.insertRoot(con);
			vchildren = tt.children((String) (vparents.elementAt(i)));
	 		// test if there are children
	 		if(vchildren.size()>0)
 	        {	 	     
	 		   LoadChildren((String) (vparents.elementAt(i)),ocroot);	 		  
 	        }
 	    }
		System.err.println("BUILDING TOTAL NUMBER OF CHILDREN FOR EACH NODE!!!");
		ttKB.BTT();		
 	    System.out.println("----------- " + "TTKB Loaded"); 	    
	 	System.out.println("----------- " + this.numberOfConcepts() + " concepts");
	 	System.out.println("----------- " + this.numberOfAssertions() +  " assertions");
 	tt.close();
 	 
 }
 catch(Exception e)
 {
  e.printStackTrace(System.out);
 } 
}



private void LoadChildren(String conceptName, ozConcept parent)
{

	int i,j;
	Vector vparent;
	Vector vchildren;
	ozConcept con;
	ttAssertion ttassertions;
	ozConcept ocroot;
	boolean rubish=false;
	String aux;

	if (conceptName.length() > 0)
	{
		try 
		{
			System.gc();
			vparent = tt.children(conceptName);
			for (i = 0; i < vparent.size(); i++)
			{
				rubish=false;
				aux = (String) vparent.elementAt(i);					
				// test if concept name is some numeric rubish					
				for(j=0;j<aux.length();j++)
				{
					if(Character.isDigit(aux.charAt(j)))
					{
							rubish=true;
							System.err.println("Warning: Concept " + aux + " won't be inserted on KB");
					}						
				}
				
				if(!rubish)
				{									
					con = new ozConceptImpl((String) (vparent.elementAt(i)));
					ttassertions = new ttAssertion((String) (vparent.elementAt(i)));
					System.err.println("");
					con.setAssetions_update(ttassertions.getAssertions());						
					
					vchildren = tt.children((String) vparent.elementAt(i));

					if(vchildren.size() > 0)
						con.setNumberOfDirectChildren_update(vchildren.size());
					
					ocroot = this.ttKB.insert((ozConcept)con,parent);	
					
					// recursive call to children
					if (vchildren.size() > 0)
					{						
						LoadChildren((String)(vparent.elementAt(i)),ocroot);
					
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace(System.out);
		}
	}
}





public ozConcept findConcept(String Concept) 
{
ozConcept caux; 

	caux = new ozConceptImpl(Concept);
	// warning changed from FindConcept to getConcept
	return (ozConcept) ttKB.getConcept(caux);
}


/*
 * Return a memory instance of a given concept
 */	
public ozConcept getConcept(String Concept)
{
ozConcept caux; 

	caux = new ozConceptImpl(Concept);
	return (ozConcept) ttKB.getConcept(caux);
}
	
	

/** 
 */
public Vector findSiblings(String Concept) 
{
ozConcept caux; 

	if(!Concept.equalsIgnoreCase("null"))	
	{
		caux = new ozConceptImpl(Concept);
		return ttKB.FindSiblings(caux);	
	}
	else
		return null;
	
}


public Vector getAncestors(String Concept)
{
ozConcept caux; 
	
	if(!Concept.equalsIgnoreCase("null"))	
	{
		caux = new ozConceptImpl(Concept);
		return ttKB.getAncestors(caux);	
	}
	else
		return null;
	
}


/**
 */
public ozConcept findParent(String sconcept) 
{
    ozConceptImpl ctf = new ozConceptImpl(sconcept);
	return this.ttKB.FindParent(ctf);
}


/**
*/
public Vector findDirectChildren(String sconcept)
{
	if(sconcept!=null)
	{
		ozConceptImpl caux = new ozConceptImpl(sconcept);	
		return ttKB.FindDirectChildren(caux);	
	}
	else
		System.err.println("ERROR - Searching children from a null concept: " + sconcept);
	return null;
}	


public Vector getIndex()
{
	return this.ttKB.getIndex();
}


public void insertConcept(String concept, String parent)
{
	ozConcept cconcept = new ozConceptImpl(concept);
	ozConcept cparent = new ozConceptImpl(parent);
	this.ttKB.insert(cconcept,cparent);	 	
}


public void delete(String concept)
{
	ozConcept cconcept = new ozConceptImpl(concept);
	this.ttKB.delete(cconcept);
}



public Boolean isA(String concept, String ancestor)
{
	ozConcept cconcept = new ozConceptImpl(concept);
	ozConcept cancestor = new ozConceptImpl(ancestor);
	Boolean result = this.ttKB.isA(cconcept,cancestor);
	return result;	
}

public int getNumberOfChildren(String concept)
{
	ozConcept cconcept = new ozConceptImpl(concept);
	return this.ttKB.getNumberofChildren(cconcept);
}

public int computeNumberOfChildren(String concept)
{
	ozConcept cconcept = new ozConceptImpl(concept);
	return this.ttKB.computeNumberofChildren(cconcept);
}

public int getNumberOfDirectChildren(String concept)
{
	ozConcept cconcept = new ozConceptImpl(concept);
	return this.ttKB.getNumberOfDirectChildren(cconcept);
}

public void renameConcept(String Concept, String NewName)
{
	ozConcept concept = new ozConceptImpl(Concept);
	ozConcept newname = new ozConceptImpl(NewName);
	this.ttKB.renameConcept(concept,newname);
}


public void Associate(String concept, String bean, String method, String param, int nparam)
{
	CElement cel = new CElement(concept,bean,method,param);
	cel.setNumberofparam(nparam);
	if(param==null)
		cel.setType("methood");
	else
		cel.setType("param");
	this.ttKB.Associate(cel);	
}


public void Dissociate(String concept, String bean, String method, String param, int nparam)
{
	CElement cel = new CElement(concept,bean,method,param);
	cel.setNumberofparam(nparam);
	if(param==null)
		cel.setType("methood");
	else
		cel.setType("param");
	this.ttKB.Dissociate(cel);	
}

public void buildTT()
{
	System.err.println("DEBUG@XKT-KB - Building Statistics Tree (This may take a while) ");
	this.ttKB.BTT();
}


}

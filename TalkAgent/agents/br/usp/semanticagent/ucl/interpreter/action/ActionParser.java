package br.usp.semanticagent.ucl.interpreter.action;

/**
 * @author Percival Lucena 
 */
import br.usp.talkagent.ucl.*;
import java.util.*;

public class ActionParser 
{

private UCLDoc ucldoc;
private Vector relations;
private Action act;
private UclConcept actobj;

	/**
	 * Constructor for ActionParser.
	 */
	public ActionParser(UCLDoc ucldoc) 
	{
		super();
		this.ucldoc = ucldoc;
		this.relations = ucldoc.getRelations();
	}
	
	
	public Action parse()
	{
	// Parse simple UCL Documents, identifing actions, active and passive actors
	// and related objects
	 	
	UCLRelation urel, objrel, objrel2;
		
		objrel = null;
		urel = null;
		act = new Action();
	
		// try to find an action relation
		// this method usually works, because ucl converter always generates 
		// an actionrelation for the sentence verb :)		
		for(int i=0;i<relations.size();i++)
		{
			urel = (UCLRelation) this.relations.elementAt(i);
			if(urel.getType().equalsIgnoreCase("agt"))
			{
				// actor
				if(urel.getElement2() instanceof UclConcept)				
					act.addActiveAgent(((UclConcept)urel.getElement2()).getName());					
				else
				{
			    	if(urel.getComplementaryElement(urel.getElement2())instanceof UclConcept)
			    		act.addActiveAgent(((UclConcept) urel.getComplementaryElement(urel.getElement2())).getName());
				}
				
				// action
				if(urel.getElement1() instanceof UclConcept)				
					act.setAction(((UclConcept)urel.getElement1()).getName());					
				else
				{
			    	if(urel.getComplementaryElement(urel.getElement1())instanceof UclConcept)
			    		act.setAction(((UclConcept) urel.getComplementaryElement(urel.getElement1())).getName());
				}	
			}						 
		}
		
		// find relation related to relation_agt
		// these may be the Action Objects, as provided by UCL conversor
		// a more precise technique shoul be studied...
		if(urel!=null)
		{			
			objrel = ucldoc.findRelationWithRelation(urel.getID());
			if(objrel.getComplementaryElement(urel) instanceof UclConcept)
			{
				// ActObj probabily == na (ucl converter...)				
				actobj = (UclConcept) objrel.getComplementaryElement(urel);
				System.err.println("DEBUG@XKT: Analysing object " + actobj.getName());
				if(actobj.getName().equalsIgnoreCase("na"))
				{
					// a good guess is that ActiveObjects are directed related to na
					objrel2 = ucldoc.findRelationWithRelation(objrel.getID());
					if(objrel2.getComplementaryElement(objrel) instanceof UclConcept)
					{
						actobj = (UclConcept) objrel2.getComplementaryElement(objrel);
						System.err.println("DEBUG@XKT: Found " + actobj.getName());
					} 
					else
					{
					System.out.println(objrel2.getComplementaryElement(objrel).toString()); 	
					; //there are more indirections, should think about it...	
					  // maybe it's better a recursivce approach
					}
															
				}			
			}
		}
		
		if((actobj!=null)&& (!(actobj.getName().equalsIgnoreCase("na"))))
		{			
			act.addActionObject(actobj.getName());
			System.err.println("DEBUG@XKT -Action Object " + actobj.getName());
		} 
		System.err.println(act.toHTML());
		return act;		
	}	
	
	
	

}

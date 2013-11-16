package br.usp.semanticagent.ucl.interpreter.action;

/**
 * @author Percival Lucena
 */

import java.util.*;

public class Action 
{

private Vector ActiveAgents;
private Vector PassiveAgents;
private String Action;
private Vector ActionObjects;

	public Action() 
	{
		super();
		ActiveAgents = new Vector(1);
		PassiveAgents = new Vector(1);
		ActionObjects = new Vector(1);
	}

/**
 * Returns the action.
 * @return String
 */
public String getAction() {
	return Action;
}

/**
 * Returns the actionObjects.
 * @return Vector
 */
public Vector getActionObjects() {
	return ActionObjects;
}

/**
 * Returns the activeAgents.
 * @return Vector
 */
public Vector getActiveAgents() {
	return ActiveAgents;
}

/**
 * Returns the passiveAgents.
 * @return Vector
 */
public Vector getPassiveAgents() {
	return PassiveAgents;
}

/**
 * Sets the action.
 * @param action The action to set
 */
public void setAction(String action) {
	Action = action;
}

/**
 * Sets the actionObjects.
 * @param actionObjects The actionObjects to set
 */
public void setActionObjects(Vector actionObjects) {
	ActionObjects = actionObjects;
}

/**
 * Sets the activeAgents.
 * @param activeAgents The activeAgents to set
 */
public void setActiveAgents(Vector activeAgents) {
	ActiveAgents = activeAgents;
}


public void addActiveAgent(String agentName)
{
	this.ActiveAgents.add(agentName);
}


public void addPassiveAgent(String agentName)
{
	this.PassiveAgents.add(agentName);
}


public void addActionObject(String object)
{
	this.ActionObjects.add(object);
}



public String toHTML()
{
String response;

	response = "<BR>Active Agents: " + this.ActiveAgents.toString() + "<BR>";
	response+= "Action: " + this.Action + "<BR>";
	response+= "Passive Agents: " + this.PassiveAgents.toString() + "<BR>";
	response+= "Action Objects: " + this.ActionObjects.toString() + "<BR>";
	return response;
}


}

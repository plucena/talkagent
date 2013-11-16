package br.usp.talkagent.aclmessages;

/**
 * Insert the type's description here.
 * Creation date: (10/06/02 15:01:44)
 * @author: 
 */
public class FipaAclMessageType implements MessageType {

String id;
String performative;
String description;
	
// Creates the same message types supported by FIPA-OS


public FipaAclMessageType() {

	super();
	
}


public FipaAclMessageType(String id, String Performative) {
	this.id = id;
	this.performative = Performative;
}
	


}
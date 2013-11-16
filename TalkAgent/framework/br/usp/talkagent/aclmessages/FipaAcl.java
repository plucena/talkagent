package br.usp.talkagent.aclmessages;

// Suport the same message types that FIPA OS does.


import java.util.*;

import java.io.*;import javax.xml.parsers.DocumentBuilderFactory;import org.w3c.dom.*;import javax.xml.parsers.DocumentBuilder;public class FipaAcl {

	Vector MessageTypes;

/**
 * // Suport the same message types that FIPA OS does.
 //
 // CHANGE /root/fipaacl.xml to ../db/fipaacl.xml
 // ====== 
 */

 
public FipaAcl() {
	super();
	MessageTypes = new Vector(25);
	AclFileName = "/TalkAgent/db/fipaacl.xml";
	loadMessageTypes();
}
/**
 Include new Message Type
 */
public void addMessageType(FipaAclMessageType mt) 
{
	MessageTypes.addElement(mt);
}	


private void createMessageTypesFile() {
	FipaAclMessageType msg;
	String filename,aux;

	int i;

	// CHANGE TO ../db/fipacl.dat
	filename="/root/db/fipacl.dat";
		
	msg = new FipaAclMessageType("ACL_ACCEPTPROPOSAL", "accept-proposal");
	msg.description = "";
	MessageTypes.addElement(msg);

	msg = new FipaAclMessageType("ACL_AGREE","agree");
	msg.description = "";
	MessageTypes.addElement(msg);

	msg = new FipaAclMessageType("ACL_CANCEL", "cancel");
	msg.description = "";
	MessageTypes.addElement(msg);
	
   	msg = new FipaAclMessageType("ACL_CFP","cfp");
	msg.description = "";
	MessageTypes.addElement(msg);
	
   	msg = new FipaAclMessageType("ACL_CONFIRM","confirm");
	msg.description = "";
	MessageTypes.addElement(msg);
	
   	msg = new FipaAclMessageType("ACL_DISCONFIRM","disconfirm");
	msg.description = "";
	MessageTypes.addElement(msg);
	
   	msg = new FipaAclMessageType("ACL_FAILURE","failure");
	msg.description = "";
	MessageTypes.addElement(msg);
	
   	msg = new FipaAclMessageType("ACL_INFORM", "inform");
	msg.description = "";
	MessageTypes.addElement(msg);
	
   	msg = new FipaAclMessageType("ACL_INFORMIF","inform-if");
	msg.description = "";
	MessageTypes.addElement(msg);
	
   	msg = new FipaAclMessageType("ACL_INFORMREF","inform-ref");
	msg.description = "";
	MessageTypes.addElement(msg);
	
   	msg = new FipaAclMessageType("ACL_NOTUNDERSTOOD","not-understood");
	msg.description = "";
	MessageTypes.addElement(msg);
	
   	msg = new FipaAclMessageType("ACL_PROPAGATE","propagate");
	msg.description = "";
	MessageTypes.addElement(msg);
	
	msg = new FipaAclMessageType("ACL_PROPOSE","propose");
	msg.description = "";
	MessageTypes.addElement(msg);
	
   	msg = new FipaAclMessageType("ACL_PROXY", "proxy");
	msg.description = "";
	MessageTypes.addElement(msg);
	
   	msg = new FipaAclMessageType("ACL_QUERYIF","query-if");
	msg.description = "";
	MessageTypes.addElement(msg);
	
   	msg = new FipaAclMessageType("ACL_QUERYREF","query-ref");
	msg.description = "";
	MessageTypes.addElement(msg);

	msg = new FipaAclMessageType("ACL_REFUSE","refuse");
	msg.description = "";
	MessageTypes.addElement(msg);
	
   	msg = new FipaAclMessageType("ACL_REJECTPROPOSAL", "reject-proposal");
	msg.description = "";
	MessageTypes.addElement(msg);

	msg = new FipaAclMessageType("ACL_REQUEST","request");
	msg.description = "";
	MessageTypes.addElement(msg);
		
   	msg = new FipaAclMessageType("ACL_REQUESTWHEN", "request-when");
	msg.description = "";
	MessageTypes.addElement(msg);

	msg = new FipaAclMessageType("ACL_REQUESTWHENEVER","request-whenever");
	msg.description = "";
	MessageTypes.addElement(msg);

	msg = new FipaAclMessageType("ACL_SUBSCRIBE", "subscribe");
	msg.description = "";
	MessageTypes.addElement(msg);

	try
	{
 		FileWriter fw = new FileWriter(filename);
		PrintWriter pw = new PrintWriter(fw);

		for (i=0;i<MessageTypes.size();i++)
		{
			aux = Integer.toString(i);
			pw.println(aux);
			aux = ((FipaAclMessageType)MessageTypes.elementAt(i)).id;
			pw.println(aux);
			aux = ((FipaAclMessageType)MessageTypes.elementAt(i)).performative;
			pw.println(aux);
		}
		pw.close();
	}
	
	catch (Exception e)
	{
		System.out.println("unable to create fipaacl message types file");
	}

	
}// LOAD MESSAGES TYPES FROM ../DB/FIPAACL.XML


// **
// READ FILE FIPAACL.XML WHICH CONTAINS FIPA-ACL MESSAGE TYPES
// **

private void loadMessageTypes() 
{
	FipaAclMessageType aclmsg;
	AclFile = new File(AclFileName);
	int i;

	// start FIPA-ACL Messagesm, XML File Parsing
	try {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		xmldoc = db.parse(AclFile);
	} catch (Exception e) {
		System.out.println("exception: " + e.getMessage());
	}

	// Read XML File and loads known message types.
	Node root, node;
	NodeList nodes;
	NamedNodeMap attrs;
	Attr attr;

	root = xmldoc.getDocumentElement();
	nodes = root.getChildNodes();

	int nels = nodes.getLength();

	for (i = 0; i < nels; i++) {
		// reads new node
		node = nodes.item(i);

		try {

			if (node != null) {
				// creates new ACL Message Type
			    attrs = node.getAttributes();  
			    attr = (Attr) attrs.item(0);
			    aclmsg = new FipaAclMessageType();				
  			    aclmsg.id = attr.getNodeValue();
				attr = (Attr) attrs.item(1);				
				aclmsg.performative = attr.getNodeValue();
				attr = (Attr) attrs.item(2);				
				aclmsg.description = attr.getNodeValue();
				// insert concepts on MessageType List
				MessageTypes.add(aclmsg);
			}

		} catch (Exception e) {			
		}

	}

}	File AclFile;	String AclFileName;	Document xmldoc;}
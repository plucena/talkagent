package br.usp.talkagent.ams;

/**
 * @author user
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import java.io.*;
import br.usp.talkagent.basicagent.AgentDescriptor;
import java.util.*;

public class LogReader 
{

private Vector agents;
private String filename;

// Constructor
public LogReader()
{
	super();
	this.filename = ".ams.log";
	this.ReadXMLConfig();
}


public LogReader(String cfgfile)
{
	super();
	this.filename = cfgfile;
	this.ReadXMLConfig();
}


private void ReadXMLConfig()
{		
	Document xmldoc;
    File prefs = new File(filename);
	int i, nels;  
	String saux;	
  	Node root,aux,aux2;
	NodeList values;
	AgentDescriptor agtd;
  	  
  // start XML Parsing
  try
  {
  	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
  	DocumentBuilder db = dbf.newDocumentBuilder();
  	xmldoc = db.parse(prefs);  
  	xmldoc.normalize();
	root = xmldoc.getDocumentElement();
	NodeList els = root.getChildNodes();
	nels = els.getLength();

	for (i = 0; i < nels; i++) 
	{
		aux = els.item(i);		
		if ((aux.getNodeName().equalsIgnoreCase("agent"))  && (aux!=null) )
		{		
			NodeList child = aux.getChildNodes();
			agtd = new AgentDescriptor();
			aux2 = child.item(0);
			values = aux2.getChildNodes();
			saux = values.item(0).getNodeValue();
			agtd.setName(saux);
			aux2 = child.item(1);
			values = aux2.getChildNodes();
			saux = values.item(0).getNodeValue();
			agtd.setAddress(saux);
			agents.add(agtd);									
		}
	}	
  }
  catch(Exception e)
  {
	e.printStackTrace();
  }
}

public Vector getAgents()
{
	return this.agents;
}
}

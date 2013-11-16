package br.usp.talkagent.util;

/**
 * @author Percival Lucena 
 */

import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import java.io.*;
import java.net.*;

public class PrefReader 
{

private String amsclient;
private String tt_host;
private String filename;
private String ozoneServer;
private boolean load_tt_ontology;
private boolean restore_tt_ontology;
private boolean findvalue=false;
private boolean buildST=false;
private boolean restoreComponentsDB=false;

// Constructor
public PrefReader()
{
	super();
	this.filename = "../db/config.xml";
	this.ReadXMLConfig();
}


public PrefReader(String cfgfile)
{
	super();
	this.filename = cfgfile;
	this.ReadXMLConfig();
}


private void ReadXMLConfig()
{		
	Document xmldoc;
    File prefs = new File(filename);
  	Node root,aux;
	int i, nels;  
	String saux;	
  	  
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
		if ((aux.getNodeName().equalsIgnoreCase("TT_HOST"))  && (aux!=null) )
		{
			NodeList child = aux.getChildNodes();
			tt_host = child.item(0).getNodeValue();
		}		
		if ((aux.getNodeName().equalsIgnoreCase("AMS"))  && (aux!=null) )
		{
			NodeList child = aux.getChildNodes();
			amsclient = child.item(0).getNodeValue();
		}
		if ((aux.getNodeName().equalsIgnoreCase("OZONE"))  && (aux!=null) )
		{
			NodeList child = aux.getChildNodes();
			ozoneServer = child.item(0).getNodeValue();
		}
		if ((aux.getNodeName().equalsIgnoreCase("LOAD_TT_ONTOLOGY"))  && (aux!=null) )
		{		
			NodeList child = aux.getChildNodes();
			saux = child.item(0).getNodeValue();			
			this.load_tt_ontology = true;
			if(saux.equalsIgnoreCase("NO"))			
			this.load_tt_ontology = false;
			this.findvalue=true;			
		}				
		if ((aux.getNodeName().equalsIgnoreCase("RESTORE_TT_ONTOLOGY"))  && (aux!=null) )
		{		
			NodeList child = aux.getChildNodes();
			saux = child.item(0).getNodeValue();
			if(saux.equalsIgnoreCase("YES"))			
			this.restore_tt_ontology=true;
			if(saux.equalsIgnoreCase("NO"))			
			this.restore_tt_ontology = false;
			this.findvalue=true;						
		}
		if ((aux.getNodeName().equalsIgnoreCase("BUILD_STATISTICS_TREE"))  && (aux!=null) )
		{		
			NodeList child = aux.getChildNodes();
			saux = child.item(0).getNodeValue();
			if(saux.equalsIgnoreCase("YES"))			
			this.buildST = true;
			if(saux.equalsIgnoreCase("NO"))			
			this.buildST = false;
			this.findvalue = true;						
		}
		if ((aux.getNodeName().equalsIgnoreCase("RESTORE_COMPONENTS_DB"))  && (aux!=null) )
		{		
			NodeList child = aux.getChildNodes();
			saux = child.item(0).getNodeValue();
			if(saux.equalsIgnoreCase("YES"))			
			this.restoreComponentsDB = true;
			if(saux.equalsIgnoreCase("NO"))			
			this.restoreComponentsDB = false;
			this.findvalue = true;						
		}	
	}	
//	this.xml = true; 	  	
  }
  catch(Exception e)
  {
	try
	  {  	
  		  this.amsclient = InetAddress.getLocalHost().getHostAddress();
  	  	  this.tt_host="127.0.0.1";  	  	
	  	  System.out.println("exception: " + e.getMessage());	
	  }
	catch(Exception e1){}   	 
  }
}


public String getAmsClient()
{	
	return this.amsclient;
}


public String getTTHost()
{
	return this.tt_host;
}


/**
 * Returns the ozondeServer.
 * @return String
 */
public String getOzoneServer()
{
	return ozoneServer;
}


/**
 * Returns the load_tt_ontology.
 * @return boolean
 */
public boolean isLoad_tt_ontology() 
{
	if(this.findvalue==false)
		System.err.println("WARNING ERROR ON READING LOADTTONTOLOGY PROPERTY");
	return load_tt_ontology;
}

/**
 * Returns the restore_tt_ontology.
 * @return boolean
 */
public boolean isRestore_tt_ontology() 
{
	if(this.findvalue==false)
		System.err.println("WARNING ERROR ON READING RESTORETTONTOLOGY PROPERTY");
	return restore_tt_ontology;
}

/**
 * Sets the restore_tt_ontology.
 * @param restore_tt_ontology The restore_tt_ontology to set
 */
public void setRestore_tt_ontology(boolean restore_tt_ontology) {
	this.restore_tt_ontology = restore_tt_ontology;
}

/**
 * Returns the buildST.
 * @return boolean
 */
public boolean isBuildST() {
	if(this.findvalue==false)
		System.err.println("WARNING ERROR ON READING RESTORETTONTOLOGY PROPERTY");
	return buildST;
}

/**
 * Sets the buildST.
 * @param buildST The buildST to set
 */
public void setBuildST(boolean buildST) {
	this.buildST = buildST;
}

/**
 * Returns the restoreComponentsDB.
 * @return boolean
 */
public boolean isRestoreComponentsDB() 
{
	if(this.findvalue==false)
		System.err.println("WARNING ERROR ON READING RESTORETTONTOLOGY PROPERTY");
	return restoreComponentsDB;
}

}

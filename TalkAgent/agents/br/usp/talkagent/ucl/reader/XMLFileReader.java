package br.usp.talkagent.ucl.reader;

// Reads a UCL Message from a file

import java.io.*;
import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import br.usp.talkagent.ucl.UCLDoc;
import br.usp.talkagent.ucl.UCLRelation;
import br.usp.talkagent.ucl.UclConcept;
import br.usp.talkagent.util.*;

public class XMLFileReader implements UCLReader 
{

private String filename;
private java.io.File uclfile;
private Document xmldoc;
public UCLDoc ucl;

/***
* Extract relations & concepts from a UCL XML encoded document
*/

public void generateUclDoc() 
{

	Node root, concepts;
	UclConcept ucon;
	UCLRelation urel;
	int i,j;

	root = xmldoc.getDocumentElement();
	ucl = new UCLDoc();

	// reads xmldoc searching for concepts and relations


	NodeList els = root.getChildNodes();
	int nels = els.getLength();

	for (i = 0; i < nels; i++) 
	{
		concepts = els.item(i);
	    
		// manage concapts
		if ((concepts.getNodeName().equalsIgnoreCase("UW"))  && (concepts!=null) )
		{
			NamedNodeMap attrs = concepts.getAttributes();
			Attr attr = (Attr) attrs.item(0);
			// creates new UCLConcept
			// UCL specifies concepts elements such as id and value as
			// attributes instead of elements
			ucon = new UclConcept();
			String value = attr.getNodeValue();
			if(value.endsWith("2340"))			
			{
				int size = value.length()-4;
				value = value.substring(0,size);
			}													
			//ucon.setID(value);
			ucon.setName(value);			
			attr = (Attr) attrs.item(1);
			value = attr.getNodeValue();
			if(value.endsWith("2340"))			
			{
				int size = value.length()-4;
				value = value.substring(0,size);
			}																
			//ucon.setName(value);
			ucon.setID(value);
			// insert concepts on UCLDoc ADT
			ucl.Concepts.insert((ListData) ucon);
			concepts = concepts.getNextSibling();
		}
		
		//manage relations
		else 
		if ((concepts.getNodeName().equalsIgnoreCase("relation")) && (concepts!=null) )
		{
			NamedNodeMap attrs = concepts.getAttributes();
						
			// creates new UCLRelation
			urel = new UCLRelation();
			Attr attr = (Attr) attrs.item(0);
			urel.setId(attr.getNodeValue());
			
		    attr = (Attr) attrs.item(1);
		    urel.setElement1(ucl.Concepts.find(attr.getNodeValue()));		    
		    urel.setE1(attr.getNodeValue());
		        		    
		    //urel.setType(attr.getNodeValue());
		    attr = (Attr) attrs.item(2);		    
			urel.setElement2(ucl.Concepts.find(attr.getNodeValue()));
			urel.setE2(attr.getNodeValue());
			
			attr = (Attr) attrs.item(3);
			urel.setType(attr.getNodeValue());			
			
			// test if relation element exixts
			// if element does not exists, maybe it is another relation,
			// so flag hasnull needs to be checked 
			if((urel.getElement1() == null) || (urel.getElement2() == null))
			{		
				urel.setHasNullValues(true);				
		    }
			
			
			// test if relation element exixts
  	    	// if element does not exists, maybe it is another relation,
			// so flag hasnull need to be checked 
						
			// insert relations on UCLDoc
			ucl.Relations.addElement(urel);	
		}
	}
	
	 // check  Relations that depend on other relations
	
	ucl.isBroken = false;
	for(i=0;i<ucl.Relations.size();i++)
	{
		if ( ((UCLRelation)ucl.Relations.elementAt(i)).HasNullValues())
		{
			// set relations that depend on other relations
			// solve simple relation depences, not mutual depencies like
			// x dep y && y dep x 

			if  ( ((UCLRelation)ucl.Relations.elementAt(i)).getE1() != null)
			{
				for(j=0;j<ucl.Relations.size();j++)
				{
					if( ((UCLRelation)ucl.Relations.elementAt(j)).getId().equalsIgnoreCase( ((UCLRelation)ucl.Relations.elementAt(i)).getE1()) ) 
					{
						((UCLRelation)ucl.Relations.elementAt(i)).setHasNullValues(false);
						((UCLRelation)ucl.Relations.elementAt(i)).setElement1(((UCLRelation)ucl.Relations.elementAt(j)));
						// relation is set now, so:
						j = ucl.Relations.size(); 
					}				
				}
				// if null values stills uclDoc is broken				
			}
			
			if  ( ((UCLRelation)ucl.Relations.elementAt(i)).getE2() != null)
			{
				for(j=0;j<ucl.Relations.size();j++)
				{
					if( ((UCLRelation)ucl.Relations.elementAt(j)).getId().equalsIgnoreCase( ((UCLRelation)ucl.Relations.elementAt(i)).getE2()) ) 
					{
						((UCLRelation)ucl.Relations.elementAt(i)).setHasNullValues(false);
						((UCLRelation)ucl.Relations.elementAt(i)).setElement2(((UCLRelation)ucl.Relations.elementAt(j)));
						// relation is set now, so:
						j = ucl.Relations.size(); 
					}				
				}											
			}	
		// if null values stills uclDoc is broken	
		if (((UCLRelation)ucl.Relations.elementAt(i)).HasNullValues() == true)
		        	ucl.isBroken = true;
		}	
		
	}
	
}

public void print()
{
}
/**
* Reads input XML file
*/

public void read(String Source)
{

  this.filename = Source;
  uclfile = new File(Source);
  
  // start XML Parsing
  try
  {
  DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
  DocumentBuilder db = dbf.newDocumentBuilder();
  xmldoc = db.parse(uclfile);  
  }
  catch(Exception e)
  {
	  System.out.println("exception: " + e.getMessage());	  
  }
}
// constructor	
public XMLFileReader()
{	
   super();
   ucl = new UCLDoc();
}
}
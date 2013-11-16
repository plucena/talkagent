package br.usp.talkagent.kb.memtree;

/**
 * @author Percival Lucena
 * 
 */

/*
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
*/
import org.w3c.dom.Node;

public class mtXmlParser 
{

/**
* Read Knowledge Base from a XML file
*/

/*
public void readFromXML(String XMLfile)
{
	Document xmldoc;
	Node root, nodeConcept;
	mtConcept con;
	File KBfile;
	int i;

	// start XML Parsing
	try
	{		
		KBfile = new File(XMLfile);
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		xmldoc = db.parse(KBfile);
		root = xmldoc.getDocumentElement();

		NodeList els = root.getChildNodes();
		int nels = els.getLength();

		for (i = 0; i < nels; i++)
		{
			nodeConcept = els.item(i);

			if (nodeConcept.getNodeName().equalsIgnoreCase("concept"))
			{
				// creates new UCLConcept
				// UCL specifies nodeConcept elements such as id and value as
				// attributes instead of elements
				con = new mtConcept();
				con.setID(nodeConcept.getNodeValue());
				con.setID(nodeConcept.getNodeValue());
				this.insert(currentNode,con);
				// test if concept has children
				// if so calls recursive methood to insert children
				if (nodeConcept.hasChildNodes())
				{
					MoveDown();
					readXmlData(currentNode,nodeConcept);
					MoveUp();
				}
				nodeConcept = nodeConcept.getNextSibling();
			}
		}
	}
	catch (Exception e)
	{
		e.printStackTrace();
	}
}
*/

/***
* Write Knowledge Base Tree into a XML File
*/
/*
public void writetoXML(String filename)
{
	Element item;
	int i;
	mtConcept caux;

	try
	{
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.newDocument();
		Element root = doc.createElement("kb");

		Elems.currNode = Elems.first; 

		for (i = 0; i < this.Elems.getSize(); i++)
		{
			caux = (mtConcept) (this.Elems.currNode.data);
			item = doc.createElement("concept");
			item.appendChild(doc.createTextNode(caux.getID()));
			root.appendChild(item);

			if (this.Elems.currNode.children != null)
			{
				writeTreeXML(this.Elems.currNode.children,item,doc);
			}
			this.Elems.moveNext();
		}
		
		File fd;
		FileOutputStream outStream;
		ObjectOutputStream objStream;
		fd = new File(filename);		 
		outStream = new FileOutputStream(fd);
		objStream = new ObjectOutputStream(outStream);		
		OutputFormat format = new OutputFormat(doc);
		format.setLineWidth(80);
		format.setIndenting(true);
		format.setIndent(2);
  		XMLSerializer serializer = new XMLSerializer(objStream, format);
		serializer.serialize(root);		
	}
	catch (Exception e)
	{
		e.printStackTrace();
	}

}
*/

/**
* 
*/ 
/*
private void writeTreeXML(List pt, Element xmlpt, Document doc) 
{

int i;
mtConcept caux;
Element item;

pt.currNode = pt.first;


	for(i=0;i<pt.getSize();i++)
	{

	  caux = (mtConcept)(pt.currNode.data);
	  item = doc.createElement("concept");
	  item.appendChild(doc.createTextNode(caux.getID()));
	  xmlpt.appendChild(item);

	  if(pt.currNode.children != null)
	  {
		  writeTreeXML(pt.currNode.children,item,doc);
	  }
	  pt.moveNext(); 
	}
}
*/

/**
*/ 
private void readXmlData(br.usp.talkagent.util.GenList ptnode, Node xmlnode) 
{
 System.err.println("Missing implementation " + ptnode.toString() + xmlnode.toString());	
}



}

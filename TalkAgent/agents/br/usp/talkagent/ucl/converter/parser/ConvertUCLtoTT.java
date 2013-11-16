package br.usp.talkagent.ucl.converter.parser;

import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
/**
 * Insert the type's description here.
 * Creation date: (06/05/2001 %r)
 * @author: 
 */
 
 import org.w3c.dom.Document;

 
public class ConvertUCLtoTT {

		static Document document;

		static NodeList o_NodeListRel;
		static NodeList o_NodeListUw;

		static String s_stringtt = "";

	
/**
 * Insert the method's description here.
 * Creation date: (06/05/2001 %r)
 * @return java.lang.String
 * @param o_namefile java.lang.String
 */
public void convert( Element root, Element o_node ) {
	if ( o_node != null ){
		
		String s_uwx = o_node.getAttribute("id1");
		String s_uwy = o_node.getAttribute("id2");

		//getfirstChild() / Left - in RELATION - id1
		Element o_nodeNext = findElement( o_NodeListRel, s_uwx );
		if ( o_nodeNext != null ){
			convert( root, (Element)o_nodeNext );
		}else {
			o_nodeNext = findElement( o_NodeListUw, s_uwx );
			s_stringtt = s_stringtt.concat( o_nodeNext.getAttribute("head")+" " );
		}
		
		//getLastChild() / right - in RELATION - id2
		o_nodeNext = findElement( o_NodeListRel, s_uwy );
		if( o_nodeNext != null ){
			s_stringtt = s_stringtt.concat("[");
			convert( root, o_nodeNext );
			s_stringtt = s_stringtt.concat("]");
		}else {
			o_nodeNext = findElement( o_NodeListUw, s_uwy );
			s_stringtt = s_stringtt.concat( o_nodeNext.getAttribute("head")+" " );
		}

	}
}
/**
 * Insert the method's description here.
 * Creation date: (06/06/2001 %r)
 * @return org.w3c.dom.Element
 * @param o_NodeListRel org.w3c.dom.NodeList
 * @param s_uwx java.lang.String
 */
public Element findElement(NodeList o_NodeListRel, String s_uwx) 
{
	for(int i = 0; i < o_NodeListRel.getLength(); i++ )
	{
		Element o_element = (Element)o_NodeListRel.item(i);
		if( o_element.getAttribute( "id" ).equalsIgnoreCase( s_uwx ) )
			return o_element;		
	}
	return null;
}
/**
 * Starts the application.
 * @param args an array of command-line arguments
 */
public static void main() 
{
	// Insert code to start the application here.
	
	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	//factory.setValidating(true);   
	//factory.setNamespaceAware(true);

	//String s_namefile = "/home2/graduate/carlos/ucl/test/AAoutput.xml";
	String s_namefile = "../AAoutput.xml";

	try {
		DocumentBuilder builder = factory.newDocumentBuilder();
		document = builder.parse( new File( s_namefile ) );
		o_NodeListRel = document.getElementsByTagName("relation");
		o_NodeListUw  = document.getElementsByTagName( "uw" );

		//first element of docuemnt->root;
		//document.getDocumentElement()	

		//To use this method "document.getElementById("uw1")" i need validate document
		//with a DTD.
		//Element o_Element = document.getElementById("uw1");

		ConvertUCLtoTT o_ucltott = new ConvertUCLtoTT();

		s_stringtt = s_stringtt.concat("[");
		o_ucltott.convert( document.getDocumentElement(), (Element)o_NodeListRel.item(0) );
		s_stringtt = s_stringtt.concat("]");
		System.out.println( s_stringtt );
		
	} catch ( Exception e) {
		e.printStackTrace(System.out);
	}
}
/**
 * Insert the method's description here.
 * Creation date: (06/06/2001 %r)
 */
public String run() {

	// Insert code to start the application here.
	
	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	//factory.setValidating(true);   
	//factory.setNamespaceAware(true);

	//String s_namefile = "/home2/graduate/carlos/ucl/test/AAoutput.xml";
	String s_namefile = "../AAoutput.xml";

	try {
		DocumentBuilder builder = factory.newDocumentBuilder();
		document = builder.parse( new File( s_namefile ) );
		o_NodeListRel = document.getElementsByTagName("relation");
		o_NodeListUw  = document.getElementsByTagName( "uw" );

		//first element of docuemnt->root;
		//document.getDocumentElement()	

		//To use this method "document.getElementById("uw1")" i need validate document
		//with a DTD.
		//Element o_Element = document.getElementById("uw1");

		ConvertUCLtoTT o_ucltott = new ConvertUCLtoTT();

		s_stringtt = s_stringtt.concat("[");
		o_ucltott.convert( document.getDocumentElement(), (Element)o_NodeListRel.item(0) );
		s_stringtt = s_stringtt.concat("]");
		//System.out.println( s_stringtt );
		
	} catch ( Exception e) {
		e.printStackTrace(System.out);
	}
return s_stringtt;
}
/**
 * ConvertUCLtoTT constructor comment.
 */
public ConvertUCLtoTT() {
	super();
}
}
package br.usp.talkagent.ucl.converter.parser;

import org.xml.sax.SAXException;
import java.io.*;
import org.apache.crimson.tree.*;
import org.w3c.dom.Element;
import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Vector;
import java.lang.*;
import java.util.*;
import com.signiform.tt.*;

/**
 * Insert the type's description here.
 * Creation date: (05/27/2001 %r)
 * @author: 
 */
public class ConvertTTtoXML {

	/** All output will be use this encoding */
	static final String outputEncoding 	= "UTF-8";
	static final String uw				= "uw";
	static final String icl 			= "icl";
	static final String head 			= "head";
	static final String id 				= "id";
	static final String relation		= "relation";
	static final String label			= "label";
	static final String id1				= "id1";
	static final String id2				= "id2";
	static int			Numid 			= 0;
	//int					NumidRel		= 0;
	int 				level			= 0;
	public static Vector v_relations = new Vector();
	
		
	// Global value so it can be ref'd 
	static Document document;
	static Document docrelations;

/**
 * Insert the method's description here.
 * Creation date: (05/31/2001 %r)
 * @param o_node org.w3c.dom.Element
 */
public void addrelations(Element root, Element node) {
	
	if (node.getFirstChild() != null ){
		
		String s_label = "icl";
		Element Erelation = document.createElement( relation );
		Erelation.setAttribute( id, node.getTagName() );

		//Only to find the right label::LOOK!!
		if( node.getFirstChild().getFirstChild() == null && node.getLastChild().getLastChild() == null ){
			s_label = "agt";
		}
		Erelation.setAttribute( label, s_label ); 
		Erelation.setAttribute( id1, ((Element)node.getFirstChild()).getAttribute( id ) ); 
		Erelation.setAttribute( id2, ((Element)node.getLastChild()).getAttribute( id ) ); 
			
		root.appendChild( Erelation );
		addrelations( root, (Element)node.getFirstChild() );
		addrelations( root, (Element)node.getLastChild() );

		}
	}
//Deprecated, exist a Class "ConvertUCltoTT" that execute this process.
/**
 * Insert the method's description here.
 * Creation date: (06/02/2001 %r)
 * @return java.lang.String
 * @param node org.w3c.dom.Element
 */
public String convertUcltoTT(Element node) {


		Document document2;
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		//factory.setValidating(true);   
		//factory.setNamespaceAware(true);
		try {
		   DocumentBuilder builder = factory.newDocumentBuilder();
		   document2 = builder.parse( new File(ioFile) );
		   document2.getFirstChild();
		   
 
		} catch (SAXException sxe) {
		   // Error generated during parsing)
		   Exception  x = sxe;
		   if (sxe.getException() != null)
			   x = sxe.getException();
		   x.printStackTrace();

		} catch (ParserConfigurationException pce) {
			// Parser with specified options can't be built
			pce.printStackTrace();

		} catch (IOException ioe) {
		   // I/O error
		   ioe.printStackTrace();
		}
		
		
/*		if (node.getFirstChild() != null ){
		
		//String s_label = "icl";
		//Element Erelation = document.createElement( relation ); "["
		//Erelation.setAttribute( id, node.getTagName() );

		//Only to find the right label:LOOK!!
		if( node.getFirstChild().getFirstChild() == null && node.getLastChild().getLastChild() == null ){
			s_label = "agt";
		}
		//Erelation.setAttribute( label, s_label ); 
		//Erelation.setAttribute( id1, ((Element)node.getFirstChild()).getAttribute( id ) ); 
		//Erelation.setAttribute( id2, ((Element)node.getLastChild()).getAttribute( id ) ); 
			
		root.appendChild( Erelation );
		convertUcltoTT( (Element)node.getFirstChild() );
		convertUcltoTT( (Element)node.getLastChild() );

		}
*/
	return null;
}
/**
 * Insert the method's description here.
 * Creation date: (05/31/2001 %r)
 * @param param com.signiform.tt.TTConnection
 */
public String inputSentence( TTConnection tt ) {

	try {
		int i_size;
		String s_string = new String();
		String s_stringElements = new String();
		String s_generate = new String();
		String s_citationForm_features = new String();
		Vector v_CtoLex = new Vector();
		Vector v_semantic = new Vector();
		TTLexEntryToObj LextoObj = new TTLexEntryToObj();
		boolean b_answer;

		//s_string = "Who directed Rendezvous in Paris?";
		//s_string = "Person had a little lamb.";
		//s_string = "Person send information to Jim.";
		//s_string = "Jim send file to Erik.";
		//s_string = "How much is a CPU?.";
		s_string = "Monkey eats bananas.";
		//isn't working
		//s_string = "UNL is a common language that would be used for network communications.";
		//More examples in Attributes of UNL//

		//s_string = "What is the price of the IBM?.";
		//s_string = "I want to buy a Fiat Spyder?.";
		//s_string = "What time is it?.";
		//s_string = "How much is CPU?.";
		//s_string = "Who created IBM?.";
		//s_string = "List the products.";



		//System.out.println(">>>>>>>>>>>>> Semantic Parse >>>>>>>>>>>>>>");
		//System.out.println("> Original: '" + s_string + "'");
		//System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		v_semantic = tt.semanticParse(String.valueOf(TT.F_ENGLISH), s_string);
		//System.out.println(TT.objectToPrettyString(v_semantic));
		i_size = v_semantic.size() - 1;
		//System.out.println(" ");
		//System.out.println(">>>>>>>>>>>>> ConceptToLexEntries >>>>>>>>>>>>>>");
		for (int n_for = 1; n_for <= i_size; n_for += 2) {
			s_stringElements = TT.objectToString(v_semantic.elementAt(n_for));
			//System.out.println(" ");
			//System.out.println("************ Tree Semantic Nro: *********<<" + n_for + ">>****************");
			//System.out.println(s_stringElements);
			//System.out.println("**************************************************************");
			StringTokenizer st = new StringTokenizer(s_stringElements, " []");
			while (st.hasMoreTokens()) {
				String token = st.nextToken();
				//System.out.println(" ");
				//System.out.println("** CONCEPT ** " + token);
				v_CtoLex = tt.conceptToLexEntries(token);
				//System.out.println(TT.objectToPrettyString(v_CtoLex));
				/*Search token into v_CtoLex(and cath attributes)*/

				b_answer = false;
				for (Enumeration e1 = v_CtoLex.elements(); e1.hasMoreElements();) {
					LextoObj = (TTLexEntryToObj) e1.nextElement();
					s_citationForm_features = LextoObj.lexentry.citationForm + ":" + LextoObj.lexentry.features;


					//Recursive function to search into the syntactic tree.
					b_answer = isItIn(v_semantic.elementAt(n_for + 1), s_citationForm_features);

					// If found then break, and continue to match other s_citationForm_features
					// with sintactic parser.(to get attributes)
					if (b_answer)
						break;
				}
				//if (b_answer)
					//System.out.println("I found It!=>" + s_citationForm_features);
				//else
					//System.out.println("###### I can't find! ###### " + s_citationForm_features );
			}
		}
		System.out.println(" ");
		System.out.println(">>>>>>>>>>>>> Generate >>>>>>>>>>>>>>");
		System.out.println(" ");
		System.out.println("> Original: '" + s_string + "'");
		for (int n_for = 1; n_for <= i_size; n_for += 2) {
			s_generate = tt.generate(String.valueOf(TT.F_ENGLISH), v_semantic.elementAt(n_for));
			System.out.println( "<"+n_for +"> " +s_generate);
		}

		BufferedReader stdIn = new BufferedReader( new InputStreamReader(System.in));
		
		System.out.println("Option:"); String op = stdIn.readLine(); System.out.println(" ");
		int i_op = Integer.parseInt( op );
		s_generate = tt.generate(String.valueOf(TT.F_ENGLISH), v_semantic.elementAt( i_op ));
		System.out.println( "<"+ op +"> " +s_generate );
		System.out.println("|==========================================================|");
		System.out.println( TT.objectToString( v_semantic.elementAt( i_op )) );
		System.out.println(" ");
		return TT.objectToString(v_semantic.elementAt( i_op ));
	} catch (Exception e) {
		e.printStackTrace(System.out);
	}
return null;
}
public static boolean isItIn(Object o_syntactic, String s_citationForm_features) {

	if (o_syntactic instanceof Vector){
		Vector v_syntactic = (Vector)o_syntactic;
		for(Enumeration e1 = v_syntactic.elements(); e1.hasMoreElements(); ) {
			if( isItIn(e1.nextElement(), s_citationForm_features))
				return true;
		}
	}else if (o_syntactic instanceof TTPNode){
		TTPNode TTPNode_node = (TTPNode)o_syntactic;
		if( TTPNode_node.leo != null ){
			//compare
			if(s_citationForm_features.equals(TTPNode_node.leo.lexentry.citationForm+":"+ 
											  TTPNode_node.leo.lexentry.features))
				return true;
		}
	}
	return false;
}
/**
 * Starts the application.
 * @param args an array of command-line arguments
 */
public static void main()
 {
	ConvertTTtoXML oConvert = new ConvertTTtoXML();
	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	try {
		TTConnection tt=null;
		tt=null;
		String host = "localhost";
		TTConnector ttc = new TTConnector(host);
		tt = ttc.connect();
		
		DocumentBuilder builder = factory.newDocumentBuilder();
		document = builder.newDocument(); // Create from whole cloth 
		//document2 = builder.parse("/home2/graduate/carlos/ucl/test/AAoutput.xml"); // 
		docrelations = builder.newDocument(); // Create from whole cloth 

		//TreeBinary only to "relations"
		++Numid;
		Element rootrelations = docrelations.createElement( uw + Numid );
		docrelations.appendChild( rootrelations );

		//Element root = (Element)document.createElement("sentence");
		Element root = document.createElement("sentence");
		document.appendChild(root);

		
		String sentence = oConvert.inputSentence( tt );
		
		//String sentence = new String("[present-indicative [eat ape banane]]");
		//"I want to buy a Fiat Spyder?."
		//String sentence = new String("[present-indicative [active-goal subject-pronoun [Fed-buy subject-pronoun Fiat-Spider]]]");

		oConvert.parse(sentence, root, rootrelations, tt);

		oConvert.addrelations(root, rootrelations);
		
		System.out.println("========================================================================");
		for (Enumeration e1 = v_relations.elements(); e1.hasMoreElements();) {
			String s_string = (e1.nextElement()).toString();
			System.out.println( s_string );
		}

		
		FileWriter oWriter = new FileWriter(ioFile);
		((XmlDocument) document).write(oWriter, outputEncoding);
		System.out.println("========================================================================");
		((XmlDocument) document).write(System.out);

		//Deconverter from UCL to TT
		ConvertUCLtoTT o_ucltott = new ConvertUCLtoTT();
		String s_tt = o_ucltott.run();
		System.out.println( s_tt );
		
		//tt.generate(,)
		System.out.println("English:");
		System.out.println(
			tt.generate(
				String.valueOf(TT.F_ENGLISH), 
				TT.stringToObject(  s_tt )));
		
		System.out.println("French :");
		System.out.println(
			tt.generate(
				String.valueOf(TT.F_FRENCH), 
				TT.stringToObject(  s_tt )));

		//String s_TT = oConvert.convertUcltoTT( rootrelations );
		
		//FileWriter oWriterRel = new FileWriter("/home2/graduate/carlos/ucl/test/AAoutputRel.xml");
		//((XmlDocument) docrelations).write(oWriterRel, outputEncoding);

		/* Close the connection. */
		tt.close();
	} catch (Exception e) {
		e.printStackTrace(System.out);
	}
} //main
public void output(Object o_parsesentence, Element node, Element noderelations, TTConnection tt){
try {
		
		if(o_parsesentence instanceof Vector){
			Vector v_parsesentence = (Vector)o_parsesentence;

			//noderelations.appendChild( nodelink );
			//++NumidRel;
			++Numid;
			Element noderelationsVector = docrelations.createElement( uw + Numid );
			noderelationsVector.setAttribute( id, uw + Numid );
			Element noderelationsTmp0;
			Element noderelationsTmp;
			
			noderelationsTmp0 = noderelationsVector;
			noderelationsTmp = noderelationsVector;
			
			level++;
			for( Enumeration e1 = v_parsesentence.elements(); e1.hasMoreElements(); ) {
				//Element uw = document.createElement( "uw" );
				//node.appendChild( uw );
				//output( e1.nextElement(), uw );

				output( e1.nextElement(), node, noderelationsTmp, tt);

				//process to create docrelations
				if ( noderelationsTmp.getAttribute("nodes") == "2" ){
					noderelationsTmp0 = noderelationsTmp;
					++Numid;
					Element noderelationsUp = docrelations.createElement( uw + Numid );
					noderelationsUp.setAttribute( id, uw + Numid );
					noderelationsUp.setAttribute("nodes","1");
					noderelationsUp.appendChild( noderelationsTmp );
					noderelationsTmp = noderelationsUp;
				}
			}
			level--;
			
			if ( noderelationsTmp.getAttribute("nodes") == "1" ){
				noderelationsTmp = noderelationsTmp0;
				noderelations.setAttribute("nodes","2");
			}
			noderelations.appendChild( noderelationsTmp );
			
		}else if(o_parsesentence instanceof String) {
			//process to create document
			String s_concept = (String)o_parsesentence;
			Element leaf0 = document.createElement( uw );
			Element leaf1 = document.createElement( icl );
			Element leaf2 = document.createElement( uw );
			++Numid;
			leaf0.setAttribute( id, uw + Numid ); 
			leaf0.setAttribute( head, s_concept );
			leaf1.setAttribute( "direction","to" );
			
			Vector v_parents = tt.parents( s_concept );
			String parent = TT.objectToString(v_parents.elementAt(0));

			leaf2.setAttribute( head, parent );
			
			node.appendChild( leaf0 );
			leaf0.appendChild( leaf1 );
			leaf1.appendChild( leaf2 );

			//it is temporal.
			v_relations.addElement( uw + Numid + ":"+ level + ":" + s_concept );

			
			//process to create docrelations
			
			Element leaf0Copia = docrelations.createElement( uw );
			leaf0Copia.setAttribute( id, uw + Numid );
			noderelations.appendChild( leaf0Copia );
			if( noderelations.getAttribute("nodes") == "1"){
				noderelations.setAttribute("nodes","2");
				} else {
				noderelations.setAttribute("nodes","1");
				}
		}
		
	} catch (Exception e) {
		e.printStackTrace(System.out);
	}
 
	}
	/** Parse the input */
	public void parse(String parsesentence, Element node, Element noderelations, TTConnection tt) 
	throws IOException 
 
	{
		try {

			Element noderelationsTmp0;
			Element noderelationsTmp;
			
			Vector v_parsesentence = new Vector();
			v_parsesentence = (Vector)TT.stringToObject(parsesentence);

			noderelationsTmp0 = noderelations;
			noderelationsTmp = noderelations;

			for(Enumeration e1 = v_parsesentence.elements(); e1.hasMoreElements(); ) {
				//output(e1.nextElement(), node, noderelations, tt);
				//N_odo = 
				output(e1.nextElement(), node, noderelationsTmp, tt);

				if ( noderelationsTmp.getAttribute("nodes") == "2" ){
					noderelationsTmp0 = noderelationsTmp;
					//++NumidRel;
					++Numid;
					Element noderelationsUp = docrelations.createElement( uw + Numid );
					noderelationsUp.setAttribute("nodes","1");
					noderelationsUp.setAttribute( id, uw + Numid );
					noderelationsUp.appendChild( noderelationsTmp );
					noderelationsTmp = noderelationsUp;
				}
			}

			if ( noderelationsTmp.getAttribute("nodes") == "1" ){
				noderelations = noderelationsTmp0;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
/**
 * ConvertTTtoXML constructor comment.
 */
public ConvertTTtoXML() {
	super();
}
  static java.lang.String ioFile;/**
 * ConvertTTtoXML constructor comment.
 */
public ConvertTTtoXML(String io) 
{
	super();
	this.ioFile = io;
}}
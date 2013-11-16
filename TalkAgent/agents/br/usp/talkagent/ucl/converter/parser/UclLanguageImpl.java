package br.usp.talkagent.ucl.converter.parser;

import java.util.StringTokenizer;
import java.io.File;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.apache.xml.serialize.*;
import org.apache.crimson.tree.XmlDocument;
import java.io.FileWriter;
import java.util.Enumeration;
import java.util.Vector;
import java.io.IOException;
import org.w3c.dom.Element;
import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import com.signiform.tt.*;
import com.oroinc.text.regex.*;

/**
 * Insert the type's description here.
 * Creation date: (06/12/2001 %r)
 * @author: 
 */
 
public class UclLanguageImpl implements UclLanguage 
{

//Converter

	static int			Numid 			= 0;
	static int 			level			= 0;
	
	// Global value so it can be ref'd 
	private org.w3c.dom.Document document;
	private  org.w3c.dom.Document docrelations;

	DocumentBuilderFactory factory;
	
	TTConnection tt;

	DocumentBuilder builder;

	Element rootrelations;

	Element root;

	Element noderelationsTmp0;
	Element noderelationsTmp;

	
	String s_sentenceLN;
	String s_sentenceTT;
	String s_filenameoutput; 

	Vector v_semantic;
	
//DeConverter
	static Document documentLoad;

	static NodeList o_NodeListRel;
	static NodeList o_NodeListUw;

	static String s_stringtt = "";

	static String s_question = ""; // to use in "find if is a sentence is a question"
	static String s_concept = "";

	//this section is only to search if exist som concept related to question.
	Pattern 		pattern;
	Perl5Compiler 	compiler	= new Perl5Compiler(); 
	PatternMatcher	matcher 	= new Perl5Matcher();

public String cleanUp0(String s_param1) 
{
	PatternCompiler compiler = new Perl5Compiler();

	//if we need add other kind of level then create a array of patterns
	//and include a "while" to perform "clean" all pattern possibilities.
	try {
		String s_ToReturn;
		s_ToReturn = Util.substitute(new Perl5Matcher(), //matcher
				//compiler.compile("\\[(\\s*)definite-article(\\s+)(\\w+)(\\s*)\\]"), //pattern
				compiler.compile("\\[(\\s*)definite-article(\\s+)(\\w+|(\\w+-\\w+)*)(\\s*)\\]"), //pattern
				new Perl5Substitution("$3", Perl5Substitution.INTERPOLATE_ALL), //substitution and interps
				s_param1, //input //"[present-indicative [kill [definite-article man] [definite-article ape]]]",  //input
				Util.SUBSTITUTE_ALL); //limit
		s_ToReturn = Util.substitute(new Perl5Matcher(), //matcher
				compiler.compile("\\[(\\s*)indefinite-article(\\s+)(\\w+|(\\w+-\\w+)*)(\\s*)\\]"), //pattern
				new Perl5Substitution("$3", Perl5Substitution.INTERPOLATE_ALL), //substitution and interps
				s_ToReturn, //input 
				Util.SUBSTITUTE_ALL); //limit
		//his son is lovely. check it!
		s_ToReturn = Util.substitute(new Perl5Matcher(), //matcher
				compiler.compile("\\[(\\s*)possessive-determiner(\\s+)(\\w+|(\\w+-\\w+)*)(\\s*)\\]"), //pattern
				new Perl5Substitution("$3", Perl5Substitution.INTERPOLATE_ALL), //substitution and interps
				s_ToReturn, //input 
				Util.SUBSTITUTE_ALL); //limit
		s_ToReturn = Util.substitute(new Perl5Matcher(), //matcher
				compiler.compile("\\[(\\s*)det-this(\\s+)(\\w+|(\\w+-\\w+)*)(\\s*)\\]"), //pattern
				new Perl5Substitution("$3", Perl5Substitution.INTERPOLATE_ALL), //substitution and interps
				s_ToReturn, //input 
				Util.SUBSTITUTE_ALL); //limit
		s_ToReturn = Util.substitute(new Perl5Matcher(), //matcher
				//[such-that human [NAME-of human [NAME Sophie]]]
				compiler.compile("\\[such-that human \\[NAME-of human \\[NAME (\\w*)\\]\\]\\]"), //pattern
				new Perl5Substitution("$1", Perl5Substitution.INTERPOLATE_ALL), //substitution and interps
				s_ToReturn, //input 
				Util.SUBSTITUTE_ALL); //limit
		return s_ToReturn;
	} catch (MalformedPatternException e) {
		System.err.println("Bad pattern.");
		System.err.println(e.getMessage());
		System.exit(1);
	}
	return null;
}
/**
 * Insert the method's description here.
 * Creation date: (06/13/2001 %r)
 */
public void closeSession() {
	try {
		if ( tt.status().equalsIgnoreCase("up") ){
			tt.close();
		}
	} catch ( Exception e ) {
		e.printStackTrace();
	}
}
/**
 * Insert the method's description here.
 * Creation date: (06/13/2001 %r)
 * @param s_string java.lang.String
 */
public Vector convertLNtoTT() {
	try {
		return tt.semanticParse( String.valueOf(TT.F_ENGLISH), getSentenceLN() );
	} catch ( Exception e ) {
		e.printStackTrace();
	}
	return null;
}
/**
 * Insert the method's description here.
 * Creation date: (05/31/2001 %r)
 * @param o_node org.w3c.dom.Element
 */
public void convertTTtoUCLaddrelations(Element root, Element node) {
	
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
		convertTTtoUCLaddrelations( root, (Element)node.getFirstChild() );
		convertTTtoUCLaddrelations( root, (Element)node.getLastChild() );

		}
	}
public void convertTTtoUCLbuildDOM(Object o_parsesentence, Element node, Element noderelations, TTConnection tt){
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

				convertTTtoUCLbuildDOM( e1.nextElement(), node, noderelationsTmp, tt);

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
	public void convertTTtoUCLparse(String parsesentence, Element node, Element noderelations, TTConnection tt) 
	throws IOException
 
	{
		try {

			noderelationsTmp0	= null;
			noderelationsTmp	= null;
			
			Vector v_parsesentence = new Vector();
			v_parsesentence = (Vector)TT.stringToObject(parsesentence);

			//:noderelationsTmp0 = noderelations;
			//:noderelationsTmp = noderelations;
			noderelationsTmp0 = (Element)noderelations.cloneNode(true);
			noderelationsTmp = (Element)noderelations.cloneNode(true);

			for(Enumeration e1 = v_parsesentence.elements(); e1.hasMoreElements(); ) {

				convertTTtoUCLbuildDOM(e1.nextElement(), node, noderelationsTmp, tt);
//				convertTTtoUCLbuildDOM( v_parsesentence, node, noderelationsTmp, tt);

				if ( noderelationsTmp.getAttribute("nodes") == "2" ){
					noderelationsTmp0 = (Element)noderelationsTmp.cloneNode(true);
					//:noderelationsTmp0 = noderelationsTmp;
					//++NumidRel;
					++Numid;
					Element noderelationsUp = docrelations.createElement( uw + Numid );
					noderelationsUp.setAttribute("nodes","1");
					noderelationsUp.setAttribute( id, uw + Numid );
					noderelationsUp.appendChild( noderelationsTmp );
					noderelationsTmp = (Element)noderelationsUp.cloneNode(true);
					//:noderelationsTmp = noderelationsUp;
				}
			}

			if ( noderelationsTmp.getAttribute("nodes") == "1" ){
				//noderelations = (Element)noderelationsTmp0.cloneNode(true);
				Document doc 	= noderelations.getOwnerDocument();
				Node nodeTmp	= doc.importNode( noderelationsTmp0.cloneNode(true), true );
				Node nodeParent = noderelations.getParentNode();
				nodeParent.replaceChild( nodeTmp, noderelations );
				//noderelations.appendChild( nodeTmp );
				//noderelations.getOwnerDocument().importNode( noderelationsTmp0.cloneNode(true), true );
				//:noderelations = noderelationsTmp0;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
/**
 * Insert the method's description here.
 * Creation date: (06/29/2001 %r)
 */
public void convertTTtoUCLparseOLD() {
/*
	// Parse the input 
	public void convertTTtoUCLparse(String parsesentence, Element node, Element noderelations, TTConnection tt) 
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

				convertTTtoUCLbuildDOM(e1.nextElement(), node, noderelationsTmp, tt);

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
*/
//======================//
/*
public void convertTTtoUCLbuildDOM(Object o_parsesentence, Element node, Element noderelations, TTConnection tt){
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

				convertTTtoUCLbuildDOM( e1.nextElement(), node, noderelationsTmp, tt);

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
*/	
	}
/**
 * convertTTtoUcl method comment.
 */
public void convertTTtoUCL() {

	try {
		//missing get RootRelation
		//convertTTtoUCLparse( getSentenceTT(), getRoot(), rootrelations, tt );
		convertTTtoUCLparse( getSentenceTT(), getRoot(), getRootRelation(), tt );
		//missing getRootRelation: 
		//convertTTtoUCLaddrelations( getRoot(), rootrelations );
		convertTTtoUCLaddrelations( getRoot(), getRootRelation() );
	} catch ( Exception e ) {
		e.printStackTrace();
	}
}
/**
 * convertTTtoUCLwrite method comment.
 */

public void convertTTtoUCLwrite(){
	try {
		XMLSerializer writer = new org.apache.xml.serialize.XMLSerializer(System.out, new OutputFormat());
		FileWriter oWriter = new FileWriter( s_filenameoutput );
		((org.apache.crimson.tree.XmlDocument) document).write(oWriter, outputEncoding);

		// show UCL on screen
		//((XmlDocument) document).write(System.out);
	} catch ( Exception e) {
		e.printStackTrace();
	}
}
/**
 * Insert the method's description here.
 * Creation date: (06/13/2001 %r)
 */
public String deconvertTTtoLN( String s_language ) {

	try {
		if( s_language.equalsIgnoreCase( String.valueOf( TT.F_FRENCH ) ) )
			return tt.generate(
				String.valueOf(TT.F_FRENCH),
				TT.stringToObject(  s_stringtt )) + 
				s_question;
		else
			return tt.generate(
				String.valueOf(TT.F_ENGLISH),
				TT.stringToObject(  s_stringtt )) + 
				s_question;
	} catch ( Exception e ) {
		// e.printStackTrace();
	}
return null;
}
/**
 * Insert the method's description here.
 * Creation date: (06/05/2001 %r)
 * @return java.lang.String
 * @param o_namefile java.lang.String
 */
public void deconvertUCLtoTTbuildstring( Element root, Element o_node ) {
	if ( o_node != null ){

		String s_uwx = o_node.getAttribute("id1");
		String s_uwy = o_node.getAttribute("id2");

		//getfirstChild() / Left - in RELATION - id1
		Element o_nodeNext = deconvertUCLtoTTfindElement( o_NodeListRel, s_uwx );
		if ( o_nodeNext != null ){
			deconvertUCLtoTTbuildstring( root, (Element)o_nodeNext );
		}else {
			o_nodeNext = deconvertUCLtoTTfindElement( o_NodeListUw, s_uwx );
			s_concept = o_nodeNext.getAttribute("head");
			ifQuestion( s_concept ); //add control: if sentence is a question, then add symbol "?"
			s_stringtt = s_stringtt.concat( s_concept+" " );
		}
		
		//getLastChild() / right - in RELATION - id2
		o_nodeNext = deconvertUCLtoTTfindElement( o_NodeListRel, s_uwy );
		if( o_nodeNext != null ){
			s_stringtt = s_stringtt.concat("[");
			deconvertUCLtoTTbuildstring( root, o_nodeNext );
			s_stringtt = s_stringtt.concat("]");
		}else {
			o_nodeNext = deconvertUCLtoTTfindElement( o_NodeListUw, s_uwy );
			s_concept = o_nodeNext.getAttribute("head");
			ifQuestion( s_concept ); //add control: if sentence is a question, then add symbol "?"
			s_stringtt = s_stringtt.concat( s_concept+" " );
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
public Element deconvertUCLtoTTfindElement(NodeList o_NodeListRel, String s_uwx) 
{
	for(int i = 0; i < o_NodeListRel.getLength(); i++ ){
		Element o_element = (Element)o_NodeListRel.item(i);
		if( o_element.getAttribute( "id" ).equalsIgnoreCase( s_uwx ) )
			return o_element;
		
	}
	return null;
}
/**
 * convertUcltoTT method comment.
 */
public String deconvertUCLtoTT() {

	try {
		documentLoad = builder.parse( new File( s_filenameoutput ) );
		o_NodeListRel = document.getElementsByTagName("relation");
		o_NodeListUw  = document.getElementsByTagName( "uw" );

		//this section is only to search if exist som concept related to question.
		s_question = ""; //isn't a question, default
		//this section is only to search if exist som concept related to question.

		s_stringtt = s_stringtt.concat("[");
		deconvertUCLtoTTbuildstring( documentLoad.getDocumentElement(), (Element)o_NodeListRel.item(0) );
		s_stringtt = s_stringtt.concat("]");

		return s_stringtt;
	} catch ( Exception e) {
		e.printStackTrace(System.out);
	}
return s_stringtt;	
}
/**
 * Insert the method's description here.
 * Creation date: (06/13/2001 %r)
 * @return java.lang.String
 */
public String getFilenameoutput() {
	return s_filenameoutput;
}
/**
 * Insert the method's description here.
 * Creation date: (06/13/2001 %r)
 * @return org.w3c.dom.Element
 */
public org.w3c.dom.Element getRoot() {
	return document.getDocumentElement();
}
public org.w3c.dom.Element getRootRelation() {
	return docrelations.getDocumentElement();
}
/**
 * Insert the method's description here.
 * Creation date: (06/12/2001 %r)
 * @return java.lang.String
 */
public String getSentenceLN() {
	return s_sentenceLN;
}
/**
 * Insert the method's description here.
 * Creation date: (06/13/2001 %r)
 * @return java.lang.String
 */
public String getSentenceTT() {
	return s_sentenceTT;
}
/**
 * Insert the method's description here.
 * Creation date: (06/26/2001 %r)
 */
public void ifQuestion( String s_param1 ) {
	
	try {
		pattern = compiler.compile("(\\w|-|\\s|\\[|\\])*interrogative(\\w|-|\\s|\\[|\\])*", Perl5Compiler.CASE_INSENSITIVE_MASK);
		if ( matcher.matches( s_param1, pattern) )
					s_question = "?";
	} catch (MalformedPatternException e) {
		System.err.println( "Bad pattern" );
		System.err.println( e.getMessage());
		return;
	}	
}
/**
 * init method comment.
 */
public void init() {
	Numid 			= 0;
	level			= 0;

	
	// Global value so it can be ref'd 
	document =  null;
	docrelations = null;

	//DocumentBuilderFactory factory;
	
	//TTConnection tt;

	//DocumentBuilder builder;

	rootrelations = null;

	root = null;

	noderelationsTmp0 	= null;
	noderelationsTmp	= null;

	
	s_sentenceLN = "";
	s_sentenceTT = "";
	s_filenameoutput = "../AAoutput.xml";

	//Vector v_semantic;
	
//DeConverter
	//static Document documentLoad;

	//static NodeList o_NodeListRel;
	//static NodeList o_NodeListUw;

	s_stringtt = "";

/////////////////////////////////////////////////////////////////////////////////////////////

	document 		= builder.newDocument(); // Create from whole cloth 
	docrelations 	= builder.newDocument(); // Create from whole cloth 

	++Numid;
	rootrelations 	= docrelations.createElement( uw + Numid );
	rootrelations.setAttribute( id, uw + Numid );
	docrelations.appendChild( rootrelations );

	root 			= document.createElement("sentence");
	document.appendChild(root);
	}
//isItIn
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
 * Insert the method's description here.
 * Creation date: (06/13/2001 %r)
 * @param s_name java.lang.String
 */
public void setFilenameoutput(String s_name) {
	s_filenameoutput = s_name;
	}
/**
 * Insert the method's description here.
 * Creation date: (06/12/2001 %r)
 * @param s_sentence java.lang.String
 */
public void setSentenceLN(String s_sentence) 
{
	s_sentenceLN = s_sentence;
}

/**
 * Insert the method's description here.
 * Creation date: (06/13/2001 %r)
 * @param i_op int
 */
public void setSentenceTT(int i_op) {
	s_sentenceTT = TT.objectToString( v_semantic.elementAt( ( i_op * 2 ) + 1 ) );
	}
/**
 * Insert the method's description here.
 * Creation date: (07/13/2001 %r)
 * @param i_op int
 */
public void takeAttOfConcept(int i_op) {
try {
			int 	n_for 					= i_op + 1; //+1 because i'll take all v_semantic elements
			String 	s_citationForm_features = new String();		
			Vector 	v_CtoLex 				= new Vector();
			boolean b_answer;
			TTLexEntryToObj LextoObj = new TTLexEntryToObj();

			// System.out.println("************ Tree Semantic Nro: *********<<" + n_for + ">>****************");
			// System.out.println( getSentenceTT() );
			// System.out.println("**************************************************************");

			//call method takeAttOfConcept after setSentenceTT
			StringTokenizer st = new StringTokenizer(getSentenceTT(), " []");
			while (st.hasMoreTokens()) {
				String token = st.nextToken();
								// debug
								// System.out.println(" ");
				// System.out.println("** CONCEPT ** " + token);
				v_CtoLex = tt.conceptToLexEntries(token);
								// debbug
				// System.out.println(TT.objectToPrettyString(v_CtoLex));
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
				if (b_answer)
										;
					//System.out.println("I found It!=>" + s_citationForm_features);
				else
				       ;
									   //
									   //System.out.println("###### I can't find! ###### " + s_citationForm_features );
			}

	} catch (Exception e) {
	//	e.printStackTrace(System.out);
	}
		
}
/**
 * Insert the method's description here.
 * Creation date: (06/13/2001 %r)
 * @return java.util.Vector
 * @param s_string java.lang.String
 */
public Vector understood(String s_string) {
	setSentenceLN( s_string );
	return understood();
}
/**
 * Insert the method's description here.
 * Creation date: (06/13/2001 %r)
 * @return java.util.Vector
 */
public Vector understood() {
	Vector v_understood = new Vector();

//	PatternCompiler compiler = new Perl5Compiler();

	String s_newElementAt = "";	
	
	try {
		v_semantic = convertLNtoTT();
		for (int n_for = 1; n_for <= v_semantic.size() - 1; n_for += 2) {

				//this section is only to search if exist som concept related to question.
				s_question = ""; //isn't a question, default. see below ifQuestion().
				
				//Here call cleanUp0 : clean extra levels.
				s_newElementAt = cleanUp0( TT.objectToString( v_semantic.elementAt(n_for) ) );
				
				//System.out.println("WithOut Clean:" + TT.objectToString( v_semantic.elementAt(n_for) ) );
				//System.out.println("With    Clean:" + s_newElementAt );

				v_semantic.setElementAt( TT.stringToObject( s_newElementAt ), n_for);

				//again:check ifQuestion
				ifQuestion( s_newElementAt );
						
			// check it! if feedback is null then "not v_understood.addElement()"
			/*
			Jim Garnier kills monkey.
			============ Choose Option ==============
			<0>Jim Garnier kills an ape.
			<1>
			Option:
			1
			=>Debug : [present-indicative [kill [such-that human [NAME-of human [NAME Jim Garnier]]] ape]]
			TT.stringToObject: missing : after NAME
			*/
			v_understood.addElement(
				tt.generate( 
					String.valueOf(TT.F_ENGLISH),
					v_semantic.elementAt(n_for)
					) + 
					s_question );
		}
	} catch ( Exception e) {
		e.printStackTrace();
	}
	return v_understood;
}
/**
 * UclLanguageImpl constructor comment.
 */
public UclLanguageImpl(String host) 
{
	super();
	try {
		factory 		= DocumentBuilderFactory.newInstance();
		
		TTConnector ttc = new TTConnector(host);		
		tt = ttc.connect();
		
		if(!tt.status().equalsIgnoreCase("up")) 
		throw (new  Exception("Could not connect to Thought Treasure Server"));

		builder 		= factory.newDocumentBuilder();

		document 		= builder.newDocument(); // Create from whole cloth 
		docrelations 	= builder.newDocument(); // Create from whole cloth 

		++Numid;
		rootrelations 	= docrelations.createElement( uw + Numid );
		docrelations.appendChild( rootrelations );

		root 			= document.createElement("sentence");
		document.appendChild(root);

		
	} catch ( Exception e ) {
		e.printStackTrace();
	}
}
/**
 * UclLanguageImpl constructor comment.
 */
public UclLanguageImpl(String host, String io) {
	super();
	try {
		s_filenameoutput = io;
		
		factory 		= DocumentBuilderFactory.newInstance();
		
		// Connect to TT which should be running at 1830, 1831, 1832
		tt=null;
		while(tt==null)
		{
			try
			{		
				tt = new TTConnection(host,1832);				 
			}
			catch(Exception e)
			{}
			if(tt==null)
			try
			{
				tt = new TTConnection(host,1831);
			}
			catch(Exception e)
			{}	
			if(tt==null)
			try
			{			
				tt = new TTConnection(host,1830);
			}
			catch(Exception e)
			{}
		}		
		builder 		= factory.newDocumentBuilder();

		document 		= builder.newDocument(); // Create from whole cloth 
		docrelations 	= builder.newDocument(); // Create from whole cloth 

		++Numid;
		rootrelations 	= docrelations.createElement( uw + Numid );
		docrelations.appendChild( rootrelations );

		root 			= document.createElement("sentence");
		document.appendChild(root);

		
	} catch ( Exception e ) {
		e.printStackTrace();
	}
}/**
 * convertTTtoUCLwrite method comment.
 */ 

public void convertedUCLwriter(String io)
{
	try
	{

		FileWriter oWriter = new FileWriter(io);
 		XMLSerializer writer = new org.apache.xml.serialize.XMLSerializer(oWriter, new OutputFormat()); 		
		if(document!=null)
		//		((org.apache.xerces.dom.DocumentImpl) document).write(oWriter, outputEncoding);
		writer.serialize(document.getDocumentElement());
		else
		throw new Exception("Converted UCL Document is Null");		
		// show UCL on screen
		//((XmlDocument) document).write(System.out);
	}
	catch (Exception e)
	{
		try
		{
			File dir1 = new File(".");
			System.err.println("\n==Error==");
			System.out.println("Current dir : " + dir1.getCanonicalPath());
			System.out.println("File: " + io + "\n");
			e.printStackTrace();
		}
		catch (Exception e1)
		{
		}
	}
}}
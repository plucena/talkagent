package br.usp.talkagent.ucl.converter.parser;

import java.util.Vector;


/**
 * Insert the type's description here.
 * Creation date: (06/12/2001 %r)
 * @author: 
 */
interface UclLanguage {

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
	public static Vector v_relations = new Vector();
/**
 * Insert the method's description here.
 * Creation date: (06/13/2001 %r)
 */
void closeSession();
/**
 * Insert the method's description here.
 * Creation date: (06/13/2001 %r)
 * @param s_string java.lang.String
 */
Vector convertLNtoTT();
/**
 * Insert the method's description here.
 * Creation date: (06/12/2001 %r)
 */
void convertTTtoUCL();
/**
 * Insert the method's description here.
 * Creation date: (06/13/2001 %r)
 */
void convertTTtoUCLwrite();
/**
 * Insert the method's description here.
 * Creation date: (06/13/2001 %r)
 */
String deconvertTTtoLN( String s_language );
/**
 * Insert the method's description here.
 * Creation date: (06/12/2001 %r)
 */
String deconvertUCLtoTT();
/**
 * Insert the method's description here.
 * Creation date: (06/13/2001 %r)
 * @return java.lang.String
 */
String getFilenameoutput();
/**
 * Insert the method's description here.
 * Creation date: (06/13/2001 %r)
 * @return org.w3c.dom.Element
 */
org.w3c.dom.Element getRoot();
/**
 * Insert the method's description here.
 * Creation date: (06/12/2001 %r)
 * @return java.lang.String
 */
String getSentenceLN();
/**
 * Insert the method's description here.
 * Creation date: (06/13/2001 %r)
 * @return java.lang.String
 */
String getSentenceTT();
/**
 * Insert the method's description here.
 * Creation date: (06/13/2001 %r)
 */
void init();
/**
 * Insert the method's description here.
 * Creation date: (06/13/2001 %r)
 * @param s_string java.lang.String
 */
void setFilenameoutput(String s_name);
/**
 * Insert the method's description here.
 * Creation date: (06/12/2001 %r)
 */
void setSentenceLN( String s_sentence );
/**
 * Insert the method's description here.
 * Creation date: (06/13/2001 %r)
 * @param i_op int
 */
void setSentenceTT(int i_op);
//takeattOfconcept
public void takeAttOfConcept(int i_op);
/**
 * Insert the method's description here.
 * Creation date: (06/13/2001 %r)
 * @return java.util.Vector
 * @param s_string java.lang.String
 */
Vector understood(String s_string);
/**
 * Insert the method's description here.
 * Creation date: (06/13/2001 %r)
 * @return java.util.Vector
 */
Vector understood();
/**
 * Insert the method's description here.
 * Creation date: (24/06/02 11:43:49)
 * @param io java.lang.String
 */
void convertedUCLwriter(String io);}
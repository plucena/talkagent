package br.usp.talkagent.ucl.converter.parser;

/**
 * Insert the type's description here.
 * Creation date: (19/06/02 18:29:37)
 * @author: 
 */

import java.util.*;


public class SentenceParser2 {
	
	public String performative;
	public Vector Options;	
	private String ttHost;	
	private	UclLanguage ucl_message;


/**
 * ParseSentence constructor comment.
 */

public SentenceParser2(String tthost) {
	super();
	this.ttHost = tthost;
	Options = new Vector(10);
}


/**
 * Insert the method's description here.
 * Creation date: (10/07/02 14:00:16)
 */
public void generateUCL(String option, String io_file) {

	String op;	
	
		op = option;	    
		
		int i_op = Integer.parseInt(op);

		

		try
		{
		//Choose message
		ucl_message.setSentenceTT(i_op);

		//Temporal
		//String s_messageTTbefore = ucl_message.getSentenceTT();
		// System.out.println( "=>Debug : "+s_messageTTbefore );

		//Take the attributes of each concept
		ucl_message.takeAttOfConcept(i_op);

		//Generate de UCL Message based in XML
		ucl_message.convertTTtoUCL();

		//Write
		ucl_message.convertedUCLwriter(io_file);

		//Open file and show
		//------------------

		//DEconverter
		// String s_messageTTafter = ucl_message.deconvertUCLtoTT();

		//Temporal
		// System.out.println( "=>Debug : "+s_messageTTafter );

		//DEconverter message
		//String s_english = ucl_message.deconvertTTtoLN("z");
		//String s_english = ucl_message.deconvertTTtoLN();
//		String s_french = ucl_message.deconvertTTtoLN("y");

//		System.out.print("Sentence: ");
//		System.out.println(s_english);
		/*
		System.out.println( "French :" );
		System.out.println( s_french );
				 */
		System.err.println("DEBUG@UCL - Resulting UCL file was writen successfully");

		//Close session
		//ucl_message.closeSession();

		/*************************************/
		//   Generates ucldoc                 //

		// xmlreader.read("XMLFILENAME".
		// xmlreader.generateUclDoc();

	}
	catch (Exception e)
	{
		System.out.println("=== Error === ");
		e.printStackTrace();
	}

	}public void Understand(String sentence, String io_file) throws Exception {

	String op, tt_host;
	tt_host = this.ttHost;
	String Sentence = sentence;
		
	try
	{
		// creates new ucl converter object .
		ucl_message = new UclLanguageImpl(tt_host, io_file);
		ucl_message.init();

		//set sentence in Natural Language
		op = Sentence;

		if (op.length() == 0)
			throw new Exception("Invalid Sentence");

		else
			ucl_message.setSentenceLN(op);

		//understood
		Vector v_understood = ucl_message.understood();

		if (v_understood.size() == 0)
			throw new Exception("Sentence not understood " + sentence);

		for (int i = 0; i < v_understood.size(); i++)
		{
			Options.addElement(v_understood.elementAt(i).toString());
		}

	}

	catch (Exception e)
	{
		System.out.println(e.toString());
	}

}
	
}
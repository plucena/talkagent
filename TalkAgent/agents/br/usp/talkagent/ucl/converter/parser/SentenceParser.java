package br.usp.talkagent.ucl.converter.parser;

/**
 * Insert the type's description here.
 * Creation date: (19/06/02 18:29:37)
 * @author: 
 */

import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.*;


public class SentenceParser {
	
	public java.lang.String performative;
/**
 * ParseSentence constructor comment.
 */
public SentenceParser() {
	super();
}
/**
 * Insert the method's description here.
 * Creation date: (19/06/02 18:32:30)
 * @param io java.lang.String
 */
public void parse(String io) throws Exception
{

	BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
	int i_op;

	//Create interface

	String op, tt_host;

	try
	{
		System.out.println("======================================");
		System.out.println("UCL Enconverter - Deconverter v 0.2 \n");
		System.out.print("TT Server: ");
		tt_host = stdIn.readLine();
		// creates new ucl converter object .
		UclLanguage ucl_message = new UclLanguageImpl(tt_host, io);
		System.out.println("======================================\n");

		ucl_message.init();
		//set sentence in Natural Language
		System.out.println("======================================");
		System.out.println("Enter a sentence to be converted:");

		op = stdIn.readLine();

		if (op.length() == 0)	
			 throw new Exception("Invalid Sentence");
		
		else
			ucl_message.setSentenceLN(op);

		//understood
		Vector v_understood = ucl_message.understood();

		if (v_understood.size() == 0)
			throw new Exception("Sentence not understood");  

		System.out.println("\n ============ Choose Option ==============");
		for (int i = 0; i < v_understood.size(); i++)
		{
			System.out.println("<" + i + ">" + v_understood.elementAt(i).toString());
		}

		System.out.print("\nOption:");
		op = stdIn.readLine();
		i_op = Integer.parseInt(op);

		//Choose message
		ucl_message.setSentenceTT(i_op);

		//Temporal
		// String s_messageTTbefore = ucl_message.getSentenceTT();
		// System.out.println( "=>Debug : "+s_messageTTbefore );

		//Take the attributes of each concept
		ucl_message.takeAttOfConcept(i_op);

		//Generate de UCL Message based in XML
		ucl_message.convertTTtoUCL();

		//Write
		ucl_message.convertedUCLwriter(io);

		//Open file and show
		//------------------

		//DEconverter
		// String s_messageTTafter = ucl_message.deconvertUCLtoTT();

		//Temporal
		// System.out.println( "=>Debug : "+s_messageTTafter );

		//DEconverter message
		// String s_english = ucl_message.deconvertTTtoLN("z");
		//String s_english = ucl_message.deconvertTTtoLN();
//		String s_french = ucl_message.deconvertTTtoLN("y");

//		System.out.print("Sentence: ");
//		System.out.println(s_english);
		/*
		System.out.println( "French :" );
		System.out.println( s_french );
				 */
		System.out.println("\n * Resulting UCL file was writen successfully");

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

}
}
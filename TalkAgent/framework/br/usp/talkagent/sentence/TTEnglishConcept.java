package br.usp.talkagent.sentence;

/**
 * @author Percival Lucena
 */

import br.usp.talkagent.util.*;
import com.signiform.tt.*;
import java.util.*;

public class TTEnglishConcept 
{

String concept;
String EnglishValue;
String ttServer;
TTConnection tt;
 
public TTEnglishConcept(String concept)
{
	this.concept = concept;
	PrefReader prefs = new PrefReader();
	this.ttServer = prefs.getTTHost();	
}

public String parse()
{
String concept;
tt =null;
	try
	{	
		String host = ttServer;
		TTConnector ttc = new TTConnector(host);
		tt = ttc.connect();
		concept = tt.generate(String.valueOf(TT.F_ENGLISH),TT.stringToObject(this.concept));
		if(concept.startsWith("An"))
		{
			EnglishValue = concept.substring(3,concept.length()-1);
		}
		else
		{
			if(concept.startsWith("A"))	
			{
				EnglishValue = concept.substring(2,concept.length()-1);
			}
			else
			{
				EnglishValue = concept;
			}
		}					
		tt.close();
	}
	catch(Exception e)	
	{
		e.printStackTrace();
	}
	
	return EnglishValue;
}


}

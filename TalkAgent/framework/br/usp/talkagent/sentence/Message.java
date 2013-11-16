package br.usp.talkagent.sentence;

/**
 * @author Percival Lucena
 */


import java.util.*;
import java.io.*;

public class Message implements Serializable 
{


private Vector Sentences;


public Message()
{
	super();	
	Sentences = new Vector(1);
}


public void addSentence(Sentence sentence)
{
	this.Sentences.addElement(sentence);
}

public Sentence getSentence(int sentenceNumber)
{
	return (Sentence) this.Sentences.elementAt(sentenceNumber);
}

public String printHTML()
{
	String sentences="";
	int i;
	
	for(i=0;i<this.Sentences.size();i++)
	{
		sentences += ((Sentence) Sentences.elementAt(i)).printHTML();	
	}
	return sentences; 		
}

}

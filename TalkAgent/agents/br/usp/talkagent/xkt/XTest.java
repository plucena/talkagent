package br.usp.talkagent.xkt;


public class XTest extends Object 
{
	
		
public static void main(String args[]) throws Exception
{
	

 	XktServer xkts = new XktServer();
	xkts.init();
	// xkts.setKB("path/filename")
	xkts.register();
	xkts.run();
	//System.err.println(args[0]);
}

}
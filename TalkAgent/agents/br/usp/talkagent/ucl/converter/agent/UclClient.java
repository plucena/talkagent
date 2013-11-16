package br.usp.talkagent.ucl.converter.agent;

import java.net.Socket;
import br.usp.talkagent.basicagent.AmsClient;
import br.usp.talkagent.ucl.*;
import br.usp.talkagent.ucl.converter.parser.*;
import java.net.*;

/**
 * @author: Percival Lucena
 */

public class UclClient 
{
private Socket connection;
private String conversationID;
private String tt_host;
private AmsClient amsclient;
private String io_file;
private UclParser ucl;


/**
*/
public UclClient(Socket connection, String ConversationID, String tt_host, String io_file, AmsClient amsclient) 
{
	super();
	this.connection = connection;
	this.conversationID = ConversationID;
	this.tt_host = tt_host;
	this.io_file = io_file;
	this.amsclient = amsclient;
	this.run();   
}


/**
 */
public String getCID() 
{
	return conversationID;
}

/**
*/
private void run()
{	
	ucl = new UclParser(this.connection, tt_host, amsclient, conversationID);
	ucl.setIOFile(this.io_file);
	ucl.parse();
}


/**
 */
public void sendDataToClient(String response) 
{
	this.ucl.sendResponse(response);
}


}

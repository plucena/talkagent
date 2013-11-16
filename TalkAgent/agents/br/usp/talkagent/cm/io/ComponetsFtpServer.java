package br.usp.talkagent.cm.io;

/**
* @author Percival Lucena 
*/

import ranab.server.ftp.FtpServer;


public class ComponetsFtpServer  
{
	
	// starts an FTP Server which recieves new components
	// (Java beans packed as jars) into the system
	public ComponetsFtpServer()
	{
		try
		{
			FtpServer ftp = new FtpServer("../db/ftpd.conf");
			ftp.run();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
}	
}

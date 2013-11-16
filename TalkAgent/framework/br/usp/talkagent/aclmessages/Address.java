package br.usp.talkagent.aclmessages;

/**
 * @author Percival Lucena
 * 
 */

public class Address 
{
	
private String IP;
private int port;

public Address()
{
	super();	
}

public Address(String IP, int port)
{
	this.IP = IP;
	this.port = port;
}		
	
/**
 * Returns the iP.
 * @return String
 */
public String getIP() {
	return IP;
}

/**
 * Returns the port.
 * @return int
 */
public int getPort() {
	return port;
}

/**
 * Sets the iP.
 * @param iP The iP to set
 */
public void setIP(String iP) {
	IP = iP;
}

/**
 * Sets the port.
 * @param port The port to set
 */
public void setPort(int port) {
	this.port = port;
}

}
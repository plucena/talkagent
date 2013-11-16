package br.usp.talkagent.basicagent;

import java.io.*;
/**
 * @author root
 */

public class AgentDescriptor implements Serializable
{
	private String name;
	private String address;
	private boolean running;

	public AgentDescriptor()
	{
		super();
	} 

	/**
	 * Returns the address.
	 * @return String
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Returns the name.
	 * @return String
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the running.
	 * @return boolean
	 */
	public boolean isRunning() {
		return running;
	}

	/**
	 * Sets the address.
	 * @param address The address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * Sets the name.
	 * @param name The name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Sets the running.
	 * @param running The running to set
	 */
	public void setRunning(boolean running) {
		this.running = running;
	}

}

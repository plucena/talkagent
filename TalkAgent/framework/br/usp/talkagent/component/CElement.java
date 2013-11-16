package br.usp.talkagent.component;

/**
 * @author root
 *
 */
import java.io.*;
import java.util.*;

public class CElement implements Serializable
{

	private String Concept;
	private String Component;
	private String Method;
	private String Param;
	private int numberofparam;
	private int paramnumber;
	private String Type;
	
	public CElement()
	{
		super();		
	}
	
	public CElement(String concept, String bean, String method, String param)
	{
		super();
		this.Concept = concept;
		this.Component = bean;
		this.Method = method;
		this.Param = param;		
	}
	
	/**
	 * Returns the component.
	 * @return String
	 */
	public String getComponent() {
		return Component;
	}

	/**
	 * Returns the concept.
	 * @return String
	 */
	public String getConcept() {
		return Concept;
	}

	/**
	 * Returns the method.
	 * @return String
	 */
	public String getMethod() {
		return Method;
	}
	
	
	public String getMethodName() 
	{
		String id = null;	
		StringTokenizer st = new StringTokenizer(Method, " ", true);
		while(st.hasMoreElements())	
		{
			id = st.nextToken();
		}
		return id;				
	}
	

	/**
	 * Returns the param.
	 * @return String
	 */
	public String getParam() {
		return Param;
	}


	public String getParamName()
	{
		String id = null;	
		StringTokenizer st = new StringTokenizer(Param, " ", true);
		while(st.hasMoreElements())	
		{
			id = st.nextToken();
		}
		return id;
	}


	/**
	 * Sets the component.
	 * @param component The component to set
	 */
	public void setComponent(String component) {
		Component = component;
	}

	/**
	 * Sets the concept.
	 * @param concept The concept to set
	 */
	public void setConcept(String concept) {
		Concept = concept;
	}

	/**
	 * Sets the method.
	 * @param method The method to set
	 */
	public void setMethod(String method) {
		Method = method;
	}

	/**
	 * Sets the param.
	 * @param param The param to set
	 */
	public void setParam(String param) {
		Param = param;
	}
	
	public boolean isMethod()
	{
		if((this.Param==null)&&(this.Method!=null)&&(this.Component!=null))
			return true;
		else
			return false;
	}
	
	public boolean isParam()
	{
		if((this.Param!=null)&&(this.Method!=null)&&(this.Component!=null))
			return true;
		else
			return false;
	}
	/**
	 * Returns the type.
	 * @return String
	 */
	public String getType() {
		return Type;
	}

	/**
	 * Sets the type.
	 * @param type The type to set
	 */
	public void setType(String type) {
		Type = type;
	}

	
	
	/**
	 * Returns the numberofparam.
	 * @return int
	 */
	public int getNumberofparam() {
		return numberofparam;
	}

	/**
	 * Returns the paramnumber.
	 * @return int
	 */
	public int getParamnumber() {
		return paramnumber;
	}

	/**
	 * Sets the numberofparam.
	 * @param numberofparam The numberofparam to set
	 */
	public void setNumberofparam(int numberofparam) {
		this.numberofparam = numberofparam;
	}

	/**
	 * Sets the paramnumber.
	 * @param paramnumber The paramnumber to set
	 */
	public void setParamnumber(int paramnumber) {
		this.paramnumber = paramnumber;
	}

}

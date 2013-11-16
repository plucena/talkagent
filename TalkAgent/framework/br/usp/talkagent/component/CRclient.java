package br.usp.talkagent.component;

/**
 * Component Repository Manager Client
 * Allow App to Upload New Components to CR
 * and also to get info from existing components on the system 
 */

import com.enterprisedt.net.ftp.*;
import br.usp.talkagent.basicagent.*;
import br.usp.talkagent.aclmessages.*;
import fipaos.ont.fipa.fipaman.AgentID;
import br.usp.talkagent.component.*; 
import java.util.*;
import java.io.*;

public class CRclient extends AgtConnector 
{

	public CRclient(AmsClient ams, AgentID Sender)
	{
		super(ams, Sender, "sharp_component_manager");		
	}

	public void upload(String filename)
	{
	AmsClient ams;
	ams = this.getAmsclient();
	
		if(ams !=null)
		{
			Address addr = this.getRecieverAddr();			
			TAFTP ftp = new TAFTP(addr.getIP(),5035);
			ftp.transfer(filename);
			File file = new File(filename);
			String rcp  = "AddComponent:";
			rcp += file.getName();								
			this.Connect();
			this.SendString("ACL_REQUEST",rcp);
			this.disConnect();			
		}
		else
			System.err.println("Error Invalid AMS Client");
	}
	
	
	public Vector getComponentList()
	{
		String rcp  = "ListComponents";
		this.Connect();
		this.SendString("ACL_REQUEST",rcp);
		Vector response = (Vector) this.ReadObject();		
		this.disConnect();		 	
		return response;
	}
	
	
	public void DeleteComponent(String componentName)
	{
		String rcp  = "DeleteComponent:" + componentName;
		this.Connect();
		this.SendString("ACL_REQUEST",rcp);			
		this.disConnect();	 	
	}
	
	public void Associate(CElement cel)	
	{
		String rcp  = "Associate";
		rcp+= cel.getConcept() + ",";
		rcp+= cel.getComponent() + ",";
		rcp+= cel.getMethod();  
		if(cel.getParam()!=null)	
			rcp+= "," + cel.getParam();
		this.Connect();
		this.SendString("ACL_REQUEST",rcp);
		this.disConnect();
	}
	
	
	public void Dissociate(CElement cel)
	{
		String rcp  = "Dissociate";
		rcp+= cel.getConcept() + ",";
		rcp+= cel.getComponent() + ",";
		rcp+= cel.getMethod();  
		if(cel.getParam()!=null)	
			rcp+= "," + cel.getParam();
		this.Connect();
		this.SendString("ACL_REQUEST",rcp);
		this.disConnect();
	}
	
	
	public String runMethod(String component, String Method, Vector params)
	{
		if((component!=null)&&(Method!=null))
		{
			int i;
			String rcp  = "Execute:";
			rcp+=component;
			rcp+=","+Method;
			if(params!=null)
			{			 		 	
				for(i=0;i<params.size();i++)
				{	
					rcp+=","+params.elementAt(i);
					//System.err.println(params.elementAt(i));
				}		
				//if(params.size()==0)
				//	rcp+=",null";
			}
			/*else 
				rcp+=",null"; */				
		 	this.Connect();
			this.SendString("ACL_REQUEST",rcp);
			String response = (String) this.ReadObject();	
			this.disConnect();
			return response;
		}
		else
			System.err.println("ERROR - RUNNING COMPONENT METHOD - INVALID INFO");
			return null;
	}
	
	
	
	public String runMethod(String component, String Method, Vector params, String assocparam)
	{
		if((component!=null)&&(Method!=null))
		{
			int i;
			String rcp  = "Execute:";
			rcp+=component;
			rcp+=","+Method;
			if(params!=null)
			{			 		 	
				for(i=0;i<params.size();i++)
				{	
					rcp+=","+params.elementAt(i);					
				}		
				//if((params.size()==0)&&(!Method.endsWith("null")))
				//	rcp+=",null";
			}
			if((assocparam!=null)&&(params==null)&&(!assocparam.equalsIgnoreCase("null")))
			{			
				rcp+=",null";
			}
			else if((assocparam!=null)&&(!assocparam.equalsIgnoreCase("null")) && (params!=null) && (params.size()==0) )			
				rcp+=",null";
								
		 	this.Connect();
			this.SendString("ACL_REQUEST",rcp);
			String response = (String) this.ReadObject();	
			this.disConnect();
			return response;
		}
		else
			System.err.println("ERROR - RUNNING COMPONENT METHOD - INVALID INFO");
			return null;
	 }
   	
	
}

package br.usp.talkagent.component;

/**
 * @author Percival Lucena 
 */

import sun.beanbox.*;  
import java.beans.*;
import java.util.*;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import br.usp.talkagent.component.*;
import org.ozoneDB.OzoneObject;


public class ComponentImpl extends OzoneObject implements Component 
{
	
private String component;
private String filename;
private String Ancestor;
private CompLink clk;
private String beanName;
private String fileName;

public ComponentImpl()
{	
	this.clk = new CompLink();
} 	


public void setComponentInfo_update(String filename)
{
try
	{
		this.filename = filename;
		int i,j,size;
		JarInfo info;
		String name;
		String returntype;
		Method method;
		CMethod cmt;
		Modifier mod = new Modifier();
		int mf;
		BeanDescriptor descriptor;				
		MethodDescriptor md[];
		CParameter param;
		JarLoader jl;
		try
		{
			 jl = new JarLoader(this.filename);
		}
		catch(Exception e)
		{
			System.err.print("Invalid component " + this.filename);
			jl = null;						
			//return null;
		}
		if(jl!=null)
		{
			info = jl.loadJar();
			BeanInfo bi = info.getBeanInfo(0);
			md =  bi.getMethodDescriptors();
			descriptor =  bi.getBeanDescriptor();
			this.beanName = descriptor.getName();
			this.Ancestor = descriptor.getBeanClass().getSuperclass().getName();
			size = md.length;
			for(i=0;i<size;i++)
			{			
		 	 	method = md[i].getMethod();			 
			 	mf = method.getModifiers();
			 	if(mod.isPublic(mf))
			 	{
			 		name = md[i].getName();
			 		if(validname(name))
			 		{ 
			 			cmt = new CMethod();
			 			cmt.setName(name);			 		
			 			returntype = method.getReturnType().getName();
			 			cmt.setReturntype(returntype);
			 			cmt.setPermission("public");
			 			Class[] parameterTypes = method.getParameterTypes();			 		
			 			for(j=0; j<parameterTypes.length; j++)
			 			{			 		
			 				param = new CParameter();
			 				param.setType(parameterTypes[j].getName());
			 				param.setName("param"+j);		 			
			 				cmt.addParameter(param);			 			
			 			}			 				 		
			 			clk.addMethod(cmt);
			 			clk.setName(this.beanName);
			 			clk.setAncestor(this.Ancestor);			 		
			 		}
			 	} 	
			}
		//return clk;
		}		 		
	}	
	catch(Exception e)
	{
		e.printStackTrace();
		//return null;
	}
	//return null;
}


private boolean validname(String name)
{
	if(!name.equalsIgnoreCase("wait"))
	if(!name.equalsIgnoreCase("toString"))
	if(!name.equalsIgnoreCase("getClass"))
	if(!name.equalsIgnoreCase("equals"))
	if(!name.equalsIgnoreCase("hashCode"))
	if(!name.equalsIgnoreCase("notify"))
	if(!name.equalsIgnoreCase("notifyAll"))
	return true;

return false;
}


public String toString()
{
	CMethod method;
	int i;
	String aux = this.beanName + "\n";; 
	aux += "Ancestor:" + this.Ancestor + "\n";
	aux += "Methods:" + "\n";
	for(i=0;i<clk.getNumeberofMethods();i++)
	{
		method = clk.getMethod(i);
		aux += method.toString() + "\n";
	}
	return aux;
}	
	

/* Instantatiate bean from a JAR File 
 * (It's supposed that the Jar file has only one bean)  
 * */
public void execute(String MethoodName, Vector Parameters)
{
	try
	{
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
}


/**
 * Returns the ancestor.
 * @return String
 */
public String getAncestor() {
	return Ancestor;
}

/**
 * Returns the beanName.
 * @return String
 */
public String getBeanName() {
	return beanName;
}


public CMethod getMethod(String methodName)
{
	return this.clk.getMethod(methodName);
}


	
public void addMethod(CMethod method) 
{
	this.clk.addMethod(method);
}


public Vector getMethods() 
{
	return this.clk.getMethods();	
}


public void setAncestor_update(String Ancestor) 
{
	this.Ancestor = Ancestor;
}


public void setBeanName_update(String beanName) 
{
	this.beanName = beanName;
}

public void AssociateConceptToMethod_update(String concept, String method)
{
	CMethod mth = this.clk.getMethod(method);
	if(mth!=null)
		mth.associateConcept(concept);
	else
		System.err.println("Failed to create Association " + concept + " -> " + method);
}


public void DissociateConceptToMethod_update(String concept, String method)
{
	CMethod mth = this.clk.getMethod(method);
	if(mth!=null)
		mth.dissociateConcept(concept);		
	else
		System.err.println("Failed to Dissociate " + concept + " -> " + method);
}


public void AssociateConceptToParam_update(String concept, String method, String param)
{
	CMethod mth = this.clk.getMethod(method);
	if(mth!=null)
	{
		CParameter pmt = mth.getParameter(param);
		if(pmt!=null)
				pmt.associateConcept(concept);
		else
				System.err.println("Failed to create Association " + concept + " -> " + param);
	}
	else
		System.err.println("Failed to create Association " + concept + " -> " + param);				
}


public void DissociateConceptToParam_update(String concept, String method, String param)
{
	CMethod mth = this.clk.getMethod(method);
	if(mth!=null)
	{
		CParameter pmt = mth.getParameter(param);
		if(pmt!=null)
				pmt.dissociateConcept(concept);
		else
				System.err.println("Failed to create Association " + concept + " -> " + param);
	}
	else
		System.err.println("Failed to create Association " + concept + " -> " + param);				

}



/**
 * Returns the clk.
 * @return CompLink
 */
public CompLink getComponentInfo() 
{
	return clk;
}

/**
 * Sets the clk.
 * @param clk The clk to set
 */
public void setComponentInfo_update(CompLink clk) {
	this.clk = clk;
}

/**
 * Returns the fileName.
 * @return String
 */
public String getFileName() {
	return fileName;
}

/**
 * Sets the fileName.
 * @param fileName The fileName to set
 */
public void setFileName_update(String fileName) 
{
	this.fileName = fileName;
}

}


package br.usp.talkagent.cm.db;

import java.beans.BeanDescriptor;
import java.beans.BeanInfo;
import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.Vector;
import org.ozoneDB.ExternalDatabase;
import sun.beanbox.JarInfo;
import sun.beanbox.JarLoader;
import br.usp.talkagent.cm.io.FileManager;
import br.usp.talkagent.component.CElement;
import br.usp.talkagent.component.CMethod;
import br.usp.talkagent.component.CParameter;
import br.usp.talkagent.component.CompLink;
import br.usp.talkagent.component.Component;
import br.usp.talkagent.component.ComponentImpl;
import br.usp.talkagent.kb.XktClient;


/** 
 * @author Percival Lucena
 */

public class CRDB
{

private ExternalDatabase db;
private CIndex cindex;
private XktClient xktc; 

public String crRepository = "../cr/repository/";

	public CRDB(String server, XktClient xktc)
	{
		try
		{
			db = ExternalDatabase.openDatabase( "ozonedb:remote://" + server + ":3333" );
			db.reloadClasses();
			this.cindex = (CIndex) db.objectForName("CIndex");
			this.xktc = xktc;
    		// test if ConceptRootIndex exists, if it is absent, so create it   	
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
			

	public Component insertComponent(String componentfile)
	{
	try
	{
		Component cpt;
		File directory = new File(this.crRepository);
		String cpath = directory.getPath() + "/" + componentfile;
		ComponentImpl cp = new ComponentImpl();
	 	cp.setComponentInfo_update(cpath);
	 	CompLink cl = cp.getComponentInfo();
	 	if(cl!=null)	 			
	 	{
	 		Component comp = (Component) db.objectForName(cp.getBeanName());
    		if(comp!=null) // comp already exists, should be recreated
    		{
    			System.err.println("Warning: Overwriting Component Information: " + cp.getBeanName());
    			db.deleteObject(comp);
    		} 				 				
	 		cpt = this.insertComponent(cp);
	 		cpt.setFileName_update(componentfile);	 				
	 		cindex.addComponent_update(cp.getBeanName());
	 		return cpt;
	 	}
	 	else
	 	{
	 		System.err.println("ERROR@CM - Inserting component " + componentfile + " - Couldn't get component info");
	 	}	
	 }
	 catch(Exception e)
	 {
	 	e.printStackTrace();
	 }
	 return null; 		 								
	}

			
	public Component insertComponent(ComponentImpl cpt)
	{
		try
		{
			
			Component cp = (Component)(db.objectForName(cpt.getBeanName()));
			if(cp==null)
			{
				Component comp = (Component) (db.createObject(ComponentImpl.class.getName(),0,cpt.getBeanName()));
				String beanName = cpt.getBeanName();				
				if(beanName!=null)
				{
					comp.setBeanName_update(beanName);
					comp.setComponentInfo_update(cpt.getComponentInfo());
					comp.setAncestor_update(cpt.getAncestor());
					System.err.println("DEBUG@CM - Component " + cpt.getBeanName() + " inserted sucessfully");
				}				
				else
				{
					System.err.println("ERROR@KM - Couldn find component name ");
				}	
				return comp; 
			}
			else
				System.err.println("ERROR@CM - Inserting Component - Component "  + cpt.getBeanName() + " already exists");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}


	public void deleteComponent(ComponentImpl cpt) 
	{
	int i,j,k,l;
	CMethod mt;
	CParameter param;
	String cname;
		
		try
		{	
			if(cpt!=null)
			{
				cname = cpt.getBeanName();
				System.err.println("Deleting component " + cname);		 
				Component comp = (Component)(db.objectForName(cpt.getBeanName()));			
				// for each method shold check if there are links
				Vector methods = comp.getMethods();
				for(i=0;i<methods.size();i++)
				{
					mt = ((CMethod) methods.elementAt(i));
					Vector mlinks = mt.getAssociatedConcepts();
					if((mlinks!=null)&&(mlinks.size()>0))
					{
						for(j=0;j<mlinks.size();j++)
						{						 
							System.err.println("* Unlinking " + cname + ":" + mt.getName() + " -> " + mlinks.elementAt(j) );
							CElement cel = new CElement();
							cel.setComponent(cname);
							cel.setConcept((String)mlinks.elementAt(j));
							cel.setMethod(mt.getName());
							cel.setType("method");
							xktc.DissociateComponent(cel);
						}
						// for each method param verifies if there a link
						int nparam = mt.getParametersNumber();
						for(k=0;k<nparam;k++)
						{
							param = mt.getParameter(k);
							Vector plinks = param.getAssociatedConcepts();
							if((plinks!=null)&&(plinks.size()>0))
							{
								for(l=0;l<plinks.size();l++)
								{									
									System.err.println("* Unlinking " + cname + ":" + mt.getName() + ":"  + param.getName() + " -> " + plinks.elementAt(l) );
									CElement cel = new CElement();
									cel.setComponent(cname);
									cel.setConcept((String)plinks.elementAt(l));
									cel.setMethod(mt.getName());
									cel.setParam(param.getName());
									cel.setType("param");									
									xktc.DissociateComponent(cel);											
								}
							} 
						}
					}
				}							
			// detete from Ozone
			System.err.println("DEBUG@CM - Deleting index entry");
			cindex.removeComponent_update(comp.getBeanName());
			String compfile = comp.getFileName();
			System.err.println("DEBUG@CM - Deleting component from CKB");
			db.deleteObject(comp);			
			//delete file phisicaly;
			System.err.println("DEBUG@CM - Deleting file: " + compfile);
    		FileManager fm = new FileManager("../cr/repository");
    		fm.Delete(compfile);
    			    				
    		} 
    		else 
    		{
     			System.out.println( "Component removal failed - Component not found: " + cpt.getBeanName());
    		}  			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	
	public void initDB() 
	{
		File directory;     	   				// File object referring to the directory.
   		String[] files;        					// Array of file names;    	
    	Vector jarfiles = null;					// Files which are actually *.jar
    	int i;  	
    	Component comp;
    	
    	try
    	{
    		// intit cindex
    		System.err.println("WARNING - RESETING COMPONENT DATABASE. ERASING ALL LINKS");
    		cindex = (CIndex) db.objectForName("CIndex");
    		if(cindex!=null)    		
    			db.deleteObject(cindex);    		
    		cindex = (CIndex) (db.createObject(CIndexImpl.class.getName(),0,"CIndex"));
    		cindex.init_update();
    		  	
        	directory = new File(this.crRepository);
    		if (directory.isDirectory() == false) 
    		{
    			if (directory.exists() == false)
        			System.err.println("There is no such directory!");
        		else
            		System.err.println("That file is not a directory.");
        	}
    		else 
    		{
    			files = directory.list();
    			jarfiles = new Vector(1);    	
    			for (i = 0; i < files.length; i++)
    			{
    				if(files[i].endsWith(".jar"))
    					jarfiles.add(files[i]);
    			}          
    		}   		
        	for(i=0;i<jarfiles.size();i++)
    		{    				 			
	 			comp = this.insertComponent((String)jarfiles.elementAt(i));
	 			if(comp==null) // it's not a valid jar!	 				 	
	 				System.err.println("Invalid component " + (String)jarfiles.elementAt(i));
	 		}
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
	}
   	
   	
	public String ExecuteComponentMethod(String componentName, String MethodName, Vector parametervalues)
	{	
		try
		{	
			System.err.println("Execute" + componentName + ":" + MethodName + ":" + parametervalues.toString());	
			Component cp = (Component) db.objectForName(componentName); 
			if(cp!=null)
			{
				int i=0;		
				String filename = crRepository + cp.getFileName();
				System.err.println("Component: " + cp.getBeanName() + ":" + filename);			
				
				// get method ID
				Vector mtds = cp.getMethods();
				String mtdID = null;
				for(i=0;i<mtds.size();i++) 
				{
					CMethod cmtd = (CMethod) mtds.elementAt(i);					
					if(MethodName.endsWith(cmtd.getName()))					
						mtdID =  cmtd.getName(); 
				}
				System.err.println("ID: " + mtdID);
				if(mtdID!=null)
				{
					JarLoader jl;
					jl = new JarLoader(filename);								
					JarInfo info = jl.loadJar();						
					BeanInfo bi = info.getBeanInfo(0);
					BeanDescriptor beand = bi.getBeanDescriptor();
					System.err.println(beand.getShortDescription());
					Class bclass = beand.getBeanClass();
					// instantiate component main class (as defined in jar)
					// one main class suport now!
					Object obj = bclass.newInstance();																	 

                   // assuming paramNames is not null
                   // int paramSize = paramNames.size(); 
                   int paramSize = parametervalues.size();                   
                   ArrayList params = null;
                   Class[] parameterTypes=null;
                   if (paramSize > 0) {
                        params = new ArrayList(paramSize);
                        for (i=0; i<paramSize; i++) {
                             // Object paramVal = paramValues.get(i); parametervalues
                             Object paramVal = parametervalues.elementAt(i);
                             if (paramVal instanceof String) {
                                  params.add(Class.forName("java.lang.String"));
                             } else if (paramVal instanceof Integer) {
                                  params.add(Class.forName("java.lang.Integer"));
                             } else if (paramVal instanceof Float) {
                                  params.add(Class.forName("java.lang.Float"));
                             } else if (paramVal instanceof Double) {
                                  params.add(Class.forName("java.lang.Double"));
                             } else if (paramVal instanceof Boolean) {
                                  params.add(Class.forName("java.lang.Boolean"));
                             }
                        }
                       // Set method's parameter type
                       parameterTypes = (Class[]) params.toArray(new Class[paramSize]);
                   }                   
                   
                    // Fetch the method for execution                  
                  Method method = bclass.getMethod(mtdID, parameterTypes);
                        
                  // Create parameters to be passed
                   //Object[] methodParams = (Object[]) paramValues.toArray(new Object[paramSize]);
                   Object[] methodParams = (Object[]) parametervalues.toArray(new Object[paramSize]);                   
                    // Execute the method
                  Object returnVal = method.invoke(obj, methodParams);                
                  
                  // if a method is not main() and has return value
                  if (returnVal != null)
                   {
                       String value = returnVal.toString();
                       return value;
                  }			
				}							
			}			
			else
				System.err.println("ERROR@CM - Couldn't find component " + componentName);
	    }
	    catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public ComponentImpl getComponent(String componentName)
	{
		try
		{
			Component comp = null;
			comp = (Component) db.objectForName(componentName);
			// create Component memory object to be transported over the network
			// (instead of ozone-db reference)
			ComponentImpl cmem = new ComponentImpl();
			if(comp!=null)
			{			
				String compName = comp.getBeanName();
				if(compName!=null)
				{				
					cmem.setBeanName_update(compName);
					cmem.setComponentInfo_update(comp.getComponentInfo());				
					cmem.setAncestor_update(comp.getAncestor());
				}
				else			
					cmem.setBeanName_update("Couln't retrieve component info " + componentName);
			}
			else
			{
				CompLink error = new CompLink();
				error.setName("Invalid Component : " + componentName);	 		
				cmem.setComponentInfo_update(error);
			}
			return cmem;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}


	public Vector listComponents() 
	{
		Vector comp = new Vector(1);
		Vector elements =cindex.getElements();	 
	 	int size = elements.size();
	 	int i;
	 	Component cp;	 
	 	//System.err.println("DEBUG@CM: - Component info - " + size + " components");
	 	for(i=0;i<size;i++)
	 	{
	 		//cp = (Component) this.getComponent(cindex.getElement(i));
	 		String index = (String)elements.elementAt(i);
	 		cp	= (Component) this.getComponent(index); 		
	 		CompLink cl = cp.getComponentInfo();
			//System.err.println("* " + index + ":" + cp.getBeanName());						
	 		comp.add(cp);
	 	}	 		 
	 	return comp; 	
	}


	public Vector getComponents() 
	{
		Vector comp = new Vector(1);
	 
	 	int size = cindex.getElementsNumber();
	 	int i;
	 
	 	for(i=0;i<size;i++)
	 	{
	 		comp.add(this.getComponent(cindex.getElement(i)));
	 	}	 
	 	return comp;	
	}


	public void Associate(String concept, String bean, String method, String param)
	{
		try
		{
			Component comp = null;
			comp = (Component) db.objectForName(bean);
			if(comp!=null)
			{
				String prm = null;
				if(param!=null)
			   		prm = this.clearParam(param);	
				String mtd = this.clearParam(method);														
				if((prm!=null)&&(!param.equalsIgnoreCase("null")) )
					comp.AssociateConceptToParam_update(concept,mtd,prm);											
			 	else
					comp.AssociateConceptToMethod_update(concept,mtd);	
			}
			else
				System.err.println("ERROR@CRM - Can not associate component " + bean + " .  Component does not exist");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	
	public void Dissociate(String concept, String bean, String method, String param)
	{
		try
		{
			Component comp = null;
			comp = (Component) db.objectForName(bean);
			if(comp!=null)
			{
				String prm = null;
				if(param!=null)
			   		prm = this.clearParam(param);	
				String mtd = this.clearParam(method);														
				if((prm!=null)&&(!param.equalsIgnoreCase("null")) )					
					comp.DissociateConceptToParam_update(concept,mtd,prm);															
			 	else
					comp.DissociateConceptToMethod_update(concept,mtd);	
			}
			else
				System.err.println("ERROR@CRM - Can not associate component " + bean + " .  Component does not exist");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	/**
 	* Returns the crRepository.
 	* @return String
 	*/
	public String getCrRepository() 
	{
		return crRepository;
	}

	/**
 	* Sets the crRepository.
 	* @param crRepository The crRepository to set
 	*/
	public void setCrRepository(String crRepository) 
	{
		this.crRepository = crRepository;
	}

	
	/**
	 *  Extracts qualificator from a method or function	 */ 
	private String clearParam(String value)	
	{
		String rvalue=null;	
		StringTokenizer st = new StringTokenizer(value, " ", true);
		while(st.hasMoreElements())
			rvalue = st.nextToken();
		return rvalue;
	}
	

}


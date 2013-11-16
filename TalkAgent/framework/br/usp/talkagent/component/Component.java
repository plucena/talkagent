package br.usp.talkagent.component;

import br.usp.talkagent.component.*;
import java.util.*;
import org.ozoneDB.OzoneRemote;

public interface Component extends OzoneRemote 
{
	public String toString();
	public String getFileName();
	public void setFileName_update(String filename);
	public void execute(String MethoodName, Vector Parameters);	
	public String getAncestor();
	public void setAncestor_update(String Ancestor); 	
	public String getBeanName();	
	public void setBeanName_update(String beanName); 
	public Vector getMethods();
	public CompLink getComponentInfo();	
	public void setComponentInfo_update(CompLink complink); 
	public void setComponentInfo_update(String filename); 
	public CMethod getMethod(String methodName);
	public void AssociateConceptToMethod_update(String concept, String method); 
	public void AssociateConceptToParam_update(String concept, String method, String param);
	public void DissociateConceptToMethod_update(String concept, String method);
	public void DissociateConceptToParam_update(String concept, String method, String param);		
}



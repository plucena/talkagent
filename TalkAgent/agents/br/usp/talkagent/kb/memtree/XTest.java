package br.usp.talkagent.kb.memtree;

/**
 * @author Administrator
 *
 */

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
//import org.apache.xml.serialize.OutputFormat;
//import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class XTest 
{

public static void main(String args[]) throws Exception
{
 mtConcept conr,con,con2;
 mtKB kb;


	kb = new mtKB(); 
	if(args.length>0)
		System.err.println(args[0]);	
		
	System.out.println("Testando MemTree KB");
	
	/* Inserindo Mamiferos */	
	conr = new mtConcept(); 	   		
	conr.setID("Mamiferos");
	kb.insertRoot(conr);
	// macaco, baleia, cachorro, leao 	
   //-----------------------------------------;
	con2 = new mtConcept(); 	   		
	con2.setID("Macaco");    
	kb.insert(con2,conr);
	con = new mtConcept(); 	   		
	con.setID("Macaco Prego");    
	kb.insert(con,con2);
	con = new mtConcept(); 	   		
	con.setID("Mico Leao");	   		 
	kb.insert(con,con2);
	//-----------------------------------------
	con2 = new mtConcept();
	con2.setID("Baleia");    
	kb.insert(con2,conr);
	con = new mtConcept(); 	
	con.setID( "Baleia Orca");
	kb.insert(con,con2);
	con = new mtConcept(); 	
	con.setID("Baleia do Sea World");
	kb.insert(con,con2);
	//-----------------------------------------
	con2 = new mtConcept(); 	
	con2.setID("Leao");    
	kb.insert(con2,conr);

 
	 
	/* Inserindo Repteis */
	// lagarto. cobra...
	conr = new mtConcept(); 
	conr.setID("Repteis");
	kb.insertRoot(conr);
	con2 = new mtConcept(); 	
	con2.setID("Lagarto");    
	kb.insert(con2,conr);   
	con2 = new mtConcept(); 	
	con2.setID("Cobra");    
	kb.insert(con2,conr);        
	
	
		
  	/* Inserindo Aves */ 	
	conr = new mtConcept(); 
	conr.setID("Aves");
	kb.insertRoot(conr);
	 
	/* Inserindo Dinossauros */ 	 	
	conr = new mtConcept(); 
	conr.setID("Dinossauros");
	kb.insertRoot(conr);

	
	/* Inserindo Peixes */	
	conr = new mtConcept(); 
	conr.setID("Peixes");
	kb.insertRoot(conr);



	/* Search and print */
	con = 	new mtConcept();
	con.setID("Macaco Prego");
	System.out.println("Searching " + con.getID());
	con2 = (mtConcept) kb.FindConcept(con);
	if(con2!=null)
	System.out.println("Concept found: "+ con2.getValue());
	else
	System.out.println("Concept not found");

	System.out.println("Printing KBTree \n");
	kb.print();
}



public static void testXML()
{
	try
	{
/*		Element item;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.newDocument();
		Element root = doc.createElement("kb");

		item = doc.createElement("concept");
		item.appendChild(doc.createTextNode("batata"));
		root.appendChild(item);
		

		File fd;
		FileOutputStream outStream;
		ObjectOutputStream objStream;
		fd = new File("/kb.xml");
		outStream = new FileOutputStream(fd);
		objStream = new ObjectOutputStream(outStream);


		OutputFormat format = new OutputFormat(doc);
		format.setLineWidth(80);
		format.setIndenting(true);
		format.setIndent(2);
  		XMLSerializer serializer = new XMLSerializer(objStream, format);
		serializer.serialize(root);		*/
	}
	catch (Exception e)
	{
		e.printStackTrace();
	}
}

}

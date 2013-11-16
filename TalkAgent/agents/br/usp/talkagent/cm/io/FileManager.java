package br.usp.talkagent.cm.io;

/**
 * @author Percival Lucena
 */

import java.io.*;

public class FileManager 
{

private String dir;

	public FileManager(String initDir)
	{
		this.dir = initDir;
	}
	
	
	public void Delete(String fileName) throws IOException {
                
        String filename = this.dir + "/" + fileName;      
        File requestedFile = new File(filename);
               
        // now delete
        if(requestedFile.delete()) 
        {
        	System.err.println("File " + filename + " deleted sucessfully"); 
        }
        else 
        {
           System.err.println("Couldn't delete file " + filename);
        }
     }
}

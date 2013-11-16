package br.usp.talkagent.ucl.converter.parser;

/**
 * Insert the type's description here.
 * Creation date: (05/20/2001 %r)
 * @author: 
 */

 import java.io.*;

 
public class Relations {
/**
 * Insert the method's description here.
 * Creation date: (05/20/2001 %r)
 * @param args java.lang.String[]
 */
public static void main(String[] argv) {
		// Check the arguments
		if (argv.length != 1) {
			System.err.println ("Usage: java relation filename");
			System.exit (1);
		}
		String filename = argv[0];
		File f = new File(filename);
		Relations reader = new Relations();
		reader.parse(f);

	
	}
		void output(String name, String prefix, String line) 
 
	{
	  int startIndex = prefix.length() + 2; // 2=length of ": " after the name
	  String text = line.substring(startIndex);
 
	  System.out.println(name + ": " + text);
 
	}
		/** Parse the input */
	public void parse(File f) 
	{
		try {
			// Get an efficient reader for the file
 
			FileReader r = new FileReader(f);
 
			BufferedReader br = new BufferedReader(r);
			
			//kind of relation
			String relation = "r9=";
			int beginIndex, endIndex;

  			System.out.println("================== Relation of: " + relation+" ==================");

			 
			// Read the file and display it's contents.
			String line;// = br.readLine();
			while (null != (line = br.readLine())) {
	  			beginIndex 			= line.indexOf(relation);
	  			String sub_line1 	= line.substring(beginIndex);
	  			endIndex 			= sub_line1.indexOf("|");

	  			if(endIndex == -1) endIndex = sub_line1.length();
		  		
		  		String sub_line2 	= sub_line1.substring(0, endIndex);
	  			System.out.println(sub_line2);
	  		}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
/**
 * Relations constructor comment.
 */
public Relations() {
	super();
}
}
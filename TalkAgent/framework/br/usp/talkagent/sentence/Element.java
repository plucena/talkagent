package br.usp.talkagent.sentence;

/**
 * @author Pecival Lucena
 *
 */
import java.io.*;
import com.signiform.tt.*;

public class Element implements Serializable
{
	
private String type;
private String value;
private boolean italic;
private boolean bold;

public Element()
{
	super();
}

public Element(String type, String value)
{
	super();
	this.type = type;
	this.value = value;
}


/**
 * Returns the type.
 * @return String
 */
public String getType() {
	return type;
}

/**
 * Returns the value.
 * @return String
 */
public String getValue() {
	return value;
}

/**
 * Sets the type.
 * @param type The type to set
 */
public void setType(String type) {
	this.type = type;
}

/**
 * Sets the value.
 * @param value The value to set
 */
public void setValue(String value) 
{
	this.value = value;
}


/**
 * Returns the bold.
 * @return boolean
 */
public boolean isBold() {
	return bold;
}

/**
 * Returns the italic.
 * @return boolean
 */
public boolean isItalic() {
	return italic;
}

/**
 * Sets the bold.
 * @param bold The bold to set
 */
public void setBold(boolean bold) {
	this.bold = bold;
}

/**
 * Sets the italic.
 * @param italic The italic to set
 */
public void setItalic(boolean italic) {
	this.italic = italic;
}

public String printHTML()
{
String concept="";
String value;
    	
		if(isItalic())
			concept += " <I> ";
		if(isBold())	    		
    		concept += " <B> ";
    		
    	if(getType().indexOf("concept")>=0) 	
    	{
				TTEnglishConcept ttec = new TTEnglishConcept(getValue());
				value = ttec.parse();
				if(value.startsWith("HASA"))
				{
					concept="has a";
				}
				else
					concept += value;				
   	}
    	else
    		concept += getValue();
    		
   		if(isItalic())
			concept += " </I> ";
		if(isBold())	    		
    		concept += " </B> ";
    	
    	return concept;
}

}

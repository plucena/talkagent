package br.usp.talkagent.component;

/**
 * @author root
 */

import java.util.Vector;
import br.usp.talkagent.component.*;
import java.io.*;

public class CompLink implements Serializable
{

private Vector methods; 
private Vector atributes;
private String name;
private String Ancestor;

public CompLink()
{
	methods = new Vector(1);
} 

public CMethod getMethod(int count)
{
	return (CMethod) this.methods.elementAt(count);
}

public int getNumeberofMethods()
{
	return this.methods.size();
}

public CMethod getMethod(String methodname)
{
int i;
	for(i=0;i<this.methods.size();i++)
	{
		CMethod mt = (CMethod)methods.elementAt(i); 
		if (mt.getName().equalsIgnoreCase(methodname))
			return mt;
	}
	return null;
}

public void addMethod(CMethod method)
{
	this.methods.add(method);
}


/**
 * Returns the ancestor.
 * @return String
 */
public String getAncestor() {
	return Ancestor;
}

/**
 * Returns the name.
 * @return String
 */
public String getName() {
	return name;
}

/**
 * Sets the ancestor.
 * @param ancestor The ancestor to set
 */
public void setAncestor(String ancestor) {
	Ancestor = ancestor;
}

/**
 * Sets the name.
 * @param name The name to set
 */
public void setName(String name) {
	this.name = name;
}

/**
 * Returns the methods.
 * @return Vector
 */
public Vector getMethods() {
	return methods;
}

/**
 * Sets the methods.
 * @param methods The methods to set
 */
public void setMethods(Vector methods) {
	this.methods = methods;
}

}

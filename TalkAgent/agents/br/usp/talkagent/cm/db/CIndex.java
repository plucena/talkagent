package br.usp.talkagent.cm.db;
import java.lang.String;
import java.util.Vector;
import org.ozoneDB.OzoneRemote;

public interface CIndex extends OzoneRemote 
{
	void init_update(); 
	void addComponent_update(String name); 
	void removeComponent_update(String name); 
	Vector getElements();
	int getElementsNumber();
	String getElement(int index);
	boolean containsElement(String name);
}
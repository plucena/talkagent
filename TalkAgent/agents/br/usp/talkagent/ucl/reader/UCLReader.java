package br.usp.talkagent.ucl.reader;

/**
Interface that UCL Readers must implement
Exemples of UCL Readers: FileReader, FipaAclReader, KqmlReader 
*/

public interface UCLReader {

public void generateUclDoc();
public void print();
public void read(String Source);
}
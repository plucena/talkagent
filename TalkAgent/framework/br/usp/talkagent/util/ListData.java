package br.usp.talkagent.util;

/*
Interface that reprsents Objects that can be
added to a list
*/

import java.io.*;

public interface ListData extends Serializable
{

public String getID();
public Object getValue();
public boolean isEqual(ListData value);
public boolean isGreater(ListData value);
public boolean isSmaller(ListData value);


}
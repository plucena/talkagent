package br.usp.talkagent.cm;

/**
 * @author Percival Lucena
 *
 */

import br.usp.talkagent.util.*;

public class XTest 
{
	
public static void main(String[] args)
{
	PrefReader pr = new PrefReader();
	CrmAgent agt = new CrmAgent();	
	if(pr.isRestoreComponentsDB())
		agt.run(true); // boolean == reset Componets KB
	else
		agt.run(false);
}

}

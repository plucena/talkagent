package br.usp.talkagent.useragent;
import java.io.PrintWriter;
import java.util.Vector;
import javax.servlet.http.*;
import br.usp.talkagent.basicagent.*;

public class GetUsrInput extends HttpServlet 
{
 
private java.util.Vector RunningClients;


/**
 *  Process incoming HTTP GET requests 
 * @param request Object that encapsulates the request to the servlet 
 * @param response Object that encapsulates the response from the servlet
 */

public void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException 
{
	try
	{
	run(request, response);
	}
	catch(Exception e)
	{
		writeErrorMessage(response, e);
	}

}



/**
 * Process incoming HTTP POST requests 
 * @param request Object that encapsulates the request to the servlet 
 * @param response Object that encapsulates the response from the servlet
 */
public void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {

	try
	{
	run(request, response);
	}
	catch(Exception e)
	{
		writeErrorMessage(response, e);
	}

}



/**
 * Returns the servlet info string.
 */
public String getServletInfo() 
{

	return super.getServletInfo();

}


/**
 * Initializes the servlet.
 */
public void init() 
{
	RunningClients = new Vector(10);
}


/**
* Process incoming requests for information
*/
public void run(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws Exception 
{

    try
    {
		String sessionID = request.getParameter("hiddenField");
		// sessionID  = Take URL to see if it has session id
		if (sessionID == null)
		{
			sessionID = createSessionID(request);
			RunningClients.addElement(new UserAgent(sessionID));
			this.runClient(request, response, sessionID);
		}
		else
			this.runClient(request, response, sessionID);
    }
    catch(Exception e)
    {	
    	this.writeErrorMessage(response,e);
    	
    	
    }       
}



private String createSessionID(javax.servlet.http.HttpServletRequest request) 
{
	String ID = "";
	ID = request.getRemoteAddr();
	long currentTime = System.currentTimeMillis();
	String Time = "" + currentTime;
	ID = ID + ":" + Time;

	return ID;
}


/**
 * @param response javax.servlet.http.HttpServletResponse
 * @param e java.lang.Exception
 */ 
private void writeErrorMessage(HttpServletResponse response, Exception e) 
{
	if(response==null)
	{ ; // what can i do?		
	}
	else
	{	/*
		if(!e.getMessage().equalsIgnoreCase("java.lang.NullPointerException")) 
		try
		{
			PrintWriter out = response.getWriter();
			out.println("<html>");
			out.println("<body>");
			out.println("<head>");
			out.println("</head>");
			out.println("==Error==: " + e.getMessage() + " <BR><BR>");
			e.printStackTrace(out);
			out.println("</body>");
			out.println("<html>");
		}
		catch (Exception e1)
		{
		//			e1.printStackTrace();
		}*/
	}
}	




/**
*/
private void runClient(javax.servlet.http.HttpServletRequest request, 
	javax.servlet.http.HttpServletResponse response, String SessionID)
{
	int i;
	UserAgent agt;

	for (i = 0; i < RunningClients.size(); i++)
	{
		agt = (UserAgent) RunningClients.elementAt(i);
		String s_id = agt.getsessionID();
		if (s_id != null)
		{
			if (s_id.equalsIgnoreCase(SessionID))
			{
				agt.run(request, response);
			}
		}
		else
		{
			String dbg = "@ERROR - Agent Session ID not found! <BR>";
			dbg = dbg + "Agents Running: ";
			dbg = dbg + Integer.toString(RunningClients.size()) + "<BR>";
			dbg = dbg + "Contacting user agent " + SessionID + "<BR><BR>";
			writeMessage(response, dbg);
		}
	}
}


/**
 * @param response javax.servlet.http.HttpServletResponse
 * @param msg java.lang.String
 */
private void writeMessage(HttpServletResponse response, String msg) 
{
	try
	{
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<body>");
		out.println("<head>");
		out.println("</head>");
		out.println("Message <BR><BR>");
		out.println(msg);
		out.println("</body>");
		out.println("<html>");

	}
	catch (Exception e)
	{

		this.writeErrorMessage(response,e);
	}	
}


}
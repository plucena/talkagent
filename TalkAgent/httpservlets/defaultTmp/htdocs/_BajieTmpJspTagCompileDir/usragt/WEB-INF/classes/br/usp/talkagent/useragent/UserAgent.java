package br.usp.talkagent.useragent;
import java.io.*;
import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import br.usp.talkagent.basicagent.AmsClient;
import fipaos.ont.fipa.fipaman.AgentID;
import java.util.*;
import br.usp.talkagent.aclmessages.addressParser;
import java.net.*;

/**
 * @author: Percival Lucena
 */

class UserAgent 
{
	private UclInterpreterConnector agtc;
	private java.util.Vector UnderstoodOptions;
	private java.lang.String performative;
	private String SelectedOption;
	private int level;	
	private java.lang.String sessionID;
    private boolean error;


/**
 * UserAgent constructor comment.
 */
public UserAgent(String SessionID) 
{
	super();
	this.level = 0;
	this.sessionID = SessionID;
}
   

/**
 * @return java.lang.String
 */
private String createReplyAddr(javax.servlet.http.HttpServletRequest request) 
{
	String addr = request.getRequestURL().toString();
	return addr;
}

 
/**
 * @param response javax.servlet.http.HttpServletResponse
 */
protected void writeInputForm(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) 
{
	try
	{
		response.setContentType("text/html");
		PrintWriter out = response.getWriter(); 
		out.println("<html>");
		out.println("<body>");
		out.println("<head>");
		out.println("<SCRIPT LANGUAGE='JavaScript'>");
		out.println("function help()");
		out.println("{ window.location = '../help.html'; }");
		out.println("function checkAll(field)");
		out.println("{");
		out.println("var found=false; var teste=false;");
		out.println("for (i = 0; i < field.length; i++)");
		out.println("{");
		out.println("test = field[i].checked;");
		out.println("if(test)");
		out.println("found=true;");
		out.println("}");
		out.println("if(!found)");
		out.println("alert('You MUST choose a Context');");
		out.println("else");
		out.println("document.form1.submit();");
		out.println("}");
		out.println("</script>");
		out.println("</head>");
		
		out.println("<form name='form1' method='post' action=''>");
		out.println("<table bgcolor='#eeeeee' width='100%' border='1'>");
		out.println("<tr><td>");
		out.println("<table bgcolor='#eeeeee' width='100%' border='1'>");
		out.println("<tr><td>");			
		out.println("<table width='100%' border='0'>");
		out.println("<tr>");	
		out.println("<td bgcolor='#6666cc'>");		
		out.println("<img src='../img/ta.gif' width='235' height='32'> ");				
		out.println("</td></tr>");		
		out.println("<tr>");
	 	out.println("<td valign='top' bgcolor='#eeeeee'>");                                        
        out.println("<table cellpadding='2' cellspacing='0' border='0'>");
        out.println("<tbody>");
        out.println("<tr>");
        out.println("<td valign='top'>USER AGENT &nbsp;");
        out.println("</td>") ;                
        out.println("<td valign='top'> &nbsp; <A HREF='" + createReplyAddr(request) +"?param=ams'>AMS</A> &nbsp; ");
        out.println("</td>");        
        out.println("</tr>");        
        out.println("</tbody>");                                                  
        out.println("</table>");	
  	 	out.println("</td>") ;	
		out.println("</tr>");
		out.println("<tr><td> </TD></TR>");
		out.println("</tr></td></table>");
		out.println("<tr>");
		out.println("<td colspan='2'>"); 
		out.println("<form name='form1' method='post' action='" +  createReplyAddr(request) + "'>");
		out.println("<BR> <b>Request</b>: <BR>");
		out.println("<textarea name='input' rows='4' cols='40'></textarea>");
		out.println("<p><b>Context</b>: <BR> ");		
		
		out.println("<input type='radio' name='radiobutton' value='ACL_QUERYREF'> <B>Query </B><I>(What, How, Who, When)</I><BR> "); 
		out.println("<input type='radio' name='radiobutton' value='ACL_QUERYIF'> <B>Validate Information</B> <I>(Validate wheather a fact is true or false)</I> <BR> "); 
		out.println("<input type='radio' name='radiobutton' value='ACL_INFORM'> <B>Inform</B> <I>(Teach Talk Agent something new)</I> <BR>"); 
		out.println("<input type='radio' name='radiobutton' value='ACL_REQUEST'> <B>Request Action</B><I> (Ask Talk Agent to do something for you)</I>"); 
		out.println("");
		
	 	out.println("<input type='hidden' name='hiddenField' value='" + sessionID + "'>");
		out.println("<p><input type='button' name='Submit' value='Go' onClick='checkAll(document.form1.radiobutton)'>");
		out.println("<input type='button' name='Submit2' value='Help' onClick='help()'></p>");
		out.println("</form> <br>&nbsp");
		out.println("</td></tr>");		
		out.println("</table>");
		
		out.println("</td></tr>");
		out.println("<tr> <td bgcolor='#eeeeee' valign='top' rowspan='1' colspan='2'>");
		out.println("<font color='#ff0000'> <B> Tip: Sentences should end with <i>.</I> or <I>!</I> or <I>?</i> </B></font> <br>");
		out.println("E.g.: [Query] What is a beer<b>?</b> <br>");
		out.println("E.g.: [Query] Agent, list animals<b>.</b><br>");
		out.println("E.g.: [Inform] &quot;Mango&quot; is a fruit<b>.</b><br>");
		out.println("E.g.: [Validate Information] A camel is an animal<b>.</b><br>");
		out.println("E.g.: [Action] Agent, retrieve  news <b>.</b><br>");
		out.println("E.g.: [Action] What time is it <b>?</b><br>");
		out.println("<A HREF='../help.html'> Help </A>  ");		
		out.println("</td></tr>");	
			
	    out.println("</table>");		 
		out.println("</form>");
		
		out.println("</body>");
		out.println("<html>");
		
	} 
	catch (Exception e)
	{
		//writeMessage(response,e);
	}

}


/**
 * @param response javax.servlet.http.HttpServletResponse
 * @param e java.lang.Exception
 */
private void writeMessage(javax.servlet.http.HttpServletResponse response, Exception e) 
{
if(!e.getMessage().equalsIgnoreCase("java.lang.NullPointerException")) 
	try
	{
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<body>");
		out.println("<head>");
		out.println("</head>");
		out.println("== Error ==: " + e.getMessage() + "<BR><BR>");
		e.printStackTrace(out);
		out.println("</body>");
		out.println("<html>");
	}
	catch (Exception e1)
	{
//		e1.printStackTrace();
	}
}


/**
 * @param response javax.servlet.http.HttpServletResponse
 * @param msg java.lang.String
 */
private void writeMessage(javax.servlet.http.HttpServletResponse response, String msg) 
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
	}

}


/**
 * @param response javax.servlet.http.HttpServletResponse
 */
protected void writeOptions(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
{
	int i;

	try
	{
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<body>");
		out.println("<head>");
		out.println("</head>");
		
		out.println("<table bgcolor='#eeeeee' width='100%' border='1'>");
		out.println("<tr><td>");		
		out.println("<table width='100%' border='0'>");
		out.println("<tr>");	
		out.println("<td bgcolor='#6666cc'>");		
		out.println("<img src='../img/ta.gif' width='235' height='32'>");		
		out.println("</td></tr></table>");		
		out.println("</tr></td>");
		out.println("<tr><td>");		
		out.println("<H2> Understanding your request </H2>");
		out.println("<form name='form2' method='post' action='" + createReplyAddr(request) + "'>"); 
		out.println("What do you mean?: <BR><BR>");
		out.println("<input type='hidden' name='hiddenField' value='" + sessionID + "'>"); 
		if (agtc.error)
			out.println("error parsing sentece: " + agtc.emsg);

		if (UnderstoodOptions.size() == 0)
		{
			out.println("I am sorry. I did not understand it. Please try it again. <BR><BR>"); 
			out.println("<A HREF='" + this.createReplyAddr(request) + "'>Return to talk Agent </A>");
			this.error = true;
			agtc.error = true;
			
		}
		else
		{
			for (i = 0; i < agtc.UnderstoodOptions.size(); i++)
			{
				out.println("<input type='radio' name='radiobutton' value='" + String.valueOf(i) +  "'>");
				out.println(agtc.UnderstoodOptions.elementAt(i).toString());
				out.println("<BR>");
			}

			out.println("<BR> <I> Communication Context: " + performative + "</I>");
			out.println("<BR><BR>");
			out.println("<input type='submit' name='Submit' value='OK'>");
		}
		out.println("<BR><BR>");
		//out.println("<A HREF='./usrinput'> Back </A>");
		out.println("</form>");
		out.println("</tr></td></table>");
		out.println("</body>");
		out.println("<html>");
	}
	catch (Exception e)
	{
		e.printStackTrace();
	}
}


/**
 * @param response javax.servlet.http.HttpServletResponse
 */
protected void writeResults(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) 
{
	try
	{
		String Result = agtc.getResult();									
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<body>");
		out.println("<head>");
		out.println("</head>");
		out.println("<table bgcolor='#eeeeee' width='100%' border='1'>");
		out.println("<tr><td>");
		out.println("<table width='100%' border='0'>");
		out.println("<tr>");	
		out.println("<td bgcolor='#6666cc'>");		
		out.println("<img src='../img/ta.gif' width='235' height='32'>");		
		out.println("</td></tr></table>");
		out.println("</tr></td>");
		out.println("<tr><td>");
		out.println("<H2> Results </H2> <BR>");
		String aux = this.SelectedOption;
		out.println("<B>Request</B>: " + UnderstoodOptions.elementAt(Integer.parseInt(aux)) + "<BR><BR>");		
		out.println("<B>Response</B>: " + Result);
		out.println("<BR><BR>");
		out.println("<A HREF='" + createReplyAddr(request) +  "'> Back to Talk Agent </A>");
		out.println("</tr></td></table>");
		out.println("</body>");
		out.println("<html>");
	}
	catch (Exception e)
	{
		writeMessage(response, e);
	}

}
	


/**
 * @return int
 */
public int getlevel() 
{
	return this.level;
}


/**
 * @return java.lang.String
 */
public String getsessionID() 
{
	return sessionID;
}


/**
 */
public int run(javax.servlet.http.HttpServletRequest request,javax.servlet.http.HttpServletResponse response)
{
	try
	{
	String pr = request.getParameter("param");
	
	if(pr!=null)
    {
		if(pr.equalsIgnoreCase("ams"))
		{
			agtc = new UclInterpreterConnector();
			this.writeAMS(request,response);
			return 1;
		}
		else
		if(pr.equalsIgnoreCase("cfg"))
		{
			this.writeCFG(request,response);	
			return 1;
		}
    }
        // USER AGENT  
		switch (this.level)
		{

			case 0 :
				{
					this.writeInputForm(request,response);
					this.level++;
					if(agtc.error)
					{
						this.error=true;
						this.writeMessage(response, "Error while processing request");
						this.level=0;
					}
					break;
				}

			case 1 :
				{
					String msgContent = request.getParameter("input");
					performative = request.getParameter("radiobutton");
					agtc = new UclInterpreterConnector();
					agtc.sendMessage(msgContent, performative);
					agtc.readUclOptions();

					while (!agtc.ucl_ok)
					{} // sits waiting...

					this.UnderstoodOptions = agtc.UnderstoodOptions;
					this.writeOptions(request,response);
					this.level++;
					if(agtc.error)
					{
						this.error=true;
						this.writeMessage(response, "Error while processing request");
						this.level=0;
						
					}					
					break;
				}

			case 2 :
				{
					this.SelectedOption = request.getParameter("radiobutton");
					this.SelectedOption = this.SelectedOption.substring(0,1);
					String perf = "ACL_INFORM";
					String optmsg = this.SelectedOption;

					if (agtc == null)
					{
						writeMessage(response, "Error UCL Agent Connector is dead! ");
						throw new Exception("Error UCL Agent Connector is dead! ");
   					}
					agtc.sentmessage = false;
					agtc.sendMessage(optmsg, perf);
					this.writeResults(request, response);
					this.level=0;
					if(agtc.error)
					{
						this.error=true;
						this.writeMessage(response, "Error while processing request");
						this.level=0;
					}
					break;
				}
			default:
			{
				this.error=true;
				this.writeMessage(response, "@ERROR: Invalid option sent to User Agent " + level);
				break;		
			}
				
		}
	}
	catch (Exception e)
	{
		writeMessage(response, e);
	}
	return 0;
}


protected void writeAMS(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) 
{
AmsClient ams;
Vector agents = null;
AgentID agent;
addressParser adr;

int i;

boolean amserror=false;
	try
	{
		
		if(agtc!=null)
		{
			ams = agtc.getAMS();
			if(ams!=null)
				agents = ams.listAgents();
			else
				amserror=true;
		}
		else
			amserror=true;
		
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter(); 
		out.println("<html>");
		out.println("<body>");
		out.println("<head>");
		out.println("</head>");
    	out.println("<SCRIPT LANGUAGE='JavaScript'>");
    	out.println("</SCRIPT>");


		out.println("<form name='form1' method='post' action=''>");		
		out.println("<table bgcolor='#eeeeee' width='100%' border='1'>");
		out.println("<tr><td>");	
		out.println("<table width='100%' border='0'>");
		out.println("<tr>");	
		out.println("<td>");		
		out.println("<table width='100%' border='0'>");
		out.println("<tr>");	
		out.println("<td bgcolor='#6666cc'>");		
		out.println("<img src='../img/ta.gif' width='235' height='32'>");		
		out.println("</td></tr></table>");				
		out.println("</td></tr>");
		
        out.println("<tr>");
	 	out.println("<td valign='top' bgcolor='#eeeeee'>");                                        
        out.println("<table cellpadding='2' cellspacing='0' border='0'>");
        out.println("<tbody>");
        out.println("<tr>");
        out.println("<td valign='top' bgcolor='#eeeeee'><A HREF='" + createReplyAddr(request) +"'> USER AGENT &nbsp; </A>");
        out.println("</td>") ;                
        out.println("<td valign='top' bgcolor='#eeeeee'> &nbsp; AMS</A> &nbsp; ");
        out.println("</td>");        
        out.println("</tr>");
        out.println("</tbody>");                                                  
        out.println("</table>");	
  	 	out.println("</td>") ;	
		out.println("</tr>");
		
		out.println("<tr>");
		out.println("<td colspan='2'>"); 
		out.println("<BR> <b>AMS - Agent Managment System</b>: <BR><BR>");

		if(!amserror)
		{
			if(agents!=null)
			{			
				for(i=0;i<agents.size();i++)
				{
					agent =  (AgentID) agents.elementAt(i);
					adr = new addressParser(agent.getAddresses());				
					adr.parse();
					out.println(adr.IP + ":" + adr.port + ": - " +  agent.getName()  + "<BR>");
				}
			}
			else 
				out.println("Couldn't connect to AMS.");
		}

		out.println("</td>"); 
		out.println("</tr>");
		out.println("</table>");
}
	catch(Exception e)	
	{
		e.printStackTrace();
	}

}




protected void writeCFG(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) 
{
	try
	{
		response.setContentType("text/html");
		PrintWriter out = response.getWriter(); 
		out.println("<html>");
		out.println("<body>");
		out.println("<head>");
		out.println("<head>");
		out.println("</head>");
    	out.println("<SCRIPT LANGUAGE='JavaScript'>");
    	out.println("</SCRIPT>");
    		
		out.println("<form name='form1' method='post' action=''>");		
		out.println("<table width='100%' border='1'>");
		out.println("<tr><td>");	
		out.println("<table width='100%' border='0'>");
		out.println("<tr>");	
		out.println("<td>");		
		out.println("<img src='../img/ta.png'>");		
		out.println("</td></tr>");
		
        out.println("<tr>");
	 	out.println("<td valign='top' bgcolor='#eeeeee'>");                                        
        out.println("<table cellpadding='2' cellspacing='0' border='0'>");
        out.println("<tbody>");
        out.println("<tr>");
         out.println("<td valign='top' bgcolor='#eeeeee'><A HREF='" + createReplyAddr(request) +"'> USER AGENT &nbsp; </A>");
        out.println("</td>") ;                
        out.println("<td valign='top' bgcolor='#eeeeee'> &nbsp; <A HREF='" + createReplyAddr(request) +"?param=ams'>AMS</A> &nbsp; ");
        out.println("</td>");        
        out.println("</tr>");
        out.println("</tbody>");                                                  
        out.println("</table>");	
  	 	out.println("</td>") ;	
		out.println("</tr>");		
		out.println("<tr>");
		out.println("<td colspan='2'>"); 
		out.println("<BR> <b>Platform Configuration </b>: <BR><BR>");
		out.println("</td>"); 
		out.println("</tr>");
		out.println("</table>");
}
	catch(Exception e)	
	{
		e.printStackTrace();
	}

}

}
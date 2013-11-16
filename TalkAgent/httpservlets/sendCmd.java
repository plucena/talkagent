/*
 *  Very simple file to send a http request to the specified port with user name, password
 *  and action information.   This file is used with the new config servlet to command
 *  the server from command line.
 */
public class sendCmd{
  public static void main(String argv[]) throws Exception{
    if(argv.length!=4){
      System.out.println("wrong number of arguments, usage: java sendCmd [port] [name] [pass] [cmd]");
      return;
    }
    int port=new Integer(argv[0]).intValue();
    java.net.Socket skt=new java.net.Socket("localhost",port);
    String name=argv[1];
    String pass=argv[2];
    String cmd=argv[3];

    //it is important to append 2 CRLF at the end of this string, signal the end of http request header.

    String sndStr="GET /srvConfig?j_username="+name+"&j_password="+pass+"&action="+cmd+"\n\n";
    skt.getOutputStream().write(sndStr.getBytes());
    System.out.println("sent "+sndStr);
  }
}
package BajieSoft;
public class beanTest{
  private int intVal;
  private String strVal;
  private char charVal;
  public int getIntVal(){
    return intVal;
  }
  public String getStrVal(){
    return strVal;
  }
  public char getCharVal(){
    return charVal;
  }
  
  public void setIntVal(String i){
    intVal=Integer.parseInt(i);
  }
  public void setStrVal(String s){
    strVal=s;
  }
  public void setCharVal(String c){
    charVal=c.charAt(0);
  }
}
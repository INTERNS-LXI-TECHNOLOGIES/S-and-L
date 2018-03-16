import java.io.*;
public class FileExamples implements Cloneable{
	
	
public static void main(String[] args)throws
                   CloneNotSupportedException{

try{
	char [      ] ch=new char[50];
	int size=0;
File f=new File("Sample.txt");
f.createNewFile();
System.out.println(f.exists());
FileWriter fw=new FileWriter(f);
fw.write("name1\nname2\nname3");
fw.flush();
fw.close();
FileReader fr=new FileReader(f);
size=fr.read(ch);
	System.out.print(size+"-----");

for(char c:ch){
	System.out.print(c+"-----");
}
}
catch(IOException ioe){
	ioe.printStackTrace();
}
printWriterExample();
}


public static void printWriterExample(){
	try{
	System.out.println("method with print writer..................");
	File f=new File("printWriter.txt");
	PrintWriter pw=new PrintWriter(f);
	pw.write("abcdefg\nxdfghjnkm");
	pw.flush();
	pw.close();
	}
	catch(IOException ioe){
		ioe.printStackTrace();
	}
	searchingFiles();
}

public static void searchingFiles(){
	    System.out.println("method with file searching..................");
		File f=new File("E:/desktop/java/sample");
		String[] search =new String[500];
		search=f.list();
		for(String s:search){
			System.out.println("files are"+s);
		}
	
		
	consoleExample();
}

public static void consoleExample(){

Console c=System.console();
String s=c.readLine("%s","username:");
char[] pw=c.readPassword("%s","password:");
System.out.print("username is:"+s);
System.out.print("password is:");
for(char ch:pw){
	/*c.format("%c",ch);
	c.format("%c",ch);
	c.format("\n");
	c.format("\n");*/
	System.out.print(ch);
	
}
}



}
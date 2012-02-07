/*
  @author EnKrypt
*/

import java.io.*;
import java.util.*;
import java.util.regex.*;

public class Lisp{
	
	static Pattern lisp;
	static Matcher now;
	static Pattern quo;
	static Matcher nowa;
	static Map var=new HashMap();
	static String result;	

	public static void main(String args[])throws IOException{
		BufferedReader b=new BufferedReader(new InputStreamReader(System.in));
		String inp="";
		while(!inp.equalsIgnoreCase("exit")){
			System.out.print("LISP> ");
			inp=b.readLine();
			System.out.println("Result: "+dot(inp));
		}
	}
	public static String dot(String code){
		quo=Pattern.compile("'[^'\"]*\"");
		nowa=quo.matcher(code);
		code=unquote(code);
		lisp=Pattern.compile("\\([^()]*\\)");
		now=lisp.matcher(code);
		while (now.find()){
			String m=now.group(0);
			m=m.replace("(","");
			m=m.replace(")","");
			String[] arg=m.split(" ");
			String res=eval(arg);
			result = now.replaceFirst(res);
			return dot(result);
		}
		String[] cfin=code.split(" ");
		return cfin[cfin.length-1];
	}
	public static String eval(String arg[]){
		arg=requote(arg);
		for (int d=0;d<arg.length;d++){
			System.out.println(arg[d]);
		}
		if (arg[0].equalsIgnoreCase("add")){
			int cres=0;
			for (int i=1;i<arg.length;i++){
				cres+=Integer.parseInt(arg[i]);
			}
			return ""+cres;
		}
		else if (arg[0].equalsIgnoreCase("sub")){
			int cres=Integer.parseInt(arg[1]);
			for (int i=2;i<arg.length;i++){
				cres-=Integer.parseInt(arg[i]);
			}
			return ""+cres;
		}
		else if (arg[0].equalsIgnoreCase("mul")){
			int cres=Integer.parseInt(arg[1]);;
			for (int i=2;i<arg.length;i++){
				cres*=Integer.parseInt(arg[i]);
			}
			return ""+cres;
		}
		else if (arg[0].equalsIgnoreCase("div")){
			int cres=Integer.parseInt(arg[1]);
			for (int i=2;i<arg.length;i++){
				cres/=Integer.parseInt(arg[i]);
			}
			return ""+cres;
		}
		else if (arg[0].equalsIgnoreCase("cat")){
			String cres="";
			for (int i=1;i<arg.length;i++){
				cres+=arg[i];
			}
			return cres;
		}
		else if (arg[0].equalsIgnoreCase("eval")){
			String cres="";
			for (int i=1;i<arg.length;i++){
				cres+=dot(arg[i]);
			}
			return cres;
		}
		else if (arg[0].equalsIgnoreCase("var")&&arg.length==3){
			var.put(arg[1],arg[2]);
			return "";
		}
		else if (arg[0].equalsIgnoreCase("var")&&arg.length==2){
			String setvar=var.get(arg[1]).toString();
			return ""+setvar;
		}
		else{
			return "";
		}
	}
	public static String[] requote(String[] ar) {
		for(int i=0;i<ar.length;i++) {
    			ar[i] = ar[i].replaceAll("---","'");
  			ar[i] = ar[i].replaceAll("~~~","\"");
  			ar[i] = ar[i].replaceAll("\\{","(");
  			ar[i] = ar[i].replaceAll("\\}",")");
  			ar[i] = ar[i].replaceAll("^'","");
    			ar[i] = ar[i].replaceAll("\"$","");
    			ar[i] = ar[i].replaceAll("<>"," ");
  		}
  		return ar;
	}
	public static String unquote(String ar) {
		quo=Pattern.compile("'[^'\"]*\"");
		nowa=quo.matcher(ar);
  		while(nowa.find()) {
			String m=nowa.group(0);
    			m = m.replaceAll("^.","---");
    			m = m.replaceAll(".$","~~~");
    			m = m.replaceAll("\\(","{");
    			m = m.replaceAll("\\)","}");
    			m = m.replaceAll(" ","<>");
    			String result = nowa.replaceFirst(m);
			return unquote(result);
  		}
		return ar;
	}
}
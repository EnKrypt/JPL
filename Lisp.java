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
	static Pattern elisp;
	static Matcher enow;
	static Pattern equo;
	static Matcher enowa;
	static Map var=new HashMap();
	static String result;
	static String eresult;	

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
				cres+=evaldot(arg[i])+" ";
			}
			String[] cfn=cres.split(" ");
			return cfn[cfn.length-1];
		}
		else if (arg[0].equalsIgnoreCase("slice")){
			String cres="";
			cres=arg[1].substring(Integer.parseInt(arg[2]),Integer.parseInt(arg[3])+1);
			return cres;
		}
		else if (arg[0].equalsIgnoreCase("eq")){
			String cres="";
			String eqchk=arg[1];
			int flag=1;
			for (int i=1;i<arg.length;i++){
				if (!arg[i].equalsIgnoreCase(eqchk)){
					flag=0;
				}
			}
			return flag+"";
		}
		else if (arg[0].equalsIgnoreCase("gt")){
			String cres="";
			int flag=1;
			for (int i=2;i<arg.length;i++){
				if (Math.max(Integer.parseInt(arg[i-1]),Integer.parseInt(arg[i]))!=Integer.parseInt(arg[i-1])){
					flag=0;
				}
			}
			return flag+"";
		}
		else if (arg[0].equalsIgnoreCase("lt")){
			String cres="";
			int flag=1;
			for (int i=2;i<arg.length;i++){
				if (Math.min(Integer.parseInt(arg[i-1]),Integer.parseInt(arg[i]))!=Integer.parseInt(arg[i-1])){
					flag=0;
				}
			}
			return flag+"";
		}
		else if (arg[0].equalsIgnoreCase("and")){
			String cres="";
			int flag=1;
			for (int i=1;i<arg.length;i++){
				if (arg[i].equalsIgnoreCase("0")){
					flag=0;
				}
			}
			return flag+"";
		}
		else if (arg[0].equalsIgnoreCase("or")){
			String cres="";
			int flag=1;
			for (int i=1;i<arg.length;i++){
				if (!arg[i].equalsIgnoreCase("0")){
					flag=0;
				}
			}
			return flag+"";
		}
		else if (arg[0].equalsIgnoreCase("not")){
			String cres="";
			if (arg.length==2&&arg[1].equalsIgnoreCase("0")){
				return "1";
			}
			else{
				return "0";
			}
		}
		else if (arg[0].equalsIgnoreCase("print")){
			String cres="";
			for (int i=1;i<arg.length;i++){
				cres+=arg[i]+" ";
			}
			System.out.println(cres);
			return "";
		}
		else if (arg[0].equalsIgnoreCase("read")){
			String cres="";
			for (int i=1;i<arg.length;i++){
				cres+=arg[i]+" ";
			}
			System.out.print(cres);
			try{
				BufferedReader b=new BufferedReader(new InputStreamReader(System.in));
				cres=b.readLine();
			}
			catch(Exception e) {}
			return cres;
		}
		else if (arg[0].equalsIgnoreCase("pass")){
			String cres="";
			return "";
		}
		else if (arg[0].equalsIgnoreCase("if")){
			if (arg[1].equalsIgnoreCase("0")){
				return evaldot(arg[3]);
			}
			else{
				return evaldot(arg[2]);
			}
		}
		else if (arg[0].equalsIgnoreCase("var")&&arg.length==3){
			var.put(arg[1],arg[2]);
			return "";
		}
		else if (arg[0].equalsIgnoreCase("var")&&arg.length==2){
			String setvar="";
			try{
				setvar=var.get(arg[1]).toString();
			}
			catch(NullPointerException npe){
				setvar="0";
			}
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
	public static String evaldot(String code){
		equo=Pattern.compile("'[^'\"]*\"");
		enowa=equo.matcher(code);
		code=unquote(code);
		elisp=Pattern.compile("\\([^()]*\\)");
		enow=elisp.matcher(code);
		while (enow.find()){
			String m=enow.group(0);
			m=m.replace("(","");
			m=m.replace(")","");
			String[] arg=m.split(" ");
			String res=eval(arg);
			eresult = enow.replaceFirst(res);
			return evaldot(eresult);
		}
		String[] cfin=code.split(" ");
		return cfin[cfin.length-1];
	}
}
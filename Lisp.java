//   **************************************************************************
//   *                                                                        *
//   *  This program is free software: you can redistribute it and/or modify  *
//   *  it under the terms of the GNU General Public License as published by  *
//   *  the Free Software Foundation, either version 3 of the License, or     *
//   * (at your option) any later version.                                    *
//   *                                                                        *
//   *  This program is distributed in the hope that it will be useful,       *
//   *  but WITHOUT ANY WARRANTY; without even the implied warranty of        *
//   *  MERCHANTABILITY || FITNESS FOR A PARTICULAR PURPOSE.  See the         *
//   *  GNU General Public License for more details.                          *
//   *                                                                        *
//   *  You should have received a copy of the GNU General Public License     *
//   *  along with this program.  If not, see <http://www.gnu.org/licenses/>. *
//   *                                                                        *
//   *         (C) Arvind Kumar 2011 .                                        *
//   *         (C) James McClain 2011 .                                       *
//   **************************************************************************

import java.util.*;
import java.util.regex.*;
import java.net.*;

public class Lisp{
	
	//Bunch of arbitrary global variables used
	static Pattern lisp;
	static Matcher now;
	static Pattern quo;
	static Matcher nowa;
	static Pattern elisp;
	static Matcher enow;
	static Pattern equo;
	static Matcher enowa;
	static Map var=new HashMap();
	static Map mkdev=new HashMap();
	static String result;
	static String eresult;	
	
	static Device[] defaultDevices={
		new Add(),
		new Sub(),
		new Mul(),
		new Div()
	};

	public static String parse(String code){
        Pattern lisp;
        Matcher now;
        Pattern quo;
        Matcher nowa;
		quo=Pattern.compile("'[^'\"]*\"");
		nowa=quo.matcher(code);
		code=unquote(code);
		lisp=Pattern.compile("\\([^()]*\\)");
		now=lisp.matcher(code);
		while (now.find()){
			String m=now.group(0);
			m=m.replace("(","");
			m=m.replace(")","");
            m=m.replaceAll(" +"," ");
			String[] arg=m.split(" ");
			String res=eval(arg);
			result = now.replaceFirst(res);
			return parse(result);
		}
		String[] cfin=code.split(" ");
        if (cfin.length != 0)
            return cfin[cfin.length-1];
        return "";
	}
	public static String eval(String arg[]){
		arg=requote(arg);
                 // for(int i=0;i<arg.length;++i){
                 //     System.out.println(i+" : "+arg[i]);
                 // }
		int deviceIndex=-1;
		for (int i=0;i<defaultDevices.length;i++){
			if (arg[0].equalsIgnoreCase(defaultDevices[i].getname())){
				deviceIndex=i;
			}
		}
		if (deviceIndex>=0){
			return defaultDevices[deviceIndex].exec(arg);
		}
		/*
		else if (arg[0].equalsIgnoreCase("div")){
			double cres=Double.parseDouble(arg[1]);
			for (int i=2;i<arg.length;i++){
				cres/=Double.parseDouble(arg[i]);
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
            arg[0] = "";
            cres = combine(arg," ");
            cres=parse(cres);
            return cres;
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
				if ((Math.max(Double.parseDouble(arg[i-1]),Double.parseDouble(arg[i]))!=Double.parseDouble(arg[i-1]))||Double.parseDouble(arg[i])==Double.parseDouble(arg[i-1])){
					flag=0;
				}
			}
			return flag+"";
		}
		else if (arg[0].equalsIgnoreCase("lt")){
			String cres="";
			int flag=1;
			for (int i=2;i<arg.length;i++){
				if ((Math.min(Double.parseDouble(arg[i-1]),Double.parseDouble(arg[i]))!=Double.parseDouble(arg[i-1]))||Double.parseDouble(arg[i])==Double.parseDouble(arg[i-1])){
					flag=0;
				}
			}
			return flag+"";
		}
		else if (arg[0].equalsIgnoreCase("strlen")){
			return ""+arg[1].length();
		}
		else if (arg[0].equalsIgnoreCase("and")){
			String cres="";
			int flag=1;
			for (int i=1;i<arg.length;i++){
				if (arg[i].equalsIgnoreCase("0")||arg[i].equalsIgnoreCase("0.0")){
					flag=0;
				}
			}
			return flag+"";
		}
		else if (arg[0].equalsIgnoreCase("or")){
			String cres="";
			int flag=0;
			for (int i=1;i<arg.length;i++){
				if (!arg[i].equalsIgnoreCase("0")||arg[i].equalsIgnoreCase("0")){
					flag=1;
				}
			}
			return flag+"";
		}
		else if (arg[0].equalsIgnoreCase("not")){
			String cres="";
			if (arg[1].equalsIgnoreCase("0")||arg[1].equalsIgnoreCase("0")){
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
		else if (arg[0].equalsIgnoreCase("read")&&arg.length==2){
			String cres="";
			for (int i=1;i<arg.length;i++){
				cres+=arg[i]+" ";
			}
			if (cres.equals(" ")){
				try{
					BufferedReader b=new BufferedReader(new InputStreamReader(System.in));
					cres=b.readLine();
				}
				catch(Exception e) {}
			}
			else{
				try{
					System.out.print(cres);
					BufferedReader b=new BufferedReader(new InputStreamReader(System.in));
					cres=b.readLine();
				}
				catch(Exception e) {}
			}
			return "'"+cres+"\"";
		}
		else if (arg[0].equalsIgnoreCase("read")&&arg.length==1){
			String cres="";
			try{
				cres=in.readLine();
				cres=cres.substring(cres.lastIndexOf(":")+1);
			}
			catch(Exception e) {}
			return "'"+cres+"\"";
		}
		else if (arg[0].equalsIgnoreCase("read-url")){
			String lin="",cres="";
			try{
				URL url=new URL(arg[1]);
				BufferedReader read=new BufferedReader(new InputStreamReader(url.openStream()));
				while ((lin=read.readLine())!=null){
					cres+=lin+" ";
				}
				read.close();
			}
			catch(Exception e){ e.printStackTrace(); }
			return "'"+cres+"\"";
		}
		else if (arg[0].equalsIgnoreCase("save")){
			String lin="",cres="";
			try{
				File fil=new File(arg[1]);
				if (!fil.exists()){
					fil.createNewFile();
				}
				BufferedWriter read=new BufferedWriter(new FileWriter(arg[1]));
				Set cvar = var.keySet();
				Set cmkdev = mkdev.keySet();
				Iterator itrv = cvar.iterator();
				Iterator itrm = cmkdev.iterator();
				while (itrv.hasNext()){
					String nex=itrv.next().toString();
					read.write("(var "+nex+" '"+var.get(nex)+"\")");
					read.newLine();
					read.flush();
				}
				while (itrm.hasNext()){
					String nex=itrm.next().toString();
					read.write("(mkdev "+nex+" '"+mkdev.get(nex)+"\")");
					read.newLine();
					read.flush();
				}
				read.close();
			}
			catch(Exception e){ e.printStackTrace(); }
			return "(eval '"+cres+"\")";
		}
		else if (arg[0].equalsIgnoreCase("include")){
			String lin="",cres="";
			try{
				BufferedReader read=new BufferedReader(new FileReader(arg[1]));
				while ((lin=read.readLine())!=null){
					cres+=lin+" ";
				}
				read.close();
			}
			catch(Exception e){ e.printStackTrace(); }
			return "(eval '"+cres+"\")";
		}
		else if (arg[0].equalsIgnoreCase("mkdev")&&arg.length==3){
			mkdev.put(arg[1],arg[2]);
			return "";
		}
		else if (arg[0].equalsIgnoreCase("pass")){
			String cres="";
			return "";
		}
		else if (arg[0].equalsIgnoreCase("if")){
			if (arg[1].equalsIgnoreCase("0")||arg[1].equalsIgnoreCase("0")){
				return arg[3];
			}
			else{
				return arg[2];
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
        else if (arg[0].equalsIgnoreCase("lambda")){
            return "runlambda '"+arg[1]+"\" '"+arg[2]+"\"";
        }
        else if (arg[0].equalsIgnoreCase("runlambda")){
            arg[1] = arg[1].replaceFirst("^\\(","");
            arg[1] = arg[1].replaceFirst("\\)$","");
            arg[1] = arg[1].replaceAll(" +"," ");
            String[] lambdaArg = arg[1].split(" ");
            String to_eval;
            to_eval = arg[2];
            to_eval = to_eval.replaceAll("\\("," ( ");
            to_eval = to_eval.replaceAll("\\)"," ) ");
            to_eval = to_eval.replaceAll("'"," ' ");
            to_eval = to_eval.replaceAll("\""," \" ");
            int i;
            int q;
            int argNum = 3;
            String[] to_eval_array;
            for(i=0,q=0; i < lambdaArg.length;++i,++argNum){
                to_eval_array = to_eval.split(" ");
                for(q=0;q < to_eval_array.length;++q){
                    if(to_eval_array[q].equals(lambdaArg[i])) {
                        to_eval_array[q] = arg[argNum];
                    }
                }
                to_eval = combine(to_eval_array," ");
            }
            to_eval = to_eval.replaceAll(" \\( ","(");
            to_eval = to_eval.replaceAll(" \\) ",")");
            to_eval = to_eval.replaceAll(" ' ","'");
            to_eval = to_eval.replaceAll(" \" ","\"");
            return "(eval '"+to_eval+"\")";
        }
		*/
		else{
			String devi="";
			String param="";
			for (int i=1;i<arg.length;i++){
				param+=" "+arg[i];
			}
			try{
				devi=mkdev.get(arg[0]).toString();
			}
			catch(Exception npe){
				return "";
			}
            return "("+devi+param+")";
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
            Pattern lisp;
            Matcher now;
            Pattern quo;
            Matcher nowa;
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
    public static String combine(String[] s, String glue) {
        int k=s.length;
        if (k==0)
            return null;
        StringBuilder out=new StringBuilder();
        out.append(s[0]);
        for (int x=1;x<k;++x)
            out.append(glue).append(s[x]);
        return out.toString();
    }
}
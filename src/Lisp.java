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

package src;

import java.util.*;
import java.util.regex.*;

import src.*;
import src.devices.defaults.*;
import src.devices.extras.*;

public class Lisp{

	Hook hook;
	
	//Bunch of arbitrary global variables used
	Pattern lisp;
	Matcher now;
	Pattern quo;
	Matcher nowa;
	Pattern elisp;
	Matcher enow;
	Pattern equo;
	Matcher enowa;
	Map var=new HashMap();
	Map mkdev=new HashMap();
	String result;
	String eresult;	
	
	public Lisp(Hook hk){
		hook=hk;
	}
	
	Device[] defaultDevices={
		new Add(),
		new Sub(),
		new Mul(),
		new Div(),
		new Cat(),
		new Eval(),
		new Slice(),
		new Eq(),
		new Gt(),
		new Lt(),
		new Strlen(),
		new And(),
		new Or(),
		new Not(),
		new Print(),
		new Read(), //TODO : Implement dynamic protocol to receive content, instead of System.in
		new ReadURL(),
		new Save(),
		new Include(),
		new Mkdev(),
		new Pass(),
		new If(),
		new Var(),
		new Lambda(),
		new Runlambda()
	};

	public String parse(String code){
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
	public String eval(String arg[]){
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
			return defaultDevices[deviceIndex].exec(arg,var,mkdev,hook,this);
		}
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
	public String[] requote(String[] ar) {
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
	public String unquote(String ar) {
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
    public String combine(String[] s, String glue) {
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
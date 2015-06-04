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

package src.devices.defaults;

import java.util.*;

import src.*;

public class Runlambda extends Device{
	
	static String name="runlambda";
	
	public String getname(){
		return this.name;
	}
	
	public String exec(String arg[], Map var, Map mkdev, Hook hook, Lisp lisp){
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
			to_eval = lisp.combine(to_eval_array," ");
		}
		to_eval = to_eval.replaceAll(" \\( ","(");
		to_eval = to_eval.replaceAll(" \\) ",")");
		to_eval = to_eval.replaceAll(" ' ","'");
		to_eval = to_eval.replaceAll(" \" ","\"");
		return "(eval '"+to_eval+"\")";
	}
}
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

import java.io.*;
import java.util.*;

import src.*;

public class Read extends Device{
	
	static String name="read";
	
	public String getname(){
		return this.name;
	}
	
	public String exec(String arg[], Map var, Map mkdev, Hook hook, Lisp lisp){
		if (arg.length==2){
			String cres="";
			for (int i=1;i<arg.length;i++){
				cres+=arg[i]+" ";
			}
			if (cres.equals(" ")){
				try{
					cres=hook.read();
				}
				catch(Exception e) {}
			}
			else{
				try{
					hook.write(cres);
					cres=hook.read();
				}
				catch(Exception e) {}
			}
			return "'"+cres+"\"";
		}
		else if (arg.length==1){
			String cres="";
			try{
				cres=hook.read();
				cres=cres.substring(cres.lastIndexOf(":")+1);
			}
			catch(Exception e) {}
			return "'"+cres+"\"";
		}
		return "";
	}
}
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

package src.DefaultDevices;

import java.io.*;
import java.util.*;

import src.*;

public class Save extends Device{
	
	static String name="save";
	
	public String getname(){
		return this.name;
	}
	
	public String exec(String arg[], Map var, Map mkdev){
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
}
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

public class Include extends Device{
	
	static String name="include";
	
	public String getname(){
		return this.name;
	}
	
	public String exec(String arg[], Map var, Map mkdev, Hook hook, Lisp lisp){
		String lin="",cres="";
		try{
			String current_directory = System.getProperty("user.dir");
			if (arg[1].indexOf("/")!=-1||arg[1].indexOf("..")!=-1){
				throw new FileNotFoundException();
			}
			BufferedReader read=new BufferedReader(new FileReader(current_directory+"/"+arg[1]));
			while ((lin=read.readLine())!=null){
				cres+=lin+" ";
			}
			read.close();
		}
		catch(Exception e){ e.printStackTrace(); return "(print 'File not found\")"; }
		return "(eval '"+cres+"\")";
	}
}
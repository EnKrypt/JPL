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

import java.io.*;

class InterpreterHook{ //Acts as an interpreter via the console. Will run directly through the command line.
	public static void main(String args[])throws IOException{
		BufferedReader b=new BufferedReader(new InputStreamReader(System.in));
		String inp="";
		while(!inp.equalsIgnoreCase("exit")){
			System.out.print("LISP> ");
			inp=b.readLine();
			System.out.println("Result: "+Lisp.parse(inp)); //Hook the Lisp parser to the interpreter
		}
	}
}
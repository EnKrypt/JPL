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

import src.*;

class InterpreterHook{ //Acts as an interpreter via the console. Will run directly through the command line.
	public static void main(String args[])throws IOException{
		BufferedReader input=new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter output=new BufferedWriter(new OutputStreamWriter(System.out));
		String inp="";
		while(!inp.equalsIgnoreCase("exit")){
			output.write("LISP> ");
			output.flush();
			inp=input.readLine();
			output.write("Result: "+Lisp.parse(inp)+"\n"); //Hook the Lisp parser to the interpreter
			output.flush();
		}
	}
}

/*
This class of the project isn't strictly part of the JPL0 library.
It simply servers to provide the input and output interface through which the library is interacted through.
*/
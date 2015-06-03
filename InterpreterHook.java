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

class InterpreterHook extends Hook{ //Acts as an interpreter via the console. Will run directly through the command line.
	
	BufferedReader input=new BufferedReader(new InputStreamReader(System.in));
	BufferedWriter output=new BufferedWriter(new OutputStreamWriter(System.out));
	
	public void write(String param)throws IOException{
		output.write(param);
		output.flush();
	}
	
	public String read()throws IOException{
		return input.readLine();
	}
	
	public static void main(String args[])throws IOException{
		InterpreterHook hook=new InterpreterHook();
		Lisp lisp=new Lisp(hook);
		String inp="";
		while(!inp.equalsIgnoreCase("exit")){
			hook.write("LISP> ");
			inp=hook.read();
			hook.write("Result: "+lisp.parse(inp)+"\n"); //Hook the Lisp parser to the interpreter
		}
	}
}

/*
This class of the project isn't strictly part of the JPL0 library.
It simply servers to provide the input and output interface through which the library is interacted through.
*/
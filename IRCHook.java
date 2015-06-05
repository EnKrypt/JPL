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
import java.net.*;

import src.*;

class IRCHook extends Hook{ //Acts as an interpreter via an IRC protocol.
	
	static String host="irc.jamezq.com"; //Test network
	static String channel="#u413"; //Test channel
	static String nick="PL0Bot"; //IRC Nickname
	static String command="!pl"; //Command to trigger the bot to parse input
	
	boolean debug=false;
	Socket con;
	BufferedReader input;
	BufferedWriter output;
	
	public IRCHook()throws IOException{
		con=new Socket(host,6667);
		input = new BufferedReader(new InputStreamReader(con.getInputStream()));
		output = new BufferedWriter(new OutputStreamWriter(con.getOutputStream()));
	}
	
	public void write(String param)throws IOException{
		output.write("PRIVMSG "+channel+" :"+param+"\r\n"); 
		if (debug){
			System.out.println(param);
		}
		output.flush();
		try{
			Thread.sleep(1000); //To prevent getting kicked from Excess Flood
		}
		catch (InterruptedException ie) {ie.printStackTrace();}
	}
	
	public String read()throws IOException{
		while (true){
			String get=input.readLine();
			if (debug){
				System.out.println(get);
			}
			if (get.startsWith("PING")){
				output.write("PONG "+get.substring(5)+"\r\n"); 
				if (debug){
					System.out.println("PONG sent");
				}
				output.flush();
			}
			String[] commands=get.split(" ");
			if (commands[1].equalsIgnoreCase("PRIVMSG")&&commands[2].equalsIgnoreCase(channel)){
				String message="";
				for (int i=3;i<commands.length;i++){
					message+=commands[i]+" ";
				}
				try{
					if (message.substring(1,command.length()+1).equalsIgnoreCase(command)){
						return message=message.substring(command.length()+2).trim();
					}
				}
				catch (Exception e){}
			}
		}
	}
	
	public static void main(String args[])throws IOException{
		IRCHook hook=new IRCHook();
		Lisp lisp=new Lisp(hook);
		hook.output.write("NICK " + nick + "\r\n");
        hook.output.write("USER " + "JPL0" + " \"\" \"\" :" + "PL0Bot" + "\r\n");
		hook.output.write("JOIN " + channel + "\r\n");
		hook.output.flush();
		System.out.println("Running");
		while(true){
			hook.write("Result: "+lisp.parse(hook.read())); //Hook the Lisp parser to the interpreter
		}
	}
}

/*
This class of the project isn't strictly part of the JPL0 library.
It simply serves to provide the input and output interface through which the library is interacted.
*/
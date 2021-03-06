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

import java.io.*;

public abstract class Hook{ //All hooks must extend this class. This is the template hooks must follow to use the library.
	
	static boolean isHook=true;
	
	public abstract void write(String param)throws IOException;
	
	public abstract String read()throws IOException;
	
	public abstract String asyncread()throws IOException;
	
	//Main method isn't required to be present. Hooks themselves can be part of another library.
	//Execution point may not be in the hook class, but as long as it has methods to read and write, the class is a valid hook.
}
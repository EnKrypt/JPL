Java Portable Lisp 0
==
[![Build Status](https://travis-ci.org/EnKrypt/JPL0.svg)](https://travis-ci.org/EnKrypt/JPL0)

**Portable Lisp Alpha Specification**:  
https://docs.google.com/document/d/1pnKDSDNnOU3ytw4FGgRkao8mfjwpyFvgKQMIJGtczVg/edit

**Standard Devices in PL0**:  
https://docs.google.com/document/d/10iZQ-L6S4k4P38m9rlO6fwcs8ItR_IYRdmZ6ZFIDb5E/edit

==
### How to use

Two executable hooks have been bundled with the JPL0 Library.
* An interpreter where you can directly run the parser on the command line
* An IRC hook where you can make the program join an IRC network and communicate through it on a channel

You can also build your own hook with relative ease. Take a look at `Hook.java` in `src/` to get an idea of the Hook template you need to stick to.

*JPL0 has also been integrated into EBot (http://github.com/EnKrypt/EBot/) for added Bot features.*

==
NOTE : This version does not strictly follow the Pl0 standard for better efficiency and ease.
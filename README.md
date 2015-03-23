Server-Client project in java
====================================
This program is to transfer between multi-client and single-server on GUI(Graphical User Interface).  
All PCs is updated in the same way when client or server controlled (e.g. push button).  

The program's author is Konishi.
This copyright belongs to  [福祉科学研究会](http://opuct-fukaken.sakura.ne.jp/magic3/).

How to use
------------------------------------
There is reference in '\doc' directory.  
Please browse 'index.html' file.

Issues of current situations
-----------------------------------
 - [x]Transmit between single-client and single-server on CUI and GUI.
 - [x]Transmit between multi-client and single-server on CUI.
 - [ ]Transmit between multi-client and single-server on GUI.
 - [ ]Each detail function.


What's the problems
-----------------------------------
JavaFX is used in the program.  
Graphics of JavaFX is another thread i.e. create new thread when create new window.  
So if There is update of action(e.g.button), the thread don't communicate notice to special transmit thread.  

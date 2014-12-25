Code Evaluator
==============

This is a system for automatically evaluating student code with appropriate grades. It may also be used for programming competitions.

### Installation

Clone this repo using:

	$ git clone http://github.com/arkeidolon/codieval
	
Install the following compilers (ignore the programming languages you don't need):

| Programming Languages | Compilers used by Codieval | Recommended download links |
| --------------------- | -------------------------- | -------------------------- |
| C		        | gcc			     | https://gcc.gnu.org/       |
| C#    	        | csc			     | http://msdn.microsoft.com/en-us/library/dd831853.aspx |
| Jav		        | javac & java		     | http://www.oracle.com/technetwork/java/javase/downloads/index.html |
| Python	        | python		     | https://www.python.org/downloads/ |
	
Make sure to include the compilers' directories to the system PATH.

### Folder structure:

	codieval/
		answers/
			problem1
				input.txt
				Problem1.java*
				output.txt*
			problem2
				input.txt
				problem2.c*
				output.txt*
			problem3
				input.txt
				problem3.py*
				output.txt*
		gui/
			GUI.java
			Evaluator.java
		problems/
			problem1
				desc.germ
			problem2
				desc.germ
			problem3
				desc.germ
		Codieval.jar
		README.md

__Note:__ The code and output files are provided by the user, thus they are included in the gitignore file. The GUI folder is to be deleted upon usage of the system to avoid the users from tinkering with the code, possibly changing the mechanics of the system.

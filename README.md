Code Evaluator
==============

This is a system for automatically evaluating student code with appropriate grades. It may also be used for programming competitions.

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

Note: The code and output files are provided by the user, thus they are included in the gitignore file. The GUI folder is to be deleted upon usage of the system to avoid the users from tinkering with the code, possibly changing the mechanics of the system.

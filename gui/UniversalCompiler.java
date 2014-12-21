import java.io.*;

public class UniversalCompiler {
	public static void main(String[] args) throws IOException {
		Runtime rt = Runtime.getRuntime();
		boolean error = false;
		String[] commands = getCommandCompile(args[0], args[1]);
		if(commands != null) {
			Process proc = rt.exec(commands);

			BufferedReader stdInput = new BufferedReader(new
				InputStreamReader(proc.getInputStream()));

			BufferedReader stdError = new BufferedReader(new
				InputStreamReader(proc.getErrorStream()));

			System.out.println("Here is the standard output of the command:");
			String s = null;
			while ((s = stdInput.readLine()) != null) {
				System.out.println(s);
			}

			System.out.println("Here is the standard error of the command (if any):");
			while ((s = stdError.readLine()) != null) {
				System.out.println(s);
				error = true;
			}
		}

		if( ! error) {
			String[] commands2 = getCommandRun(args[0], args[1]);
			Process proc = rt.exec(commands2);
			BufferedReader stdInput = new BufferedReader(new
				InputStreamReader(proc.getInputStream()));
			BufferedReader stdError = new BufferedReader(new
				InputStreamReader(proc.getErrorStream()));

			System.out.println("Here is the standard output of the command:");
			String s = null;
			while ((s = stdInput.readLine()) != null) {
				System.out.println(s);
			}

			System.out.println("Here is the standard error of the command (if any):");
			while ((s = stdError.readLine()) != null) {
				System.out.println(s);
			}
		}
	}

	private static String[] getCommandCompile(String programmingLanguage, String fileToCompile) {
		switch(programmingLanguage.toLowerCase()) {
			case "java": 	return new String[]{"javac", fileToCompile + ".java"};
			case "c": 		return new String[]{"gcc", "-o", fileToCompile, fileToCompile + ".c"};
			case "c#": 		return new String[]{"csc", fileToCompile + ".cs"};
			default: 		return null;
		}
	}

	private static String[] getCommandRun(String programmingLanguage, String fileToRun) {
		switch(programmingLanguage.toLowerCase()) {
			case "java": 	return new String[]{"java", fileToRun};
			case "c": 		return new String[]{fileToRun};
			case "c#": 		return new String[]{fileToRun};
			case "python":	return new String[]{"python", fileToRun + ".py"};
			default: return new String[]{""};
		}
	}
}

import java.io.*;

public class UniversalCompiler {
	public static long elapsedTime(long startTime) {
		return System.currentTimeMillis()-startTime;
	}
	public static String compileAndRun(String dir, String programmingLanguage, String filename) throws IOException, CompilationErrorException {
		boolean error = false;
		String[] commands = getCommandCompile(programmingLanguage, filename);

		String result = "";

		long maxTime = 10000;
		long startTime = System.currentTimeMillis();

		if(commands != null) {
			ProcessBuilder pb = new ProcessBuilder(commands);
			pb.directory(new File(dir));
			Process proc = pb.start();

			BufferedReader stdInput = new BufferedReader(new
				InputStreamReader(proc.getInputStream()));

			BufferedReader stdError = new BufferedReader(new
				InputStreamReader(proc.getErrorStream()));

			String s = null;
			while ((s = stdInput.readLine()) != null) {
				if(elapsedTime(startTime) > maxTime)
					throw new CompilationErrorException("Error! Compile time is more than 10 seconds");

				System.out.println(s);
			}

			while ((s = stdError.readLine()) != null) {
				if(elapsedTime(startTime) > maxTime)
					throw new CompilationErrorException("Error! Compile time is more than 10 seconds");

				result += s + "\n";
				error = true;
			}

			System.out.println("Compile time: " + elapsedTime(startTime) + "ms");
		}

		if(error) {
			throw new CompilationErrorException(result);
		}
		else {
			String[] commands2 = getCommandRun(programmingLanguage, filename);
			switch(programmingLanguage.toLowerCase()) {
				case "c":
				case "c#": commands2[0] = dir + "/" + commands2[0];
			}
			ProcessBuilder pb = new ProcessBuilder(commands2);
			pb.directory(new File(dir));
			Process proc = pb.start();

			BufferedReader stdInput = new BufferedReader(new
				InputStreamReader(proc.getInputStream()));
			BufferedReader stdError = new BufferedReader(new
				InputStreamReader(proc.getErrorStream()));

			String s = null;
			while ((s = stdInput.readLine()) != null) {
				if(elapsedTime(startTime) > maxTime)
					throw new CompilationErrorException("Error! Runtime is more than 10 seconds");

				result += s + "\n";
			}

			while ((s = stdError.readLine()) != null) {
				if(elapsedTime(startTime) > maxTime)
					throw new CompilationErrorException("Error! Runtime is more than 10 seconds");

				result += s + "\n";
			}
			System.out.println("Runtime: " + elapsedTime(startTime) + "ms");
		}
		System.out.println();
		return result;
	}

	private static String[] getCommandCompile(String programmingLanguage, String fileToCompile) {
		switch(programmingLanguage.toLowerCase()) {
			case "java": 	return new String[]{"javac", fileToCompile};
			case "c": 		return new String[]{"gcc", "-o", fileToCompile.split("\\.")[0], fileToCompile};
			case "c#": 		return new String[]{"csc", fileToCompile};
			default: 		return null;
		}
	}

	private static String[] getCommandRun(String programmingLanguage, String fileToRun) {
		switch(programmingLanguage.toLowerCase()) {
			case "java": 	return new String[]{"java", fileToRun.split("\\.")[0]};
			case "c": 		return new String[]{fileToRun.split("\\.")[0]};
			case "c#": 		return new String[]{fileToRun.split("\\.")[0]};
			case "python":	return new String[]{"python", fileToRun};
			default: return new String[]{""};
		}
	}

	public static String getCompiler(String programmingLanguage) {
		switch(programmingLanguage.toLowerCase()) {
			case "java":	return getCompilerVersion("javac");
			case "c":		return getCompilerVersion("gcc");
			case "c#":		return getCompilerVersion("csc");
			case "python":	return getCompilerVersion("python");
			default: return "unknown";
		}
	}

	public static String getCompilerVersion(String compiler) {
		Runtime rt = Runtime.getRuntime();
		String[] commands = {compiler, getVersionArgument(compiler)};

		try {
			Process proc = rt.exec(commands);

			BufferedReader stdInput = new BufferedReader(new
				InputStreamReader(proc.getInputStream()));

			BufferedReader stdError = new BufferedReader(new
				InputStreamReader(proc.getErrorStream()));

			String s = null;
			s = stdInput.readLine();

			if(s == null)
				s = stdError.readLine();

			return s;
		}
		catch(IOException e) {
			return "Error: Compiler not found (" + compiler + ")";
		}
	}

	public static String getVersionArgument(String compiler) {
		switch(compiler.toLowerCase()) {
			case "javac":
				return "-version";
			default:
				return "--version";
		}
	}
}

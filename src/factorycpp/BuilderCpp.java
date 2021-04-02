package factorycpp;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import interfaces.IBuilder;

public class BuilderCpp implements IBuilder{

	@Override
	public String compiler(File file) {
		
		if (file.exists()) {
			
			String errorMessage = "";
			Process processCompile = null;
			
			String output = file.getAbsolutePath().split("\\.")[0];
			String commandExecution = "g++ -o " + output + " " + file.getAbsolutePath();

			try {
				processCompile = Runtime.getRuntime().exec(commandExecution);
			} catch (IOException e) {
				return "There was an error during compilation";
			}

			BufferedReader brCompileError = new BufferedReader(new InputStreamReader(processCompile.getErrorStream()));
			String errorCompile = null;
			
			try {
				errorCompile = brCompileError.readLine();
			} catch (IOException e) {
				return errorCompile;
			}

			errorMessage += errorCompile + "\n";

			while (processCompile.isAlive()) {}
			
			if (processCompile.exitValue() == 0) {
				return "Successfully compiled";
			} else {
				return errorMessage;
			}
		}
		
		return "File not validation";
	}
	
}

import java.io.*;

public class Code {

	private final String[] prologue={	//write to the output file before main code
		"#include <stdio.h>",
		"int main() {",
	};

	private final String[] epilogue={	//write to the output file after main code
		"return 0;",
		"}",
	};

	public Code(String code, Environment env) {	//constructor that takes in an environment object that can be converted to a string
		String fn = System.getenv("Code"); //retrieve the value of the "code" environment variable
		if (fn == null) {	//if null, do nothing, and return
			return;
		}

		try {
			BufferedWriter f = new BufferedWriter(new FileWriter(fn + ".c")); //Open a new file for writing with the name of the "Code" environment variable and a ".c" extension
			for (String s: prologue) { //write contents of the "prologue" array to the output file
				f.write(s + "\n");
			}

			f.write(env.toC());	//write the representation of "env" object to the output file
			f.write(code);	//write to the output file
			
			for (String s: epilogue) {	//write the contents of the "epilogue" array to the output file
				f.write(s + "\n");
			}

			f.close();	//closes the file
		} catch (Exception e) {	//prints exception to the standard error output
			System.err.println(e);
		}
	}
}

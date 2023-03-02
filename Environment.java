import java.util.HashMap;

// This class provides a stubbed-out environment.
// You are expected to implement the methods.
// Accessing an undefined variable should throw an exception.

// Hint!
// Use the Java API to implement your Environment.
// Browse:
//   https://docs.oracle.com/javase/tutorial/tutorialLearningPaths.html
// Read about Collections.
// Focus on the Map interface and HashMap implementation.
// Also:
//   https://www.tutorialspoint.com/java/java_map_interface.htm
//   http://www.javatpoint.com/java-map
// and elsewhere.

public class Environment {
	private HashMap<String, Double> map;

	public Environment() {	//initializes the hashmap
		this.map = new HashMap<String, Double>();
	}

	public double put(String var, double val) {	//Adds a variable and its corresponding value to the HashMap and returns the previous value if the variable already existed
		Double result = this.map.put(var, new Double(val));	//Put the variable and its corresponding value in the HashMap and store the previous value (if any)

		if (result == null) {	//if previous value is null, return 0. Else return previous value
			return 0;
		} else {
			return result;
		}		
	}

	public double get(int pos, String var) throws EvalException {	//Returns the value of a variable with the given name from the HashMap. Throws an exception if the variable does not exist.
		Double result = this.map.get(var);

		if (result == null) {
			throw new EvalException(pos, var);
		}

		return result;	//Otherwise return value
	}

	public String toC() {	//Returns a string representation of the environment
		String s = "";	//Initialize a blank string and a separator string
		String sep = " ";
		for (String v : map.keySet()) {	//Loop through each variable name in the HashMap
			s += sep + v;
			sep = ",";
		}
		return s == "" ? "" : "int" + s + ";\nx=0;x=x;\n";	//If no variables, return empty string. Else return a string that declares the variables as ints and sets them to 0.
	}
}

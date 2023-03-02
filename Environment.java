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
	private HashMap<String, Integer> map;

	public Environment() {
		this.map = new HashMap<String, Integer>();
	}

	public int put(String var, int val) {
		Integer result = this.map.put(var, new Integer(val));

		if (result == null) {
			return 0;
		} else {
			return result;
		}		
	}

	public int get(int pos, String var) throws EvalException {
		Integer result = this.map.get(var);

		if (result == null) {
			throw new EvalException(pos, var);
		}

		return result;
	}

	public String toC() {
		String s = "";
		String sep = " ";
		for (String v : map.keySet()) {
			s += sep + v;
			sep = ",";
		}
		return s == "" ? "" : "int" + s + ";\nx=0;x=x;\n";
	}
}

package io.github.michellemc96;

public class SQLUtil {

	public static String toIn(String[] strings) {
		StringBuilder sb = new StringBuilder();
		
		for (int i=0; i < strings.length; i++) {
			if (i > 0) sb.append(",");
			sb.append("'").append(strings[i]).append("'");
		}		
		
		return sb.toString();
	}
	
	
}

package httpserver.tools;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Util {
	public static final String TEXT_PLAIN = "text/plain";
    public static final String TEXT_HTML = "text/html";
    public static final String TEXT_CSS = "text/CSS";
    public static final String APPLICATION_JS = "application/javascript";
    public static final String APPLICATION_JSON = "application/json";

	public static RequestMethod[] getRequestMethodHeaders() {
		return RequestMethod.values();
	}

	public static JSONArray getMapping(String filename) {
		try {
			List<String> fileContent = Files.readAllLines(Paths.get(filename));
			StringBuilder strBuilder = new StringBuilder();

			for (String str : fileContent)
				strBuilder.append(str);
			return (JSONArray) (new JSONObject(strBuilder.toString())).get("mappings");
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@SuppressWarnings("rawtypes")
	public static Class getClassFromMapping(String mapping){
		mapping = mapping.toLowerCase();
		if(mapping.equals("string"))
			return String.class;
		if(mapping.equals("int"))
			return int.class;
		if(mapping.equals("double"))
			return double.class;
		
		return String.class;
	}
	
	public static boolean isMatchingFromMapping(String value, String mapping){
		mapping = mapping.toLowerCase();
		if(mapping.equals("string"))
			return value.matches("\\w+");
		if(mapping.equals("int"))
			return value.matches("\\d+");
		if(mapping.equals("double"))
			return value.matches("\\d+.\\d+");
		
		if(mapping.contains("pattern")){
	        Pattern pattern = Pattern.compile("pattern=(.+)");
	        Matcher matcher = pattern.matcher(mapping);
	        while(matcher.find()){
	            String expr = matcher.group(1);
	            if(value.matches(expr)){
	            	return true;
	            }
	        }
		}
		return false;
	}
	
	public static Object getObjectFromMapping(String mapping, String value){
		mapping = mapping.toLowerCase();
		if(mapping.equals("string"))
			return value;
		if(mapping.equals("int"))
			return Integer.parseInt(value);
		if(mapping.equals("double"))
			return Double.parseDouble(value);
		
		return value;
	}
	public boolean containsPath(JSONArray array, String path) {
		return array.toString().contains(path);
	}
	
	public static HashMap<String, String> toHashMapBody(String body){
		HashMap<String, String> map = new HashMap<>();
		String values[] = body.split("&");
		for(String s : values){
			String value[] = s.split("=");
			map.put(value[0], value[1]);
		}
		return map;
	}
	public static String readFile(String filename) throws IOException {
		String content = "";
		for (String line : Files.readAllLines(Paths.get(filename)))
			content += line;
		return content;
	}
}

package httpserver.tools;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Util {

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

	public boolean containsPath(JSONArray array, String path) {
		return array.toString().contains(path);
	}

	public static String readFile(String filename) throws IOException {
		String content = "";
		for (String line : Files.readAllLines(Paths.get(filename)))
			content += line;
		return content;
	}
}

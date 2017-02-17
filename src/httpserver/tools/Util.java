package httpserver.tools;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

public class Util {

	public static RequestMethod[] getRequestMethodHeaders() {
		return RequestMethod.values();
	}

	public static JSONObject getMapping(String filename) {
		try {
			List<String> fileContent = Files.readAllLines(Paths.get(filename));
			StringBuilder strBuilder = new StringBuilder();

			for (String str : fileContent)
				strBuilder.append(str);
			return new JSONObject(strBuilder.toString());

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}

}

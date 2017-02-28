package httpserver.tools;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

public class ErrorGenerator {

	public static HashMap<Integer, String> errorText = new HashMap<>();
	static {
		errorText.put(400, "Sorry bad request. The request could not be undestood.");
		errorText.put(404, "Sorry This was dead link - Page not Found");
	}

	public static String getErrorPage(int error) {
		switch (error) {
		case 400:
			return get400();
		default:
			return get404();
		}
	}

	private static String get404() {
		String filename = "files/errorTemplate.html";
		return readFile(filename, 404);
	}

	private static String get400() {
		String filename = "files/errorTemplate.html";
		return readFile(filename, 400);
	}

	public static String readFile(String filename, int error) {
		String content = "HTTP/1.1 " + error + " \n\n";
		try {
			for (String line : Files.readAllLines(Paths.get(filename))) {
				if (line.contains("statusCodeMessage"))
					content += "<p>" + errorText.get(error) + "</p>";
				else if (line.contains("statusCode"))
					content += "<h1>" + error + "</h1>";
				else
					content += line;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return "HTTP/1.1 " + error + " \n";
		}
		return content;
	}

}

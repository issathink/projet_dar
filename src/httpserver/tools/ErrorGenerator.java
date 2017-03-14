package httpserver.tools;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

public class ErrorGenerator {

	public static HashMap<Integer, String> errorText = new HashMap<>();
	static {
		errorText.put(StatusCodes.ErrorBadRequest, "Sorry bad request. The request could not be undestood.");
		errorText.put(StatusCodes.ErrorNotFound, "Sorry This was dead link - Page not Found");
	}

	public static String getErrorPage(int error) {
		switch (error) {
		case StatusCodes.ErrorBadRequest:
			return get400();
		default:
			return get404();
		}
	}

	private static String get404() {
		String filename = "files/errorTemplate.html";
		return readFile(filename, StatusCodes.ErrorNotFound);
	}

	private static String get400() {
		String filename = "files/errorTemplate.html";
		return readFile(filename, StatusCodes.ErrorBadRequest);
	}

	public static String readFile(String filename, int error) {
		String content = "HTTP/1.1 " + error + " \n\n";
		try {
			for (String line : Files.readAllLines(Paths.get(filename)))
				content += line;
			content = content.replace("statusCodeMessage", "<p>" + errorText.get(error) + "</p>");
			content = content.replace("statusCode", "<p><h1>" + error + "</h1>");
		} catch (IOException e) {
			e.printStackTrace();
			return "HTTP/1.1 " + error + " \n";
		}
		return content;
	}

}

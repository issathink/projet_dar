package httpserver.format;

import java.util.Date;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import httpserver.tools.HttpServerRequest;
import httpserver.tools.HttpServerResponse;

public class Formatter {
	public static HttpServerResponse formatHttpRequest(HttpServerRequest req) {
		String contentType = req.getParams().get("Content-Type");

		if (contentType != null) {
			System.out.println("ContentType: " + contentType);
			if (contentType.equals("text/html"))
				return formatTextHtml(req);
			else if (contentType.equals("application/json"))
				return formatApplicationJson(req);
		}

		return formatTextPlain(req);
	}

	public static HttpServerResponse formatTextPlain(HttpServerRequest req) {

		HttpServerResponse response = new HttpServerResponse();
		response.setError(req.getError());
		response.setContentType("text/plain");
		response.setDate(new Date());

		String tmp = "";
		for (Map.Entry<String, String> entry : req.getParams().entrySet())
			tmp += entry.getKey() + ": " + entry.getValue() + "\n";
		String content = tmp;
		response.setContent(content);

		return response;
	}

	public static HttpServerResponse formatTextHtml(HttpServerRequest req) {
		HttpServerResponse response = new HttpServerResponse();
		response.setError(req.getError());
		response.setContentType("text/html");
		response.setDate(new Date());

		String tmp = "";
		for (Map.Entry<String, String> entry : req.getParams().entrySet())
			tmp += entry.getKey() + ":" + entry.getValue() + "\n";
		String content = tmp;

		response.setContent(content);
		return response;
	}

	public static HttpServerResponse formatApplicationJson(HttpServerRequest req) {
		HttpServerResponse response = new HttpServerResponse();
		response.setError(req.getError());
		response.setContentType("application/json");
		response.setDate(new Date());
		JSONObject content = new JSONObject();
		
		try {
			for (Map.Entry<String, String> entry : req.getParams().entrySet())
				content.append(entry.getKey(), entry.getValue());
			response.setContent(content.toString());
			System.out.println(content.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return response;
	}
}

package httpserver.urlrouting;

import httpserver.format.Formatter;
import httpserver.tools.HttpServerRequest;
import httpserver.tools.HttpServerResponse;
import httpserver.tools.Util;

import org.json.JSONException;
import org.json.JSONObject;

public class URLRouter {
	private static final String MAPPING_FILE = "mapping.json";
	private static JSONObject mapping;

	public static HttpServerResponse route(HttpServerRequest request) {
		mapping = Util.getMapping(MAPPING_FILE);
		System.out.println(mapping.toString());

		if (mapping == null) {
			HttpServerResponse response = new HttpServerResponse();
			response.setError(400);
			return response;
		}
		
		URL url = request.getUrl();
		String mappingUrl;
		
		try {
			mappingUrl = (String) mapping.get(url.getPath().get(0));
		} catch (JSONException e) {
			e.printStackTrace();
			HttpServerResponse response = new HttpServerResponse();
			response.setError(400);
			return response;
		}

		switch (request.getRequestMethod()) {
		case PUT:
			return routePUT(request, mappingUrl);
		case POST:
			return routePOST(request, mappingUrl);
		case DELETE:
			return routeDEL(request, mappingUrl);
		default:
			return routeGET(request, mappingUrl);
		}
	}

	public static HttpServerResponse routeGET(HttpServerRequest request,
			String mappingUrl) {
		
		System.out.println(mappingUrl);
		return Formatter.formatHttpRequest(request);
	}

	public static HttpServerResponse routePUT(HttpServerRequest request,
			String mappingUrl) {
		System.out.println(mappingUrl);
		return Formatter.formatHttpRequest(request);
	}

	public static HttpServerResponse routePOST(HttpServerRequest request,
			String mappingUrl) {
		System.out.println(mappingUrl);
		return Formatter.formatHttpRequest(request);
	}

	public static HttpServerResponse routeDEL(HttpServerRequest request,
			String mappingUrl) {
		System.out.println(mappingUrl);
		return Formatter.formatHttpRequest(request);
	}

}
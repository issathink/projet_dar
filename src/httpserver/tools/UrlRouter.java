package httpserver.tools;

import httpserver.format.Formatter;

import org.json.JSONObject;

public class UrlRouter {
	private static final String MAPPING_FILE = "mapping.json";
	private static JSONObject mapping;

	public static HttpServerResponse route(HttpServerRequest request) {
		mapping = Util.getMapping(MAPPING_FILE);

		if (mapping == null) {
			HttpServerResponse response = new HttpServerResponse();
			response.setError(400);
			return response;
		}

		switch (request.getRequestMethod()) {
		case PUT:
			return routePUT(request, mapping);
		case POST:
			return routePOST(request, mapping);
		case DELETE:
			return routeDEL(request, mapping);
		default:
			return routeGET(request, mapping);
		}
	}

	public static HttpServerResponse routeGET(HttpServerRequest request,
			JSONObject mapping) {
		System.out.println(mapping.toString());
		return Formatter.formatHttpRequest(request);
	}

	public static HttpServerResponse routePUT(HttpServerRequest request,
			JSONObject mapping) {
		System.out.println(mapping.toString());
		return Formatter.formatHttpRequest(request);
	}

	public static HttpServerResponse routePOST(HttpServerRequest request,
			JSONObject mapping) {
		System.out.println(mapping.toString());
		return Formatter.formatHttpRequest(request);
	}

	public static HttpServerResponse routeDEL(HttpServerRequest request,
			JSONObject mapping) {
		System.out.println(mapping.toString());
		return Formatter.formatHttpRequest(request);
	}

}
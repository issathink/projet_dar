package httpserver.urlrouting;

import httpserver.format.Formatter;
import httpserver.tools.HttpServerRequest;
import httpserver.tools.HttpServerResponse;
import httpserver.tools.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class URLRouter {
	private static final String MAPPING_FILE = "mapping.json";
	private static JSONArray mapping;

	public static HttpServerResponse route(HttpServerRequest request) {
		mapping = Util.getMapping(MAPPING_FILE);
		System.out.println(mapping.toString());
		URL url = request.getUrl();
		String mappingUrl = null;
		HttpServerResponse response = new HttpServerResponse();
		response.setError(400);
		
		if (mapping == null || url == null || url.getPath().isEmpty())
			return response;
		
		try {
			String path = url.getPath().get(0);
			JSONObject map;
			
			for (int i=0; i<mapping.length(); i++) {
				map = mapping.getJSONObject(i);
				if(map.has(path)) {
					System.out.println(map + " path: " + path);
					mappingUrl = map.getString(path);
				}
			}
			
			if(mappingUrl == null) {
				response.setError(404);
				return response;
			}
		} catch (JSONException e) {
			e.printStackTrace();
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
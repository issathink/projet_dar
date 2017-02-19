package httpserver.urlrouting;

import httpserver.format.Formatter;
import httpserver.interfaces.IServlet;
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

		return delegate(request, mappingUrl);
	}

	@SuppressWarnings("unchecked")
	private static HttpServerResponse delegate(HttpServerRequest request,
			String mappingUrl) {
		System.out.println(mappingUrl);
		try {
			Class<IServlet> cls = (Class<IServlet>) Class.forName(mappingUrl);
			IServlet instance = cls.newInstance();
			
			switch (request.getRequestMethod()) {
			case PUT:
				return instance.put(request);
			case POST:
				return instance.post(request);
			case DELETE:
				return instance.delete(request);
			default:
				return instance.get(request);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return Formatter.formatHttpRequest(request);
	}

}
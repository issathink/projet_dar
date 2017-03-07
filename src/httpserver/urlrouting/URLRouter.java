package httpserver.urlrouting;

import httpserver.format.Formatter;
import httpserver.interfaces.IServlet;
import httpserver.tools.HttpServerRequest;
import httpserver.tools.HttpServerResponse;
import httpserver.tools.Util;

import java.util.ArrayList;

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

		ArrayList<String> pathParams = url.getPath();
		ArrayList<String> resPathParams = new ArrayList<>();

		try {
			JSONObject map;
			boolean found = true;
			
			for (int i = 0; i < mapping.length(); i++) {
				map = mapping.getJSONObject(i);
				if (map.has("routing")) {
					String[] routString = map.getString("routing").split("\\?")[0].split("\\/");
					if (routString.length != pathParams.size())
						continue;

					for (int j = 0; j < routString.length; j++) {						
						if(!routString[i].equals(pathParams.get(i)) 
								&& !routString[i].startsWith("<") 
								&& !routString[i].endsWith(">")) {
							found = false;
						}
					}
				}
			}

			if (!found) {
				response.setError(404);
				return response;
			}
		} catch (JSONException e) {
			e.printStackTrace();
			return response;
		}

		return delegate(request, mappingUrl, resPathParams);
	}

	@SuppressWarnings("unchecked")
	private static HttpServerResponse delegate(HttpServerRequest request, String mappingUrl, ArrayList<String> pathParams) {
		System.out.println(mappingUrl);
		try {
			Class<IServlet> cls = (Class<IServlet>) Class.forName(mappingUrl);
			IServlet instance = cls.newInstance();

			switch (request.getRequestMethod()) {
			case PUT:
				return instance.put(request, pathParams);
			case POST:
				return instance.post(request, pathParams);
			case DELETE:
				return instance.delete(request, pathParams);
			default:
				return instance.get(request, pathParams);
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
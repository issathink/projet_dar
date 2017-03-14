package httpserver.urlrouting;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import httpserver.format.Formatter;
import httpserver.interfaces.IServlet;
import httpserver.tools.HttpServerRequest;
import httpserver.tools.HttpServerResponse;
import httpserver.tools.StatusCodes;
import httpserver.tools.Util;

public class URLRouter {
	private static final String MAPPING_FILE = "mapping.json";
	private static JSONArray mapping;

	public static HttpServerResponse route(HttpServerRequest request) {
		String mappingClass = "", mappingMethod = "";
		mapping = Util.getMapping(MAPPING_FILE);
		URL url = request.getUrl();
		HttpServerResponse response = new HttpServerResponse();
		response.setError(StatusCodes.ErrorBadRequest);

		if (mapping == null || url == null || url.getPath().isEmpty())
			return response;

		ArrayList<String> pathParams = url.getPath();
		ArrayList<String> resPathParams = new ArrayList<>();

		try {
			JSONObject map;
			boolean found = true;

			for (int i = 0; i < mapping.length(); i++) {
				map = mapping.getJSONObject(i);
				found = true;

				if (map.has("routing")) {
					String route = map.getString("routing");
					if(route.startsWith("/"))
						route = route.substring(1, route.length());
					String[] routString = route.split("\\?")[0].split("\\/");
					if (routString.length != pathParams.size())
						continue;

					for (int j = 0; j < routString.length; j++){ 
						if(routString[j].startsWith("<") && routString[j].endsWith(">")){
							// Recuperer la valeur ?
							resPathParams.add(pathParams.get(j));
							continue;
						}
						if (!routString[j].equals(pathParams.get(j))) {
							found = false;
							break;
						}
					}
					if (found) {
						System.out.println("Jai trouve un truc qui match " + resPathParams);
						// Arrays.stream(routString).forEach(a -> System.out.print(" | " + a));
						String methodFullName = map.getString("method");
						int lastIndexOfDot = methodFullName.lastIndexOf('.');
						mappingClass = methodFullName.substring(0, lastIndexOfDot);
						mappingMethod = methodFullName.substring(lastIndexOfDot+1, methodFullName.length());
						break;
					}
				}
			}

			if (!found) {
				response.setError(StatusCodes.ErrorNotFound);
				return response;
			}
		} catch (JSONException e) {
			e.printStackTrace();
			return response;
		}
		return delegate(request, mappingClass, mappingMethod, resPathParams);
	}

	@SuppressWarnings("unchecked")
	private static HttpServerResponse delegate(HttpServerRequest request, String mappingClass, String methodName,
			ArrayList<String> pathParams) {
		System.out.println("delegate mappingClass: " + mappingClass + " methodName: " + methodName + "\n" + pathParams);
		
		try {
			Class<IServlet> cls = (Class<IServlet>) Class.forName(mappingClass);
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
package httpserver.urlrouting;

import httpserver.tools.HttpServerRequest;
import httpserver.tools.HttpServerResponse;
import httpserver.tools.StatusCodes;
import httpserver.tools.Util;

import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class URLRouter {
	private static final String MAPPING_FILE = "mapping.json";
	private static JSONArray mapping;

	@SuppressWarnings("rawtypes")
	public static HttpServerResponse route(HttpServerRequest request) {
		String mappingClass = "", mappingMethod = "";
		if(mapping == null)
			mapping = Util.getMapping(MAPPING_FILE);
		URL url = request.getUrl();
		System.out.println("Url paths: " + url.getPath());
		HttpServerResponse response = new HttpServerResponse();
		response.setError(StatusCodes.ErrorBadRequest);

		if (mapping == null || url == null || url.getPath().isEmpty())
			return response;

		ArrayList<String> pathParams = url.getPath();
		ArrayList<Object> resPathParams = new ArrayList<>();
		ArrayList<Class> resPathParamsClass = new ArrayList<>();
		
		if(URLRouter.staticFiles(pathParams.get(pathParams.size()-1))) {
			return handleStaticFiles(pathParams, mapping);
		}

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
							String mappingValue = routString[j].substring(1, routString[j].length()-1);
							// Verifier si routing correspond sinon break
							if(Util.isMatchingFromMapping(pathParams.get(j), mappingValue)){
								resPathParams.add(Util.getObjectFromMapping(mappingValue, pathParams.get(j)));
								resPathParamsClass.add(Util.getClassFromMapping(mappingValue));
								continue;
							}else{
								found = false;
								break;
							}
						}
						if (!routString[j].equals(pathParams.get(j))) {
							found = false;
							break;
						}
					}
					if (found) {
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
		return delegate(request, mappingClass, mappingMethod, resPathParams, resPathParamsClass);
	}

	@SuppressWarnings("rawtypes")
	private static HttpServerResponse delegate(HttpServerRequest request, String mappingClass, String methodName,
			ArrayList<Object> pathParams, ArrayList<Class> pathParamsClass) throws SecurityException {
		HttpServerResponse response = new HttpServerResponse();
		response.setSessionId(request.getSessionId());
		
		if(methodName == null || methodName.equals("")){
			response.setError(StatusCodes.ErrorNotFound);
			return response;
		}
		Class classOfParams[] = new Class[pathParams.size()+2];
		Object mesObject[] = new Object[pathParams.size()+2];
		int i[] = {2};
		pathParamsClass.forEach((n) -> classOfParams[i[0]++] = n); // Add the classes 
		i[0] = 2;
		mesObject[0] = request;
		mesObject[1] = response;
		classOfParams[0] = HttpServerRequest.class;
		classOfParams[1] = HttpServerResponse.class;
		pathParams.forEach( n -> mesObject[i[0]++] = n); // Add the objects
		try {
			Class<?> cls = Class.forName(mappingClass);
			Object instance = cls.newInstance();
			Method m = cls.getMethod(methodName, classOfParams);
			m.invoke(instance, mesObject);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	private static HttpServerResponse handleStaticFiles(ArrayList<String> pathParams, JSONArray mapping) {
		HttpServerResponse response = new HttpServerResponse();
		JSONObject map;
		boolean found = false;
		String repo = null;
		
		for (int i = 0; i < mapping.length(); i++) {
			try {
				map = mapping.getJSONObject(i);
				if(map.has("static")) {
					found  = true;
					repo = map.getString("static");
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(found) {
			repo += "/";
			if(pathParams.size() > 0) {
				for(int i=0; i<pathParams.size()-1; i++)
					repo += pathParams.get(i) + "/";
				repo += pathParams.get(pathParams.size()-1);
				try {
					byte[] encoded = Files.readAllBytes(Paths.get(repo));
					response.setContent(new String(encoded));
					response.setContentType(getContentType(pathParams.get(pathParams.size()-1)));
				} catch (Exception e) {
					response.setError(StatusCodes.ErrorNotFound);
				}
			} else {
				response.setError(StatusCodes.ErrorNotFound);
			}
		} else {
			response.setError(StatusCodes.ErrorNotFound);
		}
		return response;
	}

	private static String getContentType(String filename) {
		if(filename.endsWith(".html"))
			return Util.TEXT_HTML;
		else if(filename.endsWith(".js"))
			return Util.APPLICATION_JS;
		else if(filename.endsWith(".css"))
			return Util.TEXT_CSS;
		return Util.TEXT_PLAIN;
	}

	private static boolean staticFiles(String filename) {
		if(filename.endsWith(".html"))
			return true;
		else if(filename.endsWith(".js"))
			return true;
		else if(filename.endsWith(".css"))
			return true;
		return false;
	}

}
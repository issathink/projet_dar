package httpserver;

import httpserver.tools.HttpServerRequest;
import httpserver.tools.RequestMethod;
import httpserver.tools.Util;
import httpserver.urlrouting.URL;

public class AnalyseRequest {

	public static HttpServerRequest analyseRequest(String source) {
		HttpServerRequest request = new HttpServerRequest();
		String[] sourceTab = source.split("\n\n"); 
		
		String[] result = sourceTab[0].split("\n");
		int hostIndex = -1;
		// parse request method and url
		URL url = null;
		String first = result[0];
		// System.out.println("Lol "+result[1]);
		for (RequestMethod req : Util.getRequestMethodHeaders()) {
			if (first.contains(req.toString())) {
				request.setRequestMethod(req);
				url = new URL(first.split(" ")[1]);
				request.setUrl(url);
				break;
			}
		}

		if (url == null)
			request.setError(400);

		// parse host
		String host = null;
		for (int i = 0; i < result.length; i++) {
			if (result[i].toLowerCase().contains("host:")) {
				host = result[i].split(" ")[1];
				request.setHost(host);
				break;
			}
		}

		if (host == null)
			request.setError(400);	
		for(int i = 1; i < result.length; i++) {
			if(i != hostIndex) {
				String[] tmp = result[i].split(":");
				request.addParam(tmp[0].trim(), tmp[1].trim());
			}
		}
		
		// TODO update session
		
		if(sourceTab.length > 1)
			request.setBody(sourceTab[1]);

		return request;
	}
	
	public static String getSessionId(HttpServerRequest request) {
		// TODO
		String cookie = request.getParams().get("Cookie");
		if(cookie == null) {
			
			return null;
		}
		return null;
	}

}

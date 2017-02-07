package httpserver;

import httpserver.tools.HttpServerRequest;
import httpserver.tools.RequestMethod;
import httpserver.tools.Util;

public class AnalyseRequest {

	public static HttpServerRequest analyseRequest(String source) throws Exception {
		HttpServerRequest request = new HttpServerRequest();
		String[] result = source.split("\n");
		int hostIndex = -1;

		// parse request method and url
		String url = null;
		String first = result[0];

		for (RequestMethod req : Util.getRequestMethodHeaders()) {
			if (first.contains(req.toString())) {
				request.setRequestMethod(req);
				url = first.split(" ")[1];
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
			}
		}

		if (host == null)
			request.setError(400);	

		for(int i=1; i<result.length; i++) {
			if(i != hostIndex) {
				String[] tmp = result[i].split(":");
				request.addParam(tmp[0].trim(), tmp[1].trim());
			}
		}

		return request;
	}

}

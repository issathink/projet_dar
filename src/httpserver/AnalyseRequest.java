package httpserver;

import httpserver.sessions.HttpSession;
import httpserver.sessions.HttpSessionManager;
import httpserver.tools.HttpServerRequest;
import httpserver.tools.RequestMethod;
import httpserver.tools.StatusCodes;
import httpserver.tools.Util;
import httpserver.urlrouting.URL;

public class AnalyseRequest {

	public static HttpServerRequest analyseRequest(String source) {
		HttpServerRequest request = new HttpServerRequest();

		String[] result = source.split("\n");
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
			request.setError(StatusCodes.ErrorBadRequest);

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
			request.setError(StatusCodes.ErrorBadRequest);
		for (int i = 1; i < result.length; i++) {
			if (i != hostIndex) {
				String[] tmp = result[i].split(":");
				request.addParam(tmp[0].trim(), tmp[1].trim());
			}
		}

		// update or create new session
		request.setSessionId(sessionId(request));

		return request;
	}

	public static String sessionId(HttpServerRequest request) {
		String cookie = request.getParams().get("Cookie");
		String sessionId = null;

		if (cookie != null) {
			sessionId = HttpSessionManager.getSessionIdFromCookie(cookie);
			if (sessionId != null) {
				HttpSession session = HttpSessionManager.getSession(sessionId);
				if (session != null) {
					session.setLastAccessedToNow();
					return sessionId;
				}
				sessionId = null;
			}
		}

		sessionId = HttpSessionManager.generateSessionId();
		HttpSessionManager.addSession(sessionId, new HttpSession());
		return sessionId;
	}

}

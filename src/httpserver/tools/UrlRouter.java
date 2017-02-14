package httpserver.tools;

public class UrlRouter {

	public static HttpServerResponse route(HttpServerRequest request) {

		switch (request.getRequestMethod()) {
		case PUT:
			return routePUT(request);
		case POST:
			return routePOST(request);
		case DELETE:
			return routeDEL(request);
		default:
			return routeGET(request);
		}
	}

	public static HttpServerResponse routeGET(HttpServerRequest request) {
		return null;
	}

	public static HttpServerResponse routePUT(HttpServerRequest request) {
		return null;
	}


	public static HttpServerResponse routePOST(HttpServerRequest request) {
		return null;
	}
	
	public static HttpServerResponse routeDEL(HttpServerRequest request) {
		return null;
	}

}
package httpserver.interfaces;

import httpserver.tools.HttpServerRequest;
import httpserver.tools.HttpServerResponse;

public interface IServlet {
	
	public HttpServerResponse get(HttpServerRequest request);
	public HttpServerResponse put(HttpServerRequest request);
	public HttpServerResponse post(HttpServerRequest request);
	public HttpServerResponse delete(HttpServerRequest request);

}

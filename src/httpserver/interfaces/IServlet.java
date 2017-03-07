package httpserver.interfaces;

import java.util.ArrayList;

import httpserver.tools.HttpServerRequest;
import httpserver.tools.HttpServerResponse;

public interface IServlet {
	
	public HttpServerResponse get(HttpServerRequest request, ArrayList<String> pathParams);
	public HttpServerResponse put(HttpServerRequest request, ArrayList<String> pathParams);
	public HttpServerResponse post(HttpServerRequest request, ArrayList<String> pathParams);
	public HttpServerResponse delete(HttpServerRequest request, ArrayList<String> pathParams);

}

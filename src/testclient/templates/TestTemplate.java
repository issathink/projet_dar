package testclient.templates;

import java.util.ArrayList;
import java.util.HashMap;

import httpserver.interfaces.IServlet;
import httpserver.tools.HttpServerRequest;
import httpserver.tools.HttpServerResponse;

public class TestTemplate implements IServlet {
	
	private static HashMap<String, Object> content = new HashMap<>();
	static {
		content.put("var1", "test 1");
		content.put("var2", "test 2");
	}
	
	@Override
	public HttpServerResponse get(HttpServerRequest request, ArrayList<String> pathParams) {
		return null;
	}

	@Override
	public HttpServerResponse put(HttpServerRequest request, ArrayList<String> pathParams) {
		return null;
	}

	@Override
	public HttpServerResponse post(HttpServerRequest request, ArrayList<String> pathParams) {
		return null;
	}

	@Override
	public HttpServerResponse delete(HttpServerRequest request, ArrayList<String> pathParams) {
		return null;
	}

}

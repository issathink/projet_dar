package testclient;

import httpserver.interfaces.IServlet;
import httpserver.tools.HttpServerRequest;
import httpserver.tools.HttpServerResponse;
import httpserver.urlrouting.URL;

public class ListServlet implements IServlet {

	@Override
	public HttpServerResponse get(HttpServerRequest request) {
		HttpServerResponse response = new HttpServerResponse();
		URL url = request.getUrl();
		
		try {
			if (url != null) {
				ListPoint2D list = new ListPoint2D();
				String cmd = url.getPath().get(0);
				if (cmd.equals("list")) {
					response.setContent(list.list().toString());
				} else if (cmd.equals("p")) {
					int id = Integer.parseInt(url.getPath().get(1));
					String coord = url.getPath().get(2);
					
					if (coord.equals("x"))
						response.setContent(list.getX(id) + "");
					else if (coord.equals("y"))
						response.setContent(list.getY(id) + "");
					else
						response.setError(400);
				}
			} else {
				response.setError(404);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setError(400);
		}
		return response;
	}

	@Override
	public HttpServerResponse put(HttpServerRequest request) {
		HttpServerResponse response = new HttpServerResponse();
		URL url = request.getUrl();
		
		try {
			if(url != null) {
				if(url.getPath().get(0).equals("p")) {
					
				}
			} else {
				response.setError(404);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setError(400);
		}
		return response;
	}

	@Override
	public HttpServerResponse post(HttpServerRequest request) {
		String body = request.getBody();
		if(body != null) {
			
		} else {
			
		}
		return null;
	}

	@Override
	public HttpServerResponse delete(HttpServerRequest request) {
		return null;
	}

}

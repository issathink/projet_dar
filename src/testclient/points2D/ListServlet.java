package testclient.points2D;

import java.util.ArrayList;

import httpserver.interfaces.IServlet;
import httpserver.tools.HttpServerRequest;
import httpserver.tools.HttpServerResponse;
import httpserver.tools.StatusCodes;
import httpserver.urlrouting.URL;

public class ListServlet implements IServlet {

	@Override
	public HttpServerResponse get(HttpServerRequest request, ArrayList<String> pathParams) {
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
						response.setError(StatusCodes.ErrorBadRequest);
				}
			} else {
				response.setError(StatusCodes.ErrorNotFound);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setError(StatusCodes.ErrorBadRequest);
		}
		System.out.println("Reponse: " + response.toString());
		return response;
	}

	@Override
	public HttpServerResponse put(HttpServerRequest request, ArrayList<String> pathParams) {
		HttpServerResponse response = new HttpServerResponse();
		URL url = request.getUrl();

		try {
			if (url != null) {
				ListPoint2D list = new ListPoint2D();
				String cmd = url.getPath().get(0);

				if (cmd.equals("p")) {
					int id = Integer.parseInt(url.getPath().get(1));
					int x = Integer.parseInt(url.getParams().get("x"));
					int y = Integer.parseInt(url.getParams().get("y"));
					list.put(id, x, y);
				} else {
					response.setError(StatusCodes.ErrorNotFound);
				}
			} else {
				response.setError(StatusCodes.ErrorNotFound);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setError(StatusCodes.ErrorBadRequest);
		}
		return response;
	}

	@Override
	public HttpServerResponse post(HttpServerRequest request, ArrayList<String> pathParams) {
		HttpServerResponse response = new HttpServerResponse();
		URL url = request.getUrl();

		try {
			if (url != null) {
				ListPoint2D list = new ListPoint2D();
				String cmd = url.getPath().get(0);

				if (cmd.equals("p")) {
					String[] bodyContent = request.getBody().split("\n");
					int x = Integer.parseInt(bodyContent[0]);
					int y = Integer.parseInt(bodyContent[1]);
					int id = list.post(x, y);
					response.setContent(id + "");
				} else {
					response.setError(StatusCodes.ErrorNotFound);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setError(StatusCodes.ErrorBadRequest);
		}
		return response;
	}

	@Override
	public HttpServerResponse delete(HttpServerRequest request, ArrayList<String> pathParams) {
		HttpServerResponse response = new HttpServerResponse();
		URL url = request.getUrl();

		try {
			if (url != null) {
				ListPoint2D list = new ListPoint2D();
				String cmd = url.getPath().get(0);

				if (cmd.equals("p")) {
					int id = Integer.parseInt(url.getPath().get(1));
					boolean result = list.delete(id);
					response.setContent(result + "");
				} else {
					response.setError(StatusCodes.ErrorNotFound);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setError(StatusCodes.ErrorBadRequest);
		}
		return response;
	}

}

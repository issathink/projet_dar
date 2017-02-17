package httpserver.tools;

import java.util.HashMap;
import java.util.Map;

public class HttpServerRequest {

	private String host;
	private URL url;
	private RequestMethod requestMethod;
	private int error;

	private Map<String, String> params;
	
	public HttpServerRequest() {
		params = new HashMap<>();
		error = -1;
	}
	
	public int getError() {
		return error;
	}
	
	public void setError(int error) {
		this.error = error;
	}

	public URL getUrl() {
		return url;
	}
	
	public void setUrl(URL url) {
		this.url = url;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public RequestMethod getRequestMethod() {
		return requestMethod;
	}

	public void setRequestMethod(RequestMethod requestMethod) {
		this.requestMethod = requestMethod;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}

	public void addParam(String key, String value) {
		this.params.put(key, value);
	}
	
	@Override
	public String toString() {
		String tmp = "";
		// System.out.println("Jai ca aussi pardon "+params.size());
		for(Map.Entry<String, String> entry: params.entrySet())
			tmp += entry.getKey() + ":" + entry.getValue() + "\n";
		
		return requestMethod 
				+ " " + url + "\n"
				+ "Host:" + host + "\n"
				+ tmp;
	}
	
}

package httpserver.tools;

import java.util.Date;

public class HttpServerResponse {
	
	private StatusCode statusCode;
	private String requestUrl;
	private RequestMethod requestMethod;
	private String remoteAddress;
	private String contentType;
	private Date date;
	private String content;
	private int error = -1;
	
	public HttpServerResponse() {
		this.date = new Date();
	}
	
	public int getError() {
		return error;
	}

	public void setError(int error) {
		this.error = error;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public StatusCode getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(StatusCode statusCode) {
		this.statusCode = statusCode;
	}

	public String getRequestUrl() {
		return requestUrl;
	}

	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}

	public RequestMethod getRequestMethod() {
		return requestMethod;
	}

	public void setRequestMethod(RequestMethod requestMethod) {
		this.requestMethod = requestMethod;
	}

	public String getRemoteAddress() {
		return remoteAddress;
	}

	public void setRemoteAddress(String remoteAddress) {
		this.remoteAddress = remoteAddress;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	@Override
	public String toString() {
		if(error != -1)
			return ErrorGenerator.getErrorPage(error); // "HTTP/1.1 " + error + "\n";
		
		String res = "HTTP/1.1 200 OK\n" 
				+ "Date: " + getDate() + "\n"
				+ "Content-Type:" + contentType + "\n\n"
				+ getContent();
		return res;
	}
}

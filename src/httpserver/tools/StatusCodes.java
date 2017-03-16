package httpserver.tools;

import java.util.ArrayList;
import java.util.List;

public class StatusCodes {

	public static List<StatusCode> statusCodes = new ArrayList<>();
	public static final int ErrorBadRequest = 404;
	public static final int ErrorNotFound = 404;

	public StatusCodes() {
		statusCodes.add(new StatusCode(100, "Continue"));
		statusCodes.add(new StatusCode(101, "Switching Protocols"));
		statusCodes.add(new StatusCode(102, "Processing")); /* WebDAV */
		statusCodes.add(new StatusCode(200, "OK"));
		statusCodes.add(new StatusCode(201, "Created"));
		statusCodes.add(new StatusCode(202, "Accepted"));
		statusCodes.add(new StatusCode(203, "Non-Authoritative Information"));
		statusCodes.add(new StatusCode(204, "No Content"));
		statusCodes.add(new StatusCode(205, "Reset Content"));
		statusCodes.add(new StatusCode(206, "Partial Content"));
		statusCodes.add(new StatusCode(207, "Multi-status")); /* WebDAV */
		statusCodes.add(new StatusCode(300, "Multiple Choices"));
		statusCodes.add(new StatusCode(301, "Moved Permanently"));
		statusCodes.add(new StatusCode(302, "Found"));
		statusCodes.add(new StatusCode(303, "See Other"));
		statusCodes.add(new StatusCode(304, "Not Modified"));
		statusCodes.add(new StatusCode(305, "Use Proxy"));
		statusCodes.add(new StatusCode(306, "(Unused)"));
		statusCodes.add(new StatusCode(307, "Temporary Redirect"));
		statusCodes.add(new StatusCode(400, "Bad Request"));
		statusCodes.add(new StatusCode(401, "Unauthorized"));
		statusCodes.add(new StatusCode(402, "Payment Required"));
		statusCodes.add(new StatusCode(403, "Forbidden"));
		statusCodes.add(new StatusCode(404, "Not Found"));
		statusCodes.add(new StatusCode(405, "Method Not Allowed"));
		statusCodes.add(new StatusCode(406, "Not Acceptable"));
		statusCodes.add(new StatusCode(407, "Proxy Authentication Required"));
		statusCodes.add(new StatusCode(408, "Request Timeout"));
		statusCodes.add(new StatusCode(409, "Conflict"));
		statusCodes.add(new StatusCode(410, "Gone"));
		statusCodes.add(new StatusCode(411, "Length Required"));
		statusCodes.add(new StatusCode(412, "Precondition Failed"));
		statusCodes.add(new StatusCode(413, "Request Entity Too Large"));
		statusCodes.add(new StatusCode(414, "Request-URI Too Long"));
		statusCodes.add(new StatusCode(415, "Unsupported Media Type"));
		statusCodes.add(new StatusCode(416, "Requested Range Not Satisfiable"));
		statusCodes.add(new StatusCode(417, "Expectation Failed"));
		statusCodes.add(new StatusCode(422, "Unprocessable Entity")); /* WebDAV */
		statusCodes.add(new StatusCode(423, "Locked")); /* WebDAV */
		statusCodes.add(new StatusCode(424, "Failed Dependency")); /* WebDAV */
		statusCodes.add(new StatusCode(426, "Upgrade Required")); /* TLS */
		statusCodes.add(new StatusCode(500, "Internal Server Error"));
		statusCodes.add(new StatusCode(501, "Not Implemented"));
		statusCodes.add(new StatusCode(502, "Bad Gateway"));
		statusCodes.add(new StatusCode(503, "Service Not Available"));
		statusCodes.add(new StatusCode(504, "Gateway Timeout"));
		statusCodes.add(new StatusCode(505, "HTTP Version Not Supported"));
		statusCodes.add(new StatusCode(507, "Insufficient Storage"));
	}

}

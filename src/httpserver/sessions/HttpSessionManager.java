package httpserver.sessions;

import java.util.HashMap;
import java.util.UUID;

public class HttpSessionManager {
	public static final String COOKIE_NAME = "__mein_cook";
	private static HashMap<String, HttpSession> sessions;
	private static HttpSessionManager manager = new HttpSessionManager();

	private HttpSessionManager() {
		sessions = new HashMap<>();
	}

	public synchronized HttpSessionManager getSessionManager() {
		return manager;
	}

	public static HashMap<String, HttpSession> getSessions() {
		return sessions;
	}

	public synchronized static void addSession(String sessionId, HttpSession session) {
		sessions.put(sessionId, session);
	}

	public synchronized static HttpSession getSession(String sessionId) {
		return sessions.get(sessionId);
	}
	
	public static String generateSessionId() {
		return UUID.randomUUID() + "" +System.currentTimeMillis();
	}
	
	public static String getSessionIdFromCookie(String cookie) {
		String session = null;
		String[] elems = cookie.split(";");
		
		for(String e: elems)
			if(e.contains(COOKIE_NAME))
				return e.split("=")[1];
		
		return session;
	}
}

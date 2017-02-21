package httpserver.sessions;

import java.util.HashMap;

public class HttpSessionManager {
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
}

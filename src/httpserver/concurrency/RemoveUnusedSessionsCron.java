 package httpserver.concurrency;

import java.util.ArrayList;
import java.util.Map.Entry;

import httpserver.sessions.HttpSession;
import httpserver.sessions.HttpSessionManager;

public class RemoveUnusedSessionsCron extends Thread {

	private static int DELAY = 300000;

	@Override
	public void run() {
		 try {
			 while(true) {
	            Thread.sleep(DELAY);
	            System.out.println("/// " + HttpSessionManager.getSessions());

	            ArrayList<String> sessionIds = new ArrayList<>();
	            for (Entry<String, HttpSession> entry : HttpSessionManager.getSessions().entrySet())
					if(!entry.getValue().isValid())
						sessionIds.add(entry.getKey());
	            for (String s: sessionIds)
	            	HttpSessionManager.getSessions().remove(s);
			 }
	     } catch (Exception e){
	            System.err.println(e);
	     }
	}
}

package httpserver.sessions;

import java.time.temporal.ChronoUnit;
import java.util.Date;

public class HttpSession {
	private Date creation;
	private Date lastAccessedTime;
	private Object content;
	
	public HttpSession() {
		this.creation = new Date();
		this.lastAccessedTime = new Date();
		content = null;
	}

	public Date getLastAccessedTime() {
		return lastAccessedTime;
	}

	public void setLastAccessedTime(Date lastAccessedTime) {
		this.lastAccessedTime = lastAccessedTime;
	}

	public Date getCreation() {
		return creation;
	}

	public void setLastAccessedToNow() {
		this.lastAccessedTime = new Date();
	}
	
	public boolean isValid() {
		return lastAccessedTime.toInstant().plus(30, ChronoUnit.MINUTES).isAfter(new Date().toInstant());
	}
	
	public Object getContent() {
		return content;
	}
	
	public void setContent(Object content) {
		this.content = content;
	}
	
	@Override
	public String toString() {
		return "LastAccessed: " + lastAccessedTime;
	}

}

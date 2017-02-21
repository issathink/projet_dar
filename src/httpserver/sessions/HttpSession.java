package httpserver.sessions;

import java.time.temporal.ChronoUnit;
import java.util.Date;

public class HttpSession {
	private Date creation;
	private Date lastAccessedTime;
	
	public HttpSession() {
		this.creation = new Date();
		this.lastAccessedTime = new Date();
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

	public void setCreation(Date creation) {
		this.creation = creation;
	}
	
	public boolean isValid() {
		return lastAccessedTime.toInstant().plus(30, ChronoUnit.MINUTES).isAfter(new Date().toInstant());
	}

}

package exemple;

public class User {
	private String username, pwd;
	private double lat, lng;
	
	public User(String username, String pwd, String lat, String lng) {
		this.username = username;
		this.pwd = pwd;
		this.lat = Double.parseDouble(lat);
		this.lng = Double.parseDouble(lng);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	@Override
	public String toString() {
		return username + ";" + pwd;
	}
	
}
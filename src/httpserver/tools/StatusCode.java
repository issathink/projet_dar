package httpserver.tools;

public class StatusCode {
	private int value;
	private String description;
	
	public StatusCode(int value, String description) {
		this.value = value;
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
	
	public int getValue() {
		return value;
	}
}

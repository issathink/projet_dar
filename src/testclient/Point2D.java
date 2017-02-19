package testclient;

public class Point2D {
	
	private int x, y, id;
	
	public Point2D(int x, int y, int id) {
		this.x = x;
		this.y = y;
		this.id = id;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getId() {
		return id;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}

	@Override
	public String toString() {
		return "[" + id + "]" + "(" + x + "," + y + ")"; 
	}
}

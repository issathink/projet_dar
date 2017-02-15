package test;

import java.util.ArrayList;

public class ListPoint2D {
	
	ArrayList<Point2D> list;
	
	public ListPoint2D() {
		list = new ArrayList<>();
	}
	
	public void p(int id, int x, int y) {
		list.add(new Point2D(id, x, y));
	}
	
	public boolean p(int id) {
		Point2D tmp = null;
		for(Point2D p: list) {
			if(p.getId() == id) {
				tmp = p;
			}
		}
		if(tmp != null) {
			list.remove(tmp);
			return true;
		}
		return false;
	}
	
	public int p(int id, int x, int modifier, int merde) {
		for(Point2D p: list) {
			if(p.getId() == id) {
				return p.getX();
			}
		}
		return -1;
	}
	
	public int p(int id, int y) {
		for(Point2D p: list) {
			if(p.getId() == id) {
				return p.getY();
			}
		}
		return -1;
	}
	
	public ArrayList<Point2D> list() {
		return list;
	}

}

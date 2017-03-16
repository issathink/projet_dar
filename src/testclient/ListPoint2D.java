package testclient;

import java.util.ArrayList;

public class ListPoint2D {
	
	ArrayList<Point2D> list;
	private static int cpt;
	
	public ListPoint2D() {
		list = new ArrayList<>();
		list.add(new Point2D(0, 0, 0));
		list.add(new Point2D(1, 1, 1));
		cpt = 0;
	}
	
	public int getX(String idz) throws Exception {
		int id = Integer.parseInt(idz);
		System.out.println("GetXString id: " + id);
		for(Point2D p: list)
			if(p.getId() == id)
				return p.getX();
		throw new Exception();
	}
	
	public int getX(Integer id) throws Exception {
		
		System.out.println("GetXInt id: " + id);
		for(Point2D p: list)
			if(p.getId() == id)
				return p.getX();
		throw new Exception();
	}
	
	public int getY(int id) throws Exception {
		for(Point2D p: list)
			if(p.getId() == id)
				return p.getY();
		throw new Exception();
	}
	
	public ArrayList<Point2D> list() {
		return list;
	}

	public boolean put(int id, int x, int y) {
		Point2D point = null;
		for(Point2D p: list)
			if(p.getId() == id)
				point = p;
		if(point != null) {
			point.setX(x);
			point.setY(y);
			return true;
		}
		System.out.println(list);
		return false;
	}
	
	public int post(int x, int y) {
		int id = cpt++;
		list.add(new Point2D(id, x, y));
		return id;
	}
	
	public boolean delete(int id) {
		Point2D tmp = null;
		for(Point2D p: list)
			if(p.getId() == id)
				tmp = p;
		if(tmp != null) {
			list.remove(tmp);
			return true;
		}
		System.out.println(list);
		return false;
	}

}

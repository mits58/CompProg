import java.util.Scanner;

class Point{
	double x, y;
	public Point(double _x, double _y){
		x = _x;
		y = _y;
	}
	// add , sub
	public Point add(Point p) {
		return new Point(x + p.x, y + p.y);
	}
	public Point sub(Point p) {
		return new Point(x - p.x, y - p.y);
	}
	public double innerdot(Point p) {
		return p.x * x + p.y * y;
	}
    	// rotate d degree
	public Point rotate(double d) {
		double theta = d / 180.0 * Math.PI;
		return new Point(Math.cos(theta) * x  - Math.sin(theta) * y, Math.sin(theta) * x + Math.cos(theta) * y);
	}
}

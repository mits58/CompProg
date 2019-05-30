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
}

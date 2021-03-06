class Point implements Comparable<Point>{
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
	public double cross(Point p) {
		return x * p.y - y * p.x;
	}
	public double norm() {
		return x * x + y * y;
	}
	public double abs() {
		return Math.sqrt(norm());
	}
	public double dist(Point a){
		return this.sub(a).abs();
	}
	// rotate d degree
	public Point rotate(double d) {
		double theta = d / 180.0 * Math.PI;
		return new Point(Math.cos(theta) * x  - Math.sin(theta) * y, Math.sin(theta) * x + Math.cos(theta) * y);
	}
	public int compareTo(Point o) {
		if(Math.abs(x - o.x) < Geo.eps) return (int)Math.signum(y - o.y);
		return (int)Math.signum(x - o.x);
	}
}

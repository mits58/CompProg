class Line{
	Point p1, p2;
	public Line(Point _p1, Point _p2){
		p1 = _p1;
		p2 = _p2;
	}
	public Line(double x1, double y1, double x2, double y2) {
		p1 = new Point(x1, y1);
		p2 = new Point(x2, y2);
	}
	public Point Projection(Point p){
		Point Between_p1p2 = p2.sub(p1); //x2, y2
		Point Between_p1p = p1.sub(p); //x1, y1
		double k = Between_p1p.innerdot(Between_p1p2) / Between_p1p2.innerdot(Between_p1p2);
		return new Point(p1.x - k * Between_p1p2.x, p1.y - k * Between_p1p2.y);
	}
	public Point Reflection(Point p) {
		Point t = this.Projection(p);
		Point PX = t.sub(p);
		return p.add(new Point(PX.x * 2.0, PX.y * 2.0));
	}
}

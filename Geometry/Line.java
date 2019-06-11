class Line{
	static final double eps=1e-10;
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
	// where is p ?
	public CCW ccw(Point p) {
		Point a = p2.sub(p1); Point b = p.sub(p1);
		if(a.cross(b) > Geo.eps) return CCW.COUNTER_CLOCKWISE;
		if(a.cross(b) < -Geo.eps) return CCW.CLOCKWISE;
		if(a.innerdot(b) < -Geo.eps) return CCW.ONLINE_BACK;
		if(a.norm() < b.norm()) return CCW.ONLINE_FRONT;
		return CCW.ON_SEGMENT;
	}
	// this line and l is Orthogonal ?
	public boolean isOrth(Line l) {
		Point l1 = p2.sub(p1), l2 = l.p2.sub(l.p1);
		return Geo.equals(l1.innerdot(l2), 0);
	}
	// this line and l is parallel ?
	public boolean isPara(Line l) {
		Point l1 = p2.sub(p1), l2 = l.p2.sub(l.p1);
		return Geo.equals(l1.cross(l2), 0);
	}
	// this line and l intersects ?
	public boolean intersect(Line l) {
		int c1 = Geo.CCWtoInt(this.ccw(l.p1)), c2 = Geo.CCWtoInt(this.ccw(l.p2));
		int c3 = Geo.CCWtoInt(l.ccw(p1)), c4 = Geo.CCWtoInt(l.ccw(p2));
		return (c1 * c2 <= 0) && (c3 * c4 <= 0);
	}
	// Where is cross point with l ?
	public Point crossPoint(Line l) {
		Point b = l.p2.sub(l.p1);
		double d1 = Math.abs(b.cross(p1.sub(l.p1)));
		double d2 = Math.abs(b.cross(p2.sub(l.p1)));
		double t = d1 / (d1 + d2);
		return p1.add(new Point(p2.sub(p1).x * t, p2.sub(p1).y * t));
	}
	// Distance between this LINE and p
	public double distLP(Point p) {
		return Math.abs(p2.sub(p1).cross(p.sub(p1)) / p2.sub(p1).abs());
	}
	// Distance between this SEGMENT and p
	public double distSP(Point p) {
		if(p2.sub(p1).innerdot(p.sub(p1)) < 0) return p.sub(p1).abs();
		if(p1.sub(p2).innerdot(p.sub(p2)) < 0) return p.sub(p2).abs();
		return this.distLP(p);
	}
	// Distance between this SEGMENT and SEGMENT l
	public double distSS(Line l) {
		if(this.intersect(l)) return 0;
		return Math.min(Math.min(this.distSP(l.p1), this.distSP(l.p2)), Math.min(l.distSP(p1), l.distSP(p2)));
	}
}

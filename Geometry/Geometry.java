import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

enum CCW{
	COUNTER_CLOCKWISE, CLOCKWISE, ONLINE_BACK, ONLINE_FRONT, ON_SEGMENT
};

class Geo{
	static final double eps = 1e-10;
	static boolean equals(double x, double y){
		return Math.abs(x - y) < eps;
	}
	static int CCWtoInt(CCW c) {
		if(c == CCW.COUNTER_CLOCKWISE) return 1;
		if(c == CCW.CLOCKWISE) return -1;
		if(c == CCW.ONLINE_BACK) return 2;
		if(c == CCW.ONLINE_FRONT) return -2;
		return 0;
	}
	static Point polar(double a, double r) {
		return new Point(Math.cos(r) * a, Math.sin(r) * a);
	}
}

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
	public Point multiple(double a) {
		return new Point(x * a, y * a);
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
	public double arg() {
		return Math.atan2(y, x);
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

class Polygon{
	int N;
	Point[] points;
	public Polygon(int n) {
		N = n; points = new Point[N];
	}
	// Calculation area
	public double area(){
		double s = 0;
		for(int i = 0; i < N; i++) {
			s += points[i].cross(points[(i + 1) % N]);
		}
		return Math.abs(s / 2);
	}
	// is this Polygon convex?
	public boolean isConvex() {
		boolean flag = true;
		for(int i = 0; i < N; i++) {
			CCW c = (new Line(points[i], points[(i + 1) % N])).ccw(points[(i + 2) % N]);
			if(c == CCW.CLOCKWISE) {
				flag = false;
				break;
			}
		}
		return flag;
	}
	// contains p -> 2, on the edge -> 1, otherwise -> 0
	public int contains(Point p){
		boolean x = false;
		for(int i = 0; i < N; i++) {
			Point a = points[i].sub(p), b = points[(i + 1) % N].sub(p);
			if(Math.abs(a.cross(b)) < Geo.eps && a.innerdot(b) < Geo.eps) return 1;
			if(a.y > b.y) {
				Point t = b;
				b = a;
				a = t;
			}
			if(a.y < Geo.eps && Geo.eps < b.y && a.cross(b) > Geo.eps) x = !x;
		}
		return x ? 2 : 0;
	}
	// calculation convex hull
	public ArrayList<Point> convhull(){
		Arrays.sort(points);
		ArrayList<Point> res = new ArrayList<Point>();
		if(points.length < 3) {
			for(int i = 0; i < points.length; i++) res.add(points[i]);
			return res;
		}
		ArrayList<Point> u = new ArrayList<Point>();
		ArrayList<Point> l = new ArrayList<Point>();
		// constructing upper convex hull
		u.add(points[0]); u.add(points[1]);
		for(int i = 2; i < points.length; i++) {
			while(u.size() >= 2) {
				CCW now = (new Line(u.get(u.size() - 2), u.get(u.size() - 1)).ccw(points[i]));
				if(Geo.CCWtoInt(now) < 0) break;
				u.remove(u.size() - 1);
			}
			u.add(points[i]);
		}
		// constructing lower convex hull
		l.add(points[N - 1]); l.add(points[N - 2]);
		for(int i = N - 3; i >= 0; i--) {
			while(l.size() >= 2) {
				CCW now = (new Line(l.get(l.size() - 2), l.get(l.size() - 1)).ccw(points[i]));
				if(Geo.CCWtoInt(now) < 0) break;
				l.remove(l.size() - 1);
			}
			l.add(points[i]);
		}
		while(l.size() > 0) {
			res.add(l.get(l.size() - 1));
			l.remove(l.size() - 1);
		}
		u.remove(u.size() - 1); u.remove(0);
		while(u.size() > 0) {
			res.add(u.get(u.size() - 1));
			u.remove(u.size() - 1);
		}
		return res;
	}
	// calculation diameter diameter pair -> (maxi, maxj)
	public double diameter(){
		// search pair
		int is = 0, js = 0;
		for(int i = 0; i < N; i++) {
			if(points[i].y > points[is].y) is = i;
			if(points[i].y < points[js].y) js = i;
		}
		double maxd = points[is].sub(points[js]).norm();
		int i, maxi, j, maxj;
		i = maxi = is;
		j = maxj = js;
		do{
			if(points[(i + 1) % N].sub(points[i]).cross(points[(j + 1) % N].sub(points[j])) >= 0) j = (j + 1) % N;
			else i = (i + 1) % N;
			if(points[i].sub(points[j]).norm() > maxd) {
				maxd = points[i].sub(points[j]).norm();
				maxi = i; maxj = j;
			}
		}while(i != is || j != js);
		return maxd;
	}
}

class Circle{
	double r; // radius
	Point p; // center
	public Circle(Point p, double r) {
		this.p = p; this.r = r;
	}
	/* The number of Common Tangent lines（共通接線）*/
	// 4: 離れている, 3: 外接している, 2: 交わる, 1: 内接している, 0: 内包している
	public int intersect(Circle c) {
		double b1 = r + c.r, b2 = Math.abs(r - c.r), dist = p.sub(c.p).abs();
		if(dist > b1) return 4;
		else if(Geo.equals(dist, b1)) return 3;
		else if(Geo.equals(dist, b2)) return 1;
		else if(b2 > dist) return 0;
		return 2;
	}
	/* Cross Points of a Circle and a Line */
	// 誤差 turaimu
	public Point[] crossPoints(Line l) {
		Point pr = l.Projection(p);
		Point e = (l.p2.sub(l.p1)).multiple((double)1 / (l.p2.sub(l.p1)).abs());
		double base = Math.sqrt(r * r - (pr.sub(p)).norm());
		Point[] ans = new Point[2];
		ans[0] = pr.add(e.multiple(base));
		ans[1] = pr.sub(e.multiple(base));
		return ans;
	}
	/* Cross Points between two Circles */
	public Point[] crossPoints(Circle c) {
		double d = p.sub(c.p).abs();
		double a = Math.acos((r * r + d * d - c.r * c.r) / (2.0 * r * d));
		double t = c.p.sub(p).arg();
		Point[] ans = new Point[2];
		ans[0] = p.add(Geo.polar(r, t + a));
		ans[1] = p.add(Geo.polar(r, t - a));
		return ans;
	}
	/* Tangent to a Circle (Given a Point) */
	public Point[] Tangent(Point p) {
		Point[] ans = new Point[2];
		Point Q = p.sub(this.p); // xp, yp
		ans[0] = new Point((Q.x * r + Q.y * Math.sqrt(Q.norm() - r * r)) / Q.norm() * r + this.p.x, (Q.y * r - Q.x * Math.sqrt(Q.norm() - r * r)) / Q.norm() * r + this.p.y);
		ans[1] = new Point((Q.x * r - Q.y * Math.sqrt(Q.norm() - r * r)) / Q.norm() * r + this.p.x, (Q.y * r + Q.x * Math.sqrt(Q.norm() - r * r)) / Q.norm() * r + this.p.y);
		return ans;
	}
	/* Tangent to Circle (Given a Circle) */
	// 誤差 turaimu
	public Point[] Tangent(Circle c) {
		Point[] res = new Point[this.intersect(c)];
		double diff = r - c.r, add = r + c.r;
		Point Q = c.p.sub(p); // xp, yp
		if(res.length == 1) {
			res[0] = this.crossPoints(c)[0];
		}else if(res.length == 2) {
			res[0] = new Point((Q.x * diff + Q.y * Math.sqrt(Q.norm() - diff * diff)) / Q.norm() * r + p.x, (Q.y * diff - Q.x * Math.sqrt(Q.norm() - diff * diff)) / Q.norm() * r + p.y);
			res[1] = new Point((Q.x * diff - Q.y * Math.sqrt(Q.norm() - diff * diff)) / Q.norm() * r + p.x, (Q.y * diff + Q.x * Math.sqrt(Q.norm() - diff * diff)) / Q.norm() * r + p.y);
		}else if(res.length == 3) {
			res[0] = new Point((Q.x * diff + Q.y * Math.sqrt(Q.norm() - diff * diff)) / Q.norm() * r + p.x, (Q.y * diff - Q.x * Math.sqrt(Q.norm() - diff * diff)) / Q.norm() * r + p.y);
			res[1] = new Point((Q.x * diff - Q.y * Math.sqrt(Q.norm() - diff * diff)) / Q.norm() * r + p.x, (Q.y * diff + Q.x * Math.sqrt(Q.norm() - diff * diff)) / Q.norm() * r + p.y);
			res[2] = this.crossPoints(c)[0];
		}else if(res.length == 4) {
			res[0] = new Point((Q.x * diff + Q.y * Math.sqrt(Q.norm() - diff * diff)) / Q.norm() * r + p.x, (Q.y * diff - Q.x * Math.sqrt(Q.norm() - diff * diff)) / Q.norm() * r + p.y);
			res[1] = new Point((Q.x * diff - Q.y * Math.sqrt(Q.norm() - diff * diff)) / Q.norm() * r + p.x, (Q.y * diff + Q.x * Math.sqrt(Q.norm() - diff * diff)) / Q.norm() * r + p.y);
			res[2] = new Point((Q.x * add + Q.y * Math.sqrt(Q.norm() - add * add)) / Q.norm() * r + p.x, (Q.y * add - Q.x * Math.sqrt(Q.norm() - add * add)) / Q.norm() * r + p.y);
			res[3] = new Point((Q.x * add - Q.y * Math.sqrt(Q.norm() - add * add)) / Q.norm() * r + p.x, (Q.y * add + Q.x * Math.sqrt(Q.norm() - add * add)) / Q.norm() * r + p.y);

		}
		return res;
	}
}

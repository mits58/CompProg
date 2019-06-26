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

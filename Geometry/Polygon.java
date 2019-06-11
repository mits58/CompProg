class Polygon{
	int N;
	Point[] points;
	public Polygon(int n) {
		N = n; points = new Point[N];
	}
	public double area(){
		double s = 0;
		for(int i = 0; i < N; i++) {
			s += points[i].cross(points[(i + 1) % N]);
		}
		return Math.abs(s / 2);
	}
}

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
}

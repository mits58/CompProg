public class Point{
    double x, y;
    
    public Point(double _x, double _y){
	x = _x;
	y = _y;
    }

    // functions
    static Point Projection(Line l, Point p){
	Point proj = new Point(l.p1
	double xp=sc.nextDouble();
	double yp=sc.nextDouble();
	double x1=xp1-xp;
	double y1=yp1-yp;
	double x=xp1-x2*(x1*x2+y1*y2)/(x2*x2+y2*y2);
	double y=yp1-y2*(x1*x2+y1*y2)/(x2*x2+y2*y2);
	System.out.println(x+" "+y);
    }
    
}
public class Line{
    Point p1, p2;

    public Line(Point _p1, Point _p2){
	p1 = _p1;
	p2 = _p2;
    }
}
       

public class Main {
    public static void main(String[] args){
        Scanner sc=new Scanner(System.in);
        while(sc.hasNext()){
	    Point p1 = 
        	double xp1=sc.nextDouble();
        	double yp1=sc.nextDouble();
        	double xp2=sc.nextDouble();
        	double yp2=sc.nextDouble();
        	double x2=xp2-xp1;
        	double y2=yp2-yp1;
        	int q=sc.nextInt();
        	for(int i=0;i<q;i++){
        		double xp=sc.nextDouble();
        		double yp=sc.nextDouble();
        		double x1=xp1-xp;
        		double y1=yp1-yp;
        		double x=xp1-x2*(x1*x2+y1*y2)/(x2*x2+y2*y2);
        		double y=yp1-y2*(x1*x2+y1*y2)/(x2*x2+y2*y2);
        		System.out.println(x+" "+y);
        	}
        }
    }
}

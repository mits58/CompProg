import java.util.Scanner;

class NumTh{
	static long mod=1000000007; // 10e9 + 7
	static long GCD(long x, long y) {
		if(Math.max(x, y) % Math.min(x, y) == 0) return Math.min(x, y);
		return GCD(Math.min(x, y), Math.max(x, y) % Math.min(x, y));
	}
	static int GCD(int x, int y) {
		return (int)GCD((long)x, (long)y);
	}
	static long LCM(long x, long y){
		return (x / GCD(x,y)) * y;
	}
	static int LCM(int x, int y) {
		return (int)LCM((long)x, (long)y);
	}
	static long pow(long a, long x, long m){
		if(x == 1) return a % m;
		else if(x % 2 == 0) return pow((a * a) % m, x / 2, m) % m;
		else return (a * pow((a * a) % m, x / 2, m)) % m;
	}
}
public class Main{
	public static void main(String[] args){
		Scanner sc=new Scanner(System.in);
		while(sc.hasNext()){
			int n = sc.nextInt();
			int ans = 1;
			for(int i = 0; i < n; i++) {
				int a = sc.nextInt();
				ans = NumTh.LCM(ans, a);
			}
			System.out.println(ans);
		}
	}
}

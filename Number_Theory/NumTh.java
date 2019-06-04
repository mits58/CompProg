import java.util.LinkedList;
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
	static boolean ip(long x) {
		if(x == 1) return false;
		for(long i = 2; i * i <= x; i++) if(x % i == 0) return false;
		return true;
	}
	static LinkedList<Long> fact(long n) {
		long tn = n;
		LinkedList<Long> factorize = new LinkedList<Long>();
		if(ip(n)) {
			factorize.add(n);
			n /= n;
		}
		for(long i = 2; i <= tn; i++) {
			if(n == 1) break;
			if(ip(i)) {
				while(n % i == 0) {
					factorize.add(i);
					n /= i;
				}
			}
		}
		return factorize;
	}
}
public class Main{
	public static void main(String[] args){
		Scanner sc=new Scanner(System.in);
		while(sc.hasNext()){
			long n = sc.nextLong();
			LinkedList<Long> fact = NumTh.fact(n);
			System.out.print(n+":");
			for(long num : fact) System.out.print(" "+num);
			System.out.println();
		}
	}
}

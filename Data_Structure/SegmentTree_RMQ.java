import java.util.Scanner;

class Segtree{
	int N, dat[];
	int inf = Integer.MAX_VALUE;
	public Segtree(int n) {
		N = 1;
		while(N < n) N *= 2; // 要素数を2のべきに
		dat = new int[2 * N - 1]; // N + (N / 2) + (N / 4) ... = 2 * N - 1
		for(int i = 0; i < 2 * N - 1; i++) dat[i] = inf;
	}
	// update a_k -> a
	public void update(int k, int a) {
		k += N - 1;
		dat[k] = a;
		while(k > 0) {
			k = (k - 1) / 2;
			dat[k] = Math.min(dat[k * 2 + 1], dat[k * 2 + 2]);
		}
	}
	// min[a, b)
	int query(int a, int b) {
		return query(a, b, 0, 0, N);
	}
	int query(int a, int b, int k, int l, int r) {
		if(r <= a || b <= l) return inf; // not include
		if(a <= l && r <= b) return dat[k]; // entirely include
		int m = (l + r) / 2;
		return Math.min(query(a, b, k * 2 + 1, l, m), query(a, b, k * 2 + 2, m , r)); // left and right recursion
	}
}

class Main{
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt(), q = sc.nextInt();
		Segtree seg = new Segtree(n);

		for(int i = 0; i < q; i++) {
			int com = sc.nextInt();
			int x = sc.nextInt();
			int y = sc.nextInt();
			if(com == 0) {
				seg.update(x, y);
			}
			else {
				System.out.println(seg.query(x, y + 1));
			}
		}
	}
}

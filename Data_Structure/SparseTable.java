import java.util.Scanner;

// not verified... :(
class SparseTable {
	int[][] t;

	public SparseTable(int[] A) {
		int N = A.length;
		int x = Integer.highestOneBit(N);
		t = new int[Integer.bitCount((x & (-x)) - 1) + 1][];

		t[0] = new int[N];
		for(int i = 0; i < N; i++) t[0][i] = A[i];

		for(int i = 1; i < t.length; i++) {
			// 2^i
			t[i] = new int[N - (1 << i) + 1];
			for(int j = 0; j < t[i].length; j++) {
				t[i][j] = Math.min(t[i - 1][j], t[i - 1][j + (1 << (i - 1))]);
			}
		}
	}

	/**
	 * Returns minimum value in [l, r)
	 * @param l	left (include)
	 * @param r	right (exclude)
	 * @return	minimum value
	 */
	public int min(int l, int r) {
		int length = r - l;
		int x = Integer.highestOneBit(length);
		int depth = Integer.bitCount((x & (-x)) - 1);
		return Math.min(t[depth][l], t[depth][r - (1 << depth)]);
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		int[] A = new int[N];
		for(int i = 0; i < N; i++) A[i] = sc.nextInt();

		SparseTable sp = new SparseTable(A);

		while(true) {
			int l = sc.nextInt(), r = sc.nextInt();
			if(l == -1) break;
			System.out.println(sp.min(l, r));
		}
	}
}
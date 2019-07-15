class Main{
	static int lower(int[] a, int key) {
		int high = a.length, low = -1;
		while(high - low > 1) {
			int mid = (high + low) / 2;
			if(a[mid] < key) low = mid;
			else high = mid;
		}
		return low;
	}
	static int upper(int[] a, int key) {
		int high = a.length, low =  -1;
		while(high - low > 1) {
			int mid = (high + low) / 2;
			if(a[mid] > key) high = mid;
			else low = mid;
		}
		return high;
	}
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		int[] A = new int[N];
		int[] B = new int[N];
		int[] C = new int[N];
		long ans = 0;
		for(int i = 0; i < N; i++) A[i] = sc.nextInt();
		for(int i = 0; i < N; i++) B[i] = sc.nextInt();
		for(int i = 0; i < N; i++) C[i] = sc.nextInt();
		Arrays.sort(A); Arrays.sort(B); Arrays.sort(C);
 
		for(int i = 0; i < N; i++) {
			int idx_A = lower(A, B[i]), idx_C = upper(C, B[i]);
			if(idx_A == -1 || idx_C == N) continue;
			ans += (long)(idx_A + 1) * (long)(N - idx_C);
		}
		System.out.println(ans);
	}
}

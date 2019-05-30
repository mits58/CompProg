class UnionFind{
    int[] par;
    int N;
    public UnionFind(int n){
	N = n;
	par = new int[N];
	for(int i = 0; i < N; i++){
	    par[i] = i;
	}
    }
    public int find(int x){
	if(par[x] == x) return x;
	return par[x] = find(par[x]);
    }
    public Boolean same(int x, int y){
	return find(x) == find(y);
    }
    public void unite(int x, int y){
	if(find(x) == find(y)) return;
	par[find(x)] = find(y);
    }
    public int DistinctElementsNum(){
	HashSet<Integer> set = new HashSet<Integer>();
	for(int i = 0; i < N; i++){
	    find(i);
	    set.add(find(i));
	}
	return set.size();
    }
}
// Verified

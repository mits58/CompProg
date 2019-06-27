import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Scanner;

class UnionFind{
	int[] par;
	UnionFind(int n){
		par=new int[n];
		for(int i=0;i<n;i++)par[i]=i;
	}
	public int find(int x){
		if(par[x]==x)return x;
		return par[x]=find(par[x]);
	}
	public Boolean same(int x,int y){
		return find(x)==find(y);
	}
	public void unite(int x,int y){
		if(find(x)==find(y))return;
		par[find(x)]=find(y);
	}
}

class State implements Comparable<State>{
	int v, cost;
	public State(int V, int c) {
		v = V; cost = c;
	}
	public int compareTo(State s) {
		return cost - s.cost;
	}
}

class Edge implements Comparable<Edge>{
	int s, t, w;
	public Edge(int S, int T, int W) {
		s = S; t = T; w = W;
	}
	public int compareTo(Edge e) {
		return w - e.w;
	}
}

class Graph{
	static int INF = Integer.MAX_VALUE/2; // INF 問題により変えよう
	private int V, E;
	LinkedList<Edge>[] edges;
	@SuppressWarnings("unchecked")
	public Graph(int v, int e){
		this.V = v; this.E = e;
		edges = new LinkedList[V];
		for(int i = 0; i < V; i++) edges[i] = new LinkedList<Edge>();
	}

	public int[] dijkstra(int s){
		int[] d = new int[V];
		boolean[] used = new boolean[V];
		Arrays.fill(used, false);
		Arrays.fill(d, INF);
		d[s] = 0;

		PriorityQueue<State> que = new PriorityQueue<State>();
		que.add(new State(s, 0));

		while(!que.isEmpty()){
			State now = que.poll();
			int point = now.v;
			if(used[point]) continue;
			used[point] = true;
			d[point] = now.cost;
			for(Edge edge : edges[point]) {
				if(!used[edge.t]) {
					que.add(new State(edge.t, now.cost + edge.w));
				}
			}
		}
		return d;
	}

	public int[] BellmanFord(int s){
		int[] d = new int[V];
		Arrays.fill(d, INF);
		d[s] = 0;

		// 全ての辺をなめる
		for(int i = 0; i < V; i++) {
			for(int j = 0; j < V; j++) {
				for(Edge edge : edges[j]) {
					if(d[j] != INF && d[edge.t] > d[j] + edge.w) {
						d[edge.t] = d[j] + edge.w;
						// negative cycleを検知 -> i == V - 1回目にも更新あり
						if(i == V - 1) return null;
					}
				}
			}
		}
		return d;
	}

	public int[][] FloydWarshall(){
		int[][] res = new int[V][V];
		for(int i = 0; i < V; i++) Arrays.fill(res[i], INF);
		for(int i = 0; i < V; i++) res[i][i] = 0;

		for(int i = 0; i < V; i++) {
			for(Edge edge : edges[i]) {
				res[edge.s][edge.t] = edge.w;
			}
		}

		for(int k = 0; k < V; k++) {
			for(int i = 0; i < V; i++) {
				for(int j = 0; j < V; j++) {
					// i -> j へ行くのに、kを経由したらよかったりする？っていう考え方
					if(res[i][k] == INF || res[k][j] == INF) continue;
					else if(res[i][j] == INF) res[i][j] = res[i][k] + res[k][j];
					else res[i][j] = Math.min(res[i][j], res[i][k] + res[k][j]);
				}
			}
		}

		boolean flag = false;
		for(int i = 0; i < V; i++) if(res[i][i] < 0) flag = true;
		if(flag) {
			return null;
		}
		return res;
	}

	/* Calculates of Minimum Spanning Tree and returns the cost using kruskal algorithm */
	public int calc_MST() {
		ArrayList<Edge> alledges = new ArrayList<Edge>();
		for(int i = 0; i < V; i++) {
			for(Edge e : edges[i]) alledges.add(e);
		}
		Collections.sort(alledges);
		UnionFind uf = new UnionFind(V);
		int sum = 0;
		for(Edge e : alledges) {
			if(!uf.same(e.s, e.t)) {
				// Using edge e
				sum += e.w;
				uf.unite(e.s, e.t);
			}
		}
		return sum;
	}

	/* Calculates of diameter of tree using dijkstra */
	public int calc_Diameter() {
		int[] dist = dijkstra(0);
		int max=0;
		for(int i = 1; i < V; i++) if(dist[max] < dist[i]) max = i;
		dist = dijkstra(max);
		int res = 0;
		for(int i = 0; i < V; i++) res = Math.max(res, dist[i]);
		return res;
	}

	/* Calculates of height of each node of the tree */
	public int[] calc_Height() {
		int[] ans = new int[V];
		int[] d = dijkstra(0);
		// Calculates farthest pair
		int first = 0;
		for(int i = 1; i < V; i++) if(d[i] > d[first]) first = i;
		int[] dist_first = dijkstra(first);
		int second = 0;
		for(int i = 1; i < V; i++) if(dist_first[i] > dist_first[second]) second = i;
		int[] dist_second = dijkstra(second);

		for(int i = 0; i < V; i++) {
			ans[i] = Math.max(dist_first[i], dist_second[i]);
		}
		return ans;
	}
}
class Main{
	public static void main(String args[]){
		Scanner sc=new Scanner(System.in);
		while(sc.hasNext()){
			int n = sc.nextInt();
			Graph g = new Graph(n, n - 1);
			for(int i = 0; i < n - 1; i++) {
				int s = sc.nextInt();
				int t = sc.nextInt();
				int d = sc.nextInt();
				g.edges[s].add(new Edge(s, t, d));
				g.edges[t].add(new Edge(t, s, d));
			}
			int q = sc.nextInt();

		}
	}
}

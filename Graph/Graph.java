import java.util.Arrays;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Scanner;

class State implements Comparable<State>{
	int v, cost;
	public State(int V, int c) {
		v = V; cost = c;
	}
	public int compareTo(State s) {
		return cost - s.cost;
	}
}
class Edge{
	int s, t, w;
	public Edge(int S, int T, int W) {
		s = S; t = T; w = W;
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
}
class Main{
	public static void main(String args[]){
		Scanner sc=new Scanner(System.in);
		while(sc.hasNext()){
			int V = sc.nextInt(), E = sc.nextInt();
			Graph g = new Graph(V, E);
			for(int i = 0; i < E; i++) {
				int s = sc.nextInt();
				int t = sc.nextInt();
				int d = sc.nextInt();
				g.edges[s].add(new Edge(s, t, d));
			}
			int[][] ans = g.FloydWarshall();
			if(ans == null) {
				System.out.println("NEGATIVE CYCLE");
			}else {
				for(int i = 0; i < V; i++) {
					System.out.print((ans[i][0] == Graph.INF) ? "INF" : ans[i][0]);
					for(int j = 1; j < V; j++) {
						System.out.print((ans[i][j] == Graph.INF) ? " INF" : " "+ans[i][j]);
					}
					System.out.println();
				}
			}
		}
	}
}

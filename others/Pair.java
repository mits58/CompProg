import java.util.Arrays;
import java.util.Comparator;
import java.util.TreeSet;

// Genericを使った 多分これがよい？
// 実際のソートするときはComparatorで持たせる（下記参照）
class Pair<F, S>{
    public final F first;
    public final S second;

    public Pair (F first, S second) {
        this.first = first;
        this.second = second;
    }

}


public class Main {
    public static void main (String[] args) {
    	TreeSet<Pair<Integer, String>> map = new TreeSet<Pair<Integer, String>>(new Comparator<Pair<Integer, String>>() {
			// Comparatorでソートさせるのがよさそう(TreeSetの使い方)
    		public int compare(Pair<Integer, String> o1, Pair<Integer, String> o2) {
				if(o1.first != o2.first) return o1.first - o2.first;
				else return o1.second.compareTo(o2.second);
			}
		});
    	map.add(new Pair<Integer, String>(1, "464"));
    	map.add(new Pair<Integer, String>(3, "464"));
    	map.add(new Pair<Integer, String>(3, "1"));
    	for(Pair p : map) {
    		System.out.println(p.first+" "+p.second);
    	}
    	Pair<Integer, String>[] parr = new Pair[3];
    	parr[0] = new Pair<Integer, String>(1, "464");
    	parr[1] = new Pair<Integer, String>(3, "464");
    	parr[2] = new Pair<Integer, String>(3, "1");
    	Arrays.sort(parr, new Comparator<Pair<Integer, String>>() {
			// Comparatorでソートさせるのがよさそう(TreeSetの使い方)
    		public int compare(Pair<Integer, String> o1, Pair<Integer, String> o2) {
				if(o1.first != o2.first) return o1.first - o2.first;
				else return o1.second.compareTo(o2.second);
			}
		});
    	for(Pair p : map) {
    		System.out.println(p.first+" "+p.second);
    	}
    }
}

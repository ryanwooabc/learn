package algorithms.template.graph;

public class UnionFind2 {

    int[] root, count;

    public UnionFind2(int n) {
        root = new int[n];
        count = new int[n];
        for (int i = 0; i < n; i ++) {
            root[i] = i;
            count[i] = 1;
        }
    }

    public void union(int i, int j) {
        int x = find(i), y = find(j);
        if (x != y) {
            root[x] = y;
            count[y] += count[x];
        }
    }

    public int find(int i) {
        return root[i] == i ? i : (root[i] = find(root[i]));
    }

    public int count(int i) {
        return count[i];
    }
}

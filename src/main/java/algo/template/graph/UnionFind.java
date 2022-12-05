package algo.template.graph;

public class UnionFind {

    public static int[] build(int n) {
        int[] root = new int[n];
        for (int i = 0; i < n; i ++) {
            root[i] = i;
        }
        return root;
    }

    public static int find(int[] root, int i) {
        return root[i] == i ? i : (root[i] = find(root, root[i]));
    }
}

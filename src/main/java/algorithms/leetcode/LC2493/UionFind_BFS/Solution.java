package algorithms.leetcode.LC2493.UionFind_BFS;

import java.util.*;

// 2493-divide-nodes-into-the-maximum-number-of-groups
// Q: https://leetcode.com/problems/divide-nodes-into-the-maximum-number-of-groups/

class Solution {

    public int magnificentSets(int n, int[][] edges) {
        int[] root = new int[n], depth = new int[n];
        List<Integer>[] graph = new List[n];
        for (int i = 0; i < n; i++) {
            root[i] = i;
            graph[i] = new ArrayList<>();
        }
        for (int[] e : edges) {
            int a = e[0] - 1, b = e[1] - 1, x = find(root, a), y = find(root, b);
            graph[a].add(b);
            graph[b].add(a);
            root[x] = root[y];
        }
        int ans = 0;
        for (int i = 0; i < n; i++) {
            int r = find(root, i), d = calc(graph, i);
            if (d == -1) {
                return -1;
            }
            depth[r] = Math.max(depth[r], d);
        }
        for (int i = 0; i < n; i ++) {
            ans += depth[i];
        }
        return ans;
    }

    int find(int[] root, int i) {
        return root[i] == i ? i : (root[i] = find(root, root[i]));
    }

    int calc(List<Integer>[] graph, int i) {
        int n = graph.length, ans = 0;
        Queue<Integer> bfs = new ArrayDeque<Integer>() {{ add(i); }};
        Set<Integer> level = new HashSet<>(), visited = new HashSet<Integer>();
        for (visited.add(i); !bfs.isEmpty(); ans++) {
            Set<Integer> next = new HashSet<>();
            for (int m = bfs.size(); m > 0; m --) {
                int a = bfs.poll();
                for (int b : graph[a]) {
                    if (level.contains(b)) {
                        return -1;
                    } else if (visited.add(b)) {
                        bfs.add(b);
                        next.add(b);
                    }
                }
            }
            level = next;
        }
        return ans;
    }

    public static void main(String[] args) {
        System.out.println(new Solution().magnificentSets(6, new int[][] {{1,2},{1,4},{1,5},{2,6},{2,3},{4,6}}));
        System.out.println(new Solution().magnificentSets(3, new int[][] {{1,2},{1,3},{2, 3}}));
        System.out.println(new Solution().magnificentSets(92, new int[][] {{67,29},{13,29},{77,29},{36,29},{82,29},{54,29},{57,29},{53,29},{68,29},{26,29},{21,29},{46,29},{41,29},{45,29},{56,29},{88,29},{2,29},{7,29},{5,29},{16,29},{37,29},{50,29},{79,29},{91,29},{48,29},{87,29},{25,29},{80,29},{71,29},{9,29},{78,29},{33,29},{4,29},{44,29},{72,29},{65,29},{61,29}}));
    }
}

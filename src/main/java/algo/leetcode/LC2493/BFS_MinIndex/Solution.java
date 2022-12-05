package algo.leetcode.LC2493.BFS_MinIndex;

import java.util.*;

// 2493-divide-nodes-into-the-maximum-number-of-groups
// Q: https://leetcode.com/problems/divide-nodes-into-the-maximum-number-of-groups/

class Solution {

    /*

    */
    public int magnificentSets(int n, int[][] edges) {
        List<Integer>[] graph = new List[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int[] e : edges) {
            int a = e[0] - 1, b = e[1] - 1;
            graph[a].add(b);
            graph[b].add(a);
        }
        int[] depth = new int[n];
        for (int i = 0; i < n; i++) {
            int r = n, d = 0, m = 0, a = -1;
            int[] level = new int[n];
            Queue<Integer> bfs = new ArrayDeque<Integer>();
            for (bfs.add(i), level[i] = 1; !bfs.isEmpty(); ) {
                for (d ++, m = bfs.size(); m > 0; m --) {
                    r = Math.min(r, a = bfs.poll());
                    for (int b : graph[a]) {
                        if (level[b] == 0) {
                            bfs.add(b);
                            level[b] = d + 1;
                        } else if (level[b] == d) {
                            return -1;
                        }
                    }
                }
            }            
            depth[r] = Math.max(depth[r], d);
        }
        int ans = 0;
        for (int i = 0; i < n; i ++) {
            ans += depth[i];
        }
        return ans;
    }

}

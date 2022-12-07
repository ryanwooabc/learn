package algorithms.template.graph;

import java.util.*;

public class BFS {

    public List<Integer> bfs(List<Integer>[] graph, int s) {
        List<Integer> ans = new ArrayList<>();
        boolean[] visited = new boolean[graph.length];
        Queue<Integer> bfs = new ArrayDeque<Integer>() {{ add(s); }};
        for (visited[0] = true; !bfs.isEmpty(); ) {
            int i = bfs.poll();
            ans.add(i);
            for (int j : graph[i]) {
                if (!visited[j]) {
                    bfs.add(j);
                    visited[j] = true;
                }
            }
        }
        return ans;
    }
}

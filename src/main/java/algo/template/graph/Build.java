package algo.template.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Build {

    public List<Integer>[] build(int n, int[][] edges) {
        List<Integer>[] graph = new List[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int[] e : edges) {
            int a = e[0] - 1, b = e[1] - 1;
            graph[a].add(b);
            graph[b].add(a);
        }
        return graph;
    }

    public Map<Integer, Integer>[] build2(int n, int[][] roads) {
        Map<Integer, Integer>[] graph = new Map[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new HashMap<>();
        }
        for (int[] r : roads) {
            int a = r[0] - 1, b = r[1] - 1, c = r[2];
            graph[a].put(b, c);
            graph[b].put(a, c);
        }
        return graph;
    }
}

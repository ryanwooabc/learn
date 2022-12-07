package algorithms.template.graph;

import java.util.ArrayDeque;
import java.util.Queue;

public class Bipartite {

    // https://en.wikipedia.org/wiki/Bipartite_graph
    public static boolean isBipartite2(int[][] graph) {
        int n = graph.length;
        int[] visited = new int[n];
        for (int i = 0, a = 0, c = 0; i < n; i ++) {
            if (visited[i] == 0) {
                Queue<Integer> bfs = new ArrayDeque<>();
                for (bfs.add(i), c = 1; !bfs.isEmpty(); c = 3 - c) {
                    for (int m = bfs.size(); m > 0; m --) {
                        visited[a = bfs.poll()] = c;
                        for (int b : graph[a]) {
                            if (visited[b] == c) {
                                return false;
                            } else if (visited[b] == 0) {
                                bfs.add(b);
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    public static boolean isBipartite(int[][] graph) {
        int n = graph.length;
        int[] color = new int[n];
        for (int i = 0; i < n; i ++) {
            if (color[i] == 0 && !valid(graph, color, i, 1)) {
                return false;
            }
        }
        return true;
    }

    public static boolean valid(int[][] graph, int[] color, int i, int c) {
        if (color[i] != 0) {
            return color[i] == c;
        }
        color[i] = c;
        for (int j : graph[i]) {
            if (!valid(graph, color, j, 3 - c)) {
                return false;
            }
        }
        return true;
    }
}

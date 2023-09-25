package main;

import java.util.*;

class Pair {
    private final String verticeLabel;
    private final int level;

    public Pair(String verticeLabel, int level) {
        this.verticeLabel = verticeLabel;
        this.level = level;
    }

    public String getVerticeLabel() {
        return verticeLabel;
    }

    public int getLevel() {
        return level;
    }

    @Override
    public String toString() {
        return "Pair{" +
                "verticeLabel='" + verticeLabel + '\'' +
                ", level=" + level +
                '}';
    }
}

class Graph {
    Map<String, Boolean> vis = new HashMap<>();
    Map<String, List<String>> adj = new HashMap<>();

    public List<String> adjList(String key) {
        return this.adj.get(key);
    }

    int breathFirstSearch(String ini, String fim) {
        int distance;
        String v;
        Queue<Pair> q = new LinkedList<>();

        q.add(new Pair(ini, 0));

        while (!q.isEmpty()) {
            v = q.peek().getVerticeLabel();
            distance = q.peek().getLevel();
            q.poll();

            if (v.equalsIgnoreCase(fim)) return distance;

            analyseVisitedAndAdj(distance, v, q);
        }
        return 0;
    }

    private void analyseVisitedAndAdj(int distance, String v, Queue<Pair> q) {
        if (Boolean.FALSE.equals(vis.get(v))) {
            vis.replace(v, true);

            int sz = adj.get(v).size();

            if (adj.get(v) != null) {

                getNewDistance(distance, v, q, sz);
            }
        }
    }

    private void getNewDistance(int distance, String v, Queue<Pair> q, int sz) {
        String temporaryVertex;
        for (int i = 0; i < sz; ++i) {
            temporaryVertex = adj.get(v).get(i);
            q.add(new Pair(temporaryVertex, distance + 1));
        }
    }

    public void initializeVisited() {
        this.vis.forEach((x, y) ->
                vis.replace(x, false)
        );
    }

    @Override
    public String toString() {
        return "Graph{" +
                "vis=" + vis +
                ", adj=" + adj +
                '}';
    }
}

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Graph graph = new Graph();
        int edges;
        int tot;

        String aux = sc.nextLine();

        edges = Integer.parseInt(aux.split(" ")[1]);

        for (int i = 0; i < edges; ++i) {
            aux = sc.nextLine();

            String labelVertexOne = aux.split(" ")[0];
            String labelVertexTwo = aux.split(" ")[1];

            graph.adj.putIfAbsent(labelVertexOne, new ArrayList<>());
            var list = graph.adjList(labelVertexOne);
            list.add(labelVertexTwo);
            graph.adj.replace(labelVertexOne, list);

            graph.adj.putIfAbsent(labelVertexTwo, new ArrayList<>());
            var list2 = graph.adjList(labelVertexTwo);
            list2.add(labelVertexOne);
            graph.adj.replace(labelVertexTwo, list2);

            graph.vis.putIfAbsent(labelVertexOne, false);
            graph.vis.putIfAbsent(labelVertexTwo, false);
        }

        tot = graph.breathFirstSearch("Entrada", "*");
        graph.initializeVisited();
        tot += graph.breathFirstSearch("*", "Saida");
        System.out.println(tot);
    }
}

package graph;

import java.util.ArrayList;
import java.util.List;

public class Graph {

    private List<Vertex> vertices = new ArrayList<>();

    private List<Edge> edges = new ArrayList<>();

    public void addVertex(Vertex v) {
        vertices.add(v);
    }

    public void addEdge(Vertex start, Vertex target, String relationLabel) {
        //Create new Edge
        edges.add(new Edge(start, target, relationLabel));
    }

    public Vertex checkLabel(String label) {
        for (Vertex v : vertices
                ) {
            if (v.getLabel().equals(label)) {
                return v;
            }
        }
        return null;
    }

    public void printGraph(Graph g) {
        //TODO gesamten Graph grafisch ausgeben lassen
    }

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder("Edges:\n");
        for (Edge edge : edges) ret.append(edge.toString()).append("\n");
        ret.append("\nVertices:\n");
        for (Vertex vertex : vertices) {
            ret.append(vertex.toString()).append("\n");
        }
        return ret.toString();
    }

    public List<Vertex> getVertices() {
        return vertices;
    }
}

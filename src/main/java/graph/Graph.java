package graph;

import java.util.ArrayList;
import java.util.List;

public class Graph {

    private List<Vertex> vertices = new ArrayList<>();

    private List<Edge> edges = new ArrayList<>();

    public void addVertex(String label, String identifier, Graph graph, Integer id) {
        vertices.add(new Vertex(label, identifier, id));
    }

    public void addVertex(Vertex v) {
        vertices.add(v);
    }

    public void addEdge(Vertex start, Vertex target, String relationLabel) {
        //Create new Edge
        edges.add(new Edge(start, target, relationLabel));
    }

    public void addEdge(Edge e) {
        edges.add(e);
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
        String ret = "Edges:\n";
        for (Edge edge : edges) {
            ret += edge.toString() + "\n";
        }
        ret += "\nVertices:\n";
        for (Vertex vertex : vertices) {
            ret += vertex.toString() + "\n";
        }
        return ret;
    }

    public List<Vertex> getVertices() {
        return vertices;
    }
}

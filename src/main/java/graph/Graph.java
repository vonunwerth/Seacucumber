package graph;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Represents the data structure in a graph
 */
public class Graph {

    /**
     * List of all nodes in the graph
     */
    private List<Vertex> vertices = new ArrayList<>();

    /**
     * List of all edges in the graph
     */
    private List<Edge> edges = new ArrayList<>();

    /**
     * With this method a new node can be inserted in the graph. (Into the list of nodes {@link Graph#vertices}.)
     * This node still has no edges.
     *
     * @param v New node
     */
    public void addVertex(Vertex v) {
        vertices.add(v);
    }

    /**
     * With this method a new edge between two nodes can be defined. The new edge gets a name (relation label).
     *
     * @param edge The new edge
     */
    public void addEdge(Edge edge) {
        edges.add(edge);
    }

    /**
     * This method checks whether a node from the list of all {@link Graph#vertices} nodes has the given label and returns the first match.
     *
     * @param label Label to be searched for
     * @return The node that was found first with the given label. If no node is found, null is returned.
     */
    public Vertex checkLabel(String label) {
        for (Vertex v : vertices
                ) {
            if (v.getLabel().equals(label)) {
                return v;
            }
        }
        return null;
    }

    /**
     * This method converts a graph to a DOT graph, which can be displayed.
     *
     * @return String of the DOT graph
     */
    private String graphToDOT() {
        StringBuilder sb = new StringBuilder();
        /*
      Start der Ausgabe des Graphen
     */
        String START = "digraph G { \n";
        sb.append(START);


        for (Vertex vertex : vertices){
            //Add every Vertex to file
            /*
      Knoten für Ausgabe des Graphen
     */
            String VERTEX = "{0} [label='{1}']; \n";
            sb.append(MessageFormat.format(VERTEX, vertex.getId(), vertex.getIdentifier()));
        }

        for (Vertex vertex : vertices){
            //Add every outgoing edge from every vertex to file
            for (Edge out : vertex.getOutgoingEdges()) {
                /*
      Kante für Ausgabe des Graphen
     */
                String EDGE = "{0} -> {1} [label='{2}']; \n";
                sb.append(MessageFormat.format(EDGE, vertex.getId(), out.getTarget().getId(), out.getLabel()));
            }
        }

        String END = "} \n";
        sb.append(END);
        return sb.toString();
    }

    public void printGraph() {
        System.out.println(graphToDOT());
    }

    /**
     * This method outputs the graph as a string, by calling {@link Graph#printGraph()}.
     *
     * @return All nodes and edges
     */
    @Override
    public String toString() {
        printGraph();
        StringBuilder ret = new StringBuilder("Edges:\n");
        for (Edge edge : edges) {
            ret.append(edge.toString()).append("\n");
        }
        ret.append("\nVertices:\n");
        for (Vertex vertex : vertices) {
            ret.append(vertex.toString()).append("\n");
        }
        return ret.toString();
    }

    /**
     * This method returns all nodes of the graph.
     *
     * @return Nodes of the graph from {@link Graph#vertices}
     */
    public List<Vertex> getVertices() {
        return vertices;
    }

    /**
     * This method returns all edges of the graph.
     *
     * @return Edges of the graph from {@link Graph#vertices}
     */
    public List<Edge> getEdges() {
        return edges;
    }
}

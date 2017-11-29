package graph;

import java.util.ArrayList;
import java.util.Arrays;

import static graph.Graph.nodes;

public class Node {

    //Bezeichnung des Knoten
    private String label;
    //unsortierte Menge (HashSet) der ausgehenden Kanten
    private ArrayList<Edge> incomingEdges = new ArrayList<>();

    private ArrayList<Edge> outgoingEdges = new ArrayList<>();

    public Node(String label) {
        this.label = label;
        nodes.add(this);
    }

    public static void main(String[] args) {
        Node a = new Node("A");
        Node b = new Node("B");
        Node c = new Node("C");

        a.addEdge(b, "Coole Verbindung von A nach B");
        a.addEdge(c, "I bims 1 Kante vong A->C");
        b.addEdge(c, "Verb. von B nach C");
        System.out.println(Arrays.toString(a.incomingEdges.toArray()));
        System.out.println(Arrays.toString(a.outgoingEdges.toArray()));
        System.out.println(Arrays.toString(b.incomingEdges.toArray()));
        System.out.println(Arrays.toString(b.outgoingEdges.toArray()));
        System.out.println(Arrays.toString(c.incomingEdges.toArray()));
        System.out.println(Arrays.toString(c.outgoingEdges.toArray()));
    }

    @Override
    public String toString() {
        return label;
    }

    public void addEdge(Node node, String relationLabel) {
        Edge edge = new Edge(this, node, relationLabel);
        outgoingEdges.add(edge);
        node.incomingEdges.add(edge);
    }

    public String getLabel() {
        return label;
    }
}
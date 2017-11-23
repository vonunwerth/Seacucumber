package graph;

import java.util.Comparator;
import java.util.HashSet;

public class Node implements Comparable<Node>, Comparator<Node> {

    //Bezeichnung des Knoten
    private String val;
    //unsortierte Menge (HashSet) der ausgehenden Kanten
    private HashSet<Edge> edges = new HashSet<Edge>();
    //vorangehender Knoten
    private Node previous = null;

    //String relation;

    public Node() {
    }

    public Node(Graph g, String val) {
        this.val = val;
        g.getAllNodes().add(this);
    }

    //testen
    public static void main(String[] args) {

        Graph gr = new Graph();

        Node a = new Node(gr, "A"),
                b = new Node(gr, "B"),
                c = new Node(gr, "C"),
                d = new Node(gr, "D"),
                e = new Node(gr, "E"),
                f = new Node(gr, "F"),
                g = new Node(gr, "G"),
                h = new Node(gr, "H");

        a.addEdge(b, "f1");
        a.addEdge(c, "3");
        a.addEdge(f, "10");
        a.addEdge(g, "6");
        b.addEdge(d, "1");
        c.addEdge(d, "4");
        c.addEdge(e, "10");
        c.addEdge(f, "5");
        d.addEdge(e, "2");
        d.addEdge(f, "1");
        f.addEdge(g, "1");
        System.out.println(gr);
    }

    public HashSet<Edge> getEdges() {
        return edges;
    }

    public void setPrevious(Node previous) {
        this.previous = previous;
    }

    public void addEdge(Node node, String relation) {
        edges.add(new Edge(node, relation));
    }

    public void addEdges(Node node, String relation) {
        this.edges.add(new Edge(node, relation));
        node.edges.add(new Edge(this, relation));
    }

    public String toString() {
        return val;
    }

    public String path() {
        return "" + (previous == null ? val : previous.path() + " " + val);
    }

    public String rel() {
        String str = "test";
        return str;
    }

    public String firstNode() {
        return "" + (previous == null ? val : previous.firstNode());
    }

    public int compare(Node n1, Node n2) {
        return n1.toString().compareTo(n2.toString());
    }

    @Override
    public int compareTo(Node o) {
        return 0;
    }

}
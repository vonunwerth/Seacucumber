package graph;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Repräsentiert die Datenstruktur eines Graphen
 */
public class Graph {

    /**
     * Liste alle Knoten des Graphen
     */
    private List<Vertex> vertices = new ArrayList<>();

    /**
     * Liste aller Kanten des Graphen
     */
    private List<Edge> edges = new ArrayList<>();

    /**
     * Mit dieser Methode kann ein neuer Knoten in den Graphen eingefügt werden (in die Liste der Knoten {@link Graph#vertices}. Dieser besitzt noch keine Kanten.
     *
     * @param v Neuer Knoten
     */
    public void addVertex(Vertex v) {
        vertices.add(v);
    }

    /**
     * Mit dieser Methode kann eine neue Kante zwischen zwei Knoten gezogen werden. Diese erhält außerdem einen Namen, das relationLabel
     *
     * @param edge         Kante
     */
    public void addEdge(Edge edge) {
        edges.add(edge);
    }

    /**
     * Diese Methode überprüft, ob ein Knoten aus der Liste aller Knoten {@link Graph#vertices} das eingegebene Label hat und gibt den ersten gefundenen mit eben solchem aus.
     *
     * @param label Label, nachdem gesucht werden soll
     * @return Knoten, der als erstes mit genau diesem Label gefunden wurde. Wird kein Knoten gefunden, wird null zurückgegeben
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
     * Diese Methode wandelt einen Graphen zu einer DOT-Grafik um, welche angezeigt werden kann
     *
     * @return String des Graphen
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
     * Ausgabe des Graphen als String, dabei wird {@link Graph#printGraph()} aufgerufen
     * @return Alle Knoten und Kanten
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
     * Diese Methode gibt alle Knoten des Graphen zurück
     * @return Knoten des Graphen aus {@link Graph#vertices}
     */
    public List<Vertex> getVertices() {
        return vertices;
    }

    /**
     * Diese Methode gibt alle Kanten des Graphen zurück
     * @return Kanten des Graphen aus {@link Graph#vertices}
     */
    public List<Edge> getEdges() {
        return edges;
    }
}

package matcher;

import graph.Graph;
import graph.Vertex;
import org.neo4j.graphdb.*;

import java.util.*;

/**
 * Jeder neue Matcher muss die abstrakte Klasse Matcher erweitern. Der Matcher stellt bereits viele Funktionalitäten zu Verfügung, die genutzt, aber auch überschrieben werden können
 */
public abstract class Matcher {

    /**
     * Verwaltung der Datenbank
     */
    GraphDatabaseService db;

    /**
     * Graph, auf dem der Matcher arbeiten soll
     */
    Graph graph;

    /**
     * Konstructor um an die Datenbank zu kommen
     * @param database Datenbank
     * @param graph Graph
     */
    public Matcher(GraphDatabaseService database, Graph graph) {
        this.db = database;
        this.graph = graph;
    }

    /**
     * Default Matcher
     */
    Matcher() {
        this.db = null;
    }

    /**
     * Gibt alle Vorgänger eines Knoten zurück
     * @param node Knoten
     * @return Vorgängerknoten
     */
    List<Node> previousNodes(Node node) {
        List<Node> result = new ArrayList<>();
        Iterable<Relationship> rel = node.getRelationships(Direction.INCOMING);
        for (Relationship r : rel
                ) {
            result.add(r.getStartNode());
        }
        return result;
    }

    /**
     * Gibt alle Nachfolger eines Knotens zurück
     * @param node Knoten
     * @return Nachfolgeknoten
     */
    List<Node> successingNodes(Node node) {
        List<Node> result = new ArrayList<>();

        Iterable<Relationship> rel = node.getRelationships(Direction.OUTGOING);
        for (Relationship r : rel
                ) {
            System.out.println(r.toString());
            result.add(r.getEndNode());
        }
        return result;
    }

    /**
     * Vergleicht einen Vertex a mit einem Node b auf die Gleichheit ihrer Namen
     *
     * @param a Vertex
     * @param b Node
     * @return Stimmten die Label überein
     */
    Boolean compare(Vertex a, Node b) {
        for (Label c : b.getLabels()
                ) {
            if (c.name().equals(a.getLabel())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Vergleicht die Labels zweier Node
     *
     * @param a Node 1
     * @param b Node 2
     * @return Stimmen die Labels überein wird wahr zurückgegeben
     */
    Boolean compare(Node a, Node b) {
        return a.getLabels() == b.getLabels();
    }


    /**
     * Gibt alle Knoten zurück, die ähnlich zum Knoten des Query Graphen sind
     * @param vertex Vertex
     * @return Nodes for Vortex
     */
    List<Node> findeNodes(Vertex vertex) {


        Label lb =  Label.label(vertex.getIdentifier());
        System.out.println(lb.name());
        ResourceIterator<Node> iterator = db.findNodes(lb);
        List<Node> nodes = new ArrayList<>();
        while (iterator.hasNext()){
            try {
                nodes.add(iterator.next());
            }catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return nodes;
    }

    /**
     * Der Matcher startet
     *
     * @return Ergebnisset
     */
    public Set<Node> simulate() {
        Map<Integer, List<Node>> map = matchingAlgorithm();
        Set<Node> set = new HashSet<>();
        for (Map.Entry<Integer, List<Node>> entry : map.entrySet()) {
            set.addAll(entry.getValue());
        }
        return set;
    }

    /**
     * Diese Methode soll mit dem zu implementierenden Matching Algorithmus überschrieben werden.
     * @return Sie soll eine Map aus Integer, List von Nodes zurückgeben
     */
    public abstract Map<Integer, List<Node>> matchingAlgorithm();
}

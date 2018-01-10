package matcher;

import graph.Graph;
import graph.Vertex;
import org.neo4j.graphdb.*;

import java.util.*;

public abstract class Matcher {
    GraphDatabaseService db;
    Graph graph;

    /**
     * Constructor um an die Datenbank zu kommen
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
     * Returns all predecessors of the given node.
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
     * Returns all predecessors of the given node which have a given label.
     * @param node Knoten
     * @param label Label der Relationship
     * @return Vorgängerknoten
     */
    List<Node> previousNodes(Node node, String label) {
        List<Node> result = new ArrayList<>();
        RelationshipType rt = RelationshipType.withName(label);
        Iterable<Relationship> rel = node.getRelationships(Direction.INCOMING,rt);
        for (Relationship r : rel
                ) {
            result.add(r.getStartNode());
        }
        return result;
    }

    /**
     * Returns all successors of the given node.
     * @param node Knoten
     * @return Nachfolgeknoten
     */
    List<Node> successingNodes(Node node) {
        List<Node> result = new ArrayList<>();
        Iterable<Relationship> rel = node.getRelationships(Direction.OUTGOING);
        for (Relationship r : rel
                ) {
            result.add(r.getEndNode());
        }
        return result;
    }

    /**
     * Returns all successors of the given node which have a given label.
     * @param node Knoten
     * @param label Label der Relationship
     * @return Vorgängerknoten
     */
    List<Node> successingNodes(Node node, String label) {
        List<Node> result = new ArrayList<>();
        RelationshipType rt = RelationshipType.withName(label);
        Iterable<Relationship> rel = node.getRelationships(Direction.OUTGOING,rt);
        for (Relationship r : rel
                ) {
            result.add(r.getEndNode());
        }
        return result;
    }

    /**
     * Compares the labels of two given nodes.
     * @param a first node
     * @param b secon node
     * @return returns true if the nodes have the same label
     */
    Boolean compare(Node a, Node b) {
        return a.getLabels() == b.getLabels();
    }

    /**
     * Returns the relationships of the given node.
     * @param node the node you want the relationships from
     * @return list of the relationships
     */
    Iterable<Relationship> getRelationships(Node node) {
        return node.getRelationships();
    }

    /**
     * Returns all nodes that are similar to the vertex from the query graph.
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

    public Set<Node> simulate() {
        Map<Integer, List<Node>> map = matchingAlgorithm();
        Set<Node> set = new HashSet<>();
        for (Map.Entry<Integer, List<Node>> entry : map.entrySet()) {
            set.addAll(entry.getValue());
        }
        return set;
    }

    public abstract Map<Integer, List<Node>> matchingAlgorithm();
}

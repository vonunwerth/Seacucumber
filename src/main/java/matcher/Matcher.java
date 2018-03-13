package matcher;

import graph.Edge;
import graph.Graph;
import graph.Vertex;
import org.neo4j.graphdb.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Abstract matcher class
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public abstract class Matcher {

    /**
     * Database service
     */
    GraphDatabaseService db;

    /**
     * Graph pattern
     */
    Graph graph;

    /**
     * Constructor to get to the database
     *
     * @param database The database
     * @param graph    The graph
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
     * Hash code for the matcher
     * finalized to dont get the suggestion to implement in specialised matchers
     *
     * @return hashCode of object
     */
    @Override
    public final int hashCode() {
        return super.hashCode();
    }

    /**
     * Equals of the matcher
     * finalized to dont get the suggestion to implement in specialised matchers
     *
     * @param obj Object to compare
     * @return Comparison of the given object and this
     */
    @Override
    public final boolean equals(Object obj) {
        return super.equals(obj);
    }

    /**
     * Cloning of matchers
     * finalized to dont get the suggestion to implement in specialised matchers
     *
     * @return cloned Object
     * @throws CloneNotSupportedException Error
     */
    @Override
    protected final Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    /**
     * toString() for the matcher
     *
     * @return Matcher information
     */
    @Override
    public String toString() {
        return super.toString();
    }

    /**
     * Finalizes the matcher
     * finalized to dont get the suggestion to implement in specialised matchers
     *
     * @throws Throwable Error
     */
    @Override
    protected final void finalize() throws Throwable {
        super.finalize();
    }

    /**
     * Returns all predecessors of the given node.
     *
     * @param node The given node
     * @return A list of all previous nodes
     */
    final List<Node> previousNodes(Node node) {
        List<Node> result = new ArrayList<>();
        Iterable<Relationship> rel = node.getRelationships(Direction.INCOMING);
        for (Relationship r : rel) {
            result.add(r.getStartNode());
        }
        return result;
    }

    /**
     * Returns all predecessors of the given node which have a given label.
     *
     * @param node  The given node
     * @param label The given label of the relationship
     * @return A list of all previous nodes
     */
    final List<Node> previousNodes(Node node, String label) {
        List<Node> result = new ArrayList<>();
        RelationshipType rt = RelationshipType.withName(label);
        Iterable<Relationship> rel = node.getRelationships(Direction.INCOMING, rt);
        for (Relationship r : rel) {
            result.add(r.getStartNode());
        }
        return result;
    }

    /**
     * Returns all predecessors of the given node which have a given label with consideration of the properties.
     *
     * @param node The given node
     * @param edge The given edge from the query graph
     * @return A list of all previous nodes
     */
    final List<Node> previousNodesProp(Node node, Edge edge) {
        List<Node> result = new ArrayList<>();
        Iterable<Relationship> rel = node.getRelationships(Direction.INCOMING);
        for (Relationship r : rel) {
            if (edge.equalsProp(r)) {
                result.add(r.getStartNode());
            }
        }
        return result;
    }

    /**
     * Returns all successors of the given node.
     *
     * @param node The given node
     * @return A list of all successing nodes
     */
    final List<Node> successingNodes(Node node) {
        List<Node> result = new ArrayList<>();
        Iterable<Relationship> rel = node.getRelationships(Direction.OUTGOING);
        for (Relationship r : rel) {
            result.add(r.getEndNode());
        }
        return result;
    }

    /**
     * Returns all successors of the given node which have a given label.
     *
     * @param node  The given node
     * @param label The given label of the relationship
     * @return A list of all successing nodes
     */
    final List<Node> successingNodes(Node node, String label) {
        List<Node> result = new ArrayList<>();
        RelationshipType rt = RelationshipType.withName(label);
        Iterable<Relationship> rel = node.getRelationships(Direction.OUTGOING, rt);
        for (Relationship r : rel) {
            result.add(r.getEndNode());
        }
        return result;
    }

    /**
     * Returns all successors of the given node which have a given label with consideration of the properties.
     *
     * @param node The given node
     * @param edge The given edge from the query graph
     * @return A list of all successing nodes
     */
    final List<Node> successingNodesProp(Node node, Edge edge) {
        List<Node> result = new ArrayList<>();
        Iterable<Relationship> rel = node.getRelationships(Direction.OUTGOING);
        for (Relationship r : rel) {
            if (edge.equalsProp(r)) {
                result.add(r.getStartNode());
            }
        }
        return result;
    }

    /**
     * Compares the labels of two given nodes.
     *
     * @param a The first node
     * @param b The second node
     * @return Returns true if the nodes have the same label otherwise false
     */
    final Boolean compare(Node a, Node b) {
        return a.getLabels() == b.getLabels();
    } //TODO compare richtig so

    /**
     * Returns the relationships of the given node.
     *
     * @param node The node you want the relationships from
     * @param dir  The direction of the relationship
     * @return The list of the relationships
     */
    final Iterable<Relationship> getRelationships(Node node, Direction dir) {
        return node.getRelationships(dir);
    }

    /**
     * Returns the relationships of the given node.
     *
     * @param node The node you want the relationships from
     * @param rel  The type of the relationship
     * @return The list of the relationships
     */
    final Iterable<Relationship> getRelationships(Node node, RelationshipType rel) {
        return node.getRelationships(rel);
    }

    /**
     * Returns the relationships of the given node.
     *
     * @param node The node you want the relationships from
     * @return The list of the relationships
     */
    final Iterable<Relationship> getRelationships(Node node) {
        return node.getRelationships();
    }

    /**
     * Returns all nodes that have the same label as the vertex from the query graph.
     *
     * @param vertex The given Vertex
     * @return Nodes for Vortex
     */
    final List<Node> findNodes(Vertex vertex) {
        Label lb = Label.label(vertex.getIdentifier());
        ResourceIterator<Node> iterator = db.findNodes(lb);
        //Simple way to collect an iterator into a List
        return iterator.stream().collect(Collectors.toList());
    }

    /**
     * Returns all nodes that have the same label as the vertex from the query graph with consideration of the properties.
     *
     * @param vertex The given Vertex
     * @return Nodes for Vortex
     */
    final List<Node> findNodesProp(Vertex vertex) {
        ResourceIterable<Node> list = db.getAllNodes();
        //Filter out the nodes equal to the Vertex and collect into a List
        return list.stream().filter(vertex::equalsProp).collect(Collectors.toList());
    }

    /**
     * This method executes the dualSimulation algorithm and formats the result for NEO4J.
     *
     * @return The result set
     */
    public final Set<Node> simulate() {
        Map<Integer, List<Node>> map = matchingAlgorithm();
        Set<Node> set = new HashSet<>();
        for (Map.Entry<Integer, List<Node>> entry : map.entrySet()) {
            set.addAll(entry.getValue());
        }
        return set;
    }

    /**
     * This method calculates the Power Set of a given Set
     *
     * @param originalSet Set to calculate Power Set about
     * @return Power Set including empty set
     */
    public final Set<Set<Node>> powerSet(Set<Node> originalSet) {
        Set<Set<Node>> sets = new HashSet<>();
        sets.add(new HashSet<>());
        for (Node node : originalSet) {
            Set<Set<Node>> newPs = new HashSet<>();
            for (Set<Node> subset : sets) {
                newPs.add(subset);
                Set<Node> newSubset = new HashSet<>(subset);
                newSubset.add(node);
                newPs.add(newSubset);
            }
            sets = newPs;
        }
        return sets;
    }

    /**
     * This function must be overridden with the dualSimulation algorithm
     *
     * @return The result of the algorithm
     */
    public abstract Map<Integer, List<Node>> matchingAlgorithm();
}

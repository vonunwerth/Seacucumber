package graph;

import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Represents vertieces in a graph
 */
@SuppressWarnings({"WeakerAccess", "unused", "ConstantConditions"})
public class Vertex {

    /**
     * Label of the vertex
     */
    private String label;

    /**
     * Identifier of the vertex
     */
    private String identifier;

    /**
     * Id of the vertex
     */
    private Integer id;

    /**
     * A map with attributes of the vertex
     */
    private Map<String, String> properties;

    /**
     * List of incoming edges
     */
    private List<Edge> incomingEdges = new ArrayList<>();

    /**
     * List of outgoing edges
     */
    private List<Edge> outgoingEdges = new ArrayList<>();

    /**
     * This method creates a new vertex.
     *
     * @param label      Label of the vertex
     * @param identifier Identifier of the vertex
     * @param id         Id of the vertex
     * @param attributes the attributes of the vertex
     */
    public Vertex(String label, String identifier, Integer id, Map<String, String> attributes) {
        this.label = label;
        this.identifier = identifier;
        this.id = id;
        this.properties = attributes;
    }

    /**
     * Returns the vertex as a formatted string
     *
     * @return Formatted string [format: label:identifier]
     */
    @Override
    public String toString() {
        return "" + label + ":" + identifier;
    }

    /**
     * This method adds an edge between this and another vertex
     *
     * @param vertex The vertex the added edge points to
     * @param edge   The added edge
     */
    public void addEdge(Vertex vertex, Edge edge) {
        outgoingEdges.add(edge);
        vertex.incomingEdges.add(edge);
    }

    /**
     * Returns the label of the vertex.
     *
     * @return The label
     */
    public String getLabel() {
        return label;
    }

    /**
     * Returns the identifier of the vertex.
     *
     * @return The identifier
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * Returns the map of attributes of the vertex.
     *
     * @return The map of attributes
     */
    public Map<String, String> getProperties() {
        return properties;
    }

    /**
     * Returns the incoming edges of the vertex.
     *
     * @return The list of the incoming edges
     */
    public List<Edge> getIncomingEdges() {
        return incomingEdges;
    }

    /**
     * Returns the outgoing edges of the vertex.
     *
     * @return The list of the outgoing edges
     */
    public List<Edge> getOutgoingEdges() {
        return outgoingEdges;
    }

    /**
     * Returns the Id of the vertex.
     *
     * @return The Id
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method compares Neo4J-node with node.
     *
     * @param b The node for the comparison
     * @return true if equal, false if not equal
     */
    public Boolean equals(Node b) {
        for (Label c : b.getLabels()) {
            if (c.name().equals(this.getIdentifier())) {
                return true;
            }
        }
        return false;
    }

    /**
     * This method compares Neo4J-node with node for isomorphic
     *
     * @param b The node for the comparison
     * @return true if equal, false if not equal
     */
    public Boolean isomorphWithoutProp(Node b) {
        return isomorphRelationships(b) && isomorphLabels(b) && isomorphProps(b);
    }

    /**
     * This method compares Neo4J-nodes properties
     *
     * @param b The node for the comparison
     * @return true if equal, false if not equal
     */
    private boolean isomorphProps(Node b) {
        Boolean equal = false;
        for (String s : this.properties.keySet()) {
            if (b.getProperty(s).toString().equals(this.properties.get(s))) {
                equal = true;
            } else {
                equal = false;
                break;
            }

        }
        return equal;
    }

    /**
     * This method compares Neo4J-node with node considering the properties.
     *
     * @param b The node for the comparison
     * @return true if equal, false if not equal
     */
    private boolean isomorphLabels(Node b) {
        for (Label c : b.getLabels()) {
            if (c.name().equals(this.getIdentifier())) {
                return true; //Zu jeden Label des Datenbankknotens existiert Label im Patternknoten
            }
        }
        return false;
    }

    /**
     * Checks if two Nodes are isomorph relating to relationsships of those node
     *
     * @param b Node to compare relationsships with to this Vertex
     */
    public Boolean isomorphRelationships(Node b) {
        int relationships = 0;
        int isomorphRelationshipsOne = 0;
        int isomorphRelationshipsTwo = 0;
        for (Relationship rel : b.getRelationships()) relationships++;
        if (relationships == this.outgoingEdges.size()) {
            for (Relationship rel : b.getRelationships()) { //Zu jeder Relation in Datenbank lässt sich Relation in Pattern finden
                for (Edge edge : this.getOutgoingEdges()) {
                    if (rel.getType().name().equals(edge.getLabel())) {
                        isomorphRelationshipsOne++;
                    }
                }
            }
            for (Edge edge : this.getOutgoingEdges()) { //Zu jeder Relation in Pattern lässt sich Realtion in Datenbank finden
                for (Relationship rel : b.getRelationships()) {
                    if (rel.getType().name().equals(edge.getLabel())) {
                        isomorphRelationshipsTwo++;
                    }
                }
            }
        } else { //Wenn Patternknoten nicht gleich viele Relationsships (Kanten) hat, sind Knoten nicht isomorph
            return false;
        }
        return (relationships == isomorphRelationshipsOne && isomorphRelationshipsOne == isomorphRelationshipsTwo);
    }


    /**
     * This method compares Neo4J-node with node considering the properties.
     *
     * @param b The node for the comparison
     * @return true if equal, false if not equal
     */
    public Boolean equalsProp(Node b) {
        Boolean equal = false;
        for (Label c : b.getLabels()) {
            if (c.name().equals(this.getLabel())) {
                equal = true;
            }
        }
        for (String s : this.properties.keySet()) {
            if (b.getProperty(s).toString().equals(this.properties.get(s))) {
                equal = true;
            } else {
                equal = false;
                break;
            }

        }
        return equal;
    }

    /**
     * This method builds a field in which all nodes and edges are entered.
     * And prints a node with all connected nodes on the console.
     */
    public void printVertex() {
        String[][] field = new String[5][5];
        field[2][2] = "[" + this.identifier.charAt(0) + "]";
        String right = " \u2192 ";
        String left = " \u2190 ";
        String up = " \u2191 ";
        String down = " \u2193 ";

        //Iterated by the list of the outgoing edges
        for (int x = 0; x < this.outgoingEdges.size(); x++) {
            if (this.outgoingEdges.size() == 0) break;
            if (field[2][3] == null) {
                field[2][3] = right;
                field[2][4] = "(" + outgoingEdges.get(x).getVertex().get(1).getIdentifier().charAt(0) + ")";
                continue;
            }
            if (field[3][3] == null) {
                field[3][3] = " \u2198 ";
                field[4][4] = "(" + outgoingEdges.get(x).getVertex().get(1).getIdentifier().charAt(0) + ")";
                continue;
            }
            if (field[3][2] == null) {
                field[3][2] = down;
                field[4][2] = "(" + outgoingEdges.get(x).getVertex().get(1).getIdentifier().charAt(0) + ")";
                continue;
            }
            if (field[1][3] == null) {
                field[1][3] = " \u2197 ";
                field[0][4] = "(" + outgoingEdges.get(x).getVertex().get(1).getIdentifier().charAt(0) + ")";
                continue;
            }
            if (field[2][1] == null) {
                field[2][1] = left;
                field[2][0] = "(" + outgoingEdges.get(x).getVertex().get(1).getIdentifier().charAt(0) + ")";
                continue;
            }
            if (field[1][1] == null) {
                field[1][1] = " \u2196 ";
                field[0][0] = "(" + outgoingEdges.get(x).getVertex().get(1).getIdentifier().charAt(0) + ")";
                continue;
            }
            if (field[1][2] == null) {
                field[1][2] = up;
                field[0][2] = "(" + outgoingEdges.get(x).getVertex().get(1).getIdentifier().charAt(0) + ")";
                continue;
            }
            if (field[3][1] == null) {
                field[3][1] = " \u2199 ";
                field[4][0] = "(" + outgoingEdges.get(x).getVertex().get(1).getIdentifier().charAt(0) + ")";
            }
        }


        //----------------------------------all incoming edges-----------------------------------
        for (int x = 0; x < this.incomingEdges.size(); x++) {
            if (this.incomingEdges.size() == 0) break;
            if (field[2][3] == null) {
                field[2][3] = left;
                field[2][4] = "(" + incomingEdges.get(x).getVertex().get(0).getIdentifier().charAt(0) + ")";
                continue;
            }
            if (field[3][3] == null) {
                field[3][3] = " \u2196 ";
                field[4][4] = "(" + incomingEdges.get(x).getVertex().get(0).getIdentifier().charAt(0) + ")";
                continue;
            }
            if (field[3][2] == null) {
                field[3][2] = up;
                field[4][2] = "(" + incomingEdges.get(x).getVertex().get(0).getIdentifier().charAt(0) + ")";
                continue;
            }
            if (field[1][3] == null) {
                field[1][3] = " \u2199 ";
                field[0][4] = "(" + incomingEdges.get(x).getVertex().get(0).getIdentifier().charAt(0) + ")";
                continue;
            }
            if (field[2][1] == null) {
                field[2][1] = right;
                field[2][0] = "(" + incomingEdges.get(x).getVertex().get(0).getIdentifier().charAt(0) + ")";
                continue;
            }
            if (field[1][1] == null) {
                field[1][1] = " \u2198 ";
                field[0][0] = "(" + incomingEdges.get(x).getVertex().get(0).getIdentifier().charAt(0) + ")";
                continue;
            }
            if (field[1][2] == null) {
                field[1][2] = down;
                field[0][2] = "(" + incomingEdges.get(x).getVertex().get(0).getIdentifier().charAt(0) + ")";
                continue;
            }
            if (field[3][1] == null) {
                field[3][1] = " \u2197 ";
                field[4][0] = "(" + incomingEdges.get(x).getVertex().get(0).getIdentifier().charAt(0) + ")";
            }
        }
        printArray(field);
    }


    /**
     * Returns a square array on the console.
     *
     * @param array Square array
     */
    private void printArray(String[][] array) {
        for (String[] single_array : array) {
            for (int y = 0; y < array.length; y++) {
                try {
                    if (single_array[y] != null) {
                        System.out.print(single_array[y]);
                    } else {
                        System.out.print("   ");
                    }
                } catch (Exception e) {
                    System.out.println("array must be square!");
                    break;
                }
            }
            System.out.println();
        }
    }
}
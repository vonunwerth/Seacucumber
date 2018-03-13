package graph;

import org.neo4j.graphdb.Relationship;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Representation of a directed edge in a graph
 */
@SuppressWarnings("unused")
public class Edge {

    /**
     * Starting node
     */
    private Vertex start;

    /**
     * Name of the relation
     */
    private String label;

    /**
     * Destination node
     */
    private Vertex target;

    /**
     * Properties
     */
    private Map<String,String> properties;

    /**
     * Visited variable for some special algorithms, which iterate over the edges of a graph
     * false by default
     */
    private boolean visited = false;

    /**
     * Unique id to identify the edge
     */
    private Integer id;

    /**
     * Create a new edge.
     *
     * @param start         Starting node
     * @param target        Destination node
     * @param relationLabel Name of the transitional relation
     */
    public Edge(Vertex start, Vertex target, String relationLabel, Map<String,String> attributes) {
        this.start = start;
        this.target = target;
        this.label = relationLabel;
        this.properties = attributes;
        String uniqueNumber = "" + start.getId() + "" + target.getId();
        this.id = new Integer(uniqueNumber);
    }

    /**
     * Output of the edge as a formatted string.
     *
     * @return Formatted string [format: Starting node + Destination node + Name of the relation]
     */
    @Override
    public String toString() {
        return start + " " + target + " " + label;
    }

    /**
     * The function returns the starting node {@link Edge#start} and the destination node {@link Edge#target} in a list of nodes.
     *
     * @return List with start and end nodes
     */
    public List<Vertex> getVertex() {
        List<Vertex> list = new ArrayList<>(2);
        list.add(start);
        list.add(target);
        return list;
    }

    /**
     * Returns the destination node.
     *
     * @return The node the edge points to
     */
    public Vertex getTarget() {
        return target;
    }

    /**
     * Returns the starting node.
     *
     * @return The node from which the edge originates
     */
    public Vertex getStart() {
        return start;
    }

    /**
     * Returns the name of the relation.
     *
     * @return The name of the transition
     */
    public String getLabel() {
        return label;
    }

    /**
     * Returns the map of attributes.
     *
     * @return The map of attributes
     */
    public Map<String, String> getProperties() {
        return properties;
    }

    /**
     * Compares edges with Neo4J Realtionships.
     *
     * @param rel relatinoship to compare to
     * @return true if both have the same properties and name, otherwise false
     */
    public Boolean equalsProp(Relationship rel){
        Boolean equ = false;
        if (this.getLabel().equals(rel.getType().name())){
            equ = true;
        }
        for (String s : this.properties.keySet()) {
            if (rel.getProperty(s).toString().equals(this.properties.get(s)) ){
                equ = true;
            } else{
                equ = false;
                break;
            }

        }
        return equ;
    }

    /**
     * Was this Edge used by an algorithm
     *
     * @return The actual state of the visited variable
     */
    public boolean isVisited() {
        return visited;
    }

    /**
     * Sets visited to true, can be useful for some special algorithms
     */
    public String visit() {
        visited = true;
        return this.label;
    }

    /**
     * Compares the ids of the given edges
     *
     * @param edge edge no. 1
     * @return are the ids the same
     */
    public boolean equals(Edge edge) {
        return this.id.equals(edge.id);
    }

    /**
     * Returns the id of the given Edge
     * @return unique id
     */
    public Integer getId() {
        return id;
    }
}

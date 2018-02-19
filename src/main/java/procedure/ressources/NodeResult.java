package procedure.ressources;

import org.neo4j.graphdb.Node;

/**
 * Result constructor for NEO4J procedures.
 */
public class NodeResult {
    /**
     * Node of results. (Must be public for NEO4J!)
     */
    @SuppressWarnings("WeakerAccess")
    public Node node;

    /**
     * Result of the query. (Must be public for NEO4J!)
     *
     * @param node The given node
     */
    @SuppressWarnings("WeakerAccess")
    public NodeResult(Node node) {
        this.node = node;
    }
}

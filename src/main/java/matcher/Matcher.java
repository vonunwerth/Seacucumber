package matcher;
import graph.Vertex;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.ResourceIterator;

import java.util.ArrayList;
import java.util.List;

public abstract class Matcher {
    GraphDatabaseService db;

    /**
     * Constructor um an die Datenbank zu kommen
     * @param database
     */
    public Matcher(GraphDatabaseService database){
        this.db = database;
    }

    /**
     * Returns all predecessors of the given node.
     * @param node
     * @return
     */
    List<Node> previousNodes(Node node) {
        return null;
    }

    /**
     * Returns all sucessors of the given node.
     * @param node
     * @return
     */
    List<Node> successingNodes(Node node) {
        return null;
    }

    /**
     * Returns all nodes that are similar to the vertex from the query graph.
     * @param vertex
     * @return
     */
    List<Node> findeNodes(Vertex vertex) {
        Label lb = Label.label(vertex.getLabel());
        ResourceIterator<Node> iterator = db.findNodes(lb);
        List<Node> nodes = new ArrayList<>();
        while (iterator.next() != null){
            nodes.add(iterator.next());
        }
        return nodes;
    }

    abstract List<Node> matchingAlgorithm();


}

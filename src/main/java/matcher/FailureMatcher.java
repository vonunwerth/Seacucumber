package matcher;

import org.neo4j.graphdb.Node;

import java.util.List;
import java.util.Map;

public class FailureMatcher extends Matcher {

    public FailureMatcher(org.neo4j.graphdb.GraphDatabaseService db, graph.Graph graph) {
        this.db = db;
        this.graph = graph;
    }

    @java.lang.Override
    public Map<Integer, List<Node>> matchingAlgorithm() {
        return null;
    }
}

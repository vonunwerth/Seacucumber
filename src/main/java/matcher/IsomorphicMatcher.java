package matcher;

import graph.Graph;
import graph.Vertex;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;

import java.util.*;

/**
 * Creates a new Isomorphic matcher
 */
public class IsomorphicMatcher extends Matcher {
    public IsomorphicMatcher(GraphDatabaseService database, Graph graph) {
        this.db = database;
        this.graph = graph;
    }

    /**
     * Simple graph isomorphism of the pattern on all subgraphs of the database
     *
     * @return isomorphic subsets
     */
    @Override
    public Map<Integer, List<Node>> matchingAlgorithm() {
        Map<Integer, List<Node>> resultMap = new HashMap<>();
        int nodeCount = 0;
        Set<Node> databaseNodes = new HashSet<>();
        for (Node node : db.getAllNodes()) {
            //amount of nodes in the database
            nodeCount++;
            databaseNodes.add(node);
        }

        //pattern has more nodes than the database
        if (graph.getVertices().size() > nodeCount) return null;

        //all subsets that have the same size as the pattern
        Set<Set<Node>> powerSet = powerSet(databaseNodes);

        //for all nodes in the pattern
        for (Vertex vertex : graph.getVertices()) {
            int setCounter = 0;
            //for all subgraphs in the database
            for (Set<Node> set : powerSet) {
                //if the subgraph has the correct size
                if (set.size() == graph.getVertices().size()) {
                    setCounter++;
                    //all isomorphic nodes that need to be found
                    int nodes = set.size();
                    int isomorphNodes = 0;
                    for (Node node : set) {
                        if (vertex.isomorphic(node)) {
                            //isomorphic node found
                            isomorphNodes++;
                        }
                    }
                    //mapping successful
                    if (nodes == isomorphNodes) {
                        List<Node> tempList = new ArrayList<>(set);
                        resultMap.put(setCounter, tempList);
                    }
                }
            }
        }
        return resultMap;
    }
}

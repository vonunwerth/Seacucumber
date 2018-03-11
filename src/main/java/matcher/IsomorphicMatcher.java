package matcher;

import graph.Graph;
import graph.Vertex;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;

import java.util.*;

public class IsomorphicMatcher extends Matcher {
    public IsomorphicMatcher(GraphDatabaseService database, Graph graph) {
        this.db = database;
        this.graph = graph;
    }

    @Override
    public Map<Integer, List<Node>> matchingAlgorithm() {
        Map<Integer, List<Node>> resultMap = new HashMap<>();
        int nodeCount = 0;
        Set<Node> databaseNodes = new HashSet<Node>();
        for (Node node : db.getAllNodes()) {
            nodeCount++; //Anzahl der Knoten in der Datenbank
            databaseNodes.add(node);
        }
        if (graph.getVertices().size() > nodeCount) return null; //Pattern hat mehr Knoten als Datenbank

        //Alle Subsets die so groß wie das Pattern sind
        Set<Set<Node>> powerSet = powerSet(databaseNodes);

        for (Vertex vertex : graph.getVertices()) {
            int setCounter = 0;
            for (Set<Node> set : powerSet) {
                setCounter++;
                int nodes = set.size();
                int isomorphNodes = 0;
                for (Node node : set) {
                    if (vertex.isomorph(node)) {
                        isomorphNodes++;
                    }
                }
                if (nodes == isomorphNodes) {
                    List<Node> tempList = new ArrayList<>(set);
                    resultMap.put(setCounter, tempList);
                }
            }
        }
        return resultMap;
    }
}

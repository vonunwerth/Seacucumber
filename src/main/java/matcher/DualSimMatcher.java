package matcher;

import graph.Edge;
import graph.Graph;
import graph.Vertex;
import org.neo4j.graphdb.Node;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DualSimMatcher extends Matcher {
    @Override
    Map<Integer, List<Node>> matchingAlgorithm() {
        Boolean changes = true;
        Map<Integer, List<Node>> sim = new HashMap<>();
        for (Vertex v : Graph.vertices
                ) {
            sim.put(v.getId(), findeNodes(v));
        }

        while (changes) {
            for (Vertex v : Graph.vertices
                    ) {
                for (Edge e : v.getOutgoingEdges()
                        ) {
                    for (Node n : sim.get(v.getId())
                            ) {
                        Boolean exists = false;
                        for (Node n2 : successingNodes(n)
                                ) {
                            exists = compare(n, n2);
                        }
                        if (!(exists)) {
                            sim.get(v.getId()).remove(n);
                        }
                    }
                }
            }

            for (Vertex v : Graph.vertices
                    ) {
                for (Edge e : v.getIncomingEdges()
                        ) {
                    for (Node n : sim.get(v.getId())
                            ) {
                        Boolean exists = false;
                        for (Node n2 : previousNodes(n)
                                ) {
                            exists = compare(n, n2);
                        }
                        if (!(exists)) {
                            sim.get(v.getId()).remove(n);
                        }
                    }
                }
            }
        }
        return sim;
    }
}

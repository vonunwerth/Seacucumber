package matcher;

import graph.Edge;
import graph.Graph;
import graph.Vertex;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DualSimMatcher extends Matcher {

    public DualSimMatcher(GraphDatabaseService db, Graph graph) {
        this.db = db;
        this.graph = graph;
    }

    @Override
    public Map<Integer, List<Node>> matchingAlgorithm() {

        Boolean changes = false;
        Map<Integer, List<Node>> sim = new HashMap<>();
        for (Vertex v : graph.getVertices()
                ) {
            sim.put(v.getId(), findeNodes(v));
            System.out.println(v.getLabel());
        }

        do{

            changes = false;
            for (Vertex v : graph.getVertices()
                    ) {
                for (Edge e : v.getOutgoingEdges()
                        ) {
                    System.out.println("Hallo");
                    System.out.println(e.toString());
                    for (Node n : sim.get(v.getId())
                            ) {
                        Boolean exists = false;

                        for (Node n2 : successingNodes(n)
                                ) {
                            exists = compare(n, n2);
                        }
                        if (!(exists)) {
                            sim.get(v.getId()).remove(n);
                            changes = true;


                        }
                    }
                }
            }

            for (Vertex v : graph.getVertices()
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
                            changes = true;
                        }
                    }
                }
            }
        } while (changes);
        for (Integer s: sim.keySet()
             ) {
            int counter = 0;
            for (Node n: sim.get(s)
                 ) {
                System.out.print(n.getId()+" ");
                System.out.println(n.getLabels().iterator().next().name());
                counter++;
            }
            System.out.println(counter);
        }
        return sim;
    }
}

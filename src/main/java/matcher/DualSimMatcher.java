package matcher;

import graph.Edge;
import graph.Graph;
import graph.Vertex;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Sample matcher that extends the abstract class {@link Matcher}.
 */
public class DualSimMatcher extends Matcher {

    /**
     * This method creates a new dual simulation matcher.
     *
     * @param db    The database to be used
     * @param graph The graph to be used
     */
    public DualSimMatcher(GraphDatabaseService db, Graph graph) {
        this.db = db;
        this.graph = graph;
    }

    static void count(Map<Integer, List<Node>> sim) {
        for (Integer s : sim.keySet()) {
            int counter = 0;
            for (Node n : sim.get(s)) {
                System.out.print(n.getId() + " ");
                System.out.println(n.getLabels().iterator().next().name());
                counter++;
            }
            System.out.println(counter);
        }
    }

    /**
     * The dualSimulation algorithm to be used.
     * @return The simulation
     */
    @Override
    public Map<Integer, List<Node>> matchingAlgorithm() {

        Boolean changes;
        Map<Integer, List<Node>> sim = new HashMap<>();
        for (Vertex v : graph.getVertices()) {
            sim.put(v.getId(), findNodes(v));
            System.out.println(v.getLabel());
        }

        do {
            changes = false;
            for (Vertex v : graph.getVertices()
                    ) {
                for (Edge e : v.getOutgoingEdges()
                        ) {
                    try {
                        List<Node> removeList = new LinkedList<>();
                        for (Node n : sim.get(v.getId())
                                ) {
                            Boolean exists = false;
                            for (Node n2 : successingNodes(n, e.getLabel())
                                    ) {
                                exists = sim.get(e.getTarget().getId()).contains(n2);
                            }
                            if (!(exists)) {
                                removeList.add(n);
                                changes = true;

                            }
                        }
                        sim.get(v.getId()).removeAll(removeList);
                    } catch (Exception ex) {
                        System.out.println(ex.toString());
                        System.out.println("error");
                        for (Node n : sim.get(v.getId())
                                ) {
                            System.out.println(n);
                        }
                    }

                }
                System.out.println("middle");
                for (Edge e : v.getIncomingEdges()) {
                    System.out.println(e.toString());
                    try {
                        List<Node> removeList = new LinkedList<>();
                        for (Node n : sim.get(v.getId())) {
                            Boolean exists = false;
                            for (Node n2 : previousNodes(n, e.getLabel())) {

                                exists = sim.get(e.getStart().getId()).contains(n2);
                            }
                            if (!(exists)) {
                                removeList.add(n);
                                changes = true;
                            }
                        }
                        sim.get(v.getId()).removeAll(removeList);
                    } catch (Exception ex) {
                        System.out.println("error");
                        for (Node n : sim.get(v.getId())
                                ) {
                            System.out.println(n);
                        }
                    }

                }
            }
        } while (changes);
        count(sim);
        return sim;
    }
}

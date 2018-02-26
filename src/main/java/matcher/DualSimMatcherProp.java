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

//TODO irgendwie gehts nicht

/**
 * Sample matcher that extends the abstract class {@link Matcher}.
 */
public class DualSimMatcherProp extends Matcher {

    /**
     * This method creates a new dual simulation matcher with consideration of the properties.
     *
     * @param db The database to be used
     * @param graph The graph to be used
     */
    public DualSimMatcherProp(GraphDatabaseService db, Graph graph) {
        this.db = db;
        this.graph = graph;
    }

    /**
     * The dualSimulation algorithm to be used. (Currently: Dual simulation with consideration of the properties)
     *
     * @return The simulation
     */
    @Override
    public Map<Integer, List<Node>> matchingAlgorithm() {

        Boolean changes;
        Map<Integer, List<Node>> sim = new HashMap<>();
        for (Vertex v : graph.getVertices()) {
            sim.put(v.getId(), findNodesProp(v));
            System.out.println(v.getLabel());
        }

        do{
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
                            for (Node n2 : successingNodesProp(n,e)
                                    ) {
                                exists = sim.get(e.getTarget().getId()).contains(n2);
                            }
                            if (!(exists)) {
                                removeList.add(n);
                                changes = true;


                            }
                        }
                        sim.get(v.getId()).removeAll(removeList);
                    } catch (Exception ex){
                        System.out.println(ex.toString());
                        System.out.println("error");
                        for (Node n: sim.get(v.getId())
                                ) {
                            System.out.println(n);
                        }
                    }

                }
                System.out.println("middle");
                for (Edge e : v.getIncomingEdges()) {
                    System.out.println(e.toString());
                    try{
                        List<Node> removeList = new LinkedList<>();
                        for (Node n : sim.get(v.getId())) {
                            Boolean exists = false;
                            for (Node n2 : previousNodesProp(n, e)) {
                                exists = sim.get(e.getStart().getId()).contains(n2);
                            }
                            if (!(exists)) {
                                removeList.add(n);
                                changes = true;
                            }
                        }
                        sim.get(v.getId()).removeAll(removeList);
                    } catch (Exception ex){
                        System.out.println("error");
                        for (Node n : sim.get(v.getId())) {
                            System.out.println(n);
                        }
                    }

                }
            }
        } while (changes);
        for (Integer s : sim.keySet()) {
            int counter = 0;
            for (Node n : sim.get(s)) {
                System.out.print(n.getId()+" ");
                System.out.println(n.getLabels().iterator().next().name());
                counter++;
            }
            System.out.println(counter);
        }
        return sim;
    }
}
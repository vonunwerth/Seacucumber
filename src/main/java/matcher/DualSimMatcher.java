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

public class DualSimMatcher extends Matcher {

    /**
     * Neuer Dual Simulation Matcher
     *
     * @param db    Datenbank, die genutzt werden soll
     * @param graph Graph
     */
    public DualSimMatcher(GraphDatabaseService db, Graph graph) {
        this.db = db;
        this.graph = graph;
    }

    /**
     * Zu nutzender Matching Algorithmus
     * @return Simulation
     */
    @Override
    public Map<Integer, List<Node>> matchingAlgorithm() {

        Boolean changes;
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
                    try {
                        List<Node> removeList = new LinkedList<>();
                        for (Node n : sim.get(v.getId())
                                ) {
                            Boolean exists = false;
                            for (Node n2 : successingNodes(n,e.getRelation())
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
                        System.out.println("Fehler");
                        for (Node n: sim.get(v.getId())
                                ) {
                            System.out.println(n);
                        }
                    }

                }
                System.out.println("Mitte");
                for (Edge e : v.getIncomingEdges()
                        ) {
                    System.out.println(e.toString());
                    try{
                        List<Node> removeList = new LinkedList<>();
                        for (Node n : sim.get(v.getId())
                                ) {
                            Boolean exists = false;
                            for (Node n2 : previousNodes(n,e.getRelation())
                                    ) {

                                exists = sim.get(e.getStart().getId()).contains(n2);
                            }
                            if (!(exists)) {
                                removeList.add(n);
                                changes = true;
                            }
                        }
                        sim.get(v.getId()).removeAll(removeList);
                    } catch (Exception ex){
                        System.out.println("Fehler");
                        for (Node n: sim.get(v.getId())
                                ) {
                            System.out.println(n);
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

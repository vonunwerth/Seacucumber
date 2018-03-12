package matcher;

import graph.Edge;
import graph.Graph;
import graph.Vertex;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;

import java.util.*;

public class TraceMatcher extends Matcher {


    /**
     * Das Set aller Traces eines Patterns
     */
    private Set<ArrayList<String>> patternTraces = new HashSet<>();

    public TraceMatcher(GraphDatabaseService database, Graph graph) {
        this.db = database;
        this.graph = graph;
    }

    @Override
    public Map<Integer, List<Node>> matchingAlgorithm() {
        Map<Integer, List<Node>> resultMap = new HashMap<>();

        for (Vertex vertex : graph.getVertices()) {
            ArrayList<String> trace = new ArrayList<>();
            Set<Integer> usedEdges = new HashSet<>(); //Merkt sich in jedem Rekursionsschritt, welche Kanten schon genutzt wurden, um nicht erneut eine Kante zu nutzen

            ArrayList<String> simpleActualNode = new ArrayList<>(Collections.singleton(vertex.getIdentifier()));
            if (!patternTraces.contains(simpleActualNode)) patternTraces.add(simpleActualNode);

            floodPattern(vertex, trace, usedEdges);
        }
        return resultMap;
    }

    /**
     * Findet alle möglichen Traces von einem Starknoten ausgehend. Kreise werden dabei ignoriert
     *
     * @param actualNode Startknoten
     * @param trace      Bisherige zurückgelegter Weg
     * @param usedEdges  Bereits genutzte Kanten, Kreise sollen verhindert werden
     */
    private void floodPattern(Vertex actualNode, ArrayList<String> trace, Set<Integer> usedEdges) {
        trace.add(actualNode.getIdentifier());
        //TODO Testen, ob Algortihmus mit Kreisen in Pattern funktioniert
        if (!patternTraces.contains(trace))
            patternTraces.add(trace); //Bisher zurückgelegten Weg zu den Traces hinzufügen
        for (Edge edge : actualNode.getOutgoingEdges()) {
            if (!usedEdges.contains(edge.getId())) {
                usedEdges.add(edge.getId()); //Sperre Kante für tiefere Aufrufe, um Kreise zu vermeiden
                actualNode = edge.getTarget(); //Nutze Nachfolger als Startknoten
                floodPattern(actualNode, trace, usedEdges); //Flute mit neuem Startknoten und aktuellem Weg
            }
        }
    }
}

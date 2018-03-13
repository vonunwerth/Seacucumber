package matcher;

import graph.Edge;
import graph.Graph;
import graph.Vertex;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;

import java.util.*;

public class TraceMatcher extends Matcher {


    /**
     * Das Set aller Traces eines Patterns
     */
    private Set<ArrayList<String>> patternTraces = new HashSet<>();

    /**
     * Das Set aller Traces der Datenbank
     */
    private Set<ArrayList<String>> dbTraces = new HashSet<>();

    //Use this to use nodes instead of relationship symbols
    //private Set<ArrayList<String>> patternTraceNodes = new HashSet<>();

    /**
     * Creates a new TraceMatcher
     *
     * @param database Database to use
     * @param graph    Pattern to find in the database
     */
    public TraceMatcher(GraphDatabaseService database, Graph graph) {
        this.db = database;
        this.graph = graph;
    }

    /**
     * The trace matching algorithm
     * @return Result of the trace matching
     */
    @Override
    public Map<Integer, List<Node>> matchingAlgorithm() {
        Map<Integer, List<Node>> resultMap = new HashMap<>();
        Set<Node> databaseNodes = new HashSet<>();

        for (Vertex vertex : graph.getVertices()) {
            ArrayList<String> trace = new ArrayList<>();
            Set<Integer> usedEdges = new HashSet<>(); //Merkt sich in jedem Rekursionsschritt, welche Kanten schon genutzt wurden, um nicht erneut eine Kante zu nutzen

            //Use this two lines to get the nodes in the traces instead of the relationsships
            //Auch als zweites Kriterium nutzbar, um aus der Datenbank die Subsets rauszuschmeißen, welche diese Knoten nicht enthalten, bzw nicht die gleiche Anzahl haben
            //ArrayList<String> simpleActualNode = new ArrayList<>(Collections.singleton(vertex.getIdentifier()));
            //if (!patternTraceNodes.contains(simpleActualNode)) patternTraceNodes.add(simpleActualNode);

            floodPattern(vertex, trace, usedEdges);
        }
        for (Node node : db.getAllNodes()) {
            databaseNodes.add(node);
        }
        //Alle Subsets die so groß wie das Pattern sind
        Set<Set<Node>> powerSet = powerSet(databaseNodes);
        int resultCount = 0;
        for (Set<Node> set : powerSet) {
            dbTraces = new HashSet<>(); //Alle Traces des vorherigen Sets löschen
            trace(set);
            if (dbTraces.equals(patternTraces)) { //Wenn Trace mit Patterntrace übereinstimmt ist ein Ergebnis gefunden
                resultCount++;
                resultMap.put(resultCount, new ArrayList<>(set)); //Zum Ergebnisset hinzufügen
            }
        }
        return resultMap;
    }

    private void trace(Set<Node> set) {
        for (Node node : set) {
            ArrayList<String> trace = new ArrayList<>();
            Set<Relationship> usedEdges = new HashSet<>(); //Merkt sich in jedem Rekursionsschritt, welche Kanten schon genutzt wurden, um nicht erneut eine Kante zu nutzen
            floodDatabaseSubset(node, trace, usedEdges, set);
        }
    }


    /**
     * Findet alle möglichen Traces von einem Starknoten ausgehend. Kreise werden dabei ignoriert
     *
     * @param actualNode Startknoten
     * @param trace      Bisherige zurückgelegter Weg
     * @param usedEdges  Bereits genutzte Kanten, Kreise sollen verhindert werden
     */
    private void floodPattern(Vertex actualNode, ArrayList<String> trace, Set<Integer> usedEdges) {
        //trace.add(actualNode.getIdentifier()); Use this to get the nodes in the traces instead of the relationsships
        if (!patternTraces.containsAll(trace)) {
            patternTraces.add(trace); //Bisher zurückgelegten Weg zu den Traces hinzufügen
        }
        for (Edge edge : actualNode.getOutgoingEdges()) {
            if (!usedEdges.contains(edge.getId())) {
                trace.add(edge.getLabel());
                usedEdges.add(edge.getId()); //Sperre Kante für tiefere Aufrufe, um Kreise zu vermeiden
                actualNode = edge.getTarget(); //Nutze Nachfolger als Startknoten
                floodPattern(actualNode, trace, usedEdges); //Flute mit neuem Startknoten und aktuellem Weg
            }
        }
    }

    /**
     * Findet alle möglichen Traces von einem Starknoten ausgehend. Kreise werden dabei ignoriert
     *
     * @param actualNode Startknoten
     * @param trace      Bisherige zurückgelegter Weg
     * @param usedEdges  Bereits genutzte Kanten, Kreise sollen verhindert werden
     */
    private void floodDatabaseSubset(Node actualNode, ArrayList<String> trace, Set<Relationship> usedEdges, Set<Node> subsetOfPowerSet) {
        if (!dbTraces.containsAll(trace))
            dbTraces.add(trace); //Bisher zurückgelegten Weg zu den Traces hinzufügen
        for (Relationship rel : actualNode.getRelationships(Direction.OUTGOING)) {
            if (!usedEdges.contains(rel) && subsetOfPowerSet.contains(rel.getEndNode())) {
                trace.add(rel.getType().name());
                usedEdges.add(rel); //Sperre Kante für tiefere Aufrufe, um Kreise zu vermeiden
                actualNode = rel.getEndNode(); //Nutze Nachfolger als Startknoten
                floodDatabaseSubset(actualNode, trace, usedEdges, subsetOfPowerSet); //Flute mit neuem Startknoten und aktuellem Weg
            }
        }
    }

}

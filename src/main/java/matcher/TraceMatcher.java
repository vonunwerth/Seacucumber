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
     * set of all traces of a pattern
     */
    private Set<ArrayList<String>> patternTraces = new HashSet<>();

    /**
     * set of all traces of the database
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

        //goes through all vertices and remembers which edges have been used in every recursive step
        for (Vertex vertex : graph.getVertices()) {
            ArrayList<String> trace = new ArrayList<>();
            Set<Integer> usedEdges = new HashSet<>();

            //Use these two lines to get the nodes in the traces instead of the relationships.
            //Also usable as a secondary criteria to remove all subsets of the database that don´t have the specific
            //nodes in them or don´t have the same amount of nodes
            //ArrayList<String> simpleActualNode = new ArrayList<>(Collections.singleton(vertex.getIdentifier()));
            //if (!patternTraceNodes.contains(simpleActualNode)) patternTraceNodes.add(simpleActualNode);

            floodPattern(vertex, trace, usedEdges);
        }
        for (Node node : db.getAllNodes()) {
            databaseNodes.add(node);
        }
        //all subsets that are the same size as the pattern
        Set<Set<Node>> powerSet = powerSet(databaseNodes);
        int resultCount = 0;
        for (Set<Node> set : powerSet) {
            //delete all traces of the previous set
            dbTraces = new HashSet<>();
            trace(set);
            //if the trace equals the patterntrace we found a result
            if (dbTraces.equals(patternTraces)) {
                resultCount++;
                //add to result
                resultMap.put(resultCount, new ArrayList<>(set));
            }
        }
        return resultMap;
    }

    /**
     * Starts the trace for a set of nodes
     *
     * @param set Set to calculate trace about
     */
    private void trace(Set<Node> set) {
        for (Node node : set) {
            ArrayList<String> trace = new ArrayList<>();
            //goes through all nodes and remembers which edges have been used in every recursive step
            Set<Relationship> usedEdges = new HashSet<>();
            floodDatabaseSubset(node, trace, usedEdges, set);
        }
    }


    /**
     * Find all possible traces starting from the starting node. Circles are ignored.
     *
     * @param actualNode starting node
     * @param trace      already traveled path
     * @param usedEdges  already used edges; circles should be avoided
     */
    private void floodPattern(Vertex actualNode, ArrayList<String> trace, Set<Integer> usedEdges) {
        //trace.add(actualNode.getIdentifier()); Use this to get the nodes in the traces instead of the relationsships
        if (!patternTraces.containsAll(trace)) {
            //add paths that have been visited to the traces
            patternTraces.add(trace);
        }
        for (Edge edge : actualNode.getOutgoingEdges()) {
            if (!usedEdges.contains(edge.getId())) {
                trace.add(edge.getLabel());
                //lock edges for deeper call to prevent circles
                usedEdges.add(edge.getId());
                //use successor as new starting node
                actualNode = edge.getTarget();
                //flood with new starting node and current path
                floodPattern(actualNode, trace, usedEdges);
            }
        }
    }

    /**
     * Find all possible traces starting from the starting node. Circles are ignored.
     *
     * @param actualNode starting node
     * @param trace      already traveled path
     * @param usedEdges  already used edges; circles should be avoided
     */
    private void floodDatabaseSubset(Node actualNode, ArrayList<String> trace, Set<Relationship> usedEdges, Set<Node> subsetOfPowerSet) {
        if (!dbTraces.containsAll(trace))
            //add paths that have been visited to the traces
            dbTraces.add(trace);
        for (Relationship rel : actualNode.getRelationships(Direction.OUTGOING)) {
            if (!usedEdges.contains(rel) && subsetOfPowerSet.contains(rel.getEndNode())) {
                trace.add(rel.getType().name());
                //lock edges for deeper call to prevent circles
                usedEdges.add(rel);
                //use successor as new starting node
                actualNode = rel.getEndNode();
                //flood with new starting node and current path
                floodDatabaseSubset(actualNode, trace, usedEdges, subsetOfPowerSet); //flood with new start node and actual way
            }
        }
    }

}

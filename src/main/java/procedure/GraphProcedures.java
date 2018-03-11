package procedure;

import graph.Graph;
import matcher.DualSimMatcher;
import matcher.DualSimMatcherProp;
import matcher.FailureMatcher;
import matcher.IsomorphicMatcher;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.logging.Log;
import org.neo4j.procedure.*;
import procedure.ressources.NodeResult;

import java.util.Set;
import java.util.stream.Stream;

import static procedure.ressources.ProcedureRessources.prepareQuery;

public class GraphProcedures {

    /**
     * Access to the database. (Must be public for NEO4J!)
     */
    @Context
    @SuppressWarnings("WeakerAccess")
    public GraphDatabaseService db;

    /**
     * Log
     */
    @Context
    @SuppressWarnings({"WeakerAccess", "unused"})
    public Log log;

    /**
     * NEO4J Procedure that can be executed in the database.
     *
     * First, the query is converted to a graph.
     * Then the matching algorithm is executed.
     * Finally, the result is returned.
     *
     * The passed query is processed with this NEO4J procedure and a result set is returned.
     *
     * @param query The given query to execute
     * @return Stream of NodeResults. Each NodeResult contains only one node, the one it represents in the result set.
     */
    @Procedure(value = "graph.dualSim", mode = Mode.READ)
    @Description("Dual Simulation Matcher")
    @SuppressWarnings("unused")
    public Stream<NodeResult> dualSim(@Name("query") String query) {
        Graph graph = prepareQuery(db, query);
        DualSimMatcher matcher = new DualSimMatcher(db, graph);
        Set<Node> simulated = matcher.simulate();
        return simulated.stream().map(NodeResult::new);
    }

    /**
     * NEO4J Procedure that can be executed in the database.
     *
     * First, the query is converted to a graph.
     * Then the matching algorithm is executed.
     * Finally, the result is returned.
     *
     * The passed query is processed with this NEO4J procedure and a result set is returned.
     *
     * @param query The given query to execute
     * @return Stream of NodeResults. Each NodeResult contains only one node, the one it represents in the result set.
     */
    @Procedure(value = "graph.failure", mode = Mode.READ)
    @Description("Failure matching")
    @SuppressWarnings("unused")
    public Stream<NodeResult> failure(@Name("query") String query) {
        Graph graph = prepareQuery(db, query);
        FailureMatcher matcher = new FailureMatcher(db, graph);
        Set<Node> simulated = matcher.simulate();
        return simulated.stream().map(NodeResult::new);
    }

    /**
     * NEO4J Procedure that can be executed in the database.
     * <p>
     * First, the query is converted to a graph.
     * Then the matching algorithm is executed.
     * Finally, the result is returned.
     * <p>
     * The passed query is processed with this NEO4J procedure and a result set is returned.
     *
     * @param query The given query to execute
     * @return Stream of NodeResults. Each NodeResult contains only one node, the one it represents in the result set.
     */
    @Procedure(value = "graph.dualSimProp", mode = Mode.READ)
    @Description("Dual Simulation matcher with properties")
    @SuppressWarnings("unused")
    public Stream<NodeResult> dualSimProp(@Name("query") String query) {
        Graph graph = prepareQuery(db, query);
        DualSimMatcherProp matcher = new DualSimMatcherProp(db, graph);
        Set<Node> simulated = matcher.simulate();
        return simulated.stream().map(NodeResult::new);
    }

    /**
     * NEO4J Procedure that can be executed in the database.
     * <p>
     * First, the query is converted to a graph.
     * Then the matching algorithm is executed.
     * Finally, the result is returned.
     * <p>
     * The passed query is processed with this NEO4J procedure and a result set is returned.
     *
     * @param query The given query to execute
     * @return Stream of NodeResults. Each NodeResult contains only one node, the one it represents in the result set.
     */
    @Procedure(value = "graph.isomorphic", mode = Mode.READ)
    @Description("Isomorphic matcher with properties")
    @SuppressWarnings("unused")
    public Stream<NodeResult> isomorphic(@Name("query") String query) {
        Graph graph = prepareQuery(db, query);
        IsomorphicMatcher matcher = new IsomorphicMatcher(db, graph);
        Set<Node> simulated = matcher.simulate();
        return simulated.stream().map(NodeResult::new);
    }

}
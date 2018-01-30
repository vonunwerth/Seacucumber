package procedure;

import graph.Graph;
import matcher.DualSimMatcher;
import matcher.DualSimMatcherProp;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.logging.Log;
import org.neo4j.procedure.*;

import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

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
    @Procedure(value = "graph.extractQuery", mode = Mode.READ)
    @Description("Get the query from NEO4J")
    @SuppressWarnings("unused")
    public Stream<NodeResult> extractQuery(@Name("query") String query) {
        System.out.println("EXTRACT QUERY: extractQuery starting...");
        query = query.trim().replaceAll("\n", " ");
        System.out.println("EXTRACT QUERY: \\n replaced Replaced...");
        TimeUnit tu = TimeUnit.MILLISECONDS;
        //Check if the query is correct. If entered incorrectly, error message appears in Neo4j!
        db.execute(query, 5, tu);
        System.out.println("EXTRACT QUERY: Query is correct...");

        QueryBuilder qb = new QueryBuilder(query);
        System.out.println("EXTRACT QUERY: Query built...");
        Graph graph = qb.build();
        System.out.println("EXTRACT QUERY: Graph built...");
        System.out.println(graph);
        DualSimMatcherProp matcher = new DualSimMatcherProp(db, graph);
        System.out.println("EXTRACT QUERY: Query finished...");
        Set<Node> simulated = matcher.simulate();
        return simulated.stream().map(NodeResult::new);
    }

    /**
     * Result constructor for NEO4J procedures.
     */
    public class NodeResult {
        /**
         * Node of results. (Must be public for NEO4J!)
         */
        @SuppressWarnings("WeakerAccess")
        public Node node;

        /**
         * Result of the query. (Must be public for NEO4J!)
         *
         * @param node The given node
         */
        @SuppressWarnings("WeakerAccess")
        public NodeResult(Node node) {
            this.node = node;
        }
    }
}

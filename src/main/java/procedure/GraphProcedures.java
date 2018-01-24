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
     * Zugriff auf unsere Datenbank
     * Muss für NEO4J public sein
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
     * NEO4J Procedure, die in der Datenbank ausgeführt werden kann
     *
     * Zuerst wird die Query in einen Graphen umgewandelt
     * Dann wird der Matching Algorithmus ausgeführt
     * Das Ergenis wird zurückgegeben
     *
     * Die übergebene Query wird mit dieser NEO4J Procedure bearbeitet und es wird ein Resultset zurückgegeben
     *
     * @param query Auszuführende Query
     * @return Stream aus NodeResults, wobei jeder NodeResult nur einen Node enthält, nämlich den, den er im Ergebnisset darstellt
     */
    @Procedure(value = "graph.extractQuery", mode = Mode.READ)
    @Description("Wir wollen die Query")
    @SuppressWarnings("unused")
    public Stream<NodeResult> extractQuery(@Name("query") String query) {
        System.out.println("EXTRACT QUERY: extractQuery startet");
        query = query.trim().replaceAll("\n", " ");
        System.out.println("EXTRACT QUERY: \\n replaced Replaced.");
        TimeUnit tu = TimeUnit.MILLISECONDS;
        //Prueft ob die Query korrekt ist. Bei falscher Eingabe Fehlermeldung in Neo4j
        db.execute(query, 5, tu);
        System.out.println("EXTRACT QUERY: Query ist korrekt");

        QueryBuilder qb = new QueryBuilder(query);
        System.out.println("EXTRACT QUERY: Query gebaut.");
        Graph graph = qb.build();
        System.out.println("EXTRACT QUERY: Graph gebaut.");
        System.out.println(graph);
        DualSimMatcherProp matcher = new DualSimMatcherProp(db, graph);
        System.out.println("EXTRACT QUERY: Query beendet.");
        Set<Node> simulated = matcher.simulate();
        return simulated.stream().map(NodeResult::new);
    }

    /**
     * Ergebniskonstrukt für NEO4J Prozeduren
     */
    public class NodeResult {
        /**
         * Knoten der Ergebnisse, Muss public sein für NEO4J
         */
        @SuppressWarnings("WeakerAccess")
        public Node node;

        /**
         * Ergebnis der Query, Muss public sein für NEO4J
         *
         * @param node Knoten
         */
        @SuppressWarnings("WeakerAccess")
        public NodeResult(Node node) {
            this.node = node;
        }
    }
}

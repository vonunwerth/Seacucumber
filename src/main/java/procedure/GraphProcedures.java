package procedure;

import graph.Graph;
import matcher.DualSimMatcher;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.logging.Log;
import org.neo4j.procedure.*;

import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class GraphProcedures {

    //Zugriff auf unsere Datenbank
    @Context
    public GraphDatabaseService db;

    @Context
    public Log log;

    @Procedure(value = "graph.extractQuery", mode = Mode.READ)
    @Description("Wir wollen die Query")
    public Stream<NodeResult> extractQuery(@Name("query") String query) {
        query = query.trim().replaceAll("\n", " ");
        System.out.println("\\n replaced Replaced.");
        TimeUnit tu = TimeUnit.MILLISECONDS;
        //Prueft ob die Query korrekt ist. Bei falscher Eingabe Fehlermeldung in Neo4j
        db.execute(query, 5, tu);
        System.out.println("Query ist korrekt");

        QueryBuilder qb = new QueryBuilder(query);
        Graph graph = qb.build();
        System.out.println(graph);
        DualSimMatcher dsim = new DualSimMatcher(db, graph);
        return dsim.simulate().stream().map(NodeResult::new);
    }
    public class NodeResult {

        public Node node;

        public NodeResult(Node node) {
            this.node = node;
        }
    }
}

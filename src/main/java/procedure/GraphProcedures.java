package procedure;

import graph.Graph;
import matcher.DualSimMatcher;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.logging.Log;
import org.neo4j.procedure.*;

import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static procedure.QueryBuilder.build;

public class GraphProcedures {

    //Zugriff auf unsere Datenbank
    @Context
    public GraphDatabaseService db;

    @Context
    public Log log;

    @Procedure(value = "graph.extractQuery", mode = Mode.WRITE)
    @Description("Wir wollen die Query")
    public Stream<Node> extractQuery(@Name("query") String query) {
        query = query.trim().replaceAll("\n", " ");

        TimeUnit tu = TimeUnit.MILLISECONDS;
        //Prueft ob die Query korrekt ist. Bei falscher Eingabe Fehlermeldung in Neo4j
        db.execute(query, 5, tu);

        QueryBuilder qb = new QueryBuilder(query);
        Graph graph = build();
        DualSimMatcher dsim = new DualSimMatcher(db, graph);
        Stream<Node> stream = dsim.simulate();
        return stream;

        //Kurzer kleiner Test!!
        //SearchHit hit1 = new SearchHit("Geht doch, du dich.");
        //list.add(hit1);

        /*List<SearchHit> list2 = new ArrayList<>();
        list2.add(new SearchHit(result+""));
        return list2.stream(); */
    }
}

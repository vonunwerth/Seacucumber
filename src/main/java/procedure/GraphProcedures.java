package procedure;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.logging.Log;
import org.neo4j.procedure.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class GraphProcedures {

    //Zugriff auf unsere Datenbank
    @Context
    public GraphDatabaseService db;

    @Context
    public Log log;

    @Procedure(value = "graph.extractQuery", mode = Mode.WRITE)
    @Description("Wir wollen die Query")
    public Stream<SearchHit> extractQuery(@Name("query") String query) {
        query = query.trim().replaceAll("\n", " ");

        TimeUnit tu = TimeUnit.MILLISECONDS;
        //Prueft ob die Query korrekt ist. Bei falscher Eingabe Fehlermeldung in Neo4j
        db.execute(query, 5, tu);


        String cleaned[] = query.split("RETURN");
        String cleaned2[] = cleaned[0].split("WHERE");
        String clean = cleaned2[0];
        List<SearchHit> list = new ArrayList<>();

        list.add(new SearchHit(cleaned2[0]));

        //Kurzer kleiner Test!!
        //SearchHit hit1 = new SearchHit("Geht doch, du dich.");
        //list.add(hit1);

        /*List<SearchHit> list2 = new ArrayList<>();
        list2.add(new SearchHit(result+""));
        return list2.stream(); */

        return list.stream();

    }
}

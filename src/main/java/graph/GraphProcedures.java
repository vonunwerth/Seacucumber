package graph;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.logging.Log;
import org.neo4j.procedure.*;

import java.util.ArrayList;
import java.util.List;
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
        String cleaned[] = query.split("WHERE");

        List<SearchHit> list = new ArrayList<>();

        for (String clean : cleaned) {
            list.add(new SearchHit(clean));
        }

        //Kurzer kleiner Test!!
        //SearchHit hit1 = new SearchHit("Geht doch, du dich.");
        //list.add(hit1);

        return list.stream();
    }

}

package procedure.ressources;

import graph.Graph;
import org.neo4j.graphdb.GraphDatabaseService;
import procedure.QueryBuilder;

import java.util.concurrent.TimeUnit;

public class ProcedureRessources {

    public static Graph prepareQuery(GraphDatabaseService db, String query) {
        query = query.trim().replaceAll("\n", " ");
        TimeUnit tu = TimeUnit.MILLISECONDS;
        //Check if the query is correct. If entered incorrectly, error message appears in Neo4j!
        db.execute(query, 5, tu);
        QueryBuilder qb = new QueryBuilder(query);
        return qb.build();
    }
}

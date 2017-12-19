package tests;

import org.junit.Rule;
import org.junit.Test;
import org.neo4j.driver.v1.*;
import org.neo4j.harness.junit.Neo4jRule;
import procedure.GraphProcedures;

import static constants.Constants.CREATE_TESTQUERY;

public class ManualFullTextIndexTest {

    @Rule
    public final Neo4jRule neo4j = new Neo4jRule()
            .withProcedure(GraphProcedures.class);

    @Test
    public void shouldAllowIndexingAndFindingANode() throws Throwable {
        // In a try-block, to make sure we close the driver after the test
        try (Driver driver = GraphDatabase.driver(neo4j.boltURI(), Config.build().toConfig())) {

            // Given I've started Neo4j with the FullTextIndex procedure class
            //       which my 'neo4j' rule above does.
            Session session = driver.session();
            System.out.println("Datenbank wurde initialisiert");
            // And given I have a node in the database
            session.run(CREATE_TESTQUERY);
            System.out.println("Testdatensätze wurden erstellt.");
            // When I use the index procedure to index a node
            //session.run( "CALL example.index({id}, ['name'])", parameters( "id", nodeId ) );

            // Then I can search for that node with lucene query syntax
            StatementResult sr  = session.run("CALL graph.extractQuery(\" MATCH (tom:Person) RETURN tom \")");
            System.out.println("Query erfolgreich ausgeführt.");
            //assertThat( result.single().get( "nodeId" ).asLong(), equalTo( nodeId ) );
            int counter = 0;
            while (counter < 100) {
                counter++;
                Thread.sleep(1000);
                System.out.println(sr.hasNext());
            }
            /*while(sr.hasNext()) {
                System.out.println(sr.single());
                sr.next();
            }*/
        }
    }
}
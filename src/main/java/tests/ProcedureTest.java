package tests;

import org.junit.Rule;
import org.junit.Test;
import org.neo4j.driver.v1.*;
import org.neo4j.harness.junit.Neo4jRule;
import procedure.GraphProcedures;

import static constants.Constants.CREATE_TESTQUERY_VERY_LITTLE;

/**
 * Beispielausführung einer Procedure in einer Klasse als Test
 * Mit session.run(String query) kann eine Query in NEO4J geladen und ausgeführt werden
 * Die Ergebnisse können in einem StatementResult sr gespeichert und einzeln mit sr.peek() angezeigt werden
 */
public class ProcedureTest {

    /**
     * NEO4J Regel
     */
    @Rule
    public final Neo4jRule neo4j = new Neo4jRule()
            .withProcedure(GraphProcedures.class);

    /**
     * NEO4J Test
     * Es wird eine neue Datenbank mit Testdatensätzen aus den Constants ({@link constants.Constants#CREATE_TESTQUERY}) geladen und dann eine Procedure ausgeführt und die Funktionalität getestet.
     *
     */
    @Test
    public void shouldAllowIndexingAndFindingANode() {
        // In a try-block, to make sure we close the driver after the test
        try (Driver driver = GraphDatabase.driver(neo4j.boltURI(), Config.build().toConfig())) {


            // Given I've started Neo4j with the FullTextIndex procedure class
            //       which my 'neo4j' rule above does.
            Session session = driver.session();
            System.out.println("TEST: Datenbank wurde initialisiert");
            // And given I have a node in the database
            /*System.out.println("Mit welcher Datenbank wollen Sie den Test ausführen\n (1) Große Datenbank > 100 Knoten + Beziehungen \n (2) Kleine Datenbank < 10 Knoten + Beziehungen");
            Scanner sc = new Scanner(System.in);
            int var = sc.nextInt();
            switch (var) {
                case 1:
                    session.run(CREATE_TESTQUERY_LITTLE);
                    break;
                case 2:
                    session.run(CREATE_TESTQUERY_LITTLE);
                    break;
                default:
                    System.out.println("Zu dieser Wahl existiert kein Test. Der Test wird beendet.");
                    System.exit(25);
            }*/
            session.run(CREATE_TESTQUERY_VERY_LITTLE);
            System.out.println("TEST: Testdatensätze wurden erstellt.");
            // When I use the index procedure to index a node
            //session.run( "CALL example.index({id}, ['name'])", parameters( "id", nodeId ) );

            // Then I can search for that node with lucene query syntax
            StatementResult sr = session.run("CALL graph.trace(\" MATCH (tom:Person)-[:DIRECTED]->(m:Movie) RETURN tom \")");
            //StatementResult sr = session.run("CALL graph.trace(\" MATCH (tom:Person) RETURN tom \")");

            System.out.println("TEST: Query gestartet");
            //assertThat( result.single().get( "nodeId" ).asLong(), equalTo( nodeId ) );
            while (sr != null && sr.hasNext()) {
                System.out.println("RESULT: " + sr.peek());
                sr.next();
            }
        }
    }
}

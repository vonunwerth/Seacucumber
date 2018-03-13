package tests;

import org.junit.Rule;
import org.junit.Test;
import org.neo4j.driver.v1.*;
import org.neo4j.harness.junit.Neo4jRule;
import procedure.GraphProcedures;

import static constants.Constants.CREATE_TESTQUERY_VERY_LITTLE;

/**
 * Example call of a procedure in a class as test
 * Using session.run(String query) a query can be used and called in NEO4J
 * The results can be saved in a StatementResult sr and can be displayed individually using sr.peek()
 */
public class ProcedureTest {

    /**
     * NEO4J rules
     */
    @Rule
    public final Neo4jRule neo4j = new Neo4jRule()
            .withProcedure(GraphProcedures.class);

    /**
     * NEO4J Test
     * A new database with test data sets from the Constants ({@link constants.Constants#CREATE_TESTQUERY})
     * is loaded and a procedure is called to test the functionality
     */
    @Test
    public void shouldAllowIndexingAndFindingANode() {
        // In a try-block, to make sure we close the driver after the test
        try (Driver driver = GraphDatabase.driver(neo4j.boltURI(), Config.build().toConfig())) {


            // Given I've started Neo4j with the FullTextIndex procedure class
            //       which my 'neo4j' rule above does.
            Session session = driver.session();
            System.out.println("TEST: Database initialized");
            // And given I have a node in the database

            session.run(CREATE_TESTQUERY_VERY_LITTLE);
            System.out.println("TEST: Test data sets created.");
            // When I use the index procedure to index a node
            //session.run( "CALL example.index({id}, ['name'])", parameters( "id", nodeId ) );

            // Then I can search for that node with lucene query syntax
            StatementResult sr = session.run("CALL graph.isomorphic(\" MATCH (tom:Person)-[:DIRECTED]->(m:Movie) RETURN tom \")");

            System.out.println("TEST: Query started");
            //assertThat( result.single().get( "nodeId" ).asLong(), equalTo( nodeId ) );
            while (sr != null && sr.hasNext()) {
                System.out.println("RESULT: " + sr.peek());
                sr.next();
            }
        }
    }
}

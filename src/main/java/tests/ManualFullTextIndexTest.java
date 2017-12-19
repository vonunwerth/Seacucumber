package tests;

import org.junit.Rule;
import org.junit.Test;
import org.neo4j.driver.v1.Config;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Session;
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

            // And given I have a node in the database
            session.run(CREATE_TESTQUERY);

            // When I use the index procedure to index a node
            //session.run( "CALL example.index({id}, ['name'])", parameters( "id", nodeId ) );

            // Then I can search for that node with lucene query syntax
            session.run("CALL graph.extractQuery(\" MATCH (tom:Person) RETURN tom \")");
            //assertThat( result.single().get( "nodeId" ).asLong(), equalTo( nodeId ) );
        }
    }
}

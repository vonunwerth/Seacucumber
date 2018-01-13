package tests;

import org.neo4j.driver.v1.*;
import org.neo4j.driver.v1.exceptions.ClientException;

import static org.neo4j.driver.v1.Values.parameters;

/**
 * In diesem Test werden einfache Methoden auf einer lokal gehosteten Datenbank aufgerufen
 */
public class NeoTest {

    /**
     * Testmethode zum Starten einer neuen Abfrage auf einer lokal gehosteten Datenbank
     *
     * @param args Programmparameter
     */
    public static void main(String[] args) {

        Driver driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("trump", "password"));
        Session session = driver.session();
        StatementResult result = session.run("CALL graph.extractQuery(\"MATCH (tom:Person {name: \'Tom Hanks\'})-[:ACTED_IN]->(tomHanksMovies) RETURN tom, tomHanksMovies\")",
                parameters("name", "Arthur"));
        //Test Fehlermeldung
        //StatementResult result = session.run("CALL graph.extractQuery(\"MATCH {name: \'Tom Hanks\'})-[:ACTED_IN]->(tomHanksMovies) RETURN tom, tomHanksMovies\")");

        try {
            while (result.hasNext()) {
                Record record = result.next();
                System.out.println(record.get("title").asString() + " " + record.get("name").asString());
            }
        } catch (ClientException e) {
            System.err.println("In Ihrer Query befindet sich ein Fehler. Bitte beheben Sie diesen.");
            System.err.println(e.getMessage());
        }

        session.close();
        driver.close();
    }

    /**
     * Testanzeige von Datensätzen aus einer lokal gehosteten Datenbank
     */
    private static void createAndShow() {
        Driver driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("trump", "password"));
        Session session = driver.session();

        session.run("CREATE (a: Person {name: {name}, title: {title}})", parameters("name", "Arthur", "title", "King"));

        StatementResult result = session.run("MATCH (a:Person) WHERE a.name = {name} " +
                        "RETURN a.name AS name, a.title AS title",
                parameters("name", "Arthur"));
        while (result.hasNext()) {
            Record record = result.next();
            System.out.println(record.get("title").asString() + " " + record.get("name").asString());
        }

        session.close();
        driver.close();
    }
}

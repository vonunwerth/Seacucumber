import org.neo4j.driver.v1.*;

import static org.neo4j.driver.v1.Values.parameters;

/**
 * @author Maximilian von Unwerth
 * Procedure aus dem Example Package wird aufgerufen.
 */
public class NeoTest {
    public static void main(String[] args) {
        //Felix war hier
        Driver driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("trump", "password"));
        Session session = driver.session();
        StatementResult result = session.run("CALL example.search('T','title:*')",  //TODO Query isnt working
                parameters("name", "Arthur"));
        while (result.hasNext()) {
            Record record = result.next();
            System.out.println(record.get("title").asString() + " " + record.get("name").asString());
        }

        session.close();
        driver.close();
    }

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
        //end
    }
}

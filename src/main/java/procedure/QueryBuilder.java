package procedure;

import graph.Edge;
import graph.Node;

public class QueryBuilder {
    static String query;

    public QueryBuilder(String query) {
        QueryBuilder.query = query;
    }

    public static void main(String[] args) {
        //query = "MATCH(marvelMovies:TAG)-[]-(marvelMovies:Avengers)"; //{name:'Marvel'})<-[:HAS_TAG]-(movie)";
        query = "MATCH \"(:Actor {name: \"Wes Studi\"})-[:ACTS_IN]->(m:Movie)<-[:ACTS_IN]-(:Actor {name: \"Matt Gerald\"})";
        build();
    }

    public static void build() {
        String label = "";
        String identifier = "";
        String[] node = new String[2];
        String[] edge = new String[2];
        int direction = 0;
        Node first = null;
        Node second = null;
        for (int x = 0; x < query.length(); x++) {
            String nodeString = "";
            String edgeString = "";
            if (query.charAt(x) == '>') direction = 1;
            if (query.charAt(x) == '<') direction = 2;
            if (query.charAt(x) == '(') {
                x++;
                while (query.charAt(x) != ')' && query.charAt(x) != '{') {
                    nodeString = nodeString + query.charAt(x);
                    x++;
                }
                node = nodeString.split(":");
                Node n = new Node(node[0], node[1]);
                if (first != null) {
                    if (direction == 1) {
                        Edge e = new Edge(first, n, edge[1]);
                        System.out.println(e.toString());
                    }
                    if (direction == 2) {
                        Edge e = new Edge(n, first, edge[1]);
                        System.out.println(e.toString());
                    }
                }
                first = n;
                System.out.println(node[0]);
                System.out.println(node[1]);
            }

            if (query.charAt(x) == '[') {
                x++;
                while (query.charAt(x) != ']' && query.charAt(x) != '{') {
                    edgeString = edgeString + query.charAt(x);
                    x++;
                }
                edge = edgeString.split(":");
                System.out.println(edge[1]);
            }
        }
    }
}

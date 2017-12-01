package procedure;

import graph.Edge;
import graph.Point;

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
        String[] point = new String[2];
        String[] edge = new String[2];
        int direction = 0;
        Point first = null;
        Point second = null;
        for (int x = 0; x < query.length(); x++) {
            String pointString = "";
            String edgeString = "";
            if (query.charAt(x) == '>') direction = 1;
            if (query.charAt(x) == '<') direction = 2;
            if (query.charAt(x) == '(') {
                x++;
                while (query.charAt(x) != ')' && query.charAt(x) != '{') {
                    pointString = pointString + query.charAt(x);
                    x++;
                }
                point = pointString.split(":");
                Point n = new Point(point[0], point[1]);
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
                System.out.println(point[0]);
                System.out.println(point[1]);
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

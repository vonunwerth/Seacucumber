package procedure;

import graph.Edge;
import graph.Graph;
import graph.Vertex;

public class QueryBuilder {
    static String query;

    public QueryBuilder(String query) {
        QueryBuilder.query = query;
    }

    public static void main(String[] args) {
        //query = "MATCH(marvelMovies:TAG)-[]-(marvelMovies:Avengers)"; //{name:'Marvel'})<-[:HAS_TAG]-(movie)";
        query = "MATCH\n" +
                "(:Actor {name: \"Wes Studi\"})-[:ACTS_IN]->(m:Movie)<-[:ACTS_IN]-(:Actor {name: \"Matt Gerald\"}),\n" +
                "(m)<-[:DIRECTED]-(d)-[:DIRECTED]->(others)\n" +
                "RETURN m.title, d.name, collect(others.title) AS productions\n" +
                "LIMIT 1";
        //query = "MATCH \"(:Actor {name: \"Wes Studi\"})-[:ACTS_IN]->(m:Movie)<-[:ACTS_IN]-(:Actor {name: \"Matt Gerald\"})";
        Graph g = build();
    }

    public static Graph build() {
        Graph g = new Graph();
        String label = "";
        String identifier = "";
        String[] point = new String[2];
        String[] edge = new String[2];
        int direction = 0;
        Vertex first = null;
        Vertex second = null;
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
                Vertex n;
                if (pointString.contains(":")) {
                    if (g.checkLabel(point[0]) == null) {
                        n = new Vertex(point[0], point[1], g);
                    } else if (g.checkLabel(point[0]).getLabel().equals("")) {
                        n = new Vertex(point[0], point[1], g);
                    } else {
                        n = g.checkLabel(point[0]);
                    }
                } else {
                    if (g.checkLabel(pointString) == null) {
                        n = new Vertex(pointString, "#", g);
                    } else {
                        n = g.checkLabel(pointString);
                    }
                }
                if (first != null) {
                    if (direction == 1) {
                        Edge e = new Edge(first, n, edge[1], g);
                        first.addEdge(n, edge[1], g);
                        //n.printVertex();
                        //System.out.println(e.toString());
                    }
                    if (direction == 2) {
                        Edge e = new Edge(n, first, edge[1], g);
                        n.addEdge(first, edge[1], g);
                        //n.printVertex();
                        //System.out.println(e.toString());
                    }
                }
                first = n;
                //System.out.println(point[0]);
                //System.out.println(point[1]);
            }

            if (query.charAt(x) == '[') {
                x++;
                while (query.charAt(x) != ']' && query.charAt(x) != '{') {
                    edgeString = edgeString + query.charAt(x);
                    x++;
                }
                edge = edgeString.split(":");
                //System.out.println(edge[1]);
            }
        }
        System.out.println("Alle Punkte:");
        for (Vertex v : Graph.vertices) {
            v.printVertex();
        }
        return g;
    }
}

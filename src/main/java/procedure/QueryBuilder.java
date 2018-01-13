package procedure;

import graph.Graph;
import graph.Vertex;

/**
 * Baut einen Graphen aus einer Query
 */
class QueryBuilder {

    /**
     * Query, aus der der Graph gebaut werden soll
     */
    private String query;

    QueryBuilder(String queryParam) {
        query = queryParam;
    }

    /**
     * Baut einen Graphen aus einer Query
     * @return Der gebaute Graph
     */
    Graph build() {
        Graph g = new Graph();

        //not needed (same query preprocessing as in GraphProcedures)
        String cleaned[] = query.split("RETURN");
        String cleaned2[] = cleaned[0].split("WHERE");
        String query = cleaned2[0];
        System.out.println(query);

        //vars
        String[] point;
        String[] edge = new String[2];
        int direction = 0;
        Vertex first = null;

        //loop that goes through the whole query
        int idCounter = 0;
        for (int x = 0; x < query.length(); x++) {
            StringBuilder pointString = new StringBuilder();
            StringBuilder edgeString = new StringBuilder();
            if (query.charAt(x) == ',') first = null;
            if (query.charAt(x) == '>') direction = 1;
            if (query.charAt(x) == '<') direction = 2;

            //'(' is an indicator for a (new) node in our graph
            if (query.charAt(x) == '(') {
                x++;

                //go through the query till the end of the node´s name
                while (query.charAt(x) != ')' && query.charAt(x) != '{') {
                    pointString.append(query.charAt(x));
                    x++;
                }
                point = pointString.toString().split(":");
                Vertex n;

                /*check for ':' and get the Label + Name of our node.
                * Also checks if there is any label at all and creates a
                * node with the label/name that is found
                 */
                if (pointString.toString().contains(":")) {
                    if (g.checkLabel(point[0]) == null) {
                        n = new Vertex(point[0], point[1], idCounter);
                        g.addVertex(n);
                        idCounter++;
                        System.out.println("Created Node! (" + point[0] + ":" + point[1] + ")");
                    } else if (g.checkLabel(point[0]).getLabel().equals("")) {
                        n = new Vertex(point[0], point[1], idCounter);
                        g.addVertex(n);
                        idCounter++;
                        System.out.println("Created Node! (" + point[0] + ":" + point[1] + ")");
                    } else {
                        n = g.checkLabel(point[0]);
                    }
                } else {
                    if (g.checkLabel(pointString.toString()) == null) {
                        n = new Vertex(pointString.toString(), "#", idCounter);
                        g.addVertex(n);
                        idCounter++;
                        System.out.println("Created Node! (" + pointString + ":" + "#)");
                    } else {
                        n = g.checkLabel(pointString.toString());
                    }
                }

                /*if a node is created earlier a new edge can be created depending
                * on the edge parameters we find
                 */
                if (first != null) {
                    if (direction == 1) {
                        g.addEdge(first, n, edge[1]);
                        System.out.println("Created Edge! (" + first.getLabel() + ":" + first.getIdentifier() + ")---" + edge[1] + "-->(" + n.getLabel() + ":" + n.getIdentifier() + ")");
                        first.addEdge(n, edge[1]);
                    }
                    if (direction == 2) {
                        g.addEdge(n, first, edge[1]);
                        System.out.println("Created Edge! (" + n.getLabel() + ":" + n.getIdentifier() + ")---" + edge[1] + "-->(" + first.getLabel() + ":" + first.getIdentifier() + ")");
                        n.addEdge(first, edge[1]);
                    }
                }
                first = n;
            }

            //find edge´s relation name
            if (query.charAt(x) == '[') {
                x++;
                while (query.charAt(x) != ']' && query.charAt(x) != '{') {
                    edgeString.append(query.charAt(x));
                    x++;
                }
                edge = edgeString.toString().split(":");
            }
        }

        //prints all Points
        System.out.println("Alle Punkte:");
        for (Vertex v : g.getVertices()) {
            v.printVertex();
        }
        return g;
    }
}

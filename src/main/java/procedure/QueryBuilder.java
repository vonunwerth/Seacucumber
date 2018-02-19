package procedure;

import graph.Edge;
import graph.Graph;
import graph.Vertex;

import java.util.HashMap;
import java.util.Map;

/**
 * Builds a graph from a query.
 */
public class QueryBuilder {

    /**
     * Query from which the graph is built.
     */
    private String query;

    /**
     * Default Constructor.
     */
    public QueryBuilder(String queryParam) {
        query = queryParam;
    }

    /**
     * This method builds a graph from a query.
     *
     * @return The built graph.
     */
    public Graph build() {
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
        Map<String,String> mapEdge = new HashMap<>();
        //loop that goes through the whole query
        int idCounter = 0;
        for (int x = 0; x < query.length(); x++) {
            StringBuilder pointString = new StringBuilder();
            StringBuilder edgeString = new StringBuilder();
            StringBuilder attString = new StringBuilder();
            StringBuilder attStringEdge = new StringBuilder();
            Map<String,String> map = new HashMap<>();
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
                if (query.charAt(x) == '{'){
                    while (query.charAt(x) != ')'){
                        attString.append(query.charAt(x));
                        x++;
                    }
                    map = forgeProperties(attString);
                }
                point = pointString.toString().split(":");
                Vertex n;

                /*check for ':' and get the Label + Name of our node.
                * Also checks if there is any label at all and creates a
                * node with the label/name that is found
                 */
                if (pointString.toString().contains(":")) {
                    if (g.checkLabel(point[0]) == null) {
                        n = new Vertex(point[0], point[1], idCounter,map);
                        g.addVertex(n);
                        idCounter++;
                        System.out.println("Created Node! (" + point[0] + ":" + point[1] + ")");
                    } else if (g.checkLabel(point[0]).getLabel().equals("")) {
                        n = new Vertex(point[0], point[1], idCounter,map);
                        g.addVertex(n);
                        idCounter++;
                        System.out.println("Created Node! (" + point[0] + ":" + point[1] + ")");
                    } else {
                        n = g.checkLabel(point[0]);
                    }
                } else {
                    if (g.checkLabel(pointString.toString()) == null) {
                        n = new Vertex(pointString.toString(), "#", idCounter,map);
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
                        Edge e = new Edge(first, n, edge[1],mapEdge);
                        g.addEdge(e);
                        System.out.println("Created Edge! (" + first.getLabel() + ":" + first.getIdentifier() + ")---[" + edge[1] + "]-->(" + n.getLabel() + ":" + n.getIdentifier() + ")");
                        first.addEdge(n, e);
                    }
                    if (direction == 2) {
                        Edge e = new Edge(first, n, edge[1],mapEdge);
                        g.addEdge(e);
                        System.out.println("Created Edge! (" + n.getLabel() + ":" + n.getIdentifier() + ")---[" + edge[1] + "]-->(" + first.getLabel() + ":" + first.getIdentifier() + ")");
                        n.addEdge(first, e);
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
                if (query.charAt(x) == '{'){
                    while (query.charAt(x) != ']'){
                        attStringEdge.append(query.charAt(x));
                        x++;
                    }
                    mapEdge = forgeProperties(attStringEdge);
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

    /**
     * This method builds the properties.
     *
     * @return The map of the properties
     */
    private Map<String,String> forgeProperties(StringBuilder attString){
        Map<String,String> map = new HashMap<>();
        String[] attributes = attString.toString().split(",");
        for(int i = 0; i < attributes.length; i++){
            attributes[i] = attributes[i].replaceAll("[^a-zA-Z0-9:]","");
            String[] keyitem = attributes[i].split(":");
            map.put(keyitem[0],keyitem[1]);
        }
        return map;
    }

}

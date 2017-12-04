package graph;

import java.util.ArrayList;
import java.util.List;

public class Graph {

    public static List<Vertex> vertices = new ArrayList<>();

    public static List<Edge> edges = new ArrayList<>();

    public Vertex checkLabel(String label) {
        for (Vertex v : vertices
                ) {
            if (v.getLabel().equals(label)) {
                return v;
            }
        }
        return null;
    }


}

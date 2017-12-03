package graph;

import java.util.LinkedList;
import java.util.List;

import static graph.Graph.edges;

public class Edge {

    private Vertex start;

    public String relation;
    private Vertex target;

    public Edge(Vertex start, Vertex target, String relationLabel) {
        this.start = start;
        this.target = target;
        this.relation = relationLabel;
        edges.add(this);
    }

    @Override
    public String toString() {
        return start + " " + target + " " + relation;
    }

    public List<Vertex> getVertex() {
        List<Vertex> list = new LinkedList<>();
        list.add(start);
        list.add(target);
        return list;
    }

    public String getRelation() {
        return relation;
    }

}

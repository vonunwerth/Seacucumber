package graph;

import java.util.LinkedList;
import java.util.List;

public class Edge {

    private Vertex start;
    private String relation;
    private Vertex target;

    public Edge(Vertex start, Vertex target, String relationLabel) {
        this.start = start;
        this.target = target;
        this.relation = relationLabel;
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

package graph;

import java.util.LinkedList;
import java.util.List;

public class Edge {

    public Vertex start;
    public String relation;
    public Vertex target;

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

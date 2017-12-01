package graph;

import java.util.LinkedList;
import java.util.List;

import static graph.Graph.edges;

public class Edge {

    private Point start;

    public String relation;
    private Point target;

    public Edge(Point start, Point target, String relationLabel) {
        this.start = start;
        this.target = target;
        this.relation = relationLabel;
        edges.add(this);
    }

    @Override
    public String toString() {
        return start + " " + target + " " + relation;
    }

    public List<Point> getPoint() {
        List<Point> list = new LinkedList<>();
        list.add(start);
        list.add(target);
        return list;
    }

    public String getRelation() {
        return relation;
    }

}

package graph;

import java.util.LinkedList;
import java.util.List;

import static graph.Graph.edges;

public class Edge {

    private Node start;

    public String relation;
    private Node target;

    public Edge(Node start, Node target, String relationLabel) {
        this.start = start;
        this.target = target;
        this.relation = relationLabel;
        edges.add(this);
    }

    @Override
    public String toString() {
        return start + " " + target + " " + relation;
    }

    public List<Node> getNode() {
        List<Node> list = new LinkedList<>();
        list.add(start);
        list.add(target);
        return list;
    }

    public String getRelation() {
        return relation;
    }

}

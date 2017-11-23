package graph;

public class Edge {

    public String relation;
    private Node node;

    public Edge(Node node, String relation) {
        this.node = node;
        this.relation = relation;
    }

    public Node getNode() {
        return node;
    }

    public String getRelation() {
        return relation;
    }

}

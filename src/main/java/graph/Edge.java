package graph;

import java.util.LinkedList;
import java.util.List;

public class Edge {

    private Vertex start;

    private String relation;

    private Vertex target;
    Edge(Vertex start, Vertex target, String relationLabel) {
        this.start = start;
        this.target = target;
        this.relation = relationLabel;
    }

    @Override
    public String toString() {
        return start + " " + target + " " + relation;
    }

    List<Vertex> getVertex() {
        List<Vertex> list = new LinkedList<>();
        list.add(start);
        list.add(target);
        return list;
    }

    /**
     * Gibt den Zielknoten aus
     *
     * @return Knoten, auf den die Kante zeigt
     */
    public Vertex getTarget() {
        return target;
    }

    /**
     * Gibt den Startknoten aus
     *
     * @return Knoten, von dem diese Kante ausgeht
     */
    public Vertex getStart() {
        return start;
    }

    public String getRelation() {
        return relation;
    }
}

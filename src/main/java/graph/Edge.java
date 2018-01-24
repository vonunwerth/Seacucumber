package graph;

import java.util.LinkedList;
import java.util.List;

/**
 * Repräsentation einer gerichteten Kante eines Graphen
 */
public class Edge {

    /**
     * Startknoten
     */
    private Vertex start;

    /**
     * Name der Relation
     */
    private String relation;

    /**
     * Zielknoten
     */
    private Vertex target;

    /**
     * Erstellt eine neue Kante
     *
     * @param start         Startknoten
     * @param target        Endknoten
     * @param relationLabel Name der Übergangsrelation
     */
    Edge(Vertex start, Vertex target, String relationLabel) {
        this.start = start;
        this.target = target;
        this.relation = relationLabel;
    }

    /**
     * Ausgabe der Kante als formatierter String
     *
     * @return formatierter String, Format: Startknoten + Endknoten + Relationsname
     */
    @Override
    public String toString() {
        return start + " " + target + " " + relation;
    }

    /**
     * Die Funktion gibt den Startknoten {@link Edge#start} und den Zielknoten {@link Edge#target} in einer Liste von Knoten zurück
     *
     * @return Liste mit Start- und Endknoten
     */
    public List<Vertex> getVertex() {
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

    /**
     * Gibt den Namen der Relation zurück
     *
     * @return Name des Übergangs
     */
    public String getRelation() {
        return relation;
    }
}

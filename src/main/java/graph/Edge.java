package graph;

import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
    private String label;

    /**
     * Zielknoten
     */
    private Vertex target;
    /**
     * Properties
     */
    private Map<String,String> properties;

    /**
     * Hiermit kann eine neue Kante erstellt werden
     *
     * @param start         Startknoten
     * @param target        Endknoten
     * @param relationLabel Name der Übergangsrelation
     */
    public Edge(Vertex start, Vertex target, String relationLabel, Map<String,String> attributes) {
        this.start = start;
        this.target = target;
        this.label = relationLabel;
        this.properties = attributes;
    }

    /**
     * Ausgabe der Kante als formatierter String
     *
     * @return Startknoten + Endknoten + Relationsname
     */
    @Override
    public String toString() {
        return start + " " + target + " " + label;
    }

    /**
     * Die Funktion gibt den Startknoten {@link Edge#start} und den Zielknoten {@link Edge#target} in einer Liste von Knoten zurück
     *
     * @return Liste mit Start, sowie -Endknoten
     */
    List<Vertex> getVertex() {
        List<Vertex> list = new LinkedList<>();
        list.add(start);
        list.add(target);
        return list;
    }

    /**
     * Gibt den Zielknoten aus
     * @return Knoten, auf den die Kante zeigt
     */
    public Vertex getTarget() {
        return target;
    }

    /**
     * Gibt den Startknoten aus
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
    public String getLabel() {
        return label;
    }
    /**
     * Returns the map of attributes.
     * @return map of attributes
     */
    public Map<String,String> getProperties() {return properties; }

    /**
     * Compares edges with Neo4J Realtionships
     * @param rel relatinoship to compare to
     * @return true if both have the same properties and name
     */
    public Boolean equalsProp(Relationship rel){
        Boolean equ = false;
        if (this.getLabel().equals(rel.getType().name())){
            equ = true;
        }
        for (String s: this.properties.keySet()
                ) {
            if (rel.getProperty(s).toString().equals(this.properties.get(s)) ){
                equ = true;
            } else{
                equ = false;
                break;
            }

        }
        return equ;
    }
}

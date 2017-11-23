package graph;

import java.util.Comparator;
import java.util.TreeSet;

public class Graph {

    Comparator<Node> comp = new Node();
    private TreeSet<Node> allNodes = new TreeSet<Node>((Node) comp);

    public TreeSet<Node> getAllNodes() {
        return allNodes;
    }

    public String toString() {
        String str = "";
        for (Node n : allNodes)
            //str += String.format("%10s --> %-2s  %2s  %s\n",
            //        n.firstNode(), n, n.getRelation(), n.path());
            str += n.firstNode() + "-->" + n.path();
        return str;
    }
}

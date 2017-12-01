package graph;

import java.util.ArrayList;

import static graph.Graph.nodes;

public class Node {

    //Bezeichnung des Knoten
    private String label;
    private String identifier;

    //Liste der ausgehenden bzw eingehenden Kanten
    private ArrayList<Edge> incomingEdges = new ArrayList<>();
    private ArrayList<Edge> outgoingEdges = new ArrayList<>();

    public Node(String label, String identifier) {
        this.label = label;
        this.identifier = identifier;
        nodes.add(this);
    }

    public static void main(String[] args) {
        Node a = new Node("BasicNode", "A");
        Node b = new Node("BasicNode", "B");
        Node c = new Node("BasicNode", "C");
        Node d = new Node("BasicNode", "D");
        Node e = new Node("BasicNode", "E");
        Node f = new Node("BasicNode", "F");
        Node g = new Node("BasicNode", "G");
        Node h = new Node("BasicNode", "H");
        Node i = new Node("BasicNode", "I");

        a.addEdge(b, "Coole Verbindung von A nach B");
        a.addEdge(d, "Kante");
        e.addEdge(a, "Kante");
        f.addEdge(a, "Test");
        a.addEdge(g, "Kante");
        a.addEdge(i, "Kante");
        g.addEdge(f, "Kante");
        g.addEdge(e, "Kante");
        a.printNode();
        f.printNode();
    }

    @Override
    public String toString() {
        return "" + label + ":" + identifier;
    }

    public void addEdge(Node node, String relationLabel) {
        Edge edge = new Edge(this, node, relationLabel);
        outgoingEdges.add(edge);
        node.incomingEdges.add(edge);
    }

    public String getLabel() {
        return label;
    }

    public String getIdentifier() {
        return identifier;
    }

    /*
    *Baut ein Feld in dem die einzelnen Knoten und Kanten eingetragen werden.
    *Gibt auf der Konsole einen Knoten mit allen verbundenen Knoten graphisch aus
     */
    public void printNode() {
        String[][] field = new String[5][5];
        field[2][2] = "[" + this.identifier + "]";
        String right = " → ";
        String left = " ← ";
        String up = " ↑ ";
        String down = " ↓ ";

        //geht durch die Liste der ausgehenden Kanten
        for (int x = 0; x < this.outgoingEdges.size(); x++) {
            if (this.outgoingEdges.size() == 0) break;
            if (field[2][3] == null) {
                field[2][3] = right;
                field[2][4] = "(" + outgoingEdges.get(x).getNode().get(1).getIdentifier().charAt(0) + ")";
                continue;
            }
            if (field[3][3] == null) {
                field[3][3] = " ↘ ";
                field[4][4] = "(" + outgoingEdges.get(x).getNode().get(1).getIdentifier().charAt(0) + ")";
                continue;
            }
            if (field[3][2] == null) {
                field[3][2] = down;
                field[4][2] = "(" + outgoingEdges.get(x).getNode().get(1).getIdentifier().charAt(0) + ")";
                continue;
            }
            if (field[1][3] == null) {
                field[1][3] = " ↗ ";
                field[0][4] = "(" + outgoingEdges.get(x).getNode().get(1).getIdentifier().charAt(0) + ")";
                continue;
            }
            if (field[2][1] == null) {
                field[2][1] = left;
                field[2][0] = "(" + outgoingEdges.get(x).getNode().get(1).getIdentifier().charAt(0) + ")";
                continue;
            }
            if (field[1][1] == null) {
                field[1][1] = " ↖ ";
                field[0][0] = "(" + outgoingEdges.get(x).getNode().get(1).getIdentifier().charAt(0) + ")";
                continue;
            }
            if (field[1][2] == null) {
                field[1][2] = up;
                field[0][2] = "(" + outgoingEdges.get(x).getNode().get(1).getIdentifier().charAt(0) + ")";
                continue;
            }
            if (field[3][1] == null) {
                field[3][1] = " ↙ ";
                field[4][0] = "(" + outgoingEdges.get(x).getNode().get(1).getIdentifier().charAt(0) + ")";
            }
        }


        //----------------------------------alle eingehenden Kanten-----------------------------------
        for (int x = 0; x < this.incomingEdges.size(); x++) {
            if (this.incomingEdges.size() == 0) break;
            if (field[2][3] == null) {
                field[2][3] = left;
                field[2][4] = "(" + incomingEdges.get(x).getNode().get(0).getIdentifier().charAt(0) + ")";
                continue;
            }
            if (field[3][3] == null) {
                field[3][3] = " ↖ ";
                field[4][4] = "(" + incomingEdges.get(x).getNode().get(0).getIdentifier().charAt(0) + ")";
                continue;
            }
            if (field[3][2] == null) {
                field[3][2] = up;
                field[4][2] = "(" + incomingEdges.get(x).getNode().get(0).getIdentifier().charAt(0) + ")";
                continue;
            }
            if (field[1][3] == null) {
                field[1][3] = " ↙ ";
                field[0][4] = "(" + incomingEdges.get(x).getNode().get(0).getIdentifier().charAt(0) + ")";
                continue;
            }
            if (field[2][1] == null) {
                field[2][1] = right;
                field[2][0] = "(" + incomingEdges.get(x).getNode().get(0).getIdentifier().charAt(0) + ")";
                continue;
            }
            if (field[1][1] == null) {
                field[1][1] = " ↘ ";
                field[0][0] = "(" + incomingEdges.get(x).getNode().get(0).getIdentifier().charAt(0) + ")";
                continue;
            }
            if (field[1][2] == null) {
                field[1][2] = down;
                field[0][2] = "(" + incomingEdges.get(x).getNode().get(0).getIdentifier().charAt(0) + ")";
                continue;
            }
            if (field[3][1] == null) {
                field[3][1] = " ↗ ";
                field[4][0] = "(" + incomingEdges.get(x).getNode().get(0).getIdentifier().charAt(0) + ")";
            }
        }
        printArray(field);
    }


    /*
    *gibt ein quadratisches Array auf der Konsole aus
     */
    private void printArray(String[][] array) {
        for (int x = 0; x < array.length; x++) {
            for (int y = 0; y < array.length; y++) {
                try {
                    if (array[x][y] != null) {
                        System.out.print(array[x][y]);
                    } else {
                        System.out.print("   ");
                    }
                } catch (Exception e) {
                    System.out.println("Größe des Array muss quadratisch sein");
                    break;
                }
            }
            System.out.println();
        }
    }
}
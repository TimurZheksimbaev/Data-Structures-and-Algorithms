import java.util.ArrayList;
import java.util.List;

public class Graph {
    public static void main(String[] args) {
        
    }
}


class Vertex<V> {
    V value;
    int ID;

    public Vertex(V value, int ID) {
        this.value = value;
        this.ID = ID;
    }
}

class WeightedGraph<V, E> {
    private class Vertex {
        V value;
        int ID;

        public Vertex(V value, int ID) {
            this.value = value;
            this.ID = ID;
        }
    }

    private class Edge {
        Vertex from, to;
        E weight;

        public Edge(Vertex from, Vertex to, E weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }
    }

    int n = 0;
    int numV, numE;
    private ArrayList<Vertex> vertices;
    private ArrayList<Edge> edges;

    public WeightedGraph(int numV, int numE) {
        this.numV = numV;
        this.numE = numE;
        this.vertices = new ArrayList<>(numV);
        this.edges = new ArrayList<>(numE);
    }

    public WeightedGraph() {
        this.vertices = new ArrayList<>();
        this.edges = new ArrayList<>();
    }

    public void addVertex(V value, int ID) {
        Vertex v = new Vertex(value, n++);
        vertices.add(v);
    }

    public void addEdge(Vertex from, Vertex to, E weight) {
        Edge e = new Edge(from, to, weight);
        edges.add(e);
    }

    public boolean areAdjacent(Vertex v1, Vertex v2) {
        for (Edge e : edges) {
            if ((e.from.value == v1.value && e.to.value == v2.value) ||
                    (e.from.value == v2.value && e.to.value == v1.value)) {
                return true;
            }
        }
        return false;
    }

    public int outDegree(Vertex v) {
        int degree = 0;
        for (Edge e : edges)
            if (e.from.value == v.value)
                degree++;
        return degree;
    }

    public int intDegree(Vertex v) {
        int degree = 0;
        for (Edge e : edges)
            if (e.to.value == v.value)
                degree++;
        return degree;
    }

    public void removeVertex(Vertex vertex) {
        for (Vertex v : vertices) {
            if (v.value == vertex.value) {
                vertices.remove(vertex.ID);
                return;
            }
        }

        System.out.println("There is no " + vertex.value + " vertex!!!");
    }

    public void removeEdge(Edge edge) {
        for (Edge e : edges) {
            if (e.from.value == edge.from.value && e.to.value == edge.to.value && edge.weight == e.weight) {
                edges.remove(edge);
            }
        }
        System.out.println("There is no such edge!!!");
    }

    public boolean findEdge(Edge edge) {
        for (Edge e : edges)
            if (edge.weight == e.weight && edge.from.value == e.from.value && edge.to.value == e.to.value)
                return true;
        return false;
    }

    public List<Edge> outgoingEdges(Vertex vertex) {
        List<Edge> outgoing = new ArrayList<>();
        for (Edge e : edges)
            if (e.from.value == vertex.value)
                outgoing.add(e);
        return outgoing;
    }

    public List<Edge> incomingEdges(Vertex vertex) {
        List<Edge> incoming = new ArrayList<>();
        for (Edge e : edges)
            if (e.to.value == vertex.value)
                incoming.add(e);
        return incoming;
    }

    public Edge getEdge(Vertex u, Vertex v) {
        for (Edge e : edges) {
            if (u.value == e.from.value && v.value == e.to.value)
                return e;
        }
        System.out.println("There is no such edge");
        return null;
    }

    public boolean empty() {
        return vertices.isEmpty();
    }

    public int numberOfVertices() {
        return vertices.size();
    }

    public int numberOfEdges() {
        return edges.size();
    }

    List<Vertex> allVertices() {
        return vertices;
    }

    List<Edge> allEdges() {
        return edges;
    }
}


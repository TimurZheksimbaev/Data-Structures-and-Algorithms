#include <iostream>
#include <list>
#include <vector>

using namespace std;

template<typename V, typename E>
class Graph {
public:

    class Vertex;

    class Edge;

    class Vertex {
    public:
        V value;
        int ID;
        list<Edge *> adjNodes;
        typename vector<Vertex *>::iterator vertexRef;

        Vertex() {
        }

        Vertex(V val, int ID) {
            value = val;
            this->ID = ID;
            //adjNodes = list<Edge*>();
        }

    };

    class Edge {
    public:
        E weight;
        Vertex *from;
        Vertex *to;
        typename list<Edge *>::iterator edgeRef;

        Edge(E wei, Vertex *fr, Vertex *t) {
            weight = wei;
            from = fr;
            to = t;
        }

        bool operator<(const Edge &a) const {
            return weight < a.weight;
        }
    };

    int n = 0;
    vector<Vertex *> vertices;
    list<Edge *> edges;
    list<Edge> MST;

    Graph() {
        vertices = new vector<Vertex>();
        edges = list<Edge>();
        MST = list<Edge>();
    }

    Vertex *addVertex(V val) {
        Vertex *vertex = new Vertex(val, n);
        n++;
        vertex->vertexRef = vertices.emplace(vertices.end(), vertex);
        return vertex;
    }

    Edge *addEdge(E weight, Vertex *from, Vertex *to) {
        Edge *edge = new Edge(weight, from, to);
        from->adjNodes.push_back(edge);
        // from.adjNodes.insert(from.adjNodes.end(), edge);
        edge->edgeRef = edges.insert(edges.end(), edge);
        return edge;
    }

    int inDegree(Vertex vertex) {
        int result = 0;
        for (Edge edge : edges)
            if (edge->to->value == vertex.value && edge->to->ID == vertex.ID) result++;
        return result;
    }

    int outDegree(Vertex vertex) {
        int result = 0;
        for (Edge edge : edges)
            if (edge->from->value == vertex.value && edge->from->ID == vertex.ID) result++;
        return result;
    }

    bool areAdjacent(Vertex v, Vertex u) {
        for (Edge edge: edges) {
            if ((edge.from.value == v.value && edge.to.value == u.value) ||
                (edge.from.value == u.value && edge.to.value == v.value))
                return true;
        }
        return false;
    }

    void removeEdge(Edge edge) {
        edges.erase(edge.edgeRef);
    }

    void removeVertex(Vertex vertex) {
        vertices.erase(vertex.vertexRef);

        auto it = edges.begin();

        while (it != edges.end()) {
            if ((*it).from.value == vertex.value || (*it).to.value == vertex.value) {
                it = edges.erase(it);
            } else
                it++;
        }
    }

    pair<Vertex, Vertex> findEdge(Edge edge) { // returns vertices between this edge
        for (Edge e: edges)
            if (e->from->value == edge.from->value && e->to->value == edge.to->value && e->weight == edge.weight)
                return make_pair(e->from, e->to);
        cout << "Edge was not found!!!";
        return;
    }

    bool containsEdge(Edge edge) {
        for (Edge e: edges)
            if (e->from->value == edge.from->value && e->to->value == edge.to->value && e->weight == edge.weight)
                return true;
        return false;
    }

    vector<Edge> outgoingEdges(Vertex vertex) {
        vector<Edge> result;
        for (Edge edge : edges)
            if (edge->from == vertex)
                result.push_back(edge);
        return result;
    }

    vector<Edge> incomingEdges(Vertex vertex) {
        vector<Edge> result;
        for (Edge edge : edges)
            if (edge->to == vertex)
                result.push_back(edge);
        return result;
    }

    pair<Vertex, Vertex> getEdge(Vertex u, Vertex v) { // returns vertices between this edge
        for (Edge edge : edges)
            if ((edge->from->value == u.value && edge->from->ID == u.ID)
                && (edge->to->value == v.value && edge->to->ID == v.ID))
                return make_pair(u, v);
        cout << "Edge was not found!!!";
        return;
    }

    bool empty() {
        return vertices.empty();
    }

    int numberOfVertices() {
        return vertices.size();
    }

    int numberOfEdges() {
        return edges.size();
    }

    vector<Vertex> allVertices() {
        vector<Vertex> result;
        for (Vertex v : vertices)
            result.push_back(v);
        return result;
    }

    vector<Edge> allEdges() {
        vector<Edge> result;
        for (Edge e : edges)
            result.push_back(e);
        return result;
    }


};

int main() {

    return 0;
}
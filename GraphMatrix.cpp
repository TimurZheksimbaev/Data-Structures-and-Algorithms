/// Graph which is represented using adjacency matrix.
#include <iostream>
#include <list>
#include <vector>
#include <set>

using namespace std;

template<typename V, typename E>
class GraphMatrix {
private:
    vector<vector<E>> adjMatrix;
    int vertices;
public:

    GraphMatrix(int n) {
        this->vertices = n;
        for (int i = 0; i < n; i++) {
            adjMatrix.push_back(vector<E>(n, 0));
        }
    }

    void addEdge(int first, int second, V value) {
        adjMatrix[first][second] = value;
    }

    void removeEdge(int first, int second) {
        adjMatrix[first][second] = 0;
    }

    bool containsEdge(E value) {
        for (int i = 0; i < vertices; i++)
            for (int j = 0; j < vertices; j++)
                if (adjMatrix[i][j] == value)
                    return true;

        return false;
    }

    bool containsEdge(int first, int second) {
        return adjMatrix[first][second];
    }

    int outDegree(int first) {
        int res = 0;
        for (int i = 0; i < vertices; i++)
            if (adjMatrix[first][i] > 0)
                res++;
        return res;
    }

    int inDegree(int first) {
        int res = 0;
        for (int i = 0; i < vertices; i++)
            if (adjMatrix[i][first] > 0)
                res++;
        return res;
    }

    void print() {
        for (int i = 0; i < vertices; i++) {
            for (int j = 0; j < vertices; j++) {
                if (adjMatrix[i][j] > 0)
                    cout << i + 1 << " goes to " << j + 1 << " with weight: " << adjMatrix[i][j] << "\n";
            }
            cout << "\n";
        }
    }

    void printMatrix() {
        for (int i = 0; i < vertices; i++) {
            for (int j = 0; j < vertices; j++) {
                cout << adjMatrix[i][j] << ' ';
            }
            cout << '\n';
        }
    }

    pair<int, int> findEdge(E value) {
        for (int i = 0; i < vertices; i++)
            for (int j = 0; j < vertices; j++)
                if (adjMatrix[i][j] == value) return make_pair(i, j);
    }

    void shortestPath(int start, int finish) { // Dijkstra algorithm!!!
        vector<int> shortest_path; // vector for shortest path
        vector<int> distances(vertices, INT_MAX); // vector for distances
        vector<int> parent(vertices, -1); // vector for predecessor vertices
        vector<bool> visited(vertices, false); // vector for already visited vertices

        distances[start] = 0; // starting vertex has 0 distance

        set<pair<int, int>> vertex_set; // set storing pairs, where first is distance to that vertex and second is this vertex
        vertex_set.insert(make_pair(0, start)); // inserting starting distance and vertex

        while (!vertex_set.empty()) {

            auto iterator = *(vertex_set.begin()); // Time complexity - O(1).
            int u = iterator.second; // index of vertex
            vertex_set.erase(iterator); // delete it. Time complexity - O(1).

            // finding shortest path
            if (u == finish) {

                for (int v = finish; v != -1; v = parent[v]) {
                    shortest_path.push_back(v);
                }

                // printing shortest path or impossible
                reverse(shortest_path.begin(), shortest_path.end());
                cout << "Total weight: " << distances[finish] << '\n';
                cout << "Vertices: ";
                for (int i: shortest_path) {
                    cout << i << " ";
                }
                return;
            }

            // if this vertex is already visited than skip it
            if (visited[u]) {
                continue;
            }

            visited[u] = true; // mark this vertex as visited

            for (int v = 0; v < vertices; v++) {

                if (adjMatrix[u][v] > 0) {
                    // relaxing
                    if (!visited[v] && distances[u] + adjMatrix[u][v] < distances[v]) {
                        // distance to vertex v is infinite, then delete it.
                        if (distances[v] == INT_MAX)
                            vertex_set.erase({distances[v], v});

                        distances[v] = distances[u] + adjMatrix[u][v]; // new dist
                        parent[v] = u; //new parent
                        vertex_set.insert(make_pair(distances[v], v)); //inserting distance to this vertex and its index
                    }
                }
            }
        }

        // if there is no solution to problem then print IMPOSSIBLE
        if (shortest_path.empty()) cout << "Cannot find shortest path from " << start << " to " << finish;
    }
};

int main() {
    int n;
    cin >> n;

    GraphMatrix<int, int> graph(n);
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            int c;
            cin >> c;
            graph.addEdge(i, j, c);
        }
    }

    graph.shortestPath(0, 2);
    return 0;
}
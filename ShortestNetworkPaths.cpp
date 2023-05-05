/// Timur Zheksimbaev
#include<iostream>
#include<vector>
#include<set>

using namespace std;

/// Program solving Shortest network paths problem.
/// I am using dijkstra algorithm and graph is represented as adjacency matrix.
/// Additional parameter is bandwidth. 

/// Generic graph class
template<typename V, typename E>
class Graph {
public:
    pair<V, E> adjMatrix[1000][1000]; //adjacency matrix of pairs, where first is length, second is bandwidth.
    int vertices; // number of vertices

    // constructor
    Graph(int n) {
        this->vertices = n;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                adjMatrix[i][j] = *new pair<V, E>;
            }
        }
    }

    // method for adding an edge. Time complexity - O(1).
    void add(int first, int second, V length, E bandwidth) {
        adjMatrix[first][second] = make_pair(length, bandwidth);
    }

    // Dijkstra's algorithm to find the shortest path from start to end with minimum bandwidth.
    // Time complexity O(V^2).
    void Dijkstra_Algorithm(int start, int finish, int min_bandwidth) {
        vector<int> shortest_path; // vector for shortest path
        vector<int> distances(vertices, INT_MAX); // vector for distances
        vector<int> bandwidths(vertices, INT_MAX); // vector for bandwidths
        vector<int> parent(vertices, -1); // vector for predecessor vertices
        vector<bool> visited(vertices, false); // vector for already visited vertices

        distances[start] = 0; // starting vertex has 0 distance
        bandwidths[start] = INT_MAX; // starting bandwidth is infinite

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
                cout << shortest_path.size() << " " << distances[finish] << " " << bandwidths[finish] << '\n';
                for (int i: shortest_path) {
                    cout << i + 1 << " ";
                }
                return;
            }


            // if this vertex is already visited than skip it
            if (visited[u]) {
                continue;
            }

            visited[u] = true; // mark this vertex as visited

            for (int v = 0; v < vertices; v++) {
                // check if length and bandwidth are positive
                if (adjMatrix[u][v].first != 0 && adjMatrix[u][v].second != 0) {

                    // check if this vertex is already visited or its bandwidth is less than minimum bandwidth
                    if (visited[v] || adjMatrix[u][v].second < min_bandwidth) {
                        continue;
                    }

                    // defining new distance and bandwidth
                    int newDist = distances[u] + adjMatrix[u][v].first;
                    int newBandwidth = min(bandwidths[u], adjMatrix[u][v].second);

                    // relaxing
                    if (newDist < distances[v] || (newDist == distances[v] && newBandwidth > bandwidths[v])) {
                        // distance to vertex v is infinite, then delete it.
                        if (distances[v] == INT_MAX)
                            vertex_set.erase({distances[v], v});
                        distances[v] = newDist; // new dist
                        bandwidths[v] = newBandwidth; // new bandwidth
                        parent[v] = u; //new parent
                        vertex_set.insert(make_pair(distances[v], v)); //inserting distance to this vertex and its index
                    }
                }
            }
        }

        // if there is no solution to problem then print IMPOSSIBLE
        if (shortest_path.empty()) cout << "IMPOSSIBLE";
    }
};

int main() {
    int n, m; // number of vertices and number of edges
    cin >> n >> m;

    Graph<int, int> graph(n); // graph object

    // reading input
    for (int i = 0; i < m; i++) {
        int first, second, length, bandwidth;
        cin >> first >> second >> length >> bandwidth;
        graph.add(first-1, second-1, length, bandwidth); // decrease by 1 because indices in input start from 1
    }

    int start, finish, min_bandwidth;
    cin >> start >> finish >> min_bandwidth;
    start--;
    finish--; // decrease by 1 because indices in input start from 1

    // calling Dijkstra algorithm
    graph.Dijkstra_Algorithm(start, finish, min_bandwidth);

    return 0;
}

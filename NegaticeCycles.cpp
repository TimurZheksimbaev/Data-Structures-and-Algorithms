/// Timur Zheksimbaev
#include<iostream>
#include<vector>

using namespace std;

/// Program to find and print negative cycles in graph represented as adjacency matrix using Bellman Ford algorithm.
/// Note that in my graph edges with weight 0 are valid, but with edge 100000 are invalid(means edge does not exist).

const int INF = 100000; // largest weight in graph

/// template graph class
template<typename V, typename E>
class Graph {
public:
    E adjMatrix[100][100]; //storing vertices andweights
    int vertices; // number of vertices

    Graph(int n) {
        this->vertices = n;
    }

    // adding into graph. Time complexity - O(1)
    void add(int first, int second, E weight) {
        adjMatrix[first][second] = weight;
    }

    // i am using bellman ford algorithm which has time complexity of O(V^3).
    void Bellman_Ford(int src) {

        // if amount of vertices is 1 and first weight is negative then it is result
        if (vertices == 1) {
            if (adjMatrix && adjMatrix[0][0] < 0) {
                cout << "YES\n" << 1 << '\n' << 1;
                exit(0);
            } else {
                cout << "NO";
                exit(0);
            }
        }

        vector<int> shortest_path(vertices);
        // initializing distances and parents
        vector<int> distances(vertices, INF);
        vector<int> parent(vertices, -1);
        distances[src] = 0;
        parent[0] = -1;

        for (int i = 0; i < vertices; i++) {
            for (int j = 0; j < vertices; j++) {
                for (int k = 0; k < vertices; k++) {
                    //relaxing
                    if (adjMatrix[j][k] < INF && distances[k] > distances[j] + adjMatrix[j][k]) {
                        distances[k] = distances[j] + adjMatrix[j][k]; // update distance
                        parent[k] = j; // update parent
                    }
                }
            }
        }

        // find negative cycle
        int negative_cycle = 0;
        for (int i = 0; i < vertices; i++) {
            for (int j = 0; j < vertices; j++) {
                //relaxing again.
                if (adjMatrix[i][j] < INF && distances[j] > distances[i] + adjMatrix[i][j] && distances[i] < INF && distances[j] < INF) {
                    negative_cycle = j; //index of negative cycle vertex

                    vector<int> cycle; //vector for storing negative cycle
                    vector<bool> visited(vertices, false); // vector for marking visited vertices

                    for (int c = 0; c < vertices; c++) {
                        negative_cycle = parent[negative_cycle];
                    }

                    int new_cycle = negative_cycle;
                    // while we not come to the same vertex we add vertices to vector cycle
                    while (!visited[new_cycle]) {
                        cycle.push_back(new_cycle);
                        visited[new_cycle] = true; // mark it visited
                        new_cycle = parent[new_cycle]; //update new_cycle
                    }

                    cycle.erase(cycle.begin()); //removing first vertex
                    cycle.push_back(new_cycle); // adding last vertex in cycle
                    reverse(cycle.begin(), cycle.end()); //reversing vector because we moved backwards while searching for negative cycles vertices

                    //printing cycle
                    cout << "YES\n" << cycle.size() << endl;
                    for (int v: cycle) {
                        cout << v + 1 << " ";
                    }
                    cout << endl;
                    exit(0);
                }
            }
        }
        cout << "NO";
        exit(0);
    }
};
int main() {
    int n;
    cin >> n;

    // graph
    Graph<int, int> graph(n);

    // read adjacency matrix
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            int weight;
            cin >> weight;
            graph.add(i, j, weight);
        }
    }

    // apply function negative_cycles to every vertex
    for (int i = 0; i < n; i++) {
        graph.Bellman_Ford(n);
    }
    return 0;
}
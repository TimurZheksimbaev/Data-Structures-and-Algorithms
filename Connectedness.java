// Timur Zheksimbaev
import java.util.*;

/** A road map for a number of cities is given as an adjacency matrix.
 * Write a program that will be able to tell if any city is reachable from any other city (perhaps via other cities).*/

/* input
5
0 0 1 0 0
0 0 1 0 1
1 1 0 0 0
0 0 0 0 0
0 1 0 0 0
*/
/* output
NO
*/
public class Connectedness {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        GraphMatrix<Integer> graph = new GraphMatrix<>(n);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                graph.add(i, j, in.nextInt());
            }
        }

        graph.checkConnectivity();

    }
}

class GraphMatrix<E extends Comparable<E>> {
    int[][] matrix;
    int numberOfVertices;

    GraphMatrix(int n) {
        this.numberOfVertices = n;
        this.matrix = new int[n][n];
    }

    void add(int i, int j, int value) {
        matrix[i][j] = value;
        matrix[j][i] = value;
    }

    private void DFS(int start, boolean[] visited) {

        visited[start] = true;
        for (int i = 0; i < numberOfVertices; i++) {
            if (matrix[start][i] == 1 && (!visited[i])) {
                DFS(i, visited);
            }
        }
    }

    public void checkConnectivity() {

        boolean[] visited = new boolean[numberOfVertices];

        DFS(0, visited);

        boolean connected = true;

        for (boolean b : visited) {
            if (!b) {
                connected = false;
                break;
            }
        }

        System.out.println(connected ? "YES" : "NO");
    }

}
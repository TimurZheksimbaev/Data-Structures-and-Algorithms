import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * Assignemnt to solve knapsack problem with printing the indices of weights.
 * */
/* input second line - weights, third line - costs.
4 6
2 4 1 2
7 2 5 1
*/
/* output
3
1 3 4
*/

public class Knapsack {

    public static void knapsack(int n, int W, int[] weights, int[] costs) {

        int[][] total = new int[n+1][W+1];

        for (int i = 0; i <= n; i++) {
            for (int w = 0; w <= W; w++) {
                if (i == 0 || w == 0) {
                    total[i][w] = 0;
                }

                else if (weights[i - 1] <= w) {
                    total[i][w] = Math.max(costs[i - 1] + total[i - 1][w - weights[i - 1]], total[i - 1][w]);
                }

                else {
                    total[i][w] = total[i - 1][w];
                }
            }
        }

        List<Integer> output = new LinkedList<>();

        int res = total[n][W];
        for (int i = n; i > 0 && res > 0; i--) {

            if (res == total[i - 1][W])
                continue;
            else {
                output.add(i);

                res = res - costs[i - 1];
                W = W - weights[i - 1];
            }
        }

        System.out.println(output.size());
        String s = "";
        for (int i = output.size()-1; i >= 0; i--) {
            s += output.get(i).toString() + " ";
        }
        System.out.println(s);
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n, W;
        n = Integer.parseInt(in.next());
        W = Integer.parseInt(in.next());

        int[] costs = new int[n];
        int[] weights = new int[n];
        for (int i = 0; i < n; i++) {
            weights[i] = Integer.parseInt(in.next());
        }

        for (int i = 0; i < n; i++) {
            costs[i] = Integer.parseInt(in.next());
        }

        knapsack(n, W, weights, costs);
    }
}

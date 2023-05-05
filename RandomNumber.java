import java.util.*;


/** Write a program that generates N random numbers in the range [0,1) [0,1), sorts them,
 *  and returns the index of the median value in the original (random) sequence.
 * Cosider the random number generator to produce random numbers 𝑋𝑖, using the following method:
 * x0 = seed, x(n+1) = (a*x(n) + c) mod m
 * y(i) = abs(2x(i)/m - 1)
 */

/* input: a, c, m, seed
2 5 19 7
10
*/
/* output
3
*/

public class RandomNumber {
    public static void quickSort(double[][] arr, int begin, int end) {
        if (begin < end) {
            int partitionIndex = partition(arr, begin, end);

            quickSort(arr, begin, partitionIndex - 1);
            quickSort(arr, partitionIndex + 1, end);
        }
    }

    private static int partition(double[][] arr, int begin, int end) {
        double pivot = arr[end][0];
        int i = (begin - 1);

        for (int j = begin; j < end; j++) {
            if (arr[j][0] <= pivot) {
                i++;

                double swapTemp = arr[i][0];
                arr[i][0] = arr[j][0];
                arr[j][0] = swapTemp;

                int swapTemp1 = (int)arr[i][1];
                arr[i][1] = arr[j][1];
                arr[j][1] = swapTemp1;
            }
        }

        double swapTemp = arr[i + 1][0];
        arr[i + 1][0] = arr[end][0];
        arr[end][0] = swapTemp;

        int swapTemp1 = (int)arr[i + 1][1];
        arr[i + 1][1] = arr[end][1];
        arr[end][1] = swapTemp1;

        return i + 1;
    }

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        int a, c, m, n, seed;
        a = in.nextInt();
        c = in.nextInt();
        m = in.nextInt();
        seed = in.nextInt();
        n = in.nextInt();
        double[] x = new double[n];
        double[] y = new double[n];
        double[] y1 = new double[n];
        double[][] v = new double[n][2];

        for (int i = 0; i < n; i++) {
            x[i] = Math.floorMod(a * seed + c, m);
            seed = (int) x[i];
            y[i] = Math.abs((2 * x[i] / m) - 1);
            y1[i] = y[i];
            v[i][0] = y[i];
            v[i][1] = i;
        }

        quickSort(v, 0, n-1);
        System.out.println((int)v[n/2][1]);
    }

}

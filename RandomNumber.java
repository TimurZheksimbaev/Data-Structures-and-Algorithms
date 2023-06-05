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
    void countingSort(double arr[])
    {
        int n = arr.length;
 
        // The output character array that will have sorted
        // arr
         double[] output[] = new double[n];
 
        // Create a count array to store count of individual
        // characters and initialize count array as 0
        int count[] = new int[500];
        for (int i = 0; i < 500; ++i)
            count[i] = 0;
 
        // store count of each character
        for (int i = 0; i < n; ++i)
            count[arr[i]]++;
 
        // Change count[i] so that count[i] now contains
        // actual position of this character in output array
        for (int i = 1; i <= 500; ++i)
            count[i] += count[i - 1];
 
        // Build the output character array
        // To make it stable we are operating in reverse
        // order.
        for (int i = n - 1; i >= 0; i--) {
            output[count[arr[i]] - 1] = arr[i];
            count[arr[i]]--;
        }
 
        // Copy the output array to arr, so that arr now
        // contains sorted characters
        for (int i = 0; i < n; ++i)
            arr[i] = output[i];
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

        countingSort(v);
        System.out.println((int)v[n/2][1]);
    }

}

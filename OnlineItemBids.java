//Timur Zheksimbaev
import java.util.*;
/** An online website youPay users can sell their items to other users. To do that they use a bidding system
 that allows a user to provide a minimal and maximal bid for every item they sell.
 You need to implement sorting mechanism for youPay that works as follows:
    - items with higher current bids come before items with lower current bids;
    - when current bids coincide — items are sorted using their maximal bid (in ascending order);
    - if those are equal as well — two items should preserve their original order relative to each other.
 With implementation of counting sort*/

/* input
5
3 50
5 720
1 7
0 0
8 500
*/
/* output
5 2 1 3 4 */
public class OnlineItemBids {
    static void countingSort(int[][] array, int size, int ind, int order) {
        int[][] output = new int[size + 1][3];

        int max = array[0][ind];
        for (int i = 1; i < size; i++) {
            if (array[i][ind] > max)
                max = array[i][ind];
        }

        int[] count = new int[max + 1];

        for (int i = 0; i < max; ++i) {
            count[i] = 0;
        }

        for (int i = 0; i < size; i++) {
            count[array[i][ind]]++;
        }

        if (order == -1) {
            for (int i = max-1; i > -1; i--) {
                count[i] += count[i + 1];
            }


        } else {
            for (int i = 1; i <= max; i++) {
                count[i] += count[i - 1];
            }
        }

        for (int i = size - 1; i >= 0; i--) {
            output[count[array[i][ind]] - 1][0] = array[i][0];
            output[count[array[i][ind]] - 1][1] = array[i][1];
            output[count[array[i][ind]] - 1][2] = array[i][2];

            count[array[i][ind]]--;
        }


        for (int i = 0; i < size; i++) {
            array[i][0] = output[i][0];
            array[i][1] = output[i][1];
            array[i][2] = output[i][2];
        }
    }



    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        int N = in.nextInt();
        int[][] arr = new int[N][3];

        for (int i = 0; i < N; i++) {
            arr[i][0] = in.nextInt();
            arr[i][1] = in.nextInt();
            arr[i][2] = i;
        }

        countingSort(arr, N, 1, 1);

        countingSort(arr, N, 0, -1);

        for (int i = 0; i < N; i++) {
            System.out.print((arr[i][2]+1) + " ");
        }

    }
}
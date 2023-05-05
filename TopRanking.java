
import java.util.*;
//Zheksimbaev Timur

/** Given an unsorted list of players with their scores, you should build a leadership table */
/* input
6 3
Luffgirl 123
Cut3_Sugarr 234
Sw33t_Sparrow 789
Th3_Inn3r_Thing 678
3tiolat3 456
Luciform 567 */

/* output
Sw33t_Sparrow 789
Th3_Inn3r_Thing 678
Luciform 567 */

public class TopRanking {
    public static void quickSort(int[] arr, int low, int high) {

        if (low >= high)
            return;

        int middle = low + (high - low) / 2;
        int middle_num = arr[middle];

        int i = low, j = high;
        while (i <= j) {
            while (arr[i] < middle_num) {
                i++;
            }

            while (arr[j] > middle_num) {
                j--;
            }

            if (i <= j) {
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
                i++;
                j--;
            }
        }

        if (low < j)
            quickSort(arr, low, j);

        if (high > i)
            quickSort(arr, i, high);
    }


    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        String s = scan.nextLine();
        String[] s1 = s.split(" ");
        int n = Integer.parseInt(s1[0]);
        int k = Integer.parseInt(s1[1]);

        String[] players = new String[n];
        int[] scores = new int[n];
        int[] scores2 = new int[n];

        for (int i = 0; i < n; i++) {
            String player1 = scan.nextLine();
            String[] player = player1.split(" ");
            players[i] = player[0];
            scores[i] = Integer.parseInt(player[1]);
            scores2[i] = Integer.parseInt(player[1]);
        }

        quickSort(scores2, 0, n-1);

        for (int i = 0; i < n/2; i++) {
            int temp = scores2[i];
            scores2[i] = scores2[n-1-i];
            scores2[n-1-i] = temp;
        }

        int t = 0;
        for (int i = 0; i < k; i++) {

            t += 1;

            if (t > n) {
                break;
            }
            for (int j = 0; j < n; j++) {
                if (scores2[i] == scores[j]) {
                    System.out.println(players[j] + " " + scores2[i]);
                    scores[j] = 0;
                }
            }
        }
    }
}

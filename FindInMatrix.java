import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FindInMatrix {

    static int[] findInSortedMatrix(int[][] matrix, int value) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0)
            return new int[]{-1, -1};

        int n = matrix.length, m = matrix[0].length;
        int i = 0, j = n - 1;
        while (i <= m && j >= 0) {
            if (matrix[i][j] == value)
                return new int[]{i, j};
            if (matrix[i][j] > value)
                j--;
            else
                i++;
        }
        return new int[]{-1, -1};
    }

    public static void main(String[] args) {
        int[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        System.out.println(Arrays.toString(findInSortedMatrix(matrix, 7)));
    }
}

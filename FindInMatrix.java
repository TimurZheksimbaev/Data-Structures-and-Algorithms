import java.util.Arrays;

public class FindInMatrix {

    static int[] findInSortedMatrix(int[][] matrix, int value) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0)
            return new int[]{-1, -1};

        int n = matrix.length, m = matrix[0].length;
        int i = 0, j = n - 1;
        while (i < m && j >= 0) {
            if (matrix[i][j] ==value)
                return new int[]{i, j};
            if (matrix[i][j] > value)
                j--;
            else
                i++;
        }
        return new int[]{-1, -1};
    }

    private int partition(int[] arr, int begin, int end) {
        int pivot = arr[end];
        int i = (begin-1);

        for (int j = begin; j < end; j++) {
            if (arr[j] <= pivot) {
                i++;

                int swapTemp = arr[i];
                arr[i] = arr[j];
                arr[j] = swapTemp;
            }
        }

        int swapTemp = arr[i+1];
        arr[i+1] = arr[end];
        arr[end] = swapTemp;

        return i+1;
    }

    public void quickSort(int[] arr, int begin, int end) {
        if (begin < end) {
            int partitionIndex = partition(arr, begin, end);

            quickSort(arr, begin, partitionIndex-1);
            quickSort(arr, partitionIndex+1, end);
        }
    }

    static int[] findInUnsortedMatrix(int[][] matrix, int value) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0)
            return new int[]{-1, -1};

        int n = matrix.length, m = matrix[0].length;
    }

    public static void main(String[] args) {
        int[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        System.out.println(Arrays.toString(findInSortedMatrix(matrix, 7)));
    }
}
import java.util.*;

/**
 You are given a dictionary of words and a corrupted text, where all punctuation and whitespaces has been removed.
 Your task is to split text into words.*/
/* input
5 29
solve me with dynamic programming
solvemewithdynamicprogramming
*/
/* output
* solve me with dynamic programming
 */

/* input
4 23
hat had did what
whathatdidwhathathadhad
*/
/* output
what hat did what hat had had
 */

/* input
3 11
aaaa aaaaa aa
aaaaaaaaaaa
*/
/* output
aa aaaaa aaaa
*/
public class RestoringSpaces {
    private static List<String> list = new LinkedList<>();
    private static Set<String> dictionary = new HashSet<>();

    public static boolean isBreakableWordRecursive(String input, Set<String> dict) {
        int counter = 0;
        if(input.length() == 0) {
            return true;
        } else {
            for(int i = 1; i <= input.length(); i++) {
                counter++;
                String firstWord = input.substring(0, i);
                String remaining = input.substring(i);
                if((dict.contains(firstWord)) && (isBreakableWordRecursive(remaining, dict))) {
                    list.add(firstWord); //Saving these in a list to display the broken words in output
                    return true;
                }
            }
        }

        return false;

    }


    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int N = Integer.parseInt(in.next());
        int K = Integer.parseInt(in.next());

        for (int i = 0; i < N; i++) {
            dictionary.add(in.next());
        }

        String text = in.next();

        String res = "";
        if (isBreakableWordRecursive(text, dictionary)) {
            for (int i = list.size()-1; i >= 0; i--) {
                System.out.print(list.get(i) + " ");
            }
        }
    }
}

//Timur Zheksimbaev

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/** Assignment where I find if delimiters are balanced and if not output error in which line they are not balanced
 * Implementation of stack.
 */

/* input
3 1
( )
[ ]
{ }
function example ( int a [ ] , int n ) { return a [ n - 3 ] ; }
*/

/* output
The input is properly balanced.
*/

/* input
3 2
( )
[ ]
{ }
function example ( int a [ ] , int n )
{ return a [ n - 3 ; }
*/

/* output
Error in line 2, column 22: expected ] but got }.
 */

/* input
3 3
( )
[ ]
{ }
function example ( int a [ ] , int n )
{
return a [ n - 3 ] ;
*/

/* output
Error in line 3, column 22: expected } but got end of input.
 */

/* input
3 1
( )
[ ]
{ }
function example ( int a [ ] , int n ) ) { return a [ n - 3 ] ; }
*/

/* output
Error in line 1, column 40: unexpected closing token ).
*/

public class BalancedDelimiters {

    public static void main(String[] args) {

        //class Scanner is for reading from input
        Scanner in = new Scanner(System.in);

        String s1 = in.nextLine();

        int amountOfDelimiters = Integer.parseInt(s1.split(" ")[0]); //amount of delimiters
        int amountOfSentences = Integer.parseInt(s1.split(" ")[1]); //amount of sentences

        //stack where i will put all opening delimiters
        LinkedStack<String> stack = new LinkedStack<>();

        //map for keeping brackets (keys: ")" or "}" etc. and values "(" or "{" etc. )
        Map<String, String> delimiters1 = new HashMap<>();

        //map for keeping brackets (keys:(" or "{" etc. and values ")" or "}" etc. )
        Map<String, String> delimiters2 = new HashMap<>();


        //putting delimiters in maps delimiters1 and delimiters2
        for (int i = 0; i < amountOfDelimiters; i++) {
            String str = in.nextLine();
            String[] inp = str.split(" ");
            delimiters1.put(inp[1], inp[0]);
            delimiters2.put(inp[0], inp[1]);
        }

        int indexOfLineError = 0; //index of a line where error will appear
        int indexOfColumnError = 0; //index of a column where error will appear

        //performing all actions
        for (int i = 0; i < amountOfSentences; i++) {
            String s = in.nextLine();
            String[] input = s.split(" ");
            //traversing through each sentence
            for (int j = 0; j < input.length; j++) {

                //checking if current token is opening delimiter
                if (delimiters1.containsValue(input[j])) {
                    stack.push(input[j]); //putting it on top of stack

                    //checking if current token is closing delimiter
                } else if (delimiters1.containsKey(input[j])) {

                    //checking and outputting error unexpected closing token
                    if (stack.isEmpty() && delimiters1.containsKey(input[j])) {
                        System.out.println("Error in line " + (i + 1) + ", column " + (s.lastIndexOf(input[j]) + 1) + ": unexpected closing token " + input[j] + ".");
                        System.exit(0); //stopping program

                        //if element on top of stack is a closing delimiter, then remove it from stack
                    } else if (stack.peek().equals(delimiters1.get(input[j]))) {
                        stack.pop();

                        //else output an error that it is a wrong closing delimiter
                    } else {
                        System.out.println("Error in line " + (i + 1) + ", column " + (s.lastIndexOf(input[j]) + 1) + ": expected " + delimiters2.get(stack.peek()) + " but got " + input[j] + ".");
                        System.exit(0); //stopping program
                    }

                }

                //saving indices of line and column where error appears
                if (!stack.isEmpty()) {
                    indexOfLineError = i + 1;
                    indexOfColumnError = s.length();
                }
            }
        }

        //if at the end stack is empty, then the input is properly balanced
        if (stack.isEmpty()) {
            System.out.println("The input is properly balanced.");
            System.exit(0);

            //else there is unclosed delimiter in stack and end of input
        } else {
            System.out.println("Error in line " + (indexOfLineError) + ", column " + (indexOfColumnError + 2) + ": expected " + delimiters2.get(stack.peek()) + " but got end of input.");
            System.exit(0);
        }

    }
}

//Interface stack<T>
interface stack<T> {
    int size();

    boolean isEmpty();

    void push(T e);

    T peek();

    T pop();
}

//Implementation of stack

//class LinkedStack<T> which implements stack<T>
class LinkedStack<T> implements stack<T> {
    public static final int CAPACITY = 1000; //capacity of stack
    private final T[] elements; //array where all elements are stored

    private int top = -1; //index of last element

    //constructor
    public LinkedStack() {

        this(CAPACITY);
    }

    //constructor
    public LinkedStack(int capacity) {

        elements = (T[]) new Object[capacity];
    }

    //method which returns size of stack
    public int size() {
        return (top + 1);
    }

    // method which returns true if stack is empty
    public boolean isEmpty() {
        return (top == -1);
    }

    //method which puts element on top of stack
    public void push(T e) throws IllegalStateException {
        if (size() == elements.length) throw new IllegalStateException("Stack is full");
        elements[++top] = e;
    }

    //method which returns top element of stack
    public T peek() {
        if (isEmpty()) return null;
        return elements[top];
    }

    //method which removes and returns top element from stack
    public T pop() {
        if (isEmpty()) return null;
        T answer = elements[top];
        elements[top] = null;
        top--;
        return answer;
    }
}

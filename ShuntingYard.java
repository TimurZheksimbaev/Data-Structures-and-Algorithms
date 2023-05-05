import java.util.*;

/** Shunting yard algorithm with additional operators min and max.
 * With your implementation of stack and queue*/

/* input
* 1 + 2 * min ( 3 , 5 ) - 4 / 2 */
/* output
1 2 3 5 min * + 4 2 / - */

public class ShuntingYard{

    public static boolean isOperator(String s) {
        if (s.equals("+") || s.equals("-") || s.equals("*") || s.equals("/")) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isDigit(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static int op_preced(String c) {
        switch(c) {
            case "*":
                return 2;
            case "/":
                return 2;
            case "+":
                return 1;
            case "-":
                return 1;
        }
        return 0;
    }

    public static boolean op_left_assoc(String c) {
        switch(c) {
            case "*":
                return true;
            case "/":
                return true;
            case "+":
                return true;
            case "-":
                return true;
        }
        return false;
    }

    public static String shuntingYard(String expression) {

        stackList<String> stack = new stackList<>();
        queueList queue = new queueList();

        String min = "min";
        String max = "max";

        String[] s = expression.split(" ");

        StringBuilder postfixStr = new StringBuilder();


        for (String token : s) {
            if (isDigit(token)) {
                queue.offer(token);

            } else if (token.equals(min) || token.equals(max)) {
                stack.push(token);

            }  else if (isOperator(token)) {
                while (stack.size() > 0) {
                    String sc = stack.peek();
                    if (isOperator(sc) && ((op_left_assoc(token) && op_preced(token) <= op_preced(sc))) ||
                            (!op_left_assoc(token) && (op_preced(token) < op_preced(sc)))) {
                        queue.offer(stack.pop());

                    } else {
                        break;
                    }
                }
                stack.push(token);

            } else if (token.equals("(")) {
                stack.push(token);
            }
            else if (token.equals(",")) {
                while (!stack.peek().equals("(")) {
                    queue.offer(stack.pop());
                }
            }

            else if (token.equals(")")) {
                while (!stack.peek().equals("(")) {
                    queue.offer(stack.pop());
                }
                stack.pop();
                if (stack.isEmpty()) continue;
                if (stack.peek().equals(min) || stack.peek().equals(max)) {
                    queue.offer(stack.pop());
                }

            }
        }
        if (stack.size() > 0) {
            while (stack.size() > 0) {
                queue.offer(stack.pop());
            }
        }

        while (queue.size() > 0) {
            postfixStr.append(queue.poll()).append(" ");
        }

        return postfixStr.toString();
    }

    public static int evaluate(queueList que) {
        stackList<Integer> result = new stackList<>();
        while (que.size() > 0) {
            String op = que.poll();
            if (isDigit(op)) {
                result.push(Integer.parseInt(op));
            }
            else {
                if (result.size() > 1) {
                    int op1 = result.pop();
                    int op2 = result.pop();
                    switch (op) {
                        case "+":
                            result.push(op2 + op1);
                            break;
                        case "-":
                            result.push(op2 - op1);
                            break;
                        case "*":
                            result.push(op2 * op1);
                            break;
                        case "/":
                            result.push(op2 / op1);
                            break;
                        case "min":
                            result.push(Math.min(op2, op1));
                            break;
                        case "max":
                            result.push(Math.max(op2, op1));
                            break;
                    }
                }
            }

        }

        return result.peek();
    }

    public static void main(String[] args) {
        Scanner in  = new Scanner(System.in);
        String expression = in.nextLine();
        System.out.println(shuntingYard(expression));

    }
}


class StackNode<T> {
    T data;
    StackNode<T> next;
}

class stackList<T> {
    private StackNode<T> top;
    private int nodesCount;

    public stackList() {
        this.top = null;
        this.nodesCount = 0;
    }

    public void push(T x) {

        StackNode<T> node = new StackNode<>();


        node.data = x;

        node.next = top;

        top = node;

        this.nodesCount += 1;
    }

    public boolean isEmpty() {
        return top == null;
    }

    public T peek() {

        return top.data;
    }

    public T pop() {


        T top = peek();


        this.nodesCount -= 1;

        this.top = (this.top).next;

        return top;
    }

    public int size() {
        return this.nodesCount;
    }
}

class QueueNode {
    String data;
    QueueNode next;

    public QueueNode(String data) {
        this.data = data;
        this.next = null;
    }
}

class queueList {
    private QueueNode rear = null, front = null;
    private int count = 0;

    public String poll() {


        QueueNode temp = front;

        front = front.next;

        if (front == null) {
            rear = null;
        }

        count -= 1;

        return temp.data;
    }

    public void offer(String item) {
        QueueNode node = new QueueNode(item);

        if (front == null) {
            front = node;
            rear = node;
        } else {
            rear.next = node;
            rear = node;
        }

        count += 1;
    }

    public String peek() {


        return front.data;
    }

    public boolean isEmpty() {
        return rear == null && front == null;
    }

    public int size() {
        return count;
    }
}
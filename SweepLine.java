import java.io.*;
import java.util.StringTokenizer;

/**
 * SweepLine algorithm with implementation of AVLTree and QuickSort.
 */

/* input
2
3 90 8 92
1 99 6 91
*/
/* output
INTERSECTION
3 90 8 92
1 99 6 91
*/

/* input
3
121316 -1988767 120756 -1988674
121584 -1988690 122016 -1988486
122342 -1989139 122660 -1989142
*/
/* output
NO INTERSECTIONS
*/

/* input
5
-10 0 10 0
-8 1 -7 1
0 1 0 -1
5 1 6 1
7 1 8 1
*/
/* output
INTERSECTION
-10 0 10 0
0 1 0 -1
*/
public class SweepLine {

    // for faster outputting
    public static PrintWriter output = new PrintWriter(System.out);

    // method for printing result of a program
    private static void printResult(Segment a, Segment b) {
        output.println("INTERSECTION");
        output.println(a.xA + " " + a.yA + " " + a.xB + " " + a.yB);
        output.println(b.xA + " " + b.yA + " " + b.xB + " " + b.yB);
        output.flush();
        System.exit(0);
    }


    public static void main(String[] args) throws IOException {

        // initializing reader
        FastInputReader in = new FastInputReader(System.in);

        int n = in.readInt(), x, y, z, w; // number of points and points
        Point[] points = new Point[n * 2]; // array of points

        // Reading and storing input.
        for (int i = 0; i < 2 * n; i += 2) {
            x = in.readInt();
            y = in.readInt();
            z = in.readInt();
            w = in.readInt();

            points[i] = new Point(x, y); // first point
            points[i + 1] = new Point(z, w); //second point

            // Link each point to its corresponding segment.
            points[i].itsSegment = new Segment(x, y, z, w);
            points[i + 1].itsSegment = new Segment(x, y, z, w);

            // To mark left/right points.
            if (x < z)
                points[i].flag = true;
            else if (x > z)
                points[i].flag = false;
            else
                points[i].flag = y < w;

            points[i + 1].flag = !points[i].flag; // if one point is left, the other one is right and vice-versa.
        }


        // Creating an AVL tree of Segments.
        AVLTree<Segment> treeOfSegments = new AVLTree<>();

        // Sorting points using Quick sort algorithm.
        QuickSort<Point> sortPoints = new QuickSort<>();
        sortPoints.quickSort(points, 0, points.length - 1);


        for (int i = 0; i < 2 * n; i++) { // Looping over all points.

            Segment currentSegment = points[i].itsSegment; // To hold the segment we are currently processing.
            treeOfSegments.definePredecessorAndSuccessor(currentSegment); // Calculates segment predecessor and successor.
            Segment preSegment = treeOfSegments.predecessor; // Predecessor segment
            Segment sucSegment = treeOfSegments.successor; // Successor segment

            if (points[i].flag) { // If the point is flagged left, we insert it in the AVL

                treeOfSegments.add(currentSegment); //insertion

                // Check if the current segment intersects with its predecessor
                if (preSegment != null && preSegment != currentSegment && currentSegment.intersects(preSegment)) {
                    printResult(currentSegment, preSegment); // print result
                }

                // Check if the current segment intersects with its successor
                if (sucSegment != null && sucSegment != currentSegment && currentSegment.intersects(sucSegment)) {
                    printResult(currentSegment, sucSegment); // print result
                }

            } else {

                // Check if the current segment predecessor intersects with its predecessor
                if (preSegment != null && sucSegment != null && preSegment != sucSegment && preSegment.intersects(sucSegment)) {
                    printResult(preSegment, sucSegment); // print result
                }

                treeOfSegments.remove(currentSegment); //removing current segment when we proceeded the whole segment
            }
        }

        // If all points are processed and the program didn't terminate, then no intersections detected.
        output.println("NO INTERSECTIONS");
        output.flush();
    }
}

/** Class for faster reading input.
 * I found this class in internet, while searching for ways to increase the speed of reading input.
 * Source: https://www.geeksforgeeks.org/fast-io-in-java-in-competitive-programming/.
 * */
class FastInputReader {
    private BufferedReader reader;
    private StringTokenizer tokenizer;

    //constructor where i initialize reader.
    FastInputReader(InputStream input) {
        reader = new BufferedReader(
                new InputStreamReader(input));
        tokenizer = new StringTokenizer("");
    }


    /** get next word */
    private String next() throws IOException {
        while (!tokenizer.hasMoreTokens()) {
            tokenizer = new StringTokenizer(
                    reader.readLine());
        }
        return tokenizer.nextToken();
    }

    public int readInt() throws IOException {
        return Integer.parseInt(next());
    }
}


/**
 * Class Point for storing coordinates x and y.
 */
class Point implements Comparable<Point> {

    int x, y;
    boolean flag; // true if point is left in its segment, false if it's right.
    Segment itsSegment;  // Holds a reference to the segment which this point belong to.

    Point(int x, int y) { // Constructor
        this.x = x;
        this.y = y;
    }

    @Override
    public int compareTo(Point p) { // To enable sorting points with respect to their x coordinate.
        if (this.x < p.x) return -1;
        else if (this.x > p.x) return 1;
        else {                         // Priority is for LEFT points.
            if (this.flag) return -1;
            else if (p.flag) return 1;
            else return 0;
        }
    }
}


/**
 * Class Segment for storing segments.
 */
class Segment implements Comparable<Segment> {
    long xA, yA, xB, yB; // coordinates

    //constructor
    public Segment(long xA, long yA, long xB, long yB) {
        this.xA = xA;
        this.yA = yA;
        this.xB = xB;
        this.yB = yB;
    }

    @Override
    public int compareTo(Segment s) { // To sort points with respect to their y coordinates
        if (this.yA < s.yA) return -1;
        else if (this.yA > s.yA) return 1;
        else {                           // Priority is for lower beginning segments.
            if (this.xA < s.xA) return -1;
            else if (this.xA > s.xA) return 1;
            else return 0;
        }
    }


    // method for checking if a point lies on segment. O(1).
    private static boolean liesOnSegment(double x, double y, double x1, double y1, double x2, double y2) {
        return (x >= Math.min(x1, x2) && x <= Math.max(x1, x2) && y >= Math.min(y1, y2) && y <= Math.max(y1, y2));
    }

    // method for checking if a segment intersects with another segment
    public boolean intersects(Segment s) {
        long xC = s.xA, xD = s.xB, yC = s.yA, yD = s.yB;

        double numeratorX = ((xA * yB - yA * xB) * (xC - xD) - (xA - xB) * (xC * yD - yC * xD));
        double numeratorY = ((xA * yB - yA * xB) * (yC - yD) - (yA - yB) * (xC * yD - yC * xD));
        double denominator = (xA - xB) * (yC - yD) - (yA - yB) * (xC - xD);

        double xP;
        double yP;

        // Segments have the same slope, They are either parallel or coincident, or they share an end.
        if (denominator == 0) {
            if (numeratorX == 0 || numeratorY == 0)
                if ((xA == xC && yA == yC) || (xA == xD && yA == yD))
                    return true; // Segments share an end

                else if ((xB == xC && yB == yC) || (xB == xD && yB == yD))
                    return true; // Segments share an end

                else if (liesOnSegment(xA, yA, xC, yC, xD, yD) || liesOnSegment(xB, yB, xC, yC, xD, yD) || liesOnSegment(xC, yC, xA, yA, xB, yB) || liesOnSegment(xD, yD, xA, yA, xB, yB))
                    return true; // Segments are coincident

                else
                    return false;
            else
                return false; // Segments are parallel

        } else {  // Segments have different slope, Either they intersect or not, but they can't be parallel or coincident.

            xP = numeratorX / denominator;
            yP = numeratorY / denominator;

            // Check if the point of intersection lies on both of the segments.
            if (liesOnSegment(xP, yP, xA, yA, xB, yB) && liesOnSegment(xP, yP, xC, yC, xD, yD))
                return true; // Segments intersect at (Xp, Yp)
            else
                return false; // Lines intersect, but segments don't.
        }
    }
}

/**
 * Generic Class AVL Tree.
 * */
class AVLTree<T extends Comparable<T>> {

    private Node root;    // Holds the root node that we need to begin recursion.
    T predecessor, successor; // hold reference to predecessor and successor segments

    private class Node {  // AVL Tree Node
        T key;           // The data stored in the node
        Node left, right; // reference to it's left and right child
        int height = 0; // height, holds the size of the subtree rooted at this node, its 1 by default.
    }

    // calls insertion method. O(logn) - best case.
    void add(T key) {
        root = addKey(key, root);
    }

    // Calls the remove method. O(logn) - best case.
    void remove(T key) {
        root = removeKey(key, root);
    }

    // calls method for defining predecessor and successor segments. O(logn) - best case.
    public void definePredecessorAndSuccessor(T seg) {
        defPredAndSuc(seg, root);
    }

    // Calculates the inorder predecessor and successor segment for a given element. O(logn) - best case.
    private void defPredAndSuc(T seg, Node n) {
        if (n == null) return;
        if (n.key == seg) {
            if (n.left != null) predecessor = subTreeMax(n.left).key;
            if (n.right != null) successor = subTreeMin(n.right).key;
            return;
        }

        if (seg.compareTo(n.key) > 0) {
            predecessor = n.key;
            defPredAndSuc(seg, n.right);
        } else if (seg.compareTo(n.key) < 0) {
            successor = n.key;
            defPredAndSuc(seg, n.left);
        }
    }


    // Returns the right-most element in the subtree at root n. O(logn) - best case.
    private Node subTreeMax(Node n) {
        Node rightMost = n;
        while (rightMost.right != null) rightMost = rightMost.right;
        return rightMost;
    }

    // method for getting height. O(1).
    int getHeight(Node node) {
        return node == null ? -1 : node.height;
    }

    //method for getting balance of a tree. O(1).
    int getBalance(Node node) {
        return (node == null) ? 0 : getHeight(node.left) - getHeight(node.right);
    }

    //method fot updating height. O(1).
    void updateHeight(Node node) {
        node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;
    }

    // Returns the left-most element in the subtree at root n. O(logn) - best case.
    private Node subTreeMin(Node n) {
        Node leftMost = n;
        while (leftMost.left != null) leftMost = leftMost.left;
        return leftMost;
    }

    // method for inserting a segment in tree. O(logn) - best case.
    private Node addKey(T key, Node n) {

        if (n == null) { // Base Case, When we reach a null link, we insert the normal way.
            n = new Node();
            n.key = key;
            return n;
        }

        // Either the element we are inserting is less than the element stored in the current node, so we go left.
        if (key.compareTo(n.key) < 0) {
            n.left = addKey(key, n.left);
        }
        // Or the element we are inserting is greater than the element stored in the current node, so we go right.
        else if (key.compareTo(n.key) > 0) {
            n.right = addKey(key, n.right);
        }

        // Updating the height of the node n after insertion of key.
        updateHeight(n);


        // If the left subtree is heavier that the right.
        if (getBalance(n) > 1) {
            if (key.compareTo(n.left.key) < 0)        // Left-Left Case
                return rightRotation(n);                    // We right-rotate to achieve balance.
            else if (key.compareTo(n.left.key) > 0) { // Left-Right Case
                n.left = leftRotation(n.left);              // We left-rotate to convert to left-left case.
                return rightRotation(n);                    // Then we right-rotate
            }
        } else if (getBalance(n) < -1) {
            if (key.compareTo(n.right.key) < 0) {     // Right-Left Case
                n.right = rightRotation(n.right);           // We right-rotate to convert to right-right case
                return leftRotation(n);                     // Then we left-rotate.leftRotation(n);
            }
            if (key.compareTo(n.right.key) > 0) {     // Right-Right Case
                return leftRotation(n);                     // We left-rotate to achieve balance.
            }
        }
        return n; // If the tree didn't need a balance, we'll return the node without modification.
    }


    // Internal recursive remove method for deleting an element from the tree.
    // At first we search for the element, if we didn't find it, we return null.
    // If we found the element, we have three cases, we handle each one separately.
    // Then we check that the tree is still an AVL tree, if not, we balance it by rotations. O(logn) - best case.

    private Node removeKey(T key, Node n) {
        if (n == null) return null;
        // Either the element we are searching for is less than the element stored in the current node, so we go left.
        if (key.compareTo(n.key) < 0) {
            n.left = removeKey(key, n.left);
        }
        // Or the element we are searching for is greater than the element stored in the current node, so we go right.
        else if (key.compareTo(n.key) > 0) {
            n.right = removeKey(key, n.right);
        }
        // Otherwise, we found the element we need to delete.
        else {
            if (n.left == null && n.right == null) { // Node has no children, we can safely assign it to null.
                return null;
            } else if (n.right == null) { // Node has no right children, to delete it we replace it with its left child.
                return n.left;
            } else if (n.left == null) {  // Node has no left children, to delete it we replace it with its right child.
                return n.right;
            } else {                                // Node has both, left and right children
                T pre = subTreeMax(n.left).key;    // We find its predecessor from in-order traversal (Successor is also ok).
                n.key = pre;                       // We replace the node with its predecessor
                n.left = removeKey(pre, n.left);       // We search for that predecessor and delete it.
                // predecessor can only be in the left subtree, that's why we don't need to do a full search from the root.
            }
        }

        // Updating the height of the node i after removing e.
        updateHeight(n);

        // Now we need to re-balance if AVL property is changed.

        // If the left subtree is heavier that the right.
        if (getBalance(n) > 1) {
            if (getHeight(n.left.left) >= getHeight(n.left.right)) // Left-Left Case
                return rightRotation(n);                                // We right-rotate to achieve balance.
            else {                                               // Left-Right Case
                n.left = leftRotation(n.left);                          // We left-rotate to convert to left-left case.
                return rightRotation(n);                                // Then we right-rotate
            }
        } else if (getBalance(n) < -1) {
            if (getHeight(n.right.left) >= getHeight(n.right.right)) { // Right-Left Case
                n.right = rightRotation(n.right);                       // We right-rotate to convert to right-right case
                return leftRotation(n);                                 // Then we left-rotate.
            } else {                                            // Right-Right Case
                return leftRotation(n);                                 // We left-rotate to achieve balance.
            }
        }
        return n; // If the tree didn't need a balance, we'll return the node without modification.
    }


    // A method to left-rotate a node, changing some links. O(1) - best case.
    private Node leftRotation(Node n) {
        Node r = n.right;
        Node l = r.left;
        r.left = n;
        n.right = l;

        // Updating the heights.
        updateHeight(n);
        updateHeight(r);
        return r; // returning the new subtree root after rotation.
    }

    // A method for right-rotation, with changing some links. O(1) - best case.
    private Node rightRotation(Node n) {
        Node l = n.left;
        Node r = l.right;
        l.right = n;
        n.left = r;

        // Updating the heights.
        updateHeight(n);
        updateHeight(l);
        return l; // returning the new subtree root after rotation.
    }
}


/**
 * Class for generic Quick Sort.
 * Time complexity in best and average cases is O(nlog(n)), in worst case is O(n^2).
 * Not stable and in-place.
 */
class QuickSort<T extends Comparable<T>> {
    public void quickSort(T[] array, int p, int r) {
        int q;
        if (p < r) {
            q = partition(array, p, r); // partition of array around pivot
            quickSort(array, p, q - 1); //sorting the low side
            quickSort(array, q + 1, r); // sorting the high side
        }
    }

    private int partition(T[] array, int p, int r) {
        T x = array[r]; //pivot
        int i = p - 1; // highest index into the low side
        for (int j = p; j < r; j++) {
            if (array[j].compareTo(x) < 0) {
                i += 1;
                T temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }
        }

        T temp = array[i + 1];
        array[i + 1] = array[r];
        array[r] = temp;
        return i + 1; // new index of a pivot
    }
}
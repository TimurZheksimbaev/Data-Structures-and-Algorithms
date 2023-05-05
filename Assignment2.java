////Timur Zheksimbaev
///* Importance of line segment intersection.
// * Line segment intersection has many applications such as in robotics, computer graphics, geometric modelling, GIS,
// * computational geometry.
// * Here are some applications:
// * 1) Reflection - if we need to reflect a point across a line, we need to find a perpendicular from that point and line
// *    and then find intersection of this two lines.
// * 2) Rotation - we need to use reflection which requires line intersections
// * 3) Convex Hull - a convex hull of a set of points is the smallest convex polygon that contains every one of the points.
// *    To do that we need to find many intersections of lines
// * 4) Finding a circle from three points - to find center of a circle we need to find intersection of two bisectors.
// * */
//
//
///* Sweep line algorithm
// * Basically a sweep line is an imaginary vertical line which we move in right direction, and we see where this sweep line
// * intersects some lines or points, which are called events. Sweep line can be used to solve line intersection problem,
// * closest pair, area of the union of rectangles, convex hull, Manhattan minimum spanning tree
// * To implement this algorithm we firstly need to sort points with respect to their x coordinates and store line segments
// * in Self-Balancing Binary Search Tree such as AVL Tree or Red Black Tree etc.
// * Its time complexity is O(nlogn) and space complexity is O(n).
// */
//
//
//public class Assignment2 {
//
//    public static void main(String[] args) {
////        Scanner in = new Scanner(System.in);
////        int n = in.nextInt();
////        int[][] points = new int[n*2][2];
////        int ind = 0;
////
////        for (int i = 0; i < n*2; i++) {
////            points[i][0] = in.nextInt();
////            points[i][1] = in.nextInt();
////        }
//
//
//    }
//}
//
//class Assignment2.Point {
//    int x, y;
//
//    public Assignment2.Point(int x) {
//        this.x = x;
//        this.y = y;
//    }
//}
//
//class Assignment2.Segment {
//    Assignment2.Point A;
//    Assignment2.Point B;
//    long xA, yA, xB, yB;
//
//    public Assignment2.Segment(long xA, long yA, long xB, long yB) {
//        this.xA = xA;
//        this.yA = yA;
//        this.xB = xB;
//        this.yB = yB;
//    }
//
//
//    private boolean liesInSegment(long x, long y, Assignment2.Segment s) {
//        return
//    }
//
//    public boolean checkIntersection(Assignment2.Segment line) {
//        long xC = line.xA, yC = line.yA, xD = line.xB, yD = line.yB;
//        long numeratorX = (xA * yB - yA * xB) * (xC - xD) - (xA - xB) * (xC * yD - yC * xD);
//        long numeratorY = (xA * yB - yA * xB) * (yC - yD) - (yA - yB) * (xC * yD - yC * xD);
//        long denominator = (xA - xB) * (yC - yD) - (yA - yB) * (xC - xD);
//
//        if (denominator == 0) {
//            if (liesInSegment(xA, yA, line) || liesInSegment(xB, yB, line) ||
//                    liesInSegment(xC, yC, line) || liesInSegment(xD, yD, line))
//                return true; //segments are coincident
//
//            else if ((xA == xC && yA == yC) || (xA == xD && yA == yD))
//                return true; //segments share an endpoint
//
//            else if ((xB == xC && yB == yC) || (xB == xD && yB == yD))
//                return true; //segments share an endpoint
//
//            else
//                return false;
//
//        } else {
//            long xP = numeratorX / denominator;
//            long yP = numeratorY / denominator;
//
//            if (liesInSegment(xP, yP, line) && liesInSegment(xP, yP, line))
//                return true;
//            else
//                return false; //lines intersect, but segments not
//        }
//    }
//
//
//
//
//}
//
//
//// Quick sort algorithm.
//// Time complexity is O(nlogn), it is not stable and in-place.
//class QuickSort<T extends Comparable<T>> {
//    public void quickSort(T[][] points, int l, int r) {
//        int q;
//        if (r > l) {
//            q = partition(points, l, r);
//            quickSort(points, l, q - 1);
//            quickSort(points, q + 1, r);
//        }
//    }
//
//    private int partition(T[][] points, int l, int r) {
//        T pivot = points[r][0];
//        int i = (l - 1);
//
//        for (int j = l; j < r; j++) {
//            if (points[j][0].compareTo(pivot) <= 0) {
//                i++;
//
//                T temp1 = points[i][0];
//                points[i][0] = points[j][0];
//                points[j][0] = temp1;
//
//                T temp2 = points[i][1];
//                points[i][1] = points[j][1];
//                points[j][1] = temp2;
//            }
//        }
//
//        T temp1 = points[i + 1][0];
//        points[i + 1][0] = points[r][0];
//        points[r][0] = temp1;
//
//        T temp2 = points[i + 1][1];
//        points[i + 1][1] = points[r][1];
//        points[r][1] = temp2;
//
//        return i + 1;
//    }
//}
//
//class Assignment2.AVLTree<E extends Comparable<E>> {
//    private Node root;
//    public E predecessor, successor;
//
//    private class Node {
//        E key;
//        int height;  //for height
//        Node left;
//        Node right;
//
//        //default constructor to create null node
//    public Node() {
//            left = null;
//            right = null;
//            key = null;
//            height = 0;
//        }
//
//        // parameterized constructor
//    public Node(E key) {
//            left = null;
//            right = null;
//            this.key = key;
//            height = 0;
//        }
//    }
//
//    int height(Node N) {
//        if (N == null)
//            return 0;
//        return N.height;
//    }
//
//    Node rotateRight(Node b) {
//        Node a = b.left;
//        Node c = a.right;
//
//        a.right = b;
//        b.left = c;
//
//        b.height = Math.max(height(b.left), height(b.right)) + 1;
//        a.height = Math.max(height(a.left), height(a.right)) + 1;
//
//        return a;
//    }
//
//    Node rotateLeft(Node a) {
//        Node b = a.right;
//        Node c = b.left;
//
//        b.left = a;
//        a.right = c;
//
//        a.height = Math.max(height(a.left), height(a.right)) + 1;
//        b.height = Math.max(height(b.left), height(b.right)) + 1;
//
//        return b;
//    }
//
//    int getBalance(Node N) {
//        if (N == null)
//            return 0;
//        return height(N.left) - height(N.right);
//    }
//
//    Node addKey(Node node, E key) {
//        if (node == null)
//            return (new Node(key));
//
//        if (key.compareTo((E) node.key) < 0)
//            node.left = addKey(node.left, key);
//        else if (key.compareTo((E) node.key) > 0)
//            node.right = addKey(node.right, key);
//        else
//            return node;
//
//        node.height = 1 + Math.max(height(node.left),
//                height(node.right));
//
//        int balance = getBalance(node);
//        if (balance > 1 && key.compareTo((E) node.left.key) < 0)
//            return rotateRight(node);
//
//        if (balance < -1 && key.compareTo((E) node.right.key) > 0)
//            return rotateLeft(node);
//
//        if (balance > 1 && key.compareTo((E) node.left.key) > 0) {
//            node.left = rotateLeft(node.left);
//            return rotateRight(node);
//        }
//
//        if (balance < -1 && key.compareTo((E) node.right.key) < 0) {
//            node.right = rotateRight(node.right);
//            return rotateLeft(node);
//        }
//
//        return node;
//    }
//
//    public void add(E key) {
//        root = addKey(root, key);
//    }
//
//    public void remove(E key) {
//        deleteNode(root, key);
//    }
//
//    Node minValueNode(Node node) {
//        Node temp;
//        for (temp = node; temp.left != null; temp = temp.left) ;
//
//        return temp;
//    }
//
//    Node deleteNode(Node root, E key) {
//        if (root == null)
//            return root;
//
//        if (key.compareTo((E) root.key) < 0)
//            root.left = deleteNode(root.left, key);
//
//        else if (key.compareTo((E) root.key) > 0)
//            root.right = deleteNode(root.right, key);
//
//        else {
//
//            if ((root.left == null) || (root.right == null)) {
//                Node temp = null;
//                if (temp == root.left)
//                    temp = root.right;
//                else
//                    temp = root.left;
//
//                if (temp == null) {
//                    temp = root;
//                    root = null;
//                } else
//                    root = temp;
//            } else {
//
//                Node temp = minValueNode(root.right);
//
//                root.key = temp.key;
//
//                root.right = deleteNode(root.right, temp.key);
//            }
//        }
//
//        if (root == null)
//            return root;
//
//        root.height = Math.max(height(root.left), height(root.right)) + 1;
//        int balance = getBalance(root);
//
//        if (balance > 1 && getBalance(root.left) >= 0)
//            return rotateRight(root);
//
//        if (balance > 1 && getBalance(root.left) < 0) {
//            root.left = rotateLeft(root.left);
//            return rotateRight(root);
//        }
//
//        if (balance < -1 && getBalance(root.right) <= 0)
//            return rotateLeft(root);
//
//        if (balance < -1 && getBalance(root.right) > 0) {
//            root.right = rotateRight(root.right);
//            return rotateLeft(root);
//        }
//
//        return root;
//    }
//
//    private void definePredecessorAndSuccessor(E e, Node i) { // O( log(n) )
//        if (i == null) return;
//        if (i.key == e) {
//            if (i.left != null) predecessor = subTreeMax(i.left).key;
//            if (i.right != null) successor = subTreeMin(i.right).key;
//            return;
//        }
//        if (e.compareTo(i.key) > 0) {
//            predecessor = i.key;
//            definePredecessorAndSuccessor(e, i.right);
//        } else if (e.compareTo(i.key) < 0) {
//            successor = i.key;
//            definePredecessorAndSuccessor(e, i.left);
//        }
//    }
//
//    // Returns the right-most element in the subtree rooted at i.
//    private Node subTreeMax(Node i) { // O( log(n) )
//        Node t = i;
//        while (t.right != null) t = t.right;
//        return t;
//    }
//
//    // Returns the left-most element in the subtree rooted at i.
//    private Node subTreeMin(Node i) { // O( log(n) )
//        Node t = i;
//        while (t.left != null) t = t.left;
//        return t;
//    }
//}
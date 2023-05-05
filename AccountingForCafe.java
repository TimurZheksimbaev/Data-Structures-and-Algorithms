import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/**
 A local café would like to keep track of its earnings. Your task is to implement a simple accounting program for the café.
 The program is given a sequence of entries, one per sold item. Each entry contains a timestamp, receipt number,
 sold item title and its cost. The task is to compute for each date (year, month and day) total amount of money earned,
 as well as number of unique customers (number of unique receipts).
 With implementation of hashMap.
 */

/* input
5
2019-01-24 15:38:17 #495-GE $99.80 CAPPUCCINO
2019-01-24 15:38:17 #495-GE $34.95 ESPRESSO RISTRETTO
2021-08-15 01:46:42 #272-YZ $80.45 CAFFE MOCHA
2019-01-24 15:38:17 #495-GE $30.82 LATTE MACCHIATO
2019-01-24 15:38:17 #495-GE $50.00 AMERICANO
*/
/* output
2021-08-15 $80.45 1
2019-01-24 $215.57 1
*/

/* input
7
2021-02-03 01:02:03 #123-AC $250.00 Latte Large
2021-02-03 03:02:03 #133-AC $150.00 Latte Small
2021-02-03 01:02:03 #123-AC $550.00 Espresso
2021-02-03 01:02:03 #123-AC $253.00 Flat White
2021-02-05 01:02:03 #143-AC $54.00 Double Espresso
2021-02-05 01:02:03 #143-AC $5.00 Sugar
2021-02-03 01:02:03 #123-AC $123.00 Brownie
*/
/* output
2021-02-03 $1326.0 2
2021-02-05 $59.0 1
*/

public class AccountingForCafe {

    public static void main(String[] args) {

        //class Scanner is for reading from input
        Scanner in = new Scanner(System.in);

        //map for keeping there dates as keys and prices as values
        hmap<String, Double> prices = new hmap<>();

        //map for keeping unique IDs, keys are dates and values is set of IDs
        hmap<String, Set<String>> uniqueIDs = new hmap<>();

        // Number of entries in input
        String n1 = in.nextLine();
        int n = Integer.parseInt(n1);

        //for loop where I read input and do everything
        for (int i = 0; i < n; i++) {

            String input = in.nextLine();
            String[] splitInput = input.split(" "); //split information

            String date = splitInput[0]; //date
            String ID = splitInput[2]; //ID
            String price = splitInput[3].replace('$', ' '); //price and i remove dollar sign

            //Putting dates and prices in map prices.
            //I count total costs for each date at once
            Double currentPrice = prices.get(date);

            //checking if there is value in this map
            if (currentPrice == null) {
                currentPrice = Double.parseDouble(price);
            } else {
                currentPrice += Double.parseDouble(price);
            }
            prices.put(date, currentPrice);

            // Putting IDs and dates in map uniqueIDs
            // I keep dates as keys and set of IDs as Set
            Set<String> setOfIDs = uniqueIDs.get(date);
            if (setOfIDs == null) {
                setOfIDs = new HashSet<>();
                uniqueIDs.put(date, setOfIDs);
            }
            setOfIDs.add(ID);
        }

        //printing dates and corresponding total costs and amount of unique receipt IDs for each day
        for (String date : prices.keys()) {
            System.out.println(date + " " + "$" + prices.get(date) + " " + uniqueIDs.get(date).size());
        }


    }
}

//Implementation of hashtable
class hmap<K, V> {
    private static final int INIT_CAPACITY = 10;

    private int numberOfEntrys; // the number of key-value pairs in the symbol table
    private int size; // size of separate chaining table
    private Node<K, V>[] hashTable; // array of linked-list tables


    //private class for keys and values
    private static class Node<K, V> {
        private K key;
        private V value;
        private Node<K, V> next;

        public Node() {} //constructor

        //constructor of Node
        public Node(K key, V value, Node<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    //constructor for map
    public hmap() {
        this(INIT_CAPACITY);
    }

    //constructor for map
    public hmap(int capacity) {
        this.size = capacity;
        this.numberOfEntrys = 0;
        hashTable = (Node<K, V>[]) new Node[capacity];
        for (int i = 0; i < size; i++) {
            hashTable[i] = (Node<K, V>) new Node();
        }
    }

    //function which returns number of elements in hashtable
    public int size() {
        return numberOfEntrys;
    }

    //function which returns true if hashtable is empty and false otherwise
    public boolean isEmpty() {
        return numberOfEntrys == 0;
    }

    //hash function for keys which gets a key as a parameter
    private int hashFunction(K key) {
        return (key.hashCode()) % size;
    }

    //function for getting a keys from hashtable, returns key, otherwise returns null
    public V get(K key) {
        int i = hashFunction(key);
        Node x = hashTable[i];
        //checking if there is the same key
        while (x != null) {
            if (key.equals(x.key)) {
                return (V) x.value;
            }
            x = x.next;
        }
        return null;
    }

    //function for putting keys and values
    public void put(K key, V value) {
        //resizing hashtable if number of elements exceeds a size of hashtable
        if (numberOfEntrys >= 10 * size) {
            resize(2 * size);
        }

        //putting keys and values in hashtable
        int i = hashFunction(key);
        Node x = hashTable[i];
        Node p = null;
        while (x != null) {
            if (key.equals(x.key)) {
                x.value = value;
                return;
            }
            p = x;
            x = x.next;
        }

        if (p == null) {
            hashTable[i] = new Node(key, value, null);
            numberOfEntrys++;
        } else {
            p.next = new Node(key, value, null);
            numberOfEntrys++;
        }
    }

    //a function by which you can iterate through keys of hashtable
    public Iterable<K> keys() {
        List<K> list = new ArrayList<K>();
        for (int i = 0; i < size; i++) {
            Node<K, V> x = hashTable[i];
            while (x != null) {
                if (x.key != null) {
                    list.add(x.key);
                }
                x = x.next;
            }
        }
        return list;
    }

    //resizing hashtable by rehashing all the keys
    private void resize(int capacity) {
        hmap<K, V> newMap = new hmap<K, V>(capacity);
        for (int i = 0; i < size; i++) {
            Node<K, V> x = hashTable[i];
            while (x != null) {
                K newKey = x.key;
                if (newKey != null) {
                    newMap.put(newKey, this.get(newKey));
                }
                x = x.next;
            }
        }
        this.size = newMap.size;
        this.numberOfEntrys = newMap.numberOfEntrys;
        this.hashTable = newMap.hashTable;
    }
}

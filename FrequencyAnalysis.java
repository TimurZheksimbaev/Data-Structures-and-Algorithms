import java.util.*;

//Timur Zheksimbaev

/** Given a text you should count frequency of each word and output them in lexicographical order.
 * With your implementation of hashmap */

/* input
6
to be or not to be
*/

/* output
be 2
to 2
not 1
or 1
*/

public class FrequencyAnalysis {


    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        map<String, Integer> map = new map<>();

        String m = in.nextLine();
        int n = Integer.parseInt(m);

        List<String> inputWords = new LinkedList<>();
        List<Integer> inputAmounts = new LinkedList<>();

        String str1 = in.nextLine();
        String[] str = str1.split(" ");


        for (String w : str) {
            Integer amount = map.get(w);
            if (amount == null) {
                amount = 1;
            } else {
                amount++;
            }
            map.put(w, amount);
        }

        String[] words = new String[map.size()];
        int[] words2 = new int[map.size()];
        int[] amounts = new int[map.size()];
        int[] amounts2 = new int[map.size()];

        for (String key : map.keySet()) {
            inputWords.add(key);
            inputAmounts.add(map.get(key));
        }
        for (int i = 0; i < inputWords.size(); i++) {
            words[i] = inputWords.get(i);
            words2[i] = i;
            amounts[i] = inputAmounts.get(i);
            amounts2[i] = i;
        }


        Comparator<myEntry<String, Integer>> comparator = (e1, e2) -> {
            int compare = e2.getValue().compareTo(e1.getValue());
            if (compare == 0) {
                compare = e1.getKey().compareTo(e2.getKey());
            }
            return compare;
        };

        List<myEntry<String, Integer>> list = new ArrayList<>(map.entrySet());


        Collections.sort(list, comparator);
        list.forEach(x -> System.out.println(x.toString()));
    }
}


class myEntry<K, V> {
    private K key;
    private V value;

    public myEntry(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }
    public String toString() {
        return getKey() + " " + getValue();
    }
}

class map<K, V> {
    private int size;
    private int DEFAULT_CAPACITY = 1000;
    private myEntry<K, V>[] values = new myEntry[DEFAULT_CAPACITY];


    public V get(K key) {
        for (int i = 0; i < size; i++) {
            if (values[i] != null) {
                if (values[i].getKey().equals(key)) {
                    return values[i].getValue();
                }
            }
        }
        return null;
    }

    public void put(K key, V value) {
        boolean insert = true;
        for (int i = 0; i < size; i++) {
            if (values[i].getKey().equals(key)) {
                values[i].setValue(value);
                insert = false;
            }
        }
        if (insert) {
            ensureCapa();
            values[size++] = new myEntry<K, V>(key, value);
        }
    }

    private void ensureCapa() {
        if (size == values.length) {
            int newSize = values.length * 2;
            values = Arrays.copyOf(values, newSize);
        }
    }

    public int size() {

        return size;
    }

    public void remove(K key) {
        for (int i = 0; i < size; i++) {
            if (values[i].getKey().equals(key)) {
                values[i] = null;
                size--;
                condenseArray(i);
            }
        }
    }

    private void condenseArray(int start) {
        for (int i = start; i < size; i++) {
            values[i] = values[i + 1];
        }
    }

    public Set<K> keySet() {
        Set<K> set = new HashSet<K>();
        for (int i = 0; i < size; i++) {
            set.add(values[i].getKey());
        }
        return set;
    }

    public Set<myEntry<K,V>> entrySet() {
        Set<myEntry<K, V>> s = new HashSet<>();
        for (int ind = 0; ind < size; ind++) {
            s.add(values[ind]);
        }
        return s;
    }

}
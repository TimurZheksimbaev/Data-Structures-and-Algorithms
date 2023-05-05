import java.util.*;

/** Given two text you should print all unique words from second text, which are not present in first.
 * With your implementation of hashmap
 * Print words in the same order as they appear in the second text !!! */

/* input
6
to be or not to be
6
to study or not to study
*/

/* output
1
study
*/

public class SpellChecker {



    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);

        String n1 = in.nextLine();
        int n = Integer.parseInt(n1);

        String str1 = in.nextLine();
        String[] text1 = str1.split(" ");

        String m1 = in.nextLine();
        int m = Integer.parseInt(m1);

        String str2 = in.nextLine();
        String[] text2 = str2.split(" ");


        hashMap<String, Integer> map1 = new hashMap<>();
        hashMap<String, Integer> map2 = new hashMap<>();

        for (String s1 : text1) {
            Integer amount = map1.get(s1);
            if (amount == null) {
                amount = 1;
            } else {
                amount++;
            }
            map1.put(s1, amount);
        }

        List<String> words = new LinkedList<>();

        for (String s2 : text2) {
            if (map1.get(s2) == null) {
                words.add(s2);
                map1.put(s2, 0);
            }
//            Integer amount = map2.get(s2);
//            if (amount == null) {
//                amount = 1;
//            } else {
//                amount++;
//            }
//            map2.put(s2, amount);
        }


//        for (String s : map2.keySet()) {
//            if (map1.get(s) == null) {
//                words.add(s);
//            }
//        }
        System.out.println(words.size());
        words.forEach(System.out::println);
    }
}

class MyKeyValueEntry<K, V> {
    private K key;
    private V value;

    public MyKeyValueEntry(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }
    public V getValue() {
        return value;
    }

    public void setKey(K key) {
        this.key = key;
    }
    public void setValue(V value) {
        this.value = value;
    }

    // hashCode & equals
}
class MyMapBucket {
    private List<MyKeyValueEntry> entries;

    public MyMapBucket() {
        if(entries == null) {
            entries = new LinkedList<>();
        }
    }

    public List<MyKeyValueEntry> getEntries() {
        return entries;
    }

    public void addEntry(MyKeyValueEntry entry) {
        this.entries.add(entry);
    }

    public void removeEntry(MyKeyValueEntry entry) {
        this.entries.remove(entry);
    }
}
class hashMap<K, V> {
    private int CAPACITY = 5000000;
    private MyMapBucket[] bucket;
    private int size = 0;

    public hashMap() {
        this.bucket = new MyMapBucket[CAPACITY];
    }
    private int getHash(K key) {
        return Math.abs(key.hashCode() % CAPACITY);
    }

    private MyKeyValueEntry getEntry(K key) {
        int hash = getHash(key);
        for (int i = 0; i < bucket[hash].getEntries().size(); i++) {
            MyKeyValueEntry myKeyValueEntry = bucket[hash].getEntries().get(i);
            if(myKeyValueEntry.getKey().equals(key)) {
                return myKeyValueEntry;
            }
        }
        return null;
    }
    public void put(K key, V value) {
        if(containsKey(key)) {
            MyKeyValueEntry entry = getEntry(key);
            entry.setValue(value);
        } else {
            int hash = getHash(key);
            if(bucket[hash] == null) {
                bucket[hash] = new MyMapBucket();
            }
            bucket[hash].addEntry(new MyKeyValueEntry<>(key, value));
            size++;
        }
    }

    public V get(K key) {
        return containsKey(key) ? (V) getEntry(key).getValue() : null;
    }

    public boolean containsKey(K key) {
        int hash = getHash(key);
        return !(Objects.isNull(bucket[hash]) || Objects.isNull(getEntry(key)));
    }

    public void delete(K key) {
        if(containsKey(key)) {
            int hash = getHash(key);
            bucket[hash].removeEntry(getEntry(key));
            size--;
        }
    }
    public int size() {
        return size;
    }
}
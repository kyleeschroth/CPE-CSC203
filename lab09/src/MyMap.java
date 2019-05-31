import java.util.List;
import java.util.ArrayList;

// You may not use any library classes other than List and ArrayList,
// above, to implement your map.  If the string "java." or "javax."
// occurs in this file after this, your submission will be rejected.


// DO NOT MODIFY ANYTHING IN THIS FILE UNTIL THIS LINE!
// Fill in you class after this.  Your submission will be 
// rejected by checkgit and by the autograder
// if you modify anything before this comment block.

/**
 * This class implements a map from any key type K to any value
 * type V.  The K type must have a valid equals() and hashCode() 
 * implementations.  MyMap<K, V> supports a public constructor that
 * takes a single int argument, giving the number of buckets for the
 * internal hashtable.
 * <p>
 * MyMap<K, V> supports a get() operation that takes
 * a key value, and delivers null if the key is not found, or a value
 * of type V (or a descendant of V) if the key is found.  The return type
 * of get() is V.
 * <p>
 * MyMap<K, V> further supports a put() operation that takes a key and a value,
 * in that order.  The value is associated with that key value, replacing any
 * other value that might have been stored at that key.
 * <p>
 * MyMap<K, V> also supports a method called getEntries() that takes no
 * arguments, and returns a List<MyMapEntry<K, V>> containing all of the
 * entries currently in the map.  MyMapEntry<K, V> has public final fields 
 * called "key" and "value".  
 * <p>
 * Finally, MyMap<K, V> supports a debugging method called getBuckets().
 * It delivers a List<List<MyMapEntry<K, V>>>, with one entry for each 
 * bucket in the internal hash table.  Because this is just a debugging
 * method, it's OK to return internal data structures; MyMap<K, V> needen't
 * make a defensive copy.  (A defensive copy is when you make a copy of
 * a data structure and return the copy, so a caller can't modify your
 * internal data structures).
 * <p>
 * All of the above methods are public.
 */
public class MyMap<K, V>{
	private List<List<MyMapEntry<K, V>>> buckets; 
	private int numbuckets; 

	public MyMap(int numbuckets){
		this.numbuckets = numbuckets; 
		this.buckets = new ArrayList<>(numbuckets); 
		while (buckets.size() < numbuckets){
			buckets.add(new ArrayList<>()); 
		}
	}

	public V get(K key) {
		int code = key.hashCode() % numbuckets;
		if (code < 0) {code += numbuckets;}
		List<MyMapEntry<K, V>> entry = buckets.get(code);
		for (int i = 0; i < entry.size(); i++) {
			if (entry.get(i).key.equals(key)) {
				return entry.get(i).value;
			}
		}
		return null;
	}


	public void put(K key, V value) {
		MyMapEntry<K, V> add = new MyMapEntry<K, V>(key, value);
		int code = key.hashCode() % numbuckets;
		if (code < 0) {code += numbuckets;}
		boolean added = false;
		List<MyMapEntry<K, V>> entry = buckets.get(code);
		for (int i = 0; i < entry.size(); i++) {
			if (entry.get(i).key.equals(key)) {
				entry.remove(i);
				entry.add(i, add);
				added = true;
			}
		}
		if (!added) {entry.add(add);}		
	}


	public List<MyMapEntry<K,V>> getEntries() {
		List<MyMapEntry<K, V>> stuff = new ArrayList<>(); 
		for (List<MyMapEntry<K, V>> map : buckets){
			if (!map.isEmpty()){
				stuff.addAll(map); 
			}
		}
		return stuff;
	}

	public List<List<MyMapEntry<K,V>>> getBuckets() {
        return buckets;
    }



}



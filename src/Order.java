import java.util.Comparator;

public class Order<K> implements Comparator<K>{
	@SuppressWarnings("unchecked")
	public int compare(K key1, K key2){
		return ((Comparable<K>)key1).compareTo(key2);
	}
}


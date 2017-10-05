
public interface PriorityQueue<K, V> {
	//view the smallest element in the queue
	public Entry<K, V> min();
	//remove the smallest element from the queue
	public Entry<K, V> removeMin();
	//return the number of elements in the priority queue
	public int size();
	//return whether or not the priority queue has any elements
	public boolean isEmpty();
	//insert a new entry into the priority queue
	public Entry<K, V> insert( K key, V value );
}

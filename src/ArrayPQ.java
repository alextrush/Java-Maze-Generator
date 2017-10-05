import java.util.Random;
public class ArrayPQ<K, V> implements PriorityQueue<K, V> {

	protected TreeComplete<Entry<K,V>> heap;
	protected java.util.Comparator<K> cmp;
	
	public ArrayPQ(){
		heap = new TreeComplete<Entry<K, V>>();
		cmp = new Order<K>();
	}
	
	protected static class HeapEntry<K, V> implements Entry<K, V>{
		private K key;
		private V value;
		
		public HeapEntry( K key, V value ){
			this.key = key;
			this.value = value;
		}
		
		public K key(){
			return key;
		}
		
		public V value(){
			return value;
		}
		
		public String toString(){
			return "[" + key + ", " + value + "]";
		}
	}
	
	public Entry<K, V> min() {
		if ( isEmpty() ){
			return null;
		}
		return heap.root().elem();
	}

	public Entry<K, V> removeMin() {
		if ( isEmpty() ){
			return null;
		}
		Entry<K, V> temp = heap.root().elem();
		if ( heap.size() == 1 ){
			//just remove the single node when the heap only has one element
			heap.remove();
		} else {
			//remove the last node in the heap and place 
			//that element in the root position of the tree
			heap.replace( heap.root(), heap.remove() );
			downHeap( heap.root() );
		}
		return temp;
	}

	public int size() {
		return heap.size();
	}

	public boolean isEmpty() {
		return heap.isEmpty();
	}

	public Entry<K, V> insert(K key, V value) {
		Entry<K, V> newEntry = new HeapEntry<K, V>( key, value );
		Pos<Entry<K, V>> pos = heap.add( newEntry );
		upHeap( pos );
		return newEntry;
	}

	protected void downHeap( Pos<Entry<K, V>> oldNode ){
		//don't down heap when the node is null
		if ( oldNode == null ){
			return;
		}
		//put the newly inserted node on top of the heap
		//System.out.println( this );
		//oldNode = heap.root();
		//the node that will be compared to
		Pos<Entry<K,V>> cmpNode;
		//keep swapping down until we find an external node
		while ( heap.isInternal( oldNode ) ){
			//check which child is smaller
			if ( !heap.hasRight( oldNode ) ){
				//there is no right child. the left child is the only option
				cmpNode = heap.left( oldNode );
			} else if ( cmp.compare( heap.left( oldNode ).elem().key(), heap.right( oldNode ).elem().key() ) < 0 ){
				//the left child is smaller. set the comparison node to the left child
				cmpNode = heap.left( oldNode );
			} else {
				//the right child is smaller than or equal to current node
				cmpNode = heap.right( oldNode );
			}
			//check if the compared to node is smaller than the newly added node
			if ( cmp.compare( oldNode.elem().key(), cmpNode.elem().key() ) <= 0 ){
				return;
			}
			//the new node is greater than the child element so it needs to move down
			swap( oldNode, cmpNode );
			// move the node reference down to the position it has been swapped with
			oldNode = cmpNode;
		}
	}
		
	protected void upHeap( Pos<Entry<K, V>> pos ){
		//don't upHeap when the passed position is invalid
		if ( pos == null ){
			return;
		}
		Pos<Entry<K, V>> parent;
		while( !heap.isRoot( pos ) ){
			parent = heap.parent(pos);
			if ( cmp.compare( parent.elem().key(), pos.elem().key() ) < 0 ){
				return;
			}
			swap( parent, pos );
			pos = parent;
		}
	}
	
	protected void swap( Pos<Entry<K, V>> elem1, Pos<Entry<K, V>> elem2 ){
		//continue no further if the positions are not compatible
		if ( elem1 == null || elem2 == null ){
			return;
		}
		//grab the element from the first position
		Entry<K,V> temp = elem1.elem();
		//put the element of the second position into the first position
		heap.replace( elem1, elem2.elem() );
		//put the element of the first position into the second position
		heap.replace( elem2, temp );
	}

	public String toString(){
		return heap.toString();
	}
	
	public static void main(String[] args) {
		int len = 20000000;
		ArrayPQ<Integer, Integer> pq = new ArrayPQ<Integer, Integer>();
		int[] num = new int[len];
		Random gen = new Random();
		int rand;
		long time1, time2, time3;
		time1 = System.currentTimeMillis();
		for ( int i = 0 ; i < len ; i++ ){
			rand = gen.nextInt( 60 ) + 1;
			pq.insert(rand, rand);
			num[i] = rand;
		}
		while ( !pq.isEmpty() ){
			pq.removeMin();
		}
		time2 = System.currentTimeMillis();
		int temp;
		for( int i = 0 ; i < len ; i++ ){
			for( int j = i + 1 ; j < len ; j++ ){
				if ( num[i] > num[j] ){
					temp = num[i];
					num[i] = num[j];
					num[j] = temp;
				}
			}
		}
		time3 = System.currentTimeMillis();
		System.out.println( "Bubble Sort: " + (time3 - time2) + "\n Heap Sort: " + (time2 - time1) );
	}

}

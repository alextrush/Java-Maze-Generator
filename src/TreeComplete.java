public class TreeComplete<H> extends ArrayBT<H> implements CompleteBinaryTree<H> {

	public TreeComplete(){
		super();
	}
	
	public Pos<H> add( H elem ) {
		int i = size() + 1;
		IndexPos<H> newPos = new IndexPos<H>( elem, i );
		heap.add(i, newPos);
		return newPos;
	}

	public H remove() {
		if ( isEmpty() ){
			System.out.println( "There is no element to be removed here." );
		}
		return heap.remove( size() ).elem();
	}

	public static void main(String[] args) {
		TreeComplete<Integer> numbers = new TreeComplete<Integer>();
		for ( int i = 1 ; i < 20 ; i ++ ){
			System.out.println( numbers.isEmpty() );
			numbers.add( i );
		}
		System.out.println(numbers.isEmpty());
		System.out.println(numbers);
		Pos<Integer> root = numbers.root();
		Pos<Integer> left = numbers.left(root);
		Pos<Integer> right = numbers.right(root);
		System.out.println( root );
		System.out.println( left );
		System.out.println( right );
		while( numbers.hasLeft(left) ){
			left = numbers.left( left );
			System.out.println( left );
		}
		System.out.println();
		while( numbers.hasRight(right) ){
			right = numbers.right( right );
			System.out.println( right );
		}
		System.out.println( numbers.children( numbers.right( root ) ) );
		System.out.println( numbers.isRoot( root ) );
		System.out.println( numbers.isRoot( left ) );
		System.out.println( numbers.parent( right ) );
		System.out.println( numbers.size() );
		System.out.println( numbers.remove() );
		System.out.println( numbers.remove() );
		System.out.println( numbers.remove() );
		System.out.println( numbers.remove() );
		System.out.println( numbers.remove() );
	}
}
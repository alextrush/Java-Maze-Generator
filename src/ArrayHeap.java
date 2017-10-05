import java.util.Comparator;
import java.util.Random;
public class ArrayHeap<H> extends TreeComplete<H> implements CompleteBinaryTree<H> {

	protected Comparator<H> cmp;
	public ArrayHeap(){
		super();
		cmp = new Order<H>();
	}
	
	public Pos<H> add( H elem ){
		int i = size() + 1;
		IndexPos<H> node = new IndexPos<H>( elem, i );
		heap.add( node );
		upHeap( node );
		return node;
	}
	
	public H remove(){
		if ( isEmpty() ){
			System.out.println( "There is nothing to remove! >.<" );
		}
		H elem = heap.get( size() ).elem();
		return elem;
	}
	
	protected void upHeap( Pos<H> pos ){
		//don't upHeap when the passed position is invalid
		if ( checkPos(pos) == null ){
			return;
		}
		Pos<H> parent;
		while( !isRoot( pos ) ){
			parent = parent(pos);
			if ( cmp.compare( parent.elem(), pos.elem() ) < 0 ){
				return;
			}
			swap( parent, pos );
			pos = parent;
		}
	}
	
	protected void swap( Pos<H> elem1, Pos<H> elem2 ){
		//cast positions to a usable state
		IndexPos<H> pos1 = checkPos( elem1 );
		IndexPos<H> pos2 = checkPos( elem2 );
		//continue no further if the positions are not compatible
		if ( pos1 == null || pos2 == null ){
			return;
		}
		//grab the element from the first position
		H temp = elem1.elem();
		//put the element of the second position into the first position
		replace( elem1, elem2.elem() );
		//put the element of the first position into the second position
		replace( elem2, temp );
	}
	
	public static void main(String[] args) {
		ArrayHeap<Integer> heap = new ArrayHeap<Integer>();
		Random gen = new Random();
		int num;
		for ( int i = 1 ; i < 16 ; i++ ){
			num = gen.nextInt( 100 );
			heap.add( num );
		}
		System.out.println( heap );
	}

}

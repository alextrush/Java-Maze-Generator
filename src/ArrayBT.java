import java.util.ArrayList;
import java.util.Iterator;
public class ArrayBT<H> implements BinaryTree<H> {

	protected ArrayList<IndexPos<H>> heap;

	public ArrayBT(){
		heap = new ArrayList<IndexPos<H>>();
		heap.add( new IndexPos<H>( null, 0) );
	}
	
	protected IndexPos<H> checkPos( Pos<H> pos ){
		if ( pos instanceof IndexPos ){
			return (IndexPos<H>)pos;
		} else {
			System.out.println( "You sent me the wrong position!" );
			return null;
		}
	}
	
	public boolean hasLeft( Pos<H> pos ) {
		IndexPos<H> check = checkPos( pos );
		if ( check == null ){
			return false;
		} else {
			return check.index() * 2 <= size();
		}
	}

	public boolean hasRight( Pos<H> pos ) {
		IndexPos<H> check = checkPos( pos );
		if ( check == null ){
			return false;
		} else {
			return check.index() * 2 + 1 <= size();
		}
	}

	public Pos<H> left( Pos<H> pos ) {
		IndexPos<H> check = checkPos( pos );
		if ( !hasLeft( pos ) ){
			return null;
		} else {
			return heap.get(check.index()*2);
		}
	}

	public Pos<H> right( Pos<H> pos ) {
		IndexPos<H> check = checkPos( pos );
		if ( !hasRight( pos ) ){
			return null;
		} else {
			return heap.get(check.index()*2 +1);
		}
	}

	public int size() {
		return heap.size() - 1;
	}

	public boolean isEmpty() {
		return size() <= 0;
	}

	public boolean isInternal(Pos<H> pos) {
		return hasLeft( pos );
	}

	public boolean isExternal(Pos<H> pos) {
		return !isInternal( pos );
	}

	public boolean isRoot(Pos<H> pos) {
		IndexPos<H> check = checkPos( pos );
		if ( check == null ){
			return false;
		} else {
			return check.index() == 1;
		}
	}

	public Pos<H> root() {
		if ( isEmpty() ){
			System.err.println("There are no elements in this heap. Therefore there is no root.");
			return null;
		}
		return heap.get( 1 );
	}

	public Pos<H> parent( Pos<H> pos ){
		IndexPos<H> check = checkPos( pos );
		if ( check == null ){
			return null;
		} else {
			return heap.get( check.index() / 2 );
		}
		
	}
	
	public Iterable<Pos<H>> children(Pos<H> pos) {
		IndexPos<H> parent = checkPos( pos );
		if ( parent == null ){
			return null;
		}
		DoubleList<Pos<H>> children = new DoubleList<Pos<H>>();
		if ( hasLeft( pos ) ){
			children.addLast( heap.get( parent.index() * 2 ) );
		}
		if ( hasRight( pos ) ){
			children.addLast( heap.get( parent.index() * 2 + 1 ) );
		}
		return children;
	}

	public H replace(Pos<H> pos, H elem) {
		IndexPos<H> temp = checkPos( pos );
		if ( temp == null ){
			return null;
		}
		return temp.setElem( elem );
	}

	public Iterator<H> iterator() {
		ArrayList<H> list = new ArrayList<H>();
		Iterator<IndexPos<H>> iter = heap.iterator();
		//skip the first element
		iter.next();
		while ( iter.hasNext() ){
			list.add( iter.next().elem() );
		}
		return list.iterator();
	}

	public Iterable<Pos<H>> positions() {
		ArrayList<Pos<H>> list = new ArrayList<Pos<H>>();
		Iterator<IndexPos<H>> iter = heap.iterator();
		//skip the first element
		iter.next();
		while ( iter.hasNext() ){
			list.add( iter.next() );
		}
		return list;
	}

	public String toString(){
		Iterator<H> iter = iterator();
		ArrayList<H> list = new ArrayList<H>();
		while (iter.hasNext()){
			list.add( iter.next() );
		}
		return list.toString();
	}
}
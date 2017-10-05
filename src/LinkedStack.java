public class LinkedStack<S> implements Stack<S> {

	protected int size;
	protected SNode<S> top;
	
	public LinkedStack (){
		top = new SNode<S>(null, null);
		size = 0;
	}
	
	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return size <= 0;
	}

	public S top(){
		if ( isEmpty() ){
			System.out.println("This stack is empty.");
			return null;
		} else {
			return top.elem();
		}
	}
	
	public S pop(){
		if ( isEmpty() ){
			System.out.println("This stack is empty.");
			return null;
		} else {
			S temp = top.elem();
			if ( top.next() != null) {
				top = top.next();
			} else {
				top = null;
			}
			size--;
			return temp;
		}
	}
	
	public void push( S elem ){
		SNode<S> node = new SNode<S>( elem, top );
		top = node;
		size++;
	}
	
	public String toString(){
		if ( isEmpty() ){
			return "{}";
		}
		String tempStr = "{" + top.elem();
		SNode<S> tempNode = top;
		while ( tempNode.next().elem() != null ){
			tempNode = tempNode.next();
			tempStr += ", " + tempNode.elem();
		}
		return tempStr + "}";
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Stack<String> words = new LinkedStack<String>();
		System.out.println(words);
		words.push("Apple");
		System.out.println(words);
		words.push("Banana");
		System.out.println(words);
		System.out.println( words.top() );
		System.out.println( "Now popping out: " + words.pop() );
		System.out.println(words);
		words.push("Strawberry");
		System.out.println(words);
		words.pop();
		words.pop();
		words.pop();
		System.out.println(words);
	}
}
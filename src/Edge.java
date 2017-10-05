public class Edge {
	protected Vertex start, end;
	protected int weight;
	
	public Edge( Vertex start, Vertex end ){
		this.start = start;
		this.end = end;
		weight = 1;
	}
	
	public Edge( Vertex start, Vertex end, int weight ){
		this.start = start;
		this.end = end;
		this.weight = weight;
	}
	
	public int weight(){
		return weight;
	}
	
	public Vertex start(){
		return start;
	}
	
	public Vertex end(){
		return end;
	}
	public String toString(){
		return "" + start + " -> " + end;
		//return ""+weight;
	}
}

import java.util.ArrayList;
import java.util.Random;

public class MazeGen {
	//holds a list of vertices which holds a list of vertices which each vertex is adjacent to
	protected ArrayList<DoubleList<Edge>> adjList;
	
	//holds several separate priority queues to hold potential edges to add to the tree
	protected ArrayList<ArrayPQ<Integer, Edge>> edgeList;
	
	//holds those edges which lie in each tree
	protected ArrayList<DoubleList<Edge>> trees;
	protected DoubleList<Edge> all;

	//holds all the vertices
	protected Vertex[] vertices;
	protected int numVert, numX, numY, numTrees, maxWeight;
	
	public MazeGen( int numX, int numY ){
		adjList = new ArrayList<DoubleList<Edge>>();
		edgeList = new ArrayList<ArrayPQ<Integer, Edge>>();
		vertices = new Vertex[numX*numY];
		trees = new ArrayList<DoubleList<Edge>>();
		all = new DoubleList<Edge>();
		this.numX = numX;
		this.numY = numY;
		numVert = 0;
		numTrees = 0;
		maxWeight = 100;
	}
	

	public void addAdj( Vertex start, int tree ){
		//loop through the adjacency list until the appropriate vertex is found
		for( Edge edge : adjList.get( start.index() ) ){
			if ( !edge.end().inTree() || !edge.start().inTree() ){
				edgeList.get( tree ).insert(edge.weight(), edge );
			}
		}
	}
	
	protected Edge addEdge( int tree ){
		//pull edge off the heap
		if( edgeList.get( tree ) == null || edgeList.get( tree ).isEmpty() ){
			return null;
		}
		Edge newEdge;
		//remove from the heap until an edge is found which has one end not in the tree and only while there are edges in the heap
		do{
			newEdge = edgeList.get(tree).removeMin().value();
		} while ( newEdge.start().inTree() && newEdge.end().inTree() && !edgeList.get( tree ).isEmpty() );
		if ( newEdge.start().inTree() && newEdge.end().inTree() ){
			return null;
		}
		//add it to the list of edges
		trees.get(tree).addLast( newEdge );
		//set the vertex to inTree
		newEdge.start().setInTree( true );
		newEdge.end().setInTree( true );
		//add the adjacent edges
		addAdj(newEdge.start(), tree );
		addAdj(newEdge.end(), tree );
		//increase the number of vertices in the tree
		numVert++;
		//return the new edge
		return newEdge;
	}

	//adds a tree by picking a random vertex in the graph
	public void addTree(){
		int randX, randY, vertIndex;
		Random gen = new Random();
		Vertex start, end;
		Edge newEdge = null;
		Pos<Edge> cmp;
		//pick a random tree starting point	
		do{
			do{
				randX = gen.nextInt( numX );
				randY = gen.nextInt( numY );
				vertIndex = randY * numX + randX;
			} while ( vertices[vertIndex].inTree() );
			start = vertices[vertIndex];
			cmp = adjList.get(vertIndex).first();
			end = ( start == cmp.elem().start() ) ? cmp.elem().end() : cmp.elem().start();
			if ( !end.inTree() ){
				newEdge = cmp.elem();
			}
			//only choose an end point if it isn't already in a tree
			do {
				cmp = adjList.get( vertIndex ).next(cmp);
				//choose the appropriate vertex to evaluate
				end = ( start == cmp.elem().start() ) ? cmp.elem().end() : cmp.elem().start();
				if ( !end.inTree() ){
					newEdge = cmp.elem();
				}
			} while ( cmp != adjList.get( vertIndex ).last() && cmp != null );
		} while ( newEdge == null );
		//create a new tree to be stored in a list of edges
		trees.add( new DoubleList<Edge>() );
		edgeList.add( new ArrayPQ<Integer, Edge>() );
		//add a newly created edge to the new tree
		trees.get(numTrees).addLast( newEdge );
		start.setInTree( true );
		newEdge.end().setInTree( true );
		//add the adjacent edges to the heap
		addAdj( start, numTrees );
		addAdj( newEdge.end(), numTrees );
		numTrees++;
		numVert+=2;
	}
	
	protected void calcAdj(){
		Random gen = new Random();
		Edge temp;
		for ( int vertex = 0 ; vertex < numX*numY ; vertex++){
			//add the edge to the right
			if ( vertex % numX < numX - 1){
				temp = new Edge( vertices[vertex], vertices[vertex+1], gen.nextInt( maxWeight ) + 1 );
				adjList.get(vertex).addLast( temp );
				adjList.get(vertex+1).addLast( temp );
				all.addLast( temp );
			}
			// add the edge below
			if( vertex / numX < numY - 1 ){	
				temp = new Edge( vertices[vertex], vertices[vertex+numX], gen.nextInt( maxWeight ) + 1 );
				adjList.get(vertex).addLast( temp );
				adjList.get(vertex + numX).addLast( temp );
				all.addLast( temp );
			}
	 	}
	}
	
	protected void createVertices( int offX, int offY, int width, int height ){
		int count = 0;
		double cellWidth = (width - 2.0*offX) / (numX - 1);
		double cellHeight = (height - 2.0*offY)/ (numY - 1);
		for( int j = 0 ; j < numY ; j++ ){
			for( int i = 0 ; i < numX ; i++ ){
				vertices[count] = new Vertex( i*cellWidth+offX, j*cellHeight + offY, count );
				adjList.add( new DoubleList<Edge>() );
				count++;
			}
		}
		calcAdj();
	}
	
	public void calcMaze( int numTrees ){
		int treeNum = 0;
		for ( int i = 0 ; i < numTrees ; i++ ){
			addTree();
		}
		//add a starting vertex for all the trees there are
		while( numVert < vertices.length ){
		//increase the tree number
			if( numTrees > 1 ){
				if( treeNum == numTrees - 1 ){
					treeNum = 0;
				} else {
					treeNum++;
				}
			}
			//add adjacent edges if an edge is found
			if ( !edgeList.get( treeNum ).isEmpty() ){
				addEdge(treeNum);
			}
		}
	}
	
	public ArrayList<DoubleList<Edge>> getTrees(){
		return trees;
	}
}
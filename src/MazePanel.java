import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Random;

@SuppressWarnings("serial")
public class MazePanel extends JPanel{
	
	protected int width, height, xOffset, yOffset, numX, numY, currTree, totVert;
	
	protected MazeGen mazeGen;
	
	protected DoubleList<Entry<Integer,Line2D>> drawing;
	protected boolean inColor;
	protected ArrayList<Color> colors;
	
	protected class LineEntry<K, V> implements Entry<K,V>{
		protected K key;
		protected V value;
		public LineEntry( K key, V value ){
			this.key = key;
			this.value = value;
		}
		public K key(){
			return key;
		}
		public V value(){
			return value;
		}
	}
	
	public MazePanel( int width, int height, int xOffset, int yOffset ){
		super();
		setPreferredSize( new Dimension( width, height ) );
		setBackground( Color.gray );
		this.width = width;
		this.height = height;
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		colors = new ArrayList<Color>();
		clear();
		currTree = 0;
	}
	
	public void addColours( int numColours ){
		colors = new ArrayList<Color>();
		for ( int i = 0 ; i < numColours ; i++ ){
			colors.add( randColor() );
		}
	}
	
	public Color randColor(){
		int[] color = new int[3];
		for ( int i = 0 ; i < 3 ; i++ ){
			color[i] = randomNum( 110, 200 );
		}
		return new Color( color[0], color[1], color[2] );
	}
	
	public int randomNum( int lower, int upper ){
		return (new Random()).nextInt( upper - lower ) + lower;
	}
	
	public void addGenerator( int type, int numX, int numY ){
		switch ( type ){
		case 1:
			this.numX = numX;
			this.numY = numY;
			mazeGen = new MazeGen( numX, numY );
			mazeGen.createVertices( xOffset, yOffset, width, height );
		}
	}
	
	//adds extra tree spawning points
	public void addTree(){
		mazeGen.addTree();
	}
	
	//clears the list which the panel uses to know what to draw
	public void clear(){
		totVert = 0;
		drawing = new DoubleList<Entry<Integer,Line2D>>();
	}

	public void createMaze( int numTrees ){
		//System.out.println( mazeGen.numTrees() );
		//mazeGen.refreshGen(xOffset, yOffset, width, height);
		//clear();
		mazeGen.calcMaze( numTrees );
	}
	
	public void drawMaze( int numTree ){
		ArrayList<DoubleList<Edge>> trees = mazeGen.getTrees();
		int i = 0;
		totVert = 0;
		while ( totVert < numX * numY - numTree ){
			i = addDrawEdge( trees, i, (numTree == 1) );
		}
		repaint();
		currTree = 0;
	}
	
	public int drawByStep( int numTree ){
		int i = currTree;
		ArrayList<DoubleList<Edge>> trees = mazeGen.getTrees();
		if ( totVert < numX * numY ){
			i = addDrawEdge( trees, i, (numTree == 1) );
			repaint();
			currTree = i;
			return 0;
		} else {
			currTree = 0;
			return -1;
		}
	}
	
	public int addDrawEdge( ArrayList<DoubleList<Edge>> trees, int i, boolean single){
		DoubleList<Edge> tree;
		Pos<Edge> edge;
		if ( i == trees.size() - 1 ){
			i = 0;
		} else {
			i++;
		}
		tree = trees.get( i );
		if ( !tree.isEmpty() ){
			edge = tree.first();
			drawing.addLast( new LineEntry<Integer,Line2D>( (single) ? edge.elem().weight() : i, 
										new Line2D.Double( 	edge.elem().start().point().getX(), 
															edge.elem().start().point().getY(), 
															edge.elem().end().point().getX(), 
															edge.elem().end().point().getY() ) ) );
			tree.remove( edge );
			totVert++;
		}
		return i;		
	}
	
	public void paintComponent( Graphics g ){
		super.paintComponent(g);
		Graphics2D canvas = (Graphics2D) g;
		setBackground( Color.white );
		canvas.setStroke( new BasicStroke( 3 ) );
		for( Entry<Integer,Line2D> line : drawing ){
			if( inColor ){
				canvas.setColor( colors.get( line.key() % colors.size() ) );
			} else {
				canvas.setColor( Color.black );
			}
			canvas.draw( line.value() );
		}
	}
	
	public void setColour( boolean inColor ){
		this.inColor = inColor;
	}
}
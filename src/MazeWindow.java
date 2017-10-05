import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.util.Scanner;

@SuppressWarnings("serial")
public class MazeWindow extends JFrame implements ActionListener, ChangeListener{
	
	protected final int WIN_WIDTH = 850, WIN_HEIGHT = 600, MAX_COLORS = 6000,
			MIN_WIDTH = 2, MIN_HEIGHT = 2, PANEL_WIDTH = 400, X_OFF = 20, Y_OFF = 20;
	protected MazePanel grid;
	protected JSlider xVert, yVert, timer, numColor, numTree;
	protected JPanel control, checks;
	protected JButton slow, run;
	protected Timer pace;

	public MazeWindow( int numX, int numY ){
		super();
		setLayout( new BorderLayout() );
		setTitle( "Maze Generator" );
		setSize( WIN_WIDTH + PANEL_WIDTH, WIN_HEIGHT + 2*Y_OFF );
		setDefaultCloseOperation( EXIT_ON_CLOSE );
		createControlPanel( numX, numY );
		add( control, BorderLayout.EAST );
		grid = new MazePanel( WIN_WIDTH, WIN_HEIGHT, X_OFF, Y_OFF );
		add( grid, BorderLayout.CENTER );
		grid.addGenerator( 1, xVert.getValue(), yVert.getValue() );
		setColor();
		setVisible( true );
	}
	
	public void createControlPanel( int numX, int numY ){
		int MAX_TREES = (numX*numY) / 4;
		//create the control panel
		control = new JPanel();
		control.setPreferredSize( new Dimension( PANEL_WIDTH, getHeight() ) );
		control.setLayout( new GridLayout( 7, 1, 5, 10 ) );
		//create a slider to vary the number of vertices on the x axis
		xVert = new JSlider( JSlider.HORIZONTAL, MIN_WIDTH+(numX%10), numX, numX);
		xVert.setBorder( BorderFactory.createTitledBorder( "X Vertices" ) );
		xVert.setPaintTicks( true );
		xVert.setPaintLabels( true );
		xVert.setMajorTickSpacing( (numX-MIN_WIDTH) / 10 );
		xVert.setMinorTickSpacing( 1 );
		xVert.addChangeListener( this );
		control.add( xVert );
		//create a slider to vary the number of vertices on the y axis
		yVert = new JSlider( JSlider.HORIZONTAL, MIN_HEIGHT+(numY%10), numY, numY );
		yVert.setBorder( BorderFactory.createTitledBorder( "Y Vertices" ) );
		yVert.setPaintTicks( true );
		yVert.setPaintLabels( true );
		yVert.setMajorTickSpacing( (numY-MIN_HEIGHT) / 10 );
		yVert.setMinorTickSpacing( 1 );
		yVert.addChangeListener( this );
		control.add( yVert );
		//create a slider to adjust the speed at which the generator runs
		timer = new JSlider( JSlider.HORIZONTAL, 0, 400, 0 );
		timer.setBorder( BorderFactory.createTitledBorder( "Delay" ) );
		timer.setPaintTicks( true );
		timer.setMajorTickSpacing( 40 );
		timer.setPaintLabels( true );
		timer.addChangeListener( this );
		control.add( timer );
		//create a slider to adjust the number of colours available
		numColor = new JSlider( JSlider.HORIZONTAL, 0, MAX_COLORS, MAX_COLORS );
		numColor.setBorder( BorderFactory.createTitledBorder( "Number of Colours" ) );
		numColor.setPaintTicks( true );
		numColor.setPaintLabels( true );
		numColor.setMajorTickSpacing( MAX_COLORS / 8 );
		numColor.addChangeListener( this );
		control.add( numColor );
		//slider to represent the number of trees
		numTree = new JSlider( JSlider.HORIZONTAL, 1, MAX_TREES, MAX_TREES );
		numTree.setBorder( BorderFactory.createTitledBorder( "Number of Trees" ) );
		numTree.setPaintTicks( true );
		numTree.setPaintLabels( true );
		numTree.setMajorTickSpacing( (MAX_TREES-1) / 6 );
		numTree.addChangeListener( this );
		control.add( numTree );
		//a button which draws the graph
		run = new JButton( "Draw Graph" );
		run.addActionListener( this );
		control.add( run );
		checks = new JPanel();
		checks.setLayout( new GridLayout( 1, 2 ) );
		//add a button for drawing it out slowly
		slow = new JButton("Step by Step");
		slow.addActionListener( this );
		control.add( slow );
	}
	
	//creates a new graph without a spanning tree
	public void createGraph(){
		stopDraw();
		grid.addGenerator( 1, xVert.getValue(), yVert.getValue() );
	}
	
	public void paint( Graphics g ){
		super.paint( g );
	}

	//pauses the drawing timer events
	public void stopDraw(){
		if( pace != null ){
			pace.stop();
		}
	}
	
	//listens for button presses and timer ticks
	public void actionPerformed( ActionEvent e ){
		if ( e.getSource() == run || e.getSource() == slow){
			//calculates the maze
			stopDraw();
			grid.clear();
			createGraph();
			grid.createMaze( numTree.getValue() );
			//draws each line of graph at a pace that can be seen
			if ( e.getSource() == slow ){
				pace = new Timer(timer.getValue(), this);
				pace.start();
				grid.drawByStep( numTree.getValue() );
			//draws out the maze all at once
			} else {
				grid.drawMaze( numTree.getValue() );		
			}
		}
		//every time the pace timer ticks, one line is drawn
		if ( e.getSource() == pace ){
			if ( grid.drawByStep( numTree.getValue() ) == -1 ){
				pace.stop();
				grid.clear();
			} 
		}
	}
	
	public void stateChanged( ChangeEvent e ){
		if ( e.getSource() == xVert || e.getSource() == yVert || e.getSource() == numTree ){
			stopDraw();
			createGraph();
		}
		if ( e.getSource() == timer ){
			if( pace != null ){
				pace.setDelay( timer.getValue() );
			}
		}
		if ( e.getSource() == numColor ){
			setColor();
			grid.repaint();
		}
	}
	
	public void setColor(){
		if ( numColor.getValue() == 0 ){
			grid.setColour( false );
		} else {
			grid.setColour( true );
			grid.addColours( numColor.getValue() );
		}		
	}
	
	@SuppressWarnings("unused")
	public static void main( String[] args ){
		int numX, numY;
		System.out.print( "How many X vertices do you wish to see? " );
		numX = ensureInt();
		if ( numX > 900 ){
			numX = reduce( numX );
			System.out.println( "I heard: " + numX );
		}
		System.out.print( "How many Y vertices do you wish to see? " );
		numY = ensureInt();
		if ( numY > 900 ){
			numY = reduce( numY );
			System.out.println( "I heard: " + numY );
		}
		System.out.println( "Now building your maze generator: " + numX + " by " + numY );
		MazeWindow test = new MazeWindow( numX, numY );
	}
	
	public static int ensureInt(){
		Scanner in = new Scanner( System.in );
		int num = 0;
		String temp;
		do{
			temp = in.nextLine();
			try{
				num = Integer.parseInt(temp);
			} catch ( Exception e ){
				System.out.println( "Please ensure that you enter a number greater than 10" );
			}
		} while (num <= 0);
		//in.close();
		return (num<10) ? num*10 : num;
	}
	
	public static int reduce( int num ){
		Random gen = new Random();
		int divisor;
		while (num > 900){
			divisor = gen.nextInt(10) + 5;
			num = num / divisor;
		}
		return num;
	}
}
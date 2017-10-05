import java.awt.*;
public class Vertex {
	protected double posX, posY;
	protected int index;
	protected boolean inTree;
	
	public Vertex( double posX, double posY, int index ){
		this.posX = posX;
		this.posY = posY;
		this.index = index;
		inTree = false;
	}

	public Point.Double point(){
		return new Point.Double( posX, posY );
	}
	
	public boolean inTree(){
		return inTree;
	}
	
	public void setInTree( boolean inTree ){
		this.inTree = inTree;
	}
	
	public int index(){
		return index;
	}
	
	public String toString(){
		return "(" + posX + ", " + posY + ")";
	}
}
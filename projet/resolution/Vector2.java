package pakkuman;


public class Vector2< Type > {
	
	/**
	 * 
	 */
	public Type x;
	public Type y;
	
	
	
	/**
	 * 
	 * @param x
	 * @param y
	 */
	public Vector2( Type x, Type y ) {
		this.x = x;
		this.y = y;
	}
	
	
	
	public String toString() {
		return ( "(" + x + "," + y + ")" );
	}
}

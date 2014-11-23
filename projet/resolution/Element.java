package pakkuman;
/**
 * 
 * Info-F-203 : Projet d’Algorithmique 2 - Évasion du labyrinthe
 * @filename Element.java
 * @author Rodrigue VAN BRANDE
 * @matricule 000362341
 *
 */
import java.awt.Point;

public class Element {
	
	private Point _position = new Point(0,0);
	
	
	
	/**
	 * 
	 * @return
	 */
	public Point getPosition() {
		return _position;
	}
	
	
	
	/**
	 * 
	 * @param position
	 */
	public void setPosition( Point position ) {
		_position = position;
	}
}

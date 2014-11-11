package pakkuman;
/**
 * 
 * Info-F-203 : Projet d’Algorithmique 2 - Évasion du labyrinthe
 * @filename Bonbon.java
 * @author Rodrigue VAN BRANDE
 * @matricule 000362341
 *
 */
import java.awt.Point;

public class Bonbon {
	
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

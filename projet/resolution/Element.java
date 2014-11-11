package pakkuman;
/**
 * 
 * Info-F-203 : Projet d’Algorithmique 2 - Évasion du labyrinthe
 * @filename Element.java
 * @author Rodrigue VAN BRANDE
 * @matricule 000362341
 *
 */

public class Element {
	
	private Vector2<Integer> _position = new Vector2<Integer> (0,0);
	
	
	
	/**
	 * 
	 * @return
	 */
	public Vector2<Integer> getPosition() {
		return _position;
	}
	
	
	
	/**
	 * 
	 * @param position
	 */
	public void setPosition( Vector2<Integer> position ) {
		_position = position;
	}
}

package pakkuman;
/**
 * 
 * Info-F-203 : Projet d’Algorithmique 2 - Évasion du labyrinthe
 * @filename Case.java
 * @author Rodrigue VAN BRANDE
 * @matricule 000362341
 *
 */

public class Case {
	
	private boolean _up    = false,
	                _right = false,
	                _down  = false,
	                _left  = false;
	private State _state = State.Free;

	
	public boolean getUp() {
		return _up;
	}
	public boolean getRight() {
		return _right;
	}
	public boolean getDown() {
		return _down;
	}
	public boolean getLeft() {
		return _left;
	}
	public void setUp( boolean up ) {
		_up = up;
	}
	public void setRight( boolean right ) {
		_right = right;
	}
	public void setDown( boolean down ) {
		_down = down;
	}
	public void setLeft( boolean left ) {
		_left = left;
	}
	public void setState( State state ) {
		_state = state;
	}
	public State getState() {
		return _state;
	}
}

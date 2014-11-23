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
	
	public boolean    top       = false,
	                  right     = false,
	                  down      = false,
	                  left      = false;
	private State state = State.Free;
	
	
	
	/**
	 * Constructeur.
	 */
	Case() {
	}
	
	
	
	/**
	 * Constructeur.
	 * @param top Possibilité d'aller au dessus.
	 * @param right Possiblité d'aller à droite.
	 * @param down Possibilité d'aller en bas.
	 * @param left Possibilité d'aller à gauche.
	 */
	Case( boolean top, boolean right, boolean down, boolean left ) {
		this.top   = top;
		this.right = right;
		this.down  = down;
		this.left  = left;
	}
	
	
	
	public boolean topOpen() {
		return false;
	}
	
	
	
	/**
	 * Indique si il y a la possiblité d'aller dans la direction demandée.
	 * @param direction La direction demandée.
	 * @return Si il est possible d'aller dans la direction.
	 */
	public boolean get( Direction direction ) {
		if ( direction == Direction.Top ) {
			return top;
		}
		else if ( direction == Direction.Right ) {
			return right;
		}
		else if ( direction == Direction.Down ) {
			return down;
		}
		else {
			return left;
		}
	}
	
	
	
	/**
	 * Modifie l'état de la case.
	 * @param state Le nouvel état de la case.
	 */
	public void setState( State state ) {
		this.state = state;
	}
	
	
	
	/**
	 * Renvoie l'état de la case.
	 * @return L'état de la case.
	 */
	public State getState() {
		return state;
	}
	
	
	
	/**
	 * Une énumération représentant une direction.
	 */
	public enum Direction {
		Top,
		Right,
		Down,
		Left
	}
	
	
	
	/**
	 * Une énumération représentant l'état d'une case.
	 */
	public enum State {
		Free,
		Candy,
		Monster,
		End
	}
}

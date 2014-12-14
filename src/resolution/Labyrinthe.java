package pakkuman;
/**
 * 
 * Info-F-203 : Projet d’Algorithmique 2 - Évasion du labyrinthe
 * @filename Labyrinthe.java
 * @author Rodrigue VAN BRANDE
 * @matricule 000362341
 *
 */
import java.awt.Dimension;
import java.awt.Point;

public class Labyrinthe {
	
	private Case[][]  _matrice;
	
	
	
	/**
	 * Constructeur.
	 * @param size La taille du labyrinthe.
	 */
	Labyrinthe( Dimension size ) {
		setSize( size );
	}
	
	
	
	/**
	 * Constructeur.
	 * @param width La longueur du labyrinthe.
	 * @param height La largeur du labyrinthe.
	 */
	Labyrinthe( int width, int height ) {
		setSize( width, height );
	}
	
	
	
	/**
	 * Modifie la taille du labyrinthe.
	 * @param size La taille du labyrinthe.
	 */
	public void setSize( Dimension size ) {
		setSize( size.width, size.height );
	}
	
	
	
	/**
	 * Modifie la taille du labyrinthe.
	 * @param width La longueur du labyrinthe.
	 * @param height La largeur du labyrinthe.
	 */
	public void setSize( int width, int height ) {
		_matrice = new Case[ width ][];
		for ( int x = 0 ; x < _matrice.length; ++x ) {
			_matrice[x] = new Case[ height ];
		}
	}
	
	
	
	/**
	 * Renvoie la taille du labyrinthe.
	 * @return La taille du labyrinthe.
	 */
	public Dimension getSize() {
		return new Dimension( _matrice.length, _matrice[0].length );
	}
	
	
	
	/**
	 * Renvoie la case du labyrinthe à la position demandée.
	 * @param point La position de la case.
	 * @return La case du labyrinthe.
	 */
	public Case get( Point point ) {
		return get( point.x, point.y );
	}
	
	
	
	/**
	 * Renvoie la case du labyrinthe à la position demandée.
	 * @param x L'abscisse de la position.
	 * @param y L'ordonnée de la position.
	 * @return La case du labyrinthe.
	 */
	public Case get( int x, int y ) {
		return _matrice[x][y];
	}
	
	
	
	/**
	 * Place une case à la position indiqué.
	 * @param x L'abscisse de la position.
	 * @param y L'ordonnée de la position.
	 * @param newCase La nouvelle case à poser.
	 */
	public void set( int x, int y, Case newCase ) {
		_matrice[x][y] = newCase;
	}
	
	
	
	public String toString() {
		String string = new String();
		
		// On un carré rempli de +
		
		for ( int x = 0 ; x < getSize().width*2+1; ++x ) {
			string += "+";
			for ( int y = 0 ; y < getSize().height; ++y ) {
				string += "++++";
			}
			string += "\n";
		}
		
		for( int x = 0; x < getSize().width; ++x ) {
			for( int y = 0; y < getSize().height; ++y ) {
				string = change( x, y, getSize().height, string, _matrice[x][y] );
			}
		}
		
		string = string.substring(0,string.length()-1);
		
		return string;
	}



	private static String change( int x, int y, int max_y, String string, Case _case ) {
		int height = (max_y*4) + 2;
		int position = ( height )*( x*2 + 1 ) + ( 2 + (y*4) );
		
		string = string.substring(0,position-1-height) + ( _case.getLeft() ? "   " : "---") + string.substring(position+2-height);
		string = string.substring(0,position-1) + "   " + string.substring(position+2);
		string = string.substring(0,position-1+height) + ( _case.getRight() ? "   " : "---") + string.substring(position+2+height);
		
		string = string.substring(0,position-2) + ( _case.getDown() ? " " : "|") + string.substring(position-1);
		string = string.substring(0,position+2) + ( _case.getUp() ? " " : "|") + string.substring(position+3);
		
		return string;
	}
	
	
	
	public static String replaceCharAt( String string, int position, char character ) {
		return string.substring(0,position) + character + string.substring(position+1);
	}



	public boolean contains( Point point ) {
		return 0 <= point.x && point.x < getSize().width &&
		       0 <= point.y && point.y < getSize().height;
	}
}

package pakkuman;
/**
 * 
 * Info-F-203 : Projet d’Algorithmique 2 - Évasion du labyrinthe
 * @filename Labyrinthe.java
 * @author Rodrigue VAN BRANDE
 * @matricule 000362341
 *
 */

public class Labyrinthe {
	
	private int _width = 0;
	private int _height = 0;
	private boolean[][] _matrice;
	
	
	Labyrinthe( Vector2<Integer> size ) {
		setSize( size );
	}
	
	
	Labyrinthe( int width, int height ) {
		setSize( width, height );
	}
	
	
	public void setSize( Vector2<Integer> size ) {
		setSize( size.x, size.y );
	}
	
	public void setSize( int width, int height ) {
		_width = width;
		_height = height;
		
		_matrice = new boolean[_width*2+1][];
		for ( int i = 0 ; i < _matrice.length; ++i ) {
			_matrice[i] = new boolean[_height*2+1];
		}
	}
	

	public Vector2<Integer> getSize() {
		return new Vector2<Integer>( _width, _height);
	}

	public void set( int x, int y, boolean wall ) {
		_matrice[x][y] = wall;
	}
	
	public boolean get( int x, int y ) {
		return _matrice[x][y];
	}
	
	
	public String toString() {
		String string = new String();
		for ( int y = 0 ; y < _matrice.length; ++y ) {
			for ( int x = 0 ; x < _matrice.length; ++x ) {
				if( _matrice[x][y] ) {
					if( y%2 == 0 ) {
						if( x%2 != 0 ) {
							string += "   ";
						}
						else {
							string += " ";
						}
					}
					else {
						if ( x%2 == 0 ) {
							string += " ";
						}
						else {
							string += "   ";
						}
					}
					
				}
				else {
					if( y%2 == 0 ) {
						if ( x%2 == 0 ) {
							string += "+";
						}
						else {
							string += "---";
						}
					}
					else {
						string += "|";
					}
					
				}
			}
			string += "\n";
		}
		return string;
	}
}

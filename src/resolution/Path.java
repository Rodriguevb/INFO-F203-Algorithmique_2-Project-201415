package pakkuman;
/**
 * 
 * Info-F-203 : Projet d’Algorithmique 2 - Évasion du labyrinthe
 * @filename Path.java
 * @author Rodrigue VAN BRANDE
 * @matricule 000362341
 *
 */
import java.awt.Point;
import java.util.LinkedList;
import java.util.List;

import pakkuman.Case.Direction;

public class Path {
	
	
	Labyrinthe    _labyrinthe   = null;
	Element       _pakkuman     = null;
	List<Element> _monsters     = null;
	List<Element> _candys       = null;
	List<Point>   _path         = null;
	int           _candysOnPath = 0;
	
	
	
	public Path() {
		
	}
	
	
	
	public Path( Labyrinthe labyrinthe, Element pakkuman, List<Element> monsters, List<Element> candys ) {
		set( labyrinthe, pakkuman, monsters, candys );
	}
	
	
	
	public void set( Labyrinthe labyrinthe, Element pakkuman, List<Element> monsters, List<Element> candys ) {
		_labyrinthe = labyrinthe;
		_pakkuman = pakkuman;
		_monsters = monsters;
		_candys = candys;
	}
	
	
	
	public int getSize() {
		return _path.size();
	}
	
	
	
	public int getNumberCandyUsed() {
		return _candysOnPath;
	}
	
	
	
	public List<Point> getPath() {
		
		List<Point> list_start = new LinkedList<Point>();
		list_start.add( _pakkuman.getPosition() );
		
		
		Point start = _pakkuman.getPosition();
		Point up    = new Point( _pakkuman.getPosition().x  , _pakkuman.getPosition().y+1 );
		Point right = new Point( _pakkuman.getPosition().x+1, _pakkuman.getPosition().y   );
		Point down  = new Point( _pakkuman.getPosition().x  , _pakkuman.getPosition().y-1 );
		Point left  = new Point( _pakkuman.getPosition().x-1, _pakkuman.getPosition().y   );
		
		List<Point> list_top   = new LinkedList<Point>( list_start );
		List<Point> list_right = new LinkedList<Point>( list_start );
		List<Point> list_down  = new LinkedList<Point>( list_start );
		List<Point> list_left  = new LinkedList<Point>( list_start );
		
		if ( _labyrinthe.get( start ).top ) {
			list_top.add( up );
			list_top = recursiveSearch( list_top, 0 );
		}
		if ( _labyrinthe.get( start ).right ) {
			list_right.add( right );
			list_right = recursiveSearch( list_right, 0 );
		}
		if ( _labyrinthe.get( start ).down ) {
			list_down.add( down );
			list_down = recursiveSearch( list_down, 0 );
		}
		if ( _labyrinthe.get( start ).left ) {
			list_left.add( left );
			list_left = recursiveSearch( list_left, 0 );
		}
		
		_path = betterPath( list_top, list_right, list_down, list_left, _labyrinthe );
		_path.remove( _path.size()-1 );
		
		return _path;
	}
	
	
	
	private List<Point> recursiveSearch( List<Point> list, int candys) {
		Point position = list.get( list.size() - 1 );
		Point up    = new Point( position.x  , position.y+1 );
		Point right = new Point( position.x+1, position.y   );
		Point down  = new Point( position.x  , position.y-1 );
		Point left  = new Point( position.x-1, position.y   );
		
		System.out.println( list );
		
		if ( list.get( list.size()-1 ) != up ) {
			list.add( up );
			recursiveSearch( list, candys );
			list.remove( list.size()-1 );
		}
		if ( list.get( list.size()-1 ) != right ) {
			list.add( right );
			recursiveSearch( list, candys );
			list.remove( list.size()-1 );
		}
		if ( list.get( list.size()-1 ) != down ) {
			list.add( down );
			recursiveSearch( list, candys );
			list.remove( list.size()-1 );
		}
		if ( list.get( list.size()-1 ) != left ) {
			list.add( left );
			recursiveSearch( list, candys );
			list.remove( list.size()-1 );
		}
		// TODO: Suite..
		
		return null;
	}



	private List<Point> testPath( Labyrinthe labyrinthe, Point position, Direction direction, int candy, List<Point> list ) {
		
		if ( labyrinthe.get( position ).get( direction ) ) {
			
			Point newPosition = new Point( position );
			if( direction == Direction.Top ) {
				newPosition.y += 1;
			}
			else if( direction == Direction.Right ) {
				newPosition.x += 1;
			}
			else if( direction == Direction.Down ) {
				newPosition.y -= 1;
			}
			else {
				newPosition.x -= 1;
			}
			
			if ( newPosition.x == -1 || newPosition.x == labyrinthe.getSize().width || newPosition.y == -1 || newPosition.y == labyrinthe.getSize().height ) {
				list.add( newPosition );
			}
			else {
				list.add( newPosition );
				
				List<Point> listTop   = new LinkedList<Point>();
				List<Point> listRight = new LinkedList<Point>();
				List<Point> listDown  = new LinkedList<Point>();
				List<Point> listLeft  = new LinkedList<Point>();
						
				if( ! isBack( direction, Direction.Top ) ) {
					listTop = testPath( labyrinthe, newPosition, Direction.Top, candy, new LinkedList<Point>( list ) );
				}
				
				if ( ! isEnd( listTop, labyrinthe ) ) {
					if ( ! isBack( direction, Direction.Right ) ) {
						listRight = testPath( labyrinthe, newPosition, Direction.Right, candy, new LinkedList<Point>( list ) );
					}
					
					if ( ! isEnd( listRight, labyrinthe ) ) {
						if ( ! isBack( direction, Direction.Down ) ) {
							listDown = testPath( labyrinthe, newPosition, Direction.Down, candy, new LinkedList<Point>( list ) );
						}
						
						if ( ! isEnd( listDown, labyrinthe ) ) {
							if ( ! isBack( direction, Direction.Left ) ) {
								listLeft = testPath( labyrinthe, newPosition, Direction.Left, candy, new LinkedList<Point>( list ) );
							}
						}
					}
				}
				
				list = betterPath( listTop, listRight, listDown, listLeft, labyrinthe );
			}
		}
		return list;
	}
	
	
	
	/**
	 * Vérifie si M. Pakkuman ne fait pas demi-tour.
	 * @param old_dir Ancienne direction.
	 * @param new_dir Nouvelle direction.
	 * @return Si il fait demi-tour.
	 */
	private static boolean isBack( Direction old_dir, Direction new_dir ) {
		return ( ( old_dir == Direction.Top ) && ( new_dir == Direction.Down ) ) ||
		       ( ( old_dir == Direction.Down ) && ( new_dir == Direction.Top ) ) ||
		       ( ( old_dir == Direction.Left ) && ( new_dir == Direction.Right ) ) ||
		       ( ( old_dir == Direction.Right ) && ( new_dir == Direction.Left ) );
	}
	
	
	
	/**
	 * Parmis les 4 chemins, renvoie en priorité celui qui est fini et le plus court sinon le plus court.
	 * @param list1 Première liste de chemin.
	 * @param list2 Deuxième liste de chemin.
	 * @param list3 Troisième liste de chemin.
	 * @param list4 Quatrième liste de chemin.
	 * @return Le chemin fini et plus court ou le chemin le plus court.
	 */
	private static List<Point> betterPath( List<Point> list1, List<Point> list2, List<Point> list3, List<Point> list4, Labyrinthe labyrinthe ) {
		List<Point> best;
		best = betterPath( list1, list2, labyrinthe );
		best = betterPath( best, list3, labyrinthe );
		best = betterPath( best, list4, labyrinthe );
		return best;
	}
	
	
	
	/**
	 * Parmis les 2 chemins, renvoie en priorité celui qui est fini et le plus court sinon le plus court.
	 * @param list1 Première liste de chemin.
	 * @param list2 Deuxième liste de chemin.
	 * @param labyrinthe 
	 * @return Le chemin fini et plus court ou le chemin le plus court.
	 */
	private static List<Point> betterPath( List<Point> list1, List<Point> list2, Labyrinthe labyrinthe ) {
		
		List<Point> best = list1;
		
		// Si le chemin est vide.
		if ( best.isEmpty() || isEnd ( list2, labyrinthe ) ) {
			best = list2;
		}
		
		return best;
	}
	
	
	
	private static boolean isEnd( List<Point> list, Labyrinthe labyrinthe ) {
		if ( ! list.isEmpty() ) {
			Point point = list.get( list.size()-1 );
			return ( point.x == -1 || point.x == labyrinthe.getSize().width || point.y == -1 || point.y == labyrinthe.getSize().height );
		}
		else {
			return false;
		}
	}
}


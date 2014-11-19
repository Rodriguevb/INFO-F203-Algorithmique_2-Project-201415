package pakkuman;

import java.util.LinkedList;
import java.util.List;

public class Path {
	
	
	public static List<State> search( Labyrinthe labyrinthe, Element pakkuman, List<Element> monsters, List<Element> candys) {
		
		List<State> listTop   = testPath( labyrinthe, pakkuman.getPosition(), State.Top, 0, new LinkedList<State>() );
		List<State> listRight = testPath( labyrinthe, pakkuman.getPosition(), State.Right, 0, new LinkedList<State>() );
		List<State> listDown  = testPath( labyrinthe, pakkuman.getPosition(), State.Down, 0, new LinkedList<State>() );
		List<State> listLeft  = testPath( labyrinthe, pakkuman.getPosition(), State.Left, 0, new LinkedList<State>() );
		
		return betterPath( listTop, listRight, listDown, listLeft );
	}
	
	/**
	 * Une énumération représentant la direction choisie.
	 */
	public enum State {
		Top,
		Right,
		Down,
		Left,
		Candy,
		Monster,
		End
	}
	
	private static List<State> testPath( Labyrinthe labyrinthe, Vector2<Integer> pos, State direction, int candy, List<State> list ) {
		
		if ( possibilityTo( labyrinthe, pos, direction, candy ) ) {
			list.add( direction );
			
			List<State> listTop   = null;
			List<State> listRight = null;
			List<State> listDown  = null;
			List<State> listLeft  = null;
					
			if( ! isBack( direction, State.Top ) ) {
				listTop = testPath( labyrinthe, pos, State.Top, candy, new LinkedList<State>( list ) );
			}
			if( ! isBack( direction, State.Right ) ) {
				listRight = testPath( labyrinthe, pos, State.Right, candy, new LinkedList<State>( list ) );
			}
			if( ! isBack( direction, State.Left ) ) {
				listDown = testPath( labyrinthe, pos, State.Down, candy, new LinkedList<State>( list ) );
			}
			if( ! isBack( direction, State.Top ) ) {
				listLeft = testPath( labyrinthe, pos, State.Left, candy, new LinkedList<State>( list ) );
			}
			
			list = betterPath( listTop, listRight, listDown, listLeft );
		}
		return list;
	}
	
	
	
	/**
	 * Vérifie si M. Pakkuman ne fait pas demi-tour.
	 * @param old_dir Ancienne direction.
	 * @param new_dir Nouvelle direction.
	 * @return Si il fait demi-tour.
	 */
	private static boolean isBack( State old_dir, State new_dir ) {
		return ( ( old_dir == State.Top ) && ( new_dir == State.Down ) ) ||
		       ( ( old_dir == State.Down ) && ( new_dir == State.Top ) ) ||
		       ( ( old_dir == State.Left ) && ( new_dir == State.Right ) ) ||
		       ( ( old_dir == State.Right ) && ( new_dir == State.Left ) );
	}
	
	
	
	/**
	 * Parmis les 4 chemins, renvoie en priorité celui qui est fini et le plus court sinon le plus court.
	 * @param list1 Première liste de chemin.
	 * @param list2 Deuxième liste de chemin.
	 * @param list3 Troisième liste de chemin.
	 * @param list4 Quatrième liste de chemin.
	 * @return Le chemin fini et plus court ou le chemin le plus court.
	 */
	private static List<State> betterPath(List<State> list1, List<State> list2, List<State> list3, List<State> list4) {
		
		List<State> best = list1;
		
		best = betterPath( best, list2 );
		best = betterPath( best, list3 );
		best = betterPath( best, list4 );
		
		return best;
	}
	
	
	/**
	 * Parmis les 2 chemins, renvoie en priorité celui qui est fini et le plus court sinon le plus court.
	 * @param list1 Première liste de chemin.
	 * @param list2 Deuxième liste de chemin.
	 * @return Le chemin fini et plus court ou le chemin le plus court.
	 */
	private static List<State> betterPath( List<State> list1, List<State> list2 ) {
		
		List<State> best = list1;
		
		if ( best.isEmpty() ) {
			best = list2;
		}
		else if (  best.get( best.size()-1 ) != State.End ) {
			if ( list2.get( list2.size()-1 ) == State.End || // Si la liste1 n'est pas finie et que la liste2 est finie.
					best.size() > list2.size() ) {           // OU que la liste1 est plus longue que liste2.
				best = list2;
			}
		}
		else{
			if ( list2.get( list2.size()-1 ) == State.End && // Si la liste2 est aussi finie
					best.size() > list2.size() ) {           // ET que la liste1 est plus longue que liste2.
				best = list2;
			}
		}
		
		return best;
	}


	private static boolean possibilityTo( Labyrinthe labyrinthe, Vector2<Integer> pos, State direction, int candy ) {
		
		if ( direction == State.Top ) {
			return labyrinthe.get(pos.x, pos.y+1);
		}
		else if ( direction == State.Right ) {
			return labyrinthe.get(pos.x+1, pos.y);
		}
		else if ( direction == State.Down ) {
			return pos.y-1 == -1 ? false : labyrinthe.get(pos.x, pos.y-1);
		}
		else {
			return pos.x-1 == -1 ? false : labyrinthe.get(pos.x-1, pos.y);
		}
	}
}

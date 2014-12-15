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
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Path {
	
	
	private   Pakkuman    pakkuman = null;
	
	public Path( Pakkuman pakkuman ) {
		this.pakkuman  = pakkuman;
	}
	
	/**
	 * Change en string.
	 */
	public String toString() {
		String string = new String();
		for ( Iterator<Point> iter = pakkuman.path.iterator(); iter.hasNext();  ) {
			Point point = iter.next();
			string += " (" + point.x + "," + point.y + ")";
		}
		return string;
	}
	
	/**
	 * Recherche le plus court chemin.
	 */
	public void search() {
		
		pakkuman.path = new LinkedList<Point>();
		int candys = howManyCandyNeeded( pakkuman.tree_exit, pakkuman.tree_root );
		
		
		if ( candys > 0 ) { // Si on a au moins besoin de 1 bonbon.
			// Le nombre de bonbon qu'on a besoin == le nombre de demi-tour autorisé.
			List<Point> path = new LinkedList<Point>();
			path.add( pakkuman.tree_root.getNode().getPoint() );
			recursiveMakePath( pakkuman, path, pakkuman.tree_root, candys );
		}
		else { // Si on a pas besoin de bonbon.
			// TODO: Prendre la path qui va de la fin au début.
		}
		
		pakkuman.candys += pakkuman.monsters;
	}
	
	
	/**
	 * Compte combien de bonbon on a besoin pour arriver de la racine jusqu'à coord_end
	 * @param coord_end L'arrivée.
	 * @param racine La racine.
	 * @return Le nombre de bonbon qu'on a besoin.
	 */
	private static int howManyCandyNeeded( Tree end, Tree racine ) {
		
		Tree actual = end.getParent();
		int candys = 0;
		
		do {
			if ( actual.getNode().isCandy() ) {
				candys -= 1;
			}
			else if ( actual.getNode().isMonster() ) {
				candys += 1;
			}
			
			actual = actual.getParent();
		} while ( actual != racine );
		
		return candys;
	}
	
	
	private static boolean recursiveMakePath( Pakkuman pakkuman, List<Point> path, Tree tree, int returnPossible, int candys, int monsters ) {
		
		if ( isPossibleToFoundABetterSolution(pakkuman, path) ) {
			
			boolean left_exist  = false;
			boolean right_exist = false;
			
			// On teste le chemin à gauche.
			if ( tree.getLeft() != null ) {
				left_exist = true;
				if ( testPath( pakkuman, path, tree.getLeft(), returnPossible ) ) {
					return true;
				}
			}
			
			// On teste le chemin à droite.
			if ( tree.getRight() != null ) {
				right_exist = true;
				if ( testPath( pakkuman, path, tree.getRight(), returnPossible ) ) {
					return true;
				}
			}
			
			// On teste le chemin en faisant un demi tour.
			if ( returnPossible > 0 & !left_exist && !right_exist && tree.getParent() != null ) {
				recursiveRecoverPath( pakkuman, path, tree, returnPossible );
			}
		}
		return false;
	}

	

	private static boolean recursiveRecoverPath( Pakkuman pakkuman, List<Point> path, Tree child, int returnPossible, int candys, int monsters ) {
		
		Tree parent = child.getParent();
		Node node = parent.getNode();
		
		path.add( node.getPoint() );
		returnPossible -= 1;
		
		if ( isPossibleToFoundABetterSolution(pakkuman, path) ) {
			
			// Va faire l'autre fils
			if ( parent.getLeft() != null && parent.getLeft() != child ) {
				testPath( pakkuman, path, parent.getLeft(), returnPossible  );
			}
			else if ( parent.getRight() != null && parent.getRight() != child ) {
				testPath( pakkuman, path, parent.getRight(), returnPossible  );
			}
			
			// Remonte l'arbre
			if ( parent.getParent() != null ) {
				recursiveRecoverPath( pakkuman, path, parent, returnPossible );
			}
		}
		
		path.remove( path.size() - 1 );
		returnPossible += 1;
		
		return false;
	}
	


	/**
	 * Permet de tester une case précise.
	 * @param solution Le parcours optimal.
	 * @param path Le parcours actuel.
	 * @param tree Le tree à essayer.
	 * @return Si un nouveau parcours optimal a été trouvé.
	 */
	private static boolean testPath( Pakkuman pakkuman, List<Point> path, Tree tree, int returnPossible, int candys, int monsters ) {
		
		Node node = tree.getNode();
		path.add( tree.getNode().getPoint() );
		
		if ( node.isCandy() ) {
			candys += 1;
			recursiveMakePath( pakkuman, path, tree, returnPossible );
			candys -= 1;
		}
		else if ( node.isMonster() ) {
			if ( pakkuman.candys > 0 ) {
				candys -= 1;
				monsters += 1;
				recursiveMakePath( pakkuman, path, tree, returnPossible );
				candys += 1;
				ùonsters -= 1;
			}
			
		}
		else if ( tree.getNode().isEnd() ) {
			pakkuman.path = new LinkedList<Point>( path );
			pakkuman.candys = candys + monsters;
			pakkuman.monsters = monsters;
			pakkuman.found = true;
			return true;
		}
		else{
			recursiveMakePath( pakkuman, path, tree, returnPossible );
		}
		
		path.remove( path.size() - 1 );
		return false;
	}
	
	
	/**
	 * Renvoie si c'est encore possible de trouver une meilleure solution ou non.
	 * @param solution Le parcours optimal actuel.
	 * @param path Le parcours actuel.
	 * @return Si la longueur du chemin est plus petite que la solution déjà trouvée ou qu'aucune solution n'a encore été trouvée.
	 */
	private static boolean isPossibleToFoundABetterSolution( Pakkuman pakkuman, List<Point> path ) {
		return pakkuman.path.size() == 0 || pakkuman.path.size() > path.size();
	}
}


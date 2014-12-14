package pakkuman;

import java.awt.Point;
import java.util.LinkedList;
import java.util.List;

public class MakerBigTree {
	
	Pakkuman pakkuman  = null; // Pakkuman contenant toutes les informations.
	
	/**
	 * Constructeur.
	 * @param pakkuman Pakkuman contenant toutes les informations.
	 */
	public MakerBigTree( Pakkuman pakkuman ) {
		this.pakkuman  = pakkuman;
	}

	
	/**
	 * Crée l'arbre
	 */
	public void create() {
		pakkuman.tree_root = new Tree( new Node( pakkuman.coord_start ) );
		pakkuman.trees_terminals  = new LinkedList<Tree>();
		
		// On crée l'arbre depuis la racine
		recursiveMakeTree( pakkuman, pakkuman.tree_root, null );
		
		// On supprime les terminaux inutiles.
		deleteUselessTree( pakkuman.trees_terminals );
	}
	
	/**
	 * 
	 * @param pakkuman
	 * @param tree
	 * @param direction
	 */
	private static void recursiveMakeTree( Pakkuman pakkuman, Tree tree, Direction direction ) {
		
		// On récupère la case courante.
		Point point = tree.getNode().getPoint();
		Case  case_ = pakkuman.labyrinthe.get( point );
		
		// On note les directions possibles.
		List<Direction> directions = new LinkedList<Direction>();
		if( case_.getUp() && direction != Direction.Down ) {
			directions.add( Direction.Up );
		}
		if( case_.getRight() && direction != Direction.Left  ) {
			directions.add( Direction.Right );
		}
		if( case_.getDown() && direction != Direction.Up ) {
			directions.add( Direction.Down );
		}
		if( case_.getLeft() && direction != Direction.Right ) {
			directions.add( Direction.Left );
		}
		
		
		// Si il n'existe au moins 1 direction possible
		if( 1 <= directions.size() ) {
			
			// On définit son fils gauche.
			Point point2 = getPointViaDirection( point, directions.get(0));
			if ( pakkuman.labyrinthe.contains( point2 ) ) {
				Tree left = new Tree( new Node(point2) );
				
				setState( pakkuman, left ); // On définit l'état du noeud (Monstre, Bonbons, Début, Start).
				
				left.setParent( tree );
				tree.setLeft( left );
				recursiveMakeTree( pakkuman, tree.getLeft(), directions.get(0) );
			}
			
			// Si il exite une deuxième direction possible.
			if ( 2 == directions.size() ) {
				
				// On définit son fils droit.
				point2 = getPointViaDirection( point, directions.get(1) );
				if ( pakkuman.labyrinthe.contains( point2 ) ) {
					Tree right = new Tree( new Node(point2) );
					
					setState( pakkuman, right ); // On définit l'état du noeud (Monstre, Bonbons, Début, Start).
					
					right.setParent( tree );
					tree.setRight( right );
					recursiveMakeTree( pakkuman, tree.getRight(), directions.get(1) );
				}
				
			}
		}
		else{
			pakkuman.trees_terminals.add( tree );
		}
	}
	
	/**
	 * 
	 * @param pakkuman
	 * @param tree
	 */
	private static void setState( Pakkuman pakkuman, Tree tree ) {
		Node  node  = tree.getNode();
		Point point = node.getPoint();
		
		// On regarde si c'est pas un monstre.
		boolean found = false;
		for( int i = 0; i < pakkuman.coords_monsters.size() && !found; ++i ) {
			if ( point.x == pakkuman.coords_monsters.get(i).x && point.y == pakkuman.coords_monsters.get(i).y ) {
				node.isMonster( true );
				found = true;
			}
		}
		
		// On regarde si c'est pas un bonbon (un bonbon ne peut pas être au même endroit qu'un monstre)
		if( !found ) {
			for( int i = 0; i < pakkuman.coords_candys.size() && !found; ++i ) {
				if ( point.x == pakkuman.coords_candys.get(i).x && point.y == pakkuman.coords_candys.get(i).y ) {
					node.isCandy( true );
					found = true;
				}
			}
		}
		
		// On regarde si c'est le début
		if ( point.x == pakkuman.coord_start.x && point.y == pakkuman.coord_start.y ) {
			node.isStart( true );
		}
		
		// On regarde si c'est la fin
		if ( point.x == pakkuman.coord_exit.x && point.y == pakkuman.coord_exit.y ) {
			node.isEnd( true );
			pakkuman.tree_exit = tree;
		}
	}

	/**
	 * Retourne le point dans la direction du point donné.
	 * @param point Le point donné.
	 * @param direction 
	 * @return Le point dans la direction
	 */
	private static Point getPointViaDirection( Point start, Direction direction ) {
		Point point = new Point( start );
		if ( direction == Direction.Up ) {
			point.y += 1;
		}
		else if ( direction == Direction.Right ) {
			point.x += 1;
		}
		else if ( direction == Direction.Down ) {
			point.y -= 1;
		}
		else {
			point.x -= 1;
		}
		return point;
	}
	
	/**
	 * On supprime les terminaux inutiles de l'arbre
	 * @param trees_terminals Les Tree terminaux.
	 */
	private static void deleteUselessTree( List<Tree> terminals ) {
		for( int i = 0; i < terminals.size(); ++i ) {
			deleteUselessTree( terminals.get(i) );
		}
	}
	
	/**
	 * Recursivité de la suppression des terminaux inutiles de l'arbre.
	 * @param tree Le tree actuel à supprimer.
	 */
	private static void deleteUselessTree( Tree tree ) {
		Tree parent = tree.getParent();
		
		if ( parent != null ) {
			// On enlève les liens entre le fils et son parent
			tree.setParent( null );
			if ( parent.getLeft() == tree ) {
				parent.setLeft( null );
			}
			else if ( parent.getRight() == tree ) {
				parent.setRight( null );
			}
			
			// Si le parent est maintenant une fin inutile.
			Node node = parent.getNode();
			if ( !node.isStart() && !node.isEnd() && !node.isCandy() && parent.getLeft() == null && parent.getRight() == null ) {
				deleteUselessTree( parent );
			}
		}
	}

}

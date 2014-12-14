package pakkuman;
/**
 * 
 * Info-F-203 : Projet d’Algorithmique 2 - Évasion du labyrinthe
 * @filename Pakkuman.java
 * @author Rodrigue VAN BRANDE
 * @matricule 000362341
 *
 */
import java.awt.Point;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Pakkuman {
	
	/**
	 * Main du projet
	 * @param args Le chemin du fichier.
	 */
	public static void main( String[] args ) {

		if ( args.length >= 1 ) {
			
			FileLoader fileloader = new FileLoader();
			if ( fileloader.loadFromFile( args[0] ) ) {
				
				Pakkuman pakkuman = new Pakkuman();
				pakkuman.labyrinthe  = fileloader.getLabyrinthe();
				pakkuman.coord_start = fileloader.getStart();
				pakkuman.coord_exit  = fileloader.getEnd();
				pakkuman.coords_monsters    = fileloader.getMonsters();
				pakkuman.coords_candys      = fileloader.getBonbons();
				
				///////////////////////////////////////////////////////
				// Affichage d'informations fidèle à l'énoncé.
				System.out.println( "Le labyrinthe a un dimension " + pakkuman.labyrinthe.getSize().width + " fois " + pakkuman.labyrinthe.getSize().height );
				System.out.println( "Il contient " + pakkuman.coords_monsters.size() + " monstres et " + pakkuman.coords_candys.size() + " bonbons.");
				System.out.println( "M. Pakkuman se trouve en position: " + "(" + pakkuman.coord_start.x + "," + pakkuman.coord_start.y + ")" );
				System.out.print( "Le monstres se trouvent en position: " );
				for ( Iterator<Point> iter = pakkuman.coords_monsters.iterator(); iter.hasNext();  ) {
					Point point = iter.next();
					System.out.print( "(" + point.x + "," + point.y + ") " );
				}
				System.out.print( "\nLe bonbons se trouvent en position: " );
				for ( Iterator<Point> iter = pakkuman.coords_candys.iterator(); iter.hasNext();  ) {
					Point point = iter.next();
					System.out.print( "(" + point.x + "," + point.y + ") " );
				}
				System.out.print( "\n" );
				
				
				pakkuman.createFileStartSituation( "init.txt" );
				
				
				// On crée un arbre.
				MakerBigTree makerBigTree = new MakerBigTree( pakkuman );
				makerBigTree.create();
				
				// On va chercher le chemin le plus court.
				Path path = new Path( pakkuman );
				path.search();
				
				System.out.println( "Trouvé un plus court chemin de longueur " + pakkuman.path.size() + "." );
				System.out.println( "M. Pakkuman a pris " + path.howManyCandysUsed() + " Bonbons!" );
				System.out.print( "Déplacements de M. Pakkuman:" + path );
				
				pakkuman.createFileEndSituation( "coord_end.txt" );
				
			}
		}
		
		else {
			System.out.println("ERROR: Argument manquant.");
		}
		
		
		//Date date_end = new Date( );
		//System.out.println( "Current Date: " + ft.format(date_end) );
	}
	
	
	
	
	protected Labyrinthe  labyrinthe      = null; // Le labyrinthe.
	protected List<Point> coords_monsters = null; // Les coordonnées des monstres.
	protected List<Point> coords_candys   = null; // Les coordonnées des bonbons.
	protected Point       coord_start     = null; // Les coordonnées de l'entrée.
	protected Point       coord_exit      = null; // Les coordonnées de la sortie.
	protected List<Tree>  trees_terminals = null; // Les Tree terminaux
	protected Tree        tree_root       = null; // Le Tree de départ.
	protected Tree        tree_exit       = null; // Le Tree de sortie.
	protected List<Point> path            = null; // Le meilleur chemin.
	
	/**
	 * 
	 * @param filename
	 * @return
	 */
	public boolean createFileStartSituation( String filename ) {
		File file = new File ( filename );
		try {
			FileWriter filewriter = new FileWriter ( file );
			filewriter.write ( "Sitation de départ:\n" );
			
			String string = new String();
			
			string = add( labyrinthe, string );
			string = add( coord_start,   string, 'P' );
			string = add( coords_monsters,   string, 'M' );
			string = add( coords_candys,     string, 'o' );
			
			
			filewriter.write ( string );
			filewriter.close();
			return true;
		}
		catch (IOException exception) {
			System.out.println( exception.toString() );
			return false;
		}
	}
	
	
	
	public boolean createFileEndSituation( String filename ) {
		File file = new File ( filename );
		try {
			FileWriter filewriter = new FileWriter ( file );
			filewriter.write ( "Sitation finale:\n" );
			
			String string = new String();
			
			string = add( labyrinthe, string );
			string = add( coord_start,   string, 'P' );
			string = add( coords_monsters,   string, 'M' );
			string = add( coords_candys,     string, 'o' );
			
			for ( Iterator<Point> iter = path.iterator(); iter.hasNext();  ) {
				Point point = iter.next();
				string = add( point, string, '#' );
			}
			
			filewriter.write ( string );
			filewriter.close();
			return true;
		}
		catch (IOException exception) {
			System.out.println( exception.toString() );
			return false;
		}
	}



	private String add( Labyrinthe labyrinthe, String string) {
		string += labyrinthe.toString();
		return string;
	}
	
	
	
	private String add( Point element, String string, char character ) {
		List<Point> list = new LinkedList<Point>();
		list.add( element );
		return add( list, string, character );
	}
	
	
	
	private String add( List<Point> list, String string, char character ) {
		
		for( int i = 0; i < list.size(); ++i ) {
			Point position_real = list.get( i );
			int position_str = realPos(position_real.x, position_real.y, labyrinthe.getSize().height);
			string = replaceCharAt(string, position_str, character);
		}
		
		return string;
	}
	
	
	
	private static int realPos( int x, int y, int max_y ) {
		return ( (max_y*4) + 2 )*( x*2 + 1 ) + ( 2 + (y*4) );
	}
	
	
	
	public static char intToChar( int i ) {
		String s = ""+i;
		return s.charAt(0);
    }
	
	
	
	public static String replaceCharAt(String string, int position, char character) {
		return string.substring(0,position) + character + string.substring(position+1);
	}
}

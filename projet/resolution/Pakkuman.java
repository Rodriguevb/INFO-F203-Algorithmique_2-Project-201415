package pakkuman;
/**
 * 
 * Info-F-203 : Projet d’Algorithmique 2 - Évasion du labyrinthe
 * @filename Pakkuman.java
 * @author Rodrigue VAN BRANDE
 * @matricule 000362341
 *
 */
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

import java.util.List;
import pakkuman.Path.State;

public class Pakkuman {
	
	private Labyrinthe      _labyrinthe = null;
	private Element         _pakkuman   = null;
	private List< Element > _monsters   = null;
	private List< Element > _candys     = null;

	
	/**
	 * Main du projet
	 * @param args
	 */
	public static void main(String[] args) {
		
		if ( args.length >= 1 ) {
			
			FileLoader fileloader = new FileLoader();
			if ( fileloader.loadFromFile( args[0] ) ) {
				
				Labyrinthe labyrinthe = fileloader.getLabyrinthe();
				Element pakkuman = fileloader.getPakkuman();
				List< Element > monsters = fileloader.getMonsters();
				List< Element > candys = fileloader.getBonbons();
				
				// Affichage d'informations fidèle à l'énoncé.
				System.out.println( "Le labyrinthe a un dimension " + labyrinthe.getSize().x + " fois " + labyrinthe.getSize().y );
				System.out.println( "Il contient " + monsters.size() + " monstres et " + candys.size() + " bonbons.");
				System.out.println( "M. Pakkuman se trouve en position: " + pakkuman.getPosition() );
				System.out.print( "Le monstres se trouvent en position: " );
				for ( Iterator<Element> iter = monsters.iterator(); iter.hasNext();  ) {
					Element element = iter.next();
					System.out.print( element.getPosition() + " " );
				}
				System.out.print( "\nLe bonbons se trouvent en position: " );
				for ( Iterator<Element> iter = candys.iterator(); iter.hasNext();  ) {
					Element element = iter.next();
					System.out.print( element.getPosition() + " " );
				}
				System.out.print( "\n" );
				
				Pakkuman game = new Pakkuman( labyrinthe, pakkuman, monsters, candys );
				game.createFileStartSituation( "init.txt" );
				List<State> path = Path.search(labyrinthe, pakkuman, monsters, candys);
				for ( Iterator<State> iter = path.iterator(); iter.hasNext();  ) {
					State state = iter.next();
					System.out.print( state );
				}
				
				game.createFileEndSituation( "init.txt" );
				
			}
		}
		
		else {
			System.out.println("ERROR: Argument manquant.");
		}
	}
	
	
	/**
	 * 
	 * @param pakkuman
	 * @param monsters
	 * @param bonbons
	 */
	public Pakkuman( Labyrinthe labyrinthe, Element pakkuman, List<Element> monsters, List<Element> candys) {
		_labyrinthe = labyrinthe;
		_pakkuman   = pakkuman;
		_monsters   = monsters;
		_candys     = candys;
	}
	


	public boolean createFileStartSituation( String filename ) {
		File file = new File ( filename );
		try {
			FileWriter filewriter = new FileWriter ( file );
			filewriter.write ( "Sitation de départ:\n" );
			
			String string = new String();
			
			string = add( _labyrinthe, string );
			string = add( _pakkuman,   string, 'P' );
			string = add( _monsters,   string, 'M' );
			string = add( _candys,     string, 'o' );
			
			
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
			
			string = add( _labyrinthe, string );
			string = add( _pakkuman,   string, 'P' );
			string = add( _monsters,   string, 'M' );
			string = add( _candys,     string, 'o' );
			
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
	
	
	
	private String add( Element element, String string, char character ) {
		List<Element> list = new LinkedList<Element>();
		list.add( element );
		return add( list, string, character );
	}
	
	
	
	private String add( List<Element> list, String string, char character ) {
		
		for( int i = 0; i < list.size(); ++i ) {
			Vector2<Integer> position_real = list.get( i ).getPosition();
			int position_str = realPos(position_real.x, position_real.y, _labyrinthe.getSize().x);
			string = replaceCharAt(string, position_str, character);
		}
		
		return string;
	}


	


	private static int realPos( int x, int y, int y_of_maze ) {
		return (x + 1 )*(34*2) - 34 + (y*4 + 2 );
	}
	
	public static char intToChar( int i ) {
		String s = ""+i;
		return s.charAt(0);
    }
	
	
	
	/**
	 * Remplace un caractère par un autre dans un string
	 * @param s
	 * @param pos
	 * @param c
	 * @return
	 */
	public static String replaceCharAt(String s, int pos, char c) {
	   return s.substring(0,pos) + c + s.substring(pos+1);
	}
}

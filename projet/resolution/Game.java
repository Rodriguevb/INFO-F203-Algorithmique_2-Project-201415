package pakkuman;
/**
 * 
 * Info-F-203 : Projet d’Algorithmique 2 - Évasion du labyrinthe
 * @filename Game.java
 * @author Rodrigue VAN BRANDE
 * @matricule 000362341
 *
 */
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Game {
	
	private Labyrinthe      _labyrinthe;
	private Pakkuman        _pakkuman;
	private List< Monster > _monsters;
	private List< Bonbon >  _bonbons;
	
	
	
	/**
	 * 
	 * @param pakkuman
	 * @param monsters
	 * @param bonbons
	 */
	public Game( Labyrinthe labyrinthe, Pakkuman pakkuman, List<Monster> monsters, List<Bonbon> bonbons) {
		_labyrinthe = labyrinthe;
		_pakkuman = pakkuman;
		_monsters = monsters;
		_bonbons = bonbons;
	}

	
	
	/**
	 * 
	 */
	public boolean createFileSituation( String filename ) {
		File file = new File ( filename );
		try {
			FileWriter filewriter = new FileWriter ( file );
			filewriter.write ( toString() );
			filewriter.close();
			return true;
		}
		catch (IOException exception) {
			System.out.println( exception.toString() );
			return false;
		}
	}
	
	
	
	/**
	 * 
	 */
	public String toString() {
		return _labyrinthe.toString();
	}
}

package pakkuman;
/**
 * 
 * Info-F-203 : Projet d’Algorithmique 2 - Évasion du labyrinthe
 * @filename Pakkuman.java
 * @author Rodrigue VAN BRANDE
 * @matricule 000362341
 *
 */
import java.util.List;

public class Pakkuman extends Element {

	
	/**
	 * Main du projet
	 * @param args
	 */
	public static void main(String[] args) {
		
		if ( args.length >= 1 ) {
			
			FileLoader fileloader = new FileLoader();
			if ( fileloader.loadFromFile( args[0] ) ) {
				
				Labyrinthe labyrinthe = fileloader.getLabyrinthe();
				Pakkuman pakkuman = fileloader.getPakkuman();
				List< Monster > monsters = fileloader.getMonsters();
				List< Bonbon > bonbons = fileloader.getBonbons();
				
				System.out.println("Le labyrinthe a un dimension " + labyrinthe.getSize().x + " fois " + labyrinthe.getSize().y );
				System.out.println("Il contient " + monsters.size() + " monstres et " + bonbons.size() + " bonbons.");
				
				Game game = new Game( labyrinthe, pakkuman, monsters, bonbons );
				game.createFileSituation( "init.txt" );
				
			}
		}
		
		else {
			System.out.println("ERROR: Argument manquant.");
		}
	}
}

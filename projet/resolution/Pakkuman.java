package pakkuman;
/**
 * 
 * Info-F-203 : Projet d’Algorithmique 2 - Évasion du labyrinthe
 * @filename Pakkuman.java
 * @author Rodrigue VAN BRANDE
 * @matricule 000362341
 *
 */
import java.util.LinkedList;
import java.util.List;

public class Pakkuman {
	
	private List< Monster > _monsters = new LinkedList< Monster >();
	private List< Bonbon > _bonbons = new LinkedList< Bonbon >();
	
	/**
	 * Main du projet
	 * @param args
	 */
	public static void main(String[] args) {
		
		if ( args.length >= 1 ) {
			
			FileLoader fileloader = new FileLoader();
			if ( fileloader.loadFromFile( args[0] ) ) {
				
				Pakkuman pakkuman = new Pakkuman( fileloader.getMonsters(), fileloader.getBonbons());
				pakkuman.getInitSituation();
				
			}
			else {
				System.out.println(":::ERROR::: Format du fichier non respecté.");
			}
		}
		
		else {
			System.out.println(":::ERROR::: Argument manquant.");
		}
	}
	
	
	
	/**
	 * 
	 * @param monsters
	 * @param bonbons
	 */
	public Pakkuman(List<Monster> monsters, List<Bonbon> bonbons) {
		_monsters = monsters;
		_bonbons = bonbons;
	}
	
	
	
	/**
	 * 
	 */
	private void getInitSituation() {
		System.out.println(":::ERROR::: getInitSituation");
	}
	
	
}

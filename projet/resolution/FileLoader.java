package pakkuman;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

public class FileLoader {
	
	private List< Monster > _monsters = new LinkedList< Monster >();
	private List< Bonbon > _bonbons = new LinkedList< Bonbon >();
	
	
	
	/**
	 * 
	 * @return
	 */
	public List< Monster > getMonsters() {
		return _monsters;
	}
	
	
	
	/**
	 * 
	 * @return
	 */
	public List< Bonbon > getBonbons() {
		return _bonbons;
	}
	
	
	
	/**
	 * 
	 * @param file
	 */
	public boolean loadFromFile( String file ) {
		try{
			InputStream ips = new FileInputStream( file ); 
			InputStreamReader ipsr = new InputStreamReader(ips);
			BufferedReader br = new BufferedReader(ipsr);
			
			int nbOfMonsters = 0;
			int nbOfBonbons = 0;
			List< Point > monstersCoords = new LinkedList< Point >();
			List< Point > bonbonsCoords = new LinkedList< Point >();
			
			String line;
			while ( ( line = br.readLine() ) != null ){
				
				// Si la ligne contient le mot-clé "monstres:"
				if( line.toLowerCase().contains( "monstres:" ) ) {
					
					// Si le format du fichier est respectée, la ligne contenant les paramètres contient au moins une parenthèse ouverte.
					if ( line.toLowerCase().contains( "(" ) ) { 
						monstersCoords = searchCoords( line );
					}
					
					// Si le format du fichier est respectée, 
					else {
						nbOfMonsters = Integer.parseInt( cleanAlphabet( line ) );
					}
				}
				
				// Si la ligne contient le mot-clé "bonbons:"
				else if( line.toLowerCase().contains( "bonbons:" ) ) {
					
					//Si le format du fichier est respectée, la première ligne trouvée est le nombre de monstre.
					if ( line.toLowerCase().contains( "(" ) ) { 
							bonbonsCoords = searchCoords( line );
					}
					
					// Si le format du fichier est respectée, le deuxième ligne trouvée est les coordonnées des monstres.
					else {
						nbOfBonbons = Integer.parseInt( cleanAlphabet( line ) );
					}
				}
			}
			br.close();
			
			for ( int i = 0; i < nbOfMonsters; ++i ) {
				Monster newMonster = new Monster();
				newMonster.setPosition( monstersCoords.get(i) );
				_monsters.add( newMonster);
			}
			for ( int i = 0; i < nbOfBonbons; ++i ) {
				Bonbon newBonbon = new Bonbon();
				newBonbon.setPosition( bonbonsCoords.get(i) );
				_bonbons.add( newBonbon );
			}
			
			System.out.println("INFO: " + nbOfMonsters + " monsters loaded.");
			System.out.println("INFO: " + nbOfBonbons + " bonbons loaded.");
			
			return true;
		}		
		catch (Exception e){
			System.out.println(e.toString());
			return false;
		}
	}
	
	
	
	/**
	 * Récupère l'information sur le nombre de monstre depuis un String
	 * @param string Le string contenant le nombre de monstre
	 */
	private static String cleanAlphabet( String string ) {
		return string.replaceAll("[\\D]", "");
	}
	
	
	
	/**
	 * 
	 * @param string
	 * @return
	 */
	private static List< Point > searchCoords( String string ) {
		List< Point > list = new LinkedList< Point >();
		String coords = cleanAlphabet ( string );
		for( int i = 0; i < coords.length(); ++i ) {
			int x = (int) coords.charAt( i );
			int y = (int) coords.charAt( ++i );
			list.add( new Point(x,y) );
		}
		return list;
	}
}

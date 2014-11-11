package pakkuman;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

public class FileLoader {
	
	private Pakkuman _pakkuman = new Pakkuman();
	private List< Monster > _monsters = new LinkedList< Monster >();
	private List< Bonbon > _bonbons= new LinkedList< Bonbon >();
	private Labyrinthe _labyrinthe = new Labyrinthe(0,0);
	
	
	
	public Pakkuman getPakkuman() {
		return _pakkuman;
	}
	
	
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
	
	
	public Labyrinthe getLabyrinthe() {
		return _labyrinthe;
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
			List<  Vector2<Integer> > pakkumanCoords = new LinkedList<  Vector2<Integer> >();
			List<  Vector2<Integer> > monstersCoords = new LinkedList<  Vector2<Integer> >();
			List<  Vector2<Integer> > bonbonsCoords = new LinkedList<  Vector2<Integer> >();
			
			String line;
			while ( ( line = br.readLine() ) != null ){
				
				String[] parts = line.split(" ");
				
				if( 1 <= parts.length ) {
					
					
					// Première ligne: "Labyrinthe: x fois x"
					if ( parts[0].contains("Labyrinthe:") && parts[2].contains("fois") ) {
						_labyrinthe = new Labyrinthe( Integer.parseInt(parts[1]), Integer.parseInt( parts[3] ) );
						
						
						// On crée une matrice au format du fichier qu'on réduira plus tard.
						boolean[][] matrice = new boolean[_labyrinthe.getSize().x*4+1][];
						for ( int x = 0 ; x < matrice.length; ++x ) {
							matrice[x] = new boolean[_labyrinthe.getSize().y*2+1];
						}
						
						for( int y = 0; y < matrice[0].length; ++y ) {
							line = br.readLine();
							line = line.replace("-", "+");
							line = line.replace(" ", " ");
							for( int x = 0; x < matrice.length; ++x ) {
								if ( line.charAt(x) == ' ' ) {
									matrice[x][y] = true;
								}
							}
						}
						
						
						// On copie le contenu de la matrice géante en "divisant" les abscisses par 3
						int x_real = 0;
						for( int y = 0; y < matrice[0].length; ++y ) {
							for( int x = 0; x < matrice.length; ++x ) {
								if ( x%2 == 0 ) {
									if ( matrice[x][y] ) {
										_labyrinthe.set( x_real, y, true);
									}
									else {
									}
									x_real += 1;
								}
							}
							x_real = 0;
						}
							
					}
					
					// Ligne: "Elements du Labyrinthe:"
					else if( parts[0].contains("Elements") && parts[1].contains("du") && parts[2].contains("Labyrinthe:") ) {
						line = br.readLine();
						parts = line.split(" ");
						nbOfMonsters = Integer.parseInt( keepOnlyNumber( parts[1] ) );
						line = br.readLine();
						parts = line.split(" ");
						nbOfBonbons = Integer.parseInt( keepOnlyNumber( parts[1] ) );
					}
					
					// Ligne: "Emplacements:"
					else if( parts[0].contains("Emplacements:") ) {
						line = br.readLine();
						parts = line.split(" ");
						pakkumanCoords = searchCoords( line );
						line = br.readLine();
						parts = line.split(" ");
						monstersCoords = searchCoords( line );
						line = br.readLine();
						parts = line.split(" ");
						bonbonsCoords = searchCoords( line );
					}
				}
			}
			br.close();
			
			_pakkuman.setPosition( pakkumanCoords.get(0) );
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
			
			System.out.println("INFO: labyrinthe loaded with size (" + _labyrinthe.getSize().x + "," + _labyrinthe.getSize().y +")" );
			System.out.println( _labyrinthe );
			System.out.println("INFO: pakkuman loaded at (" + _pakkuman.getPosition().x + "," + _pakkuman.getPosition().y + ")." );
			System.out.print("INFO: " + _monsters.size() + " monsters loaded at ");
			for( int i = 0; i < _monsters.size(); ++i ) {
				System.out.print( "(" + _monsters.get(i).getPosition().x + "," + _monsters.get(i).getPosition().y + ")" );
			}
			System.out.println(".");
			System.out.print("INFO: " + _bonbons.size() + " bonbons loaded at ");
			for( int i = 0; i < _bonbons.size(); ++i ) {
				System.out.print( "(" + _bonbons.get(i).getPosition().x + "," + _bonbons.get(i).getPosition().y + ")" );
			}
			System.out.println(".");
			
			return true;
		}		
		catch (Exception exception){
			System.out.println("ERROR: Format du fichier non respecté.");
			System.out.println( exception.toString() );
			return false;
		}
	}
	
	
	
	/**
	 * Supprime tous se qui n'est pas un chiffre.
	 * @param string Le string demandé.
	 */
	private static String keepOnlyNumber( String string ) {
		return string.replaceAll("[\\D]", "");
	}
	
	
	
	/**
	 * 
	 * @param string
	 * @return
	 */
	private static List< Vector2<Integer> > searchCoords( String string ) {
		List<  Vector2<Integer> > list = new LinkedList<  Vector2<Integer> >();
		String coords = keepOnlyNumber ( string );
		for( int i = 0; i < coords.length(); ++i ) {
			int x = (int) coords.charAt( i );
			int y = (int) coords.charAt( ++i );
			list.add( new Vector2<Integer>(x,y) );
		}
		return list;
	}
}

package pakkuman;
/**
 * 
 * Info-F-203 : Projet d’Algorithmique 2 - Évasion du labyrinthe
 * @filename FileLoader.java
 * @author Rodrigue VAN BRANDE
 * @matricule 000362341
 *
 */
import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

public class FileLoader {
	
	private Point         _start      = new Point();
	private Point         _end        = null;
	private List< Point > _monsters   = new LinkedList< Point >();
	private List< Point > _bonbons    = new LinkedList< Point >();
	private Labyrinthe    _labyrinthe = new Labyrinthe(0,0);
	
	
	
	/**
	 * 
	 * @return
	 */
	public Point getStart() {
		return _start;
	}
	
	

	/**
	 * 
	 * @return
	 */
	public Point getEnd() {
		return _end;
	}
	
	
	
	/**
	 * Retourne le nombre de monstres trouvé en chargant le fichier.
	 * @return Un entier du nombre de monstres.
	 */
	public List< Point > getMonsters() {
		return _monsters;
	}
	
	
	
	/**
	 * 
	 * @return
	 */
	public List< Point > getBonbons() {
		return _bonbons;
	}
	
	
	
	/**
	 * 
	 * @return
	 */
	public Labyrinthe getLabyrinthe() {
		return _labyrinthe;
	}
	
	
	
	/**
	 * 
	 * @param file
	 */
	public boolean loadFromFile( String file ) {
		try{
			InputStream ips        = new FileInputStream  ( file ); 
			InputStreamReader ipsr = new InputStreamReader( ips );
			BufferedReader br      = new BufferedReader   ( ipsr );
			
			int nbOfMonsters = 0;
			int nbOfBonbons = 0;
			List<Point> pakkumanCoords = new LinkedList<Point>();
			List<Point> monstersCoords = new LinkedList<Point>();
			List<Point> candysCoords   = new LinkedList<Point>();
			
			String line;
			while ( ( line = br.readLine() ) != null ){
				
				String[] parts = line.split(" ");
				
				if( 1 <= parts.length ) {
					
					
					// Première ligne: "Labyrinthe: x fois y"
					if ( parts[0].contains("Labyrinthe:") && parts[2].contains("fois") ) {
						
						// Si le format du fichier est respecté: "width fois height"
						int width  = Integer.parseInt( parts[1] );
						int height = Integer.parseInt( parts[3] );
						
						// On définit la taille du labyrinthe.
						_labyrinthe = new Labyrinthe( width, height );
						
						// On crée une matrice au format du fichier qu'on réduira plus tard.
						boolean[][] matrice = new boolean[_labyrinthe.getSize().width*2+1][];
						for ( int x = 0 ; x < matrice.length; ++x ) {
							matrice[x] = new boolean[_labyrinthe.getSize().height*4+1];
						}
						
						// On remplit cette grande matrice
						for( int x = 0; x < matrice.length; ++x ) {
							line = br.readLine();
							line = line.replace("-", "+");
							for( int y = 0; y < matrice[0].length; ++y ) {
								if ( line.charAt(y) == ' ' ) {
									matrice[x][y] = true;
								}
							}
						}
				
						// On copie le contenu de la matrice géante dans une plus petite en "divisant" les abscisses par 3
						int x_real = 0;
						int y_real = 0;
						for( int x = 1; x < matrice.length-1; x+=2 ) {
							for( int y = 2; y < matrice[0].length-2; y+=4 ) {
								if ( matrice[x][y] ) {
									Case newCase = new Case();
									newCase.setUp   ( matrice[x][y+2] );
									newCase.setRight( matrice[x+1][y] );
									newCase.setDown ( matrice[x][y-2] );
									newCase.setLeft ( matrice[x-1][y] );
									_labyrinthe.set( x_real, y_real, newCase);
									
									if ( _end == null ) {
										if ( newCase.getUp()    && y_real == _labyrinthe.getSize().height-1 ||
											 newCase.getRight() && x_real == _labyrinthe.getSize().width-1 ||
											 newCase.getDown()  && y_real == 0 ||
											 newCase.getLeft()  && x_real == 0 ) {
											_end = new Point( x_real, y_real );
										}
									}
									
								}
								y_real += 1;
							}
							x_real += 1;
							y_real = 0;
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
						pakkumanCoords = searchCoords( line );
						line = br.readLine();
						monstersCoords = searchCoords( line );
						line = br.readLine();
						candysCoords = searchCoords( line );
					}
				}
			}
			br.close();
			
			_start = pakkumanCoords.get(0);
			for ( int i = 0; i < nbOfMonsters; ++i ) {
				Point newMonster = new Point();
				newMonster = monstersCoords.get(i);
				_monsters.add( newMonster);
			}
			for ( int i = 0; i < nbOfBonbons; ++i ) {
				Point newBonbon = new Point();
				newBonbon = candysCoords.get(i);
				_bonbons.add( newBonbon );
			}
			
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
	private static List<Point> searchCoords( String string ) {
		List<Point> list = new LinkedList<Point>();
		
		String[] parts = string.split(" ");
		for( int i = 1; i < parts.length; ++i ) {
			String[] parts2 = parts[i].split(",");
			int x =  Integer.parseInt( parts2[0].replace("(", "") );
			int y =  Integer.parseInt( parts2[1].replace(")", "") );
			list.add( new Point(x,y) );
		}
		return list;
	}
}

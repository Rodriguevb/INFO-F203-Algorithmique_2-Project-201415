package pakkuman;

public class Tree {
	
	private Node _value  = null;
	private Tree  _left   = null,
	              _right  = null,
	              _parent = null;   
	
	
	
	/**
	 * Constructeur.
	 * @param value Valeur du noeud.
	 */
	public Tree( Node value ) {
		_value = value;
	}
	
	
	
	/**
	 * 
	 * @param value
	 * @param left
	 * @param right
	 */
	public Tree( Node value, Tree left, Tree right ) {
		_value = value;
		_left  = left;
		_right = right;
    }
	
	
	
	public Tree getParent() {
		return _parent;
	}
	
	
	
	/**
	 * Renvoie la valeur du noeud.
	 * @return La valeur du noeud.
	 */
	public Node getNode() {
		return _value;
	}
	
	
	
	/**
	 * Renvoie son fils gauche du noeud.
	 * @return Son fils gauche du noeud.
	 */
	public Tree getLeft() {
		return _left;
	}
	
	
	
	
	/**
	 * Renvoie le fils droit du noeud.
	 * @return Le fils droit du noeud.
	 */
	public Tree getRight() {
		return _right;
	}
	
	
	/**
	 * Défini le sous arbre à gauche.
	 * @param tree L'arbre à gauche.
	 */
	public void setLeft( Tree tree ) {
		_left = tree;
	}
	
	
	
	/**
	 * Défini le sous arbre à droite.
	 * @param tree L'arbre à droite.
	 */
	public void setRight( Tree tree ) {
		_right = tree;
	}



	public void setParent( Tree tree ) {
		 _parent = tree;
	}
	
	
	public String toString() {
		return new String( "(" + getNode().getPoint().x + "," + getNode().getPoint().y + ")" );
	}
	
	
	public String toStringPrefixe() {
		String string = new String();
		string += toStringPrefixe( this, string );
		return string;
	}
	
	private static String toStringPrefixe( Tree tree, String string ) {
		String text = new String( string );
		text += tree;
		if ( tree.getLeft() != null ){
			text += toStringPrefixe( tree.getLeft(), string );
		}
		if ( tree.getRight() != null ) {
			text += toStringPrefixe( tree.getRight(), string );
		}
		return text;
	}
	
	

	
	
	

	
	/**
	 * Renvoie la hauteur de l'arbre.
	 * @param tree L'arbre.
	 * @return La hauteur.
	 */
	
/*
	public static int hauteur( Tree tree ) {
		return ( tree == null ) ?
			0 :
			( 1 + Math.max( hauteur( tree.getLeft()  ),
			                hauteur( tree.getRight() ) ) );
	}
	
	
	
	// AFFICHAGE
    public String toString() {
        return toString("\t");
    }

    public String toString(String s) {
	if (_left!=null) {
	if (_right!=null) 
	    return(s+_value+"\n"+_left.toString(s+"\t")+_right.toString(s+"\t"));
	else
	    return(s+_value+"\n"+_left.toString(s+"\t")+"\n");
        }
        else 
	if (_right!=null) 
	    return(s+_value+"\n\n"+_right.toString(s+"\t"));
	else
	    return(s+_value+"\n");
    }



    public void ParcoursPrefixe() {
	System.out.println(getValeur());
	if (getLeft() != null)
	    getLeft().ParcoursPrefixe();
	if (getRight() != null)
	    getRight().ParcoursPrefixe();
    }


    public void ParcoursInfixe() {
	if (getLeft() != null)
	    getLeft().ParcoursInfixe();
	System.out.println(getValeur());
	if (getRight() != null)
	    getRight().ParcoursInfixe();
    }


    public void ParcoursPostfixe() {
	if (getLeft() != null)
	    getLeft().ParcoursPostfixe();
	if (getRight() != null)
	    getRight().ParcoursPostfixe();
	System.out.println(getValeur());
    }

   /*
     * Teste si deux arbres sont egaux, meme valeurs et meme disposition
     * @param a l'arbre a comparer a b
     * @param b l'arbre a comparer a a
     * @return un boolean indiquant si les arbres sont egaux
     
    public static boolean arbresEgaux(Tree a, Tree b) {
	if ((a == null) && (b == null))
	    return true;
	if ((a == null) && (b != null))
	    return false;
	if ((a != null) && (b == null))
	    return false;

	// A ce point, a et b != null, on peut acceder a leurs champs
	if (a.getValeur() != b.getValeur())
	    return false;
	return (arbresEgaux(a.getLeft(), b.getLeft()) 
		&& arbresEgaux(a.getRight(), b.getRight()));
    }

 


    public void ParcoursLargeur() {
        File file = new File();
	file.ajouter(this);

	Tree a,b;
	while (!file.estVide()) {
	    a = (Tree) file.retirer();
	    System.out.println(a.getValeur());
	    b = a.getLeft();
	    if (b != null)
		file.ajouter(b);
	    b = a.getRight();
	    if (b != null)  
		file.ajouter(b);
	}
    } 


   /**
     * @param a un arbre
     * @return un boolean indiquant si a est un arbre binaire de recherche
     
    public static boolean estABR(Tree a) {
	if (a == null)
	    return true;
	if ((a.getLeft() != null) && (a.getLeft().getValeur() > a.getValeur()))
	    return false;
	if ((a.getRight() != null) && (a.getValeur() > a.getRight().getValeur()))
	    return false;
	return (estABR(a.getLeft()) && estABR(a.getRight()));
    }

   /**
     * @param value la valeur a recherche dans l'arbre
     * @return un boolean indiquant si value a ete trouve ou non
     
    public boolean recherche(int value) {
	if (value == getValeur())
	    return true;
	if ((value < getValeur()) && (getLeft() != null))
	    return (getLeft().recherche(value));
	if ((value > getValeur()) && (getRight() != null))
	    return (getRight().recherche(value));
	return false;
    }

   /**
     * @param value la valeur a inserer dans l'arbre
     
    public void insertion(int value) {
	if (value == getValeur())
	    return;  // la valeur est deja dans l'arbre
	if (value < getValeur()) {
	    if (getLeft() != null)
		getLeft().insertion(value);
	    else
		_left = new Tree(value);
	}
	if (value > getValeur()) {
	    if (getRight() != null)
		getRight().insertion(value);
	    else 
		_right = new Tree(value);
	}
    }
    
*/
}
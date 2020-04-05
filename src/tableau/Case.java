package tableau;

import main.Cellule;
import main.Global.Type;

/**
 * Classe Case
 * Représente une case du tableau
 */
public class Case extends Cellule {

	/**
	 * Booleen permettant de savoir si la case a �t� compt�e
	 */
	private boolean compte;

    /**
     * Constructeur
     * @param abscisse
     * 			Coordonnée en abscisse de la case
     * @param ordonnee
     * 			Coordonnée en ordonnée de la case
     * @param type
     * 			Type de la case
     */
    public Case(int abscisse, int ordonnee, Type type) {
    	super(abscisse, ordonnee, type);
        compte = false;
    }
    
    /**
     * Methode caseCorrect
     * Permet de comparer la case actuelle avec la case suivante afin de determiner si elles sont liees ou non
     * @param suivante
     * 				case suivante
     * @param direction
     * 				direction vers laquelle l'algorithme se d�place
     * @return
     * 				vrai si les deux cases sont liees, faux sinon
     */
    public boolean caseCorrect(Case suivante, Case start, String direction) {
    	boolean res = false;
    	
    	if((suivante.getType() != Type.BLANC && (!suivante.estCompte()) || (suivante.estCompte() && suivante == start)) || (suivante.estCompte() && suivante.getType() == Type.CROIX)) {
    		if(this.type == Type.CROIX) {
	    		if(direction.equals("Haut")) {
	    			if(suivante.getType() == Type.ANGLE_BAS_DROITE || suivante.getType() == Type.ANGLE_BAS_GAUCHE || suivante.getType() == Type.CROIX || suivante.getType() == Type.VERTICAL) {
		    			res = true;
		    		}
	    		}else if(direction.equals("Bas")) {
	    			if(suivante.getType() == Type.ANGLE_HAUT_DROITE || suivante.getType() == Type.ANGLE_HAUT_GAUCHE || suivante.getType() == Type.CROIX || suivante.getType() == Type.VERTICAL) {
		    			res = true;
		    		}
	    		}else if(direction.equals("Gauche")) {
	    			if(suivante.getType() == Type.ANGLE_HAUT_DROITE || suivante.getType() == Type.ANGLE_BAS_DROITE || suivante.getType() == Type.CROIX || suivante.getType() == Type.HORIZONTAL) {
		    			res = true;
		    		}
	    		}else if(direction.equals("Droite")) {
	    			if(suivante.getType() == Type.ANGLE_HAUT_GAUCHE || suivante.getType() == Type.ANGLE_BAS_GAUCHE || suivante.getType() == Type.CROIX || suivante.getType() == Type.HORIZONTAL) {
		    			res = true;
		    		}
	    		}
	    	}else if(this.type == Type.VERTICAL) {
	    		if(direction.equals("Haut")) {
	    			if(suivante.getType() == Type.ANGLE_BAS_DROITE || suivante.getType() == Type.ANGLE_BAS_GAUCHE || suivante.getType() == Type.CROIX || suivante.getType() == Type.VERTICAL) {
		    			res = true;
		    		}
	    		}else if(direction.equals("Bas")) {
	    			if(suivante.getType() == Type.ANGLE_HAUT_DROITE || suivante.getType() == Type.ANGLE_HAUT_GAUCHE || suivante.getType() == Type.CROIX || suivante.getType() == Type.VERTICAL) {
		    			res = true;
		    		}
	    		}
	    	}else if(this.type == Type.HORIZONTAL) {
	    		if(direction.equals("Gauche")) {
	    			if(suivante.getType() == Type.ANGLE_BAS_DROITE || suivante.getType() == Type.ANGLE_HAUT_DROITE || suivante.getType() == Type.CROIX || suivante.getType() == Type.HORIZONTAL) {
		    			res = true;
		    		}
	    		}else if(direction.equals("Droite")) {
	    			if(suivante.getType() == Type.ANGLE_BAS_GAUCHE || suivante.getType() == Type.ANGLE_HAUT_GAUCHE || suivante.getType() == Type.CROIX || suivante.getType() == Type.HORIZONTAL) {
		    			res = true;
		    		}
	    		}
	    	}else if(this.type == Type.ANGLE_BAS_DROITE) {
	    		if(direction.equals("Droite")) {
	    			if(suivante.getType() == Type.ANGLE_HAUT_GAUCHE || suivante.getType() == Type.ANGLE_BAS_GAUCHE || suivante.getType() == Type.CROIX || (suivante.getType() == Type.HORIZONTAL)) {
		    			res = true;
		    		}
	    		}else if(direction.equals("Bas")) {
	    			if(suivante.getType() == Type.ANGLE_HAUT_GAUCHE || suivante.getType() == Type.ANGLE_HAUT_DROITE || suivante.getType() == Type.CROIX || (suivante.getType() == Type.VERTICAL)) {
		    			res = true;
		    		}
	    		}
	    	}else if(this.type == Type.ANGLE_BAS_GAUCHE) {
	    		if(direction.equals("Gauche")) {
	    			if(suivante.getType() == Type.ANGLE_HAUT_DROITE || suivante.getType() == Type.ANGLE_BAS_DROITE || suivante.getType() == Type.CROIX || suivante.getType() == Type.HORIZONTAL) {
		    			res = true;
		    		}
	    		}else if(direction.equals("Bas")) {
	    			if(suivante.getType() == Type.ANGLE_HAUT_DROITE || suivante.getType() == Type.ANGLE_HAUT_GAUCHE || suivante.getType() == Type.CROIX || suivante.getType() == Type.VERTICAL) {
		    			res = true;
		    		}
	    		}
	    	}else if(this.type == Type.ANGLE_HAUT_DROITE) {
	    		if(direction.equals("Droite")) {
	    			if(suivante.getType() == Type.ANGLE_BAS_GAUCHE || suivante.getType() == Type.ANGLE_HAUT_GAUCHE || suivante.getType() == Type.CROIX || suivante.getType() == Type.HORIZONTAL) {
		    			res = true;
		    		}
	    		}else if(direction.equals("Haut")) {
	    			if(suivante.getType() == Type.ANGLE_BAS_DROITE || suivante.getType() == Type.ANGLE_BAS_GAUCHE || suivante.getType() == Type.CROIX || suivante.getType() == Type.VERTICAL) {
		    			res = true;
		    		}
	    		}
	    	}else if(this.type == Type.ANGLE_HAUT_GAUCHE) {
	    		if(direction == "Gauche") {
	    			if(suivante.getType() == Type.ANGLE_BAS_DROITE || suivante.getType() == Type.ANGLE_HAUT_DROITE || suivante.getType() == Type.CROIX || suivante.getType() == Type.HORIZONTAL) {
		    			res = true;
		    		}
	    		}else if(direction.equals("Haut")) {
	    			if(suivante.getType() == Type.ANGLE_BAS_DROITE || suivante.getType() == Type.ANGLE_BAS_GAUCHE || suivante.getType() == Type.CROIX || suivante.getType() == Type.VERTICAL) {
		    			res = true;
		    		}
	    		}
	    	}
    	}
    	
    	return res;
    }
    
    /*************************/
   /****GETTERS & SETTERS****/
  /*************************/
    
	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}
	
	public void setCaseCompte() {
		this.compte = true;
	}
	
	public boolean estCompte() {
		return this.compte;
	}
}

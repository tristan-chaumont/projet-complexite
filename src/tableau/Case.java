package tableau;

import main.Global.Type;

/**
 * Classe Case
 * Represente une case du plateau
 */

public class Case {

	/**
	 * Bool�en permettant de savoir si la case a �t� compt�e
	 */
	boolean compte;

    private Type type;

    public Case(Type t) {
        type = t;
        compte = false;
    }
    
    /**
     * Methode caseCorrect
     * Permet de comparer la case actuelle avec la case suivante afin de determiner si elles sont liees ou non
     * @param suivante
     * 				case suivante
     * @param last
     * 				dernier mouvement fait lors du parcours du plateau
     * @return
     * 				vrai si les deux cases sont liees, faux sinon
     */
    
    public boolean caseCorrect(Case suivante, String last) {
    	boolean res = false;
    	if(suivante.getType() != Type.BLANC) {
	    	if(this.type == Type.CROIX) {
	    		if(suivante.getType() == Type.ANGLE_BAS_DROITE || suivante.getType() == Type.ANGLE_HAUT_DROITE || suivante.getType() == Type.CROIX || suivante.getType() == Type.HORIZONTAL) {
	    			res = true;
	    		}
	    	}else if(this.type == Type.VERTICAL) {
	    		if(last == "Haut" || last == "Gauche") {
	    			if(suivante.getType() == Type.ANGLE_HAUT_DROITE || suivante.getType() == Type.ANGLE_HAUT_GAUCHE || suivante.getType() == Type.CROIX || suivante.getType() == Type.VERTICAL) {
		    			res = true;
		    		}
	    		}else if(last == "Bas" || last == "Droite") {
	    			if(suivante.getType() == Type.ANGLE_BAS_DROITE || suivante.getType() == Type.ANGLE_BAS_GAUCHE || suivante.getType() == Type.CROIX || suivante.getType() == Type.VERTICAL) {
		    			res = true;
		    		}
	    		}
	    	}else if(this.type == Type.HORIZONTAL) {
	    		if(last == "Bas" || last == "Gauche") {
	    			if(suivante.getType() == Type.ANGLE_BAS_GAUCHE || suivante.getType() == Type.ANGLE_HAUT_GAUCHE || suivante.getType() == Type.CROIX || suivante.getType() == Type.HORIZONTAL) {
		    			res = true;
		    		}
	    		}else if(last == "Haut" || last == "Droite") {
	    			if(suivante.getType() == Type.ANGLE_BAS_DROITE || suivante.getType() == Type.ANGLE_HAUT_DROITE || suivante.getType() == Type.CROIX || suivante.getType() == Type.HORIZONTAL) {
		    			res = true;
		    		}
	    		}
	    	}else if(this.type == Type.ANGLE_BAS_DROITE) {
	    		if(suivante.getType() == Type.ANGLE_HAUT_DROITE || suivante.getType() == Type.ANGLE_BAS_DROITE || suivante.getType() == Type.CROIX || suivante.getType() == Type.HORIZONTAL) {
	    			res = true;
	    		}
	    	}else if(this.type == Type.ANGLE_BAS_GAUCHE) {
	    		if(suivante.getType() == Type.ANGLE_BAS_DROITE || suivante.getType() == Type.ANGLE_BAS_GAUCHE || suivante.getType() == Type.CROIX || suivante.getType() == Type.VERTICAL) {
	    			res = true;
	    		}
	    	}else if(this.type == Type.ANGLE_HAUT_DROITE) {
	    		if(suivante.getType() == Type.ANGLE_HAUT_DROITE || suivante.getType() == Type.ANGLE_HAUT_GAUCHE || suivante.getType() == Type.CROIX || suivante.getType() == Type.VERTICAL) {
	    			res = true;
	    		}
	    	}else if(this.type == Type.ANGLE_HAUT_GAUCHE) {
	    		if(suivante.getType() == Type.ANGLE_BAS_GAUCHE || suivante.getType() == Type.ANGLE_HAUT_GAUCHE || suivante.getType() == Type.CROIX || suivante.getType() == Type.HORIZONTAL) {
	    			res = true;
	    		}
	    	}
    	}
    	
    	return res;
    }
    
    /**
     * Methode toString
     */
    
    public String toString() {
    	return this.type.toString();
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

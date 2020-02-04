package tableau;

import main.Global.*;
import main.Plateau;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Classe Tableau
 * Represente le plateau
 */
public class Tableau extends Plateau {
	
	/**
	 * 	Tableau multidimentionnels de cases permettant de représenter le plateau
	 */
    private Case[][] cases;
    
    /**
     * Constructeur
     * @param hauteur
     * 			hauteur du tableau
     * @param largeur
     * 			largeur du tableau
     * @param random
     * 			tableau random ou correct
     */

    public Tableau(int hauteur, int largeur, boolean random) {
    	super(hauteur, largeur);
    	cases = new Case[hauteur][largeur];
    	if(random)
    		this.genererTableau();
    	else
    		this.genererTableauCorrect();
    }
    
    /**
     * Methode genererTableau
     * Permet de generer un tableau aleatoirement
     */

	public void genererTableau() {
    	for(int i = 0; i < hauteur; i++) {
    		for(int j = 0; j < largeur; j++) {
    			int random = (int)(Math.random() * 8);
    			Type type = null;
    			switch(random) {
	    			case 0:
	    				type = Type.CROIX;
	    				break;
	    			case 1:
	    				type = Type.BLANC;
	    				break;
	    			case 2:
	    				type = Type.VERTICAL;
	    				break;
	    			case 3:
	    				type = Type.HORIZONTAL;
	    				break;
	    			case 4:
	    				type = Type.ANGLE_HAUT_GAUCHE;
	    				break;
	    			case 5:
	    				type = Type.ANGLE_HAUT_DROITE;
	    				break;
	    			case 6:
	    				type = Type.ANGLE_BAS_DROITE;
	    				break;
	    			case 7:
	    				type = Type.ANGLE_BAS_GAUCHE;
	    				break;
    				default:
    					break;
    			}
    			
    			cases[i][j] = new Case(type);
        	}
    	}
    }
	
	/**
	 * Méthode genererTableauCorrect
	 * Permet de générer un tableau avec des cycles correct
	 */
	
	public void genererTableauCorrect() {
			cases[0][0] = new Case(Type.ANGLE_BAS_DROITE);
			cases[0][1] = new Case(Type.ANGLE_BAS_GAUCHE);
			cases[0][2] = new Case(Type.BLANC);
			cases[0][3] = new Case(Type.BLANC);
			cases[0][4] = new Case(Type.BLANC);
			
			cases[1][0] = new Case(Type.ANGLE_HAUT_DROITE);
			cases[1][1] = new Case(Type.ANGLE_HAUT_GAUCHE);
			cases[1][2] = new Case(Type.BLANC);
			cases[1][3] = new Case(Type.BLANC);
			cases[1][4] = new Case(Type.BLANC);
			
			cases[2][0] = new Case(Type.BLANC);
			cases[2][1] = new Case(Type.BLANC);
			cases[2][2] = new Case(Type.ANGLE_BAS_DROITE);
			cases[2][3] = new Case(Type.ANGLE_BAS_GAUCHE);
			cases[2][4] = new Case(Type.BLANC);

			cases[3][0] = new Case(Type.BLANC);
			cases[3][1] = new Case(Type.BLANC);
			cases[3][2] = new Case(Type.VERTICAL);
			cases[3][3] = new Case(Type.ANGLE_HAUT_DROITE);
			cases[3][4] = new Case(Type.ANGLE_BAS_GAUCHE);
			
			cases[4][0] = new Case(Type.BLANC);
			cases[4][1] = new Case(Type.BLANC);
			cases[4][2] = new Case(Type.ANGLE_HAUT_DROITE);
			cases[4][3] = new Case(Type.HORIZONTAL);
			cases[4][4] = new Case(Type.ANGLE_HAUT_GAUCHE);
	}

	/**
	 * Methode getCycles
	 * Permet d'obtenir la liste des différents cycles trouvés
	 * @return
	 * 			la liste des cycles trouvés
	 */
    
    public ArrayList<ArrayList<Case>> getCycles() {
    	ArrayList<ArrayList<Case>> listCycles = new ArrayList<ArrayList<Case>>();
    	ArrayList<Case> listCases = new ArrayList<Case>();
    	
    	Case start;
    	boolean continu = false;
    	String last = "";
    	
    	for(int i = 0; i < hauteur; i++) {
    		for(int j = 0; j < largeur; j++) {
    			if((!continu) && (cases[i][j].getType() != Type.BLANC) && (cases[i][j].estCompte() == false)) {
    				continu = true;
    				start = cases[i][j];
    				cases[i][j].setCaseCompte();
    				
    				while(continu) {
    					//System.out.println("(" +i +";" +j +")");
    					if((j < largeur-1) && (cases[i][j].caseCorrect(cases[i][j+1], last, "Droite")) && (last != "Gauche") && (cases[i][j+1].estCompte() == false || cases[i][j+1] == start)) {
    						cases[i][j+1].setCaseCompte();
    						j++;
    						last = "Droite";
    						
    						listCases.add(cases[i][j]);
    					}else if((i < hauteur-1) && (cases[i][j].caseCorrect(cases[i+1][j], last, "Bas")) && (last != "Haut") && (cases[i+1][j].estCompte() == false || cases[i+1][j] == start)) {
    						cases[i+1][j].setCaseCompte();
    						i++;
    						last = "Bas";
    						
    						listCases.add(cases[i][j]);
    					}else if((j > 0) && (cases[i][j].caseCorrect(cases[i][j-1], last, "Gauche")) && (last != "Droite") && (cases[i][j-1].estCompte() == false || cases[i][j-1] == start)) {
    						cases[i][j-1].setCaseCompte();
							j--;
							last = "Gauche";
							
							listCases.add(cases[i][j]);
    					}else if((i > 0) && (cases[i][j].caseCorrect(cases[i-1][j], last, "Haut")) && (last != "Bas") && (cases[i-1][j].estCompte() == false || cases[i-1][j] == start)) {
    						cases[i-1][j].setCaseCompte();
    						i--;
    						last = "Haut";
    						
    						listCases.add(cases[i][j]);
    					}else {
    						continu = false;
    						i = 0; j = 0;
    						listCases = new ArrayList<Case>();
    					}
    					
    					if(cases[i][j] == start) {
    						listCycles.add(listCases);
    						listCases = new ArrayList<Case>();
    						continu = false;
    					}
    				}
    			}
    		}
    	}
    	return listCycles;
    }
    
    /**
     * Methode getMeilleurCycle
     * @param liste
     * 			liste de cycles
     * @return
     * 			le meilleur cycle
     */
    
    public ArrayList<Case> getMeilleurCycle(ArrayList<ArrayList<Case>> liste) {
    	if(!liste.isEmpty()) {
    		ArrayList<Case> listCases = liste.get(0);
        	for(int i = 1; i < liste.size(); i++) {
        		if(liste.get(i).size() > listCases.size()) {
        			listCases = liste.get(i);
        		}
        	}
        	
        	return listCases;
    	}
    	return new ArrayList<Case>();
    }
    
    /**
     * Methode toString
     * @return
     * 			le tableau avec les types des cases
     */
    
    public String toString() {
    	String res = "";
    	for(int i = 0; i < hauteur; i++) {
    		for(int j = 0; j < largeur; j++) {
    			res += cases[i][j].toString();
    			if(j != largeur-1) {
    				res += ", ";
    			}
    		}
    		res += "\n";
    	}
    	
    	return res;
    }
    
    /*************************/
   /****GETTERS & SETTERS****/
  /*************************/
    
    public Case getCase(int i, int j) {
    	return cases[i][j];
    }
    
    public void setCase(int i, int j, Case c) {
    	this.cases[i][j] = c;
    }
    
    public Case[][] getCases() {
		return cases;
	}

	public void setCases(Case[][] cases) {
		this.cases = cases;
	}
	
	public void setHauteur(int h) {
		this.hauteur = h;
	}
	
	public int getHauteur() {
		return hauteur;
	}
	
	public int getLargeur() {
		return largeur;
	}
	
	public void setLargeur(int l) {
		this.largeur = l;
	}
}

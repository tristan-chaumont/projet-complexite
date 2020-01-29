package tableau;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Classe Plateau
 * Represente le plateau
 */
public class Plateau {
	
	/**
	 * Attributs :
	 * cases
	 * 		tableau multidimentionnels de cases permettant de représenter le plateau
	 * taille
	 * 		taille du tableau
	 */

    private Case[][] cases;
    private int taille;
    
    /**
     * Constructeur
     * @param size
     * 			taille du plateau
     */

    public Plateau(int size) {
    	taille = size;
    	cases = new Case[taille][taille];
    }
    
    /**
     * Methode genererPlateau
     * Permet de generer un plateau aleatoirement
     */

	public void genererPlateau() {
    	for(int i = 0; i < taille; i++) {
    		for(int j = 0; j < taille; j++) {
    			int random = (int)(Math.random() * 8);
    			Case.Type type = null;
    			switch(random) {
	    			case 0:
	    				type = Case.Type.CROIX;
	    				break;
	    			case 1:
	    				type = Case.Type.BLANC;
	    				break;
	    			case 2:
	    				type = Case.Type.VERTICAL;
	    				break;
	    			case 3:
	    				type = Case.Type.HORIZONTAL;
	    				break;
	    			case 4:
	    				type = Case.Type.ANGLE_HAUT_GAUCHE;
	    				break;
	    			case 5:
	    				type = Case.Type.ANGLE_HAUT_DROITE;
	    				break;
	    			case 6:
	    				type = Case.Type.ANGLE_BAS_DROITE;
	    				break;
	    			case 7:
	    				type = Case.Type.ANGLE_BAS_GAUCHE;
	    				break;
    				default:
    					break;
    			}
    			
    			cases[i][j] = new Case(type);
        	}
    	}
    }
	
	/**
	 * Methode getChemins
	 * Permet d'obtenir la taille de tous les chemins possibles
	 * @return
	 * 			la liste de la taille des chemins
	 */
    
    public ArrayList<Integer> getChemins() {
    	Case start;
    	boolean continu = false;
    	int countChemin = 0;
    	String last = "";
    	ArrayList<Integer> listChemins = new ArrayList<Integer>();
    	
    	for(int i = 0; i < cases.length; i++) {
    		for(int j = 0; j < cases.length; j++) {
    			if((!continu) && (cases[i][j].getType() != Case.Type.BLANC) && (cases[i][j].estCompte() == false)) {
    				continu = true;
    				start = cases[i][j];
    				cases[i][j].setCaseCompte();
    				
    				while(continu) {
    					//System.out.println("(" +i +";" +j +")");
    					if((j < cases.length-1) && (cases[i][j].caseCorrect(cases[i][j+1], last)) && (last != "Gauche") && (cases[i][j+1].estCompte() == false || cases[i][j+1] == start)) {
    						cases[i][j+1].setCaseCompte();
    						j++;
    						last = "Droite";
    					}else if((i < cases.length-1) && (cases[i][j].caseCorrect(cases[i+1][j], last)) && (last != "Haut") && (cases[i+1][j].estCompte() == false || cases[i+1][j] == start)) {
    						cases[i+1][j].setCaseCompte();
    						i++;
    						last = "Bas";
    					}else if((j > 0) && (cases[i][j].caseCorrect(cases[i][j-1], last)) && (last != "Droite") && (cases[i][j-1].estCompte() == false || cases[i][j-1] == start)) {
    						cases[i][j-1].setCaseCompte();
							j--;
							last = "Gauche";
    					}else if((i > 0) && (cases[i][j].caseCorrect(cases[i-1][j], last)) && (last != "Bas") && (cases[i-1][j].estCompte() == false || cases[i-1][j] == start)) {
    						cases[i-1][j].setCaseCompte();
    						i--;
    						last = "Haut";
    					}else {
    						continu = false;
    					}
    					
    					countChemin++;
    					
    					if(cases[i][j] == start) {
    						listChemins.add(countChemin);
    						countChemin = 0;
    						continu = false;
    					}
    				}
    			}
    		}
    	}
    	return listChemins;
    }
    
    /**
     * Methode getMeilleur
     * @param liste
     * 			liste des circuits
     * @return
     * 			la taille du meilleur circuit
     */
    
    public int getMeilleur(ArrayList<Integer> liste) {
    	return Collections.max(liste);
    }
    
    /**
     * Methode toString
     * ! Methode a supprimer !
     */
    
    public String toString() {
    	String res = "";
    	for(int i = 0; i < cases.length; i++) {
    		for(int j = 0; j < cases.length; j++) {
    			res += cases[i][j].toString();
    		}
    		res += "\n";
    	}
    	
    	return res;
    }
    
    /*************************/
   /****GETTERS & SETTERS****/
  /*************************/
    
    public Case[][] getCases() {
		return cases;
	}

	public void setCases(Case[][] cases) {
		this.cases = cases;
	}
}

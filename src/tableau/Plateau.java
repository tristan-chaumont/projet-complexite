package tableau;

import java.util.ArrayList;
import java.util.Collections;

public class Plateau {

    private Case[][] cases;

    public Plateau(Case[][] c) {
        cases = c;
    }
    
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
    					if((j < 4) && (cases[i][j+1].getType() != Case.Type.BLANC) && (last != "Gauche") && (cases[i][j+1].estCompte() == false || cases[i][j+1] == start)) {
    						cases[i][j+1].setCaseCompte();
    						j++;
    						last = "Droite";
    					}else if((i < 4) && (cases[i+1][j].getType() != Case.Type.BLANC) && (last != "Haut") && (cases[i+1][j].estCompte() == false || cases[i+1][j] == start)) {
    						cases[i+1][j].setCaseCompte();
    						i++;
    						last = "Bas";
    					}else if((j > 0) && (cases[i][j-1].getType() != Case.Type.BLANC) && (last != "Droite") && (cases[i][j-1].estCompte() == false || cases[i][j-1] == start)) {
    						cases[i][j-1].setCaseCompte();
							j--;
							last = "Gauche";
    					}else if((i > 0) && (cases[i-1][j].getType() != Case.Type.BLANC) && (last != "Bas") && (cases[i-1][j].estCompte() == false || cases[i-1][j] == start)) {
    						cases[i-1][j].setCaseCompte();
    						i--;
    						last = "Haut";
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
    
    public int getMeilleur(ArrayList<Integer> liste) {
    	return Collections.max(liste);
    }
    
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
}

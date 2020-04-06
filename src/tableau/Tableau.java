package tableau;

import main.Cellule;
import main.Global.*;
import plateau.Graphe;
import plateau.Sommet;
import main.Plateau;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Classe Tableau
 * Représente le plateau
 */
public class Tableau extends Plateau {
	
	/**
	 * 	Tableau multidimentionnels de cases permettant de représenter le plateau
	 */
	private static Case[][] cases;
	/**
	 * Case de départ d'un cycle
	 */
	private static Case start;
	/**
	 * Liste de cases constituant un cycle
	 */
	private static ArrayList<Case> listeCases = new ArrayList<Case>();
	/**
	 * Liste de cycle trouvés
	 */
	private static ArrayList<ArrayList<Case>> listeCycle = new ArrayList<ArrayList<Case>>();
    
    /**
     * Constructeur
     * @param hauteur
     * 			hauteur du tableau
     * @param largeur
     * 			largeur du tableau
	 */
    public Tableau(int hauteur, int largeur) {
    	super(largeur, hauteur);
    	cases = new Case[hauteur][largeur];
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
    			
    			cases[i][j] = new Case(i, j, type);
        	}
    	}
    }

	/**
	 * Methode getCycles
	 * Permet d'obtenir la liste des différents cycles trouvés
	 * @return
	 * 			la liste des cycles trouvés
	 * @throws Exception 
	 */
    /*
    public ArrayList<ArrayList<Case>> getCycles() throws Exception {
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
    					if((j < largeur-1) && (cases[i][j].caseCorrect(cases[i][j+1], "Droite")) && (last != "Gauche") && (cases[i][j+1].estCompte() == false || cases[i][j+1] == start)) {
    						cases[i][j+1].setCaseCompte();
    						j++;
    						last = "Droite";
    						
    						listCases.add(cases[i][j]);
    					}else if((i < hauteur-1) && (cases[i][j].caseCorrect(cases[i+1][j], "Bas")) && (last != "Haut") && (cases[i+1][j].estCompte() == false || cases[i+1][j] == start)) {
    						cases[i+1][j].setCaseCompte();
    						i++;
    						last = "Bas";
    						
    						listCases.add(cases[i][j]);
    					}else if((j > 0) && (cases[i][j].caseCorrect(cases[i][j-1], "Gauche")) && (last != "Droite") && (cases[i][j-1].estCompte() == false || cases[i][j-1] == start)) {
    						cases[i][j-1].setCaseCompte();
							j--;
							last = "Gauche";
							
							listCases.add(cases[i][j]);
    					}else if((i > 0) && (cases[i][j].caseCorrect(cases[i-1][j], "Haut")) && (last != "Bas") && (cases[i-1][j].estCompte() == false || cases[i-1][j] == start)) {
    						cases[i-1][j].setCaseCompte();
    						i--;
    						last = "Haut";
    						
    						listCases.add(cases[i][j]);
    					}else {
    						continu = false;
    						i = 0; j = 0;
    						ArrayList<Case> contains = this.containsCase(listCases);
    						if(contains == null) {
    							listCases = new ArrayList<Case>();
    						}else {
    							listCycles.add(listCases);
        						listCases = new ArrayList<Case>();
    						}
    					}
    					
    					if((cases[i][j] == start) && (listCases.size() >= 4)) {
    						System.out.println(listCases);
    						listCycles.add(listCases);
    						listCases = new ArrayList<Case>();
    						continu = false;
    					}
    				}
    			}
    		}
    	}
    	return listCycles;
    }*/
    
	/**
	 * Methodé backtrack
	 * Permet de trouver les cycles
	 * @param prec
	 * 			Case précédente
	 * @param i
	 * 			Index ligne
	 * @param j
	 * 			Index colonne
	 * @param affect
	 * 			Boolean permettant de savoir si la case de départ vient d'être affectée
	 * @return
	 * 			Boolean
	 */
    public static boolean backtrack(Case prec, int i, int j, boolean affect) {
		boolean estPasse = false;
		
		//Si la case de début n'est pas initialisée
		//-> On l'initialise et on la marque
		if(start == null || start.getType() == Type.BLANC) {
			start = cases[i][j];
			start.setCaseCompte();
			affect = true;
		}
		
		//Si la case actuel est la case de début, qu'elle ne vient pas d'être affectée 
		//-> On ajoute le cycle, puis on recommence
		if(cases[i][j] == start && !affect && listeCases.size() >= 4) {
			if(start.getType() != Type.CROIX) {
				listeCycle.add(listeCases);
				
				listeCases = new ArrayList<Case>();
				start = getFirstNonMarque();
				cases[i][j].setCaseCompte();
				
				ArrayList<Integer> indexes = getIndexes(start);
				
				if(backtrack(cases[i][j], indexes.get(0), indexes.get(1), true)) {
					return true;
				}
			}
		}
		
		//On prend les possibilites de la case actuel
		//Pour chaque case adjacente
		//-> Si celle-ci n'est pas déjà comptée ou que c'est la case de début
		//	 -> On ajoute la case, on la marque et on indique qu'on a trouvé une case correct, puis on recommence
		ArrayList<Case> possibilites = getNumConnexionsStrict(start, prec, i, j);
		for(int k = 0; k < possibilites.size(); k++) {
			if(possibilites.get(k) != null) {
				
				//On vérifie que si c'est une croix, tout les branches soit occupées
				ArrayList<Integer> ind = getIndexes(possibilites.get(k));
				ArrayList<Case> possibilitesC1 = getNumConnexions(start, cases[i][j], ind.get(0), ind.get(1));
				
				if((!possibilites.get(k).estCompte()) || (possibilites.get(k) == start) || (possibilites.get(k).getType() == Type.CROIX && possibilitesC1.size() == 4 && getOccCase(possibilites.get(k)) <= 4)) {
					listeCases.add(possibilites.get(k));
					possibilites.get(k).setCaseCompte();
					estPasse = true;
					ArrayList<Integer> indexes = getIndexes(possibilites.get(k));

					if(backtrack(cases[i][j], indexes.get(0), indexes.get(1), false)) {
						return true;
					}
				}
			}
		}

		//Si on n'est pas passé dans la boucle ci-dessus
		//-> On recommance avec une nouvelle case de départ, s'il ne reste plus de case, on arrête
		if(!estPasse) {
			listeCases = new ArrayList<Case>();
			start = getFirstNonMarque();
			
			if(start != null) {
				start.setCaseCompte();
				ArrayList<Integer> indexes = getIndexes(start);
				
				if(backtrack(cases[i][j], indexes.get(0), indexes.get(1), true)) {
					return true;
				}
			}
		}
		
		return false;
	}
    
    /**
     * Methode getMeilleurCycle
     * @return
     * 			le meilleur cycle
     */
    public static ArrayList<Case> getMeilleurCycle() {
    	if(!listeCycle.isEmpty()) {
    		ArrayList<Case> listCases = listeCycle.get(0);
        	for(int i = 1; i < listeCycle.size(); i++) {
        		if(listeCycle.get(i).size() > listCases.size()) {
        			listCases = listeCycle.get(i);
        		}
        	}
        	
        	return listCases;
    	}
    	return new ArrayList<Case>();
    }
    
    /**
	 * Méthode permettant d'avoir les indexes de la case c
	 * @param c
	 * 			Case
	 * @return
	 * 			La liste des indexes (hauteur,largeur)
	 */
	public static ArrayList<Integer> getIndexes(Case c){
		ArrayList<Integer> res = new ArrayList<Integer>();
		res.add(c.getX());
		res.add(c.getY());
		return res;
	}
	
	/**
	 * Methode permettant d'obtenir la première case non marquée du plateau
	 * @return
	 * 			La première case non marquée
	 */
	public static Case getFirstNonMarque() {
		for(int i = 0; i < hauteur; i++) {
			for(int j = 0; j < largeur; j++) {
				if(!cases[i][j].estCompte()) {
					return cases[i][j];
				}
			}
		}
		
		return null;
	}
	
	/**
	 * Méthode getNumConnexionsStrict
	 * Permet d'obtenir le nombres de connexions d'une case en tenant compte des restrictions
	 * @param i
	 * 			Index hauteur
	 * @param j
	 * 			Index largeur
	 * @return
	 * 			La liste de case adjacente
	 */
	public static ArrayList<Case> getNumConnexionsStrict(Case start, Case prec, int i, int j) {
    	ArrayList<Case> res = new ArrayList<Case>();
    	if(start != null) {
    		if((j < largeur-1) && cases[i][j].caseCorrect(cases[i][j+1], start, "Droite") && cases[i][j+1] != prec) {
    			res.add(cases[i][j+1]);
    		}
    		if((i < hauteur-1) && cases[i][j].caseCorrect(cases[i+1][j], start, "Bas") && cases[i+1][j] != prec) {
    			res.add(cases[i+1][j]);
    		}
    		if((j > 0) && cases[i][j].caseCorrect(cases[i][j-1], start, "Gauche") && cases[i][j-1] != prec) {
    			res.add(cases[i][j-1]);
    		}
    		if((i > 0) && cases[i][j].caseCorrect(cases[i-1][j], start, "Haut") && cases[i-1][j] != prec) {
    			res.add(cases[i-1][j]);
    		}
    	}
    	
    	return res;
    }
	
	/**
	 * Méthode getNumConnexionsStrict
	 * Permet d'obtenir le nombres de connexions d'une case sans tenir compte des restrictions
	 * @param i
	 * 			Index hauteur
	 * @param j
	 * 			Index largeur
	 * @return
	 * 			La liste de case adjacente
	 */
	public static ArrayList<Case> getNumConnexions(Case start, Case prec, int i, int j) {
    	ArrayList<Case> res = new ArrayList<Case>();
    	if(start != null) {
    		if((j < largeur-1) && cases[i][j+1] != null) {
    			if(contientCase(listeCycle, cases[i][j+1]) || listeCases.contains(cases[i][j+1]) ||!cases[i][j+1].estCompte() || cases[i][j+1] == prec)
    				res.add(cases[i][j+1]);
    		}
    		if((i < hauteur-1) && cases[i+1][j] != null) {
    			if(contientCase(listeCycle, cases[i+1][j]) || listeCases.contains(cases[i+1][j]) || !cases[i+1][j].estCompte() || cases[i+1][j] == prec)
    				res.add(cases[i+1][j]);
    		}
    		if((j > 0) && cases[i][j-1] != null) {
    			if(contientCase(listeCycle, cases[i][j-1]) || listeCases.contains(cases[i][j-1]) || !cases[i][j-1].estCompte() || cases[i][j-1] == prec)
    				res.add(cases[i][j-1]);
    		}
    		if((i > 0) && cases[i-1][j] != null) {
    			if(contientCase(listeCycle, cases[i-1][j]) || listeCases.contains(cases[i-1][j]) || !cases[i-1][j].estCompte() || cases[i-1][j] == prec)
    				res.add(cases[i-1][j]);
    		}
    	}
    	
    	return res;
    }
	
	@Override
	public Cellule getCellule(int x, int y, String type) {
		Case c;
        switch (type) {
            case "AHD":
                c = new Case(x, y, Type.ANGLE_HAUT_DROITE);
                break;
            case "AHG":
                c = new Case(x, y, Type.ANGLE_HAUT_GAUCHE);
                break;
            case "ABG":
                c = new Case(x, y, Type.ANGLE_BAS_GAUCHE);
                break;
            case "ABD":
                c = new Case(x, y, Type.ANGLE_BAS_DROITE);
                break;
            case "H":
                c = new Case(x, y, Type.HORIZONTAL);
                break;
            case "V":
                c = new Case(x, y, Type.VERTICAL);
                break;
            case "C":
                c = new Case(x, y, Type.CROIX);
                break;
            default:
                c = new Case(x, y, Type.BLANC);
                break;
        }
        return c;
	}
	
	/**
	 * Méthode getOccCase
	 * @param c
	 * 			Case
	 * @return
	 * 			le nombre d'occurences de la case c dans la liste
	 */
	public static int getOccCase(Case c) {
		return Collections.frequency(listeCases, c);
	}
	
	/**
	 * Méthode verifieListes
	 * Permet de vérifier les différente listes
	 * Si une case apparait dans deux liste, alors on fusionne les deux listes
	 * @param best
	 * 				Meilleur cycle
	 */
	
	public static void verifieListes(ArrayList<Case> best) {
		boolean stop;
		for(int i = 0; i < listeCycle.size(); i++) {
			stop = false;
			ArrayList<Case> lc = listeCycle.get(i);
			for(int j = 0; j < best.size(); j++) {
				if(lc.contains(best.get(j)) && !stop) {
					best.addAll(lc);
					stop = true;
				}
			}
		}
	}
	
	/**
	 * Méthode contientCase
	 * @param cycles
	 * 			Liste de cycles
	 * @param c
	 * 			Case
	 * @return
	 * 			Vrai si la case est dans un des cycles
	 */
	public static boolean contientCase(ArrayList<ArrayList<Case>> cycles, Case c) {
        for (ArrayList<Case> cycle: cycles) {
            if (cycle.contains(c)) return true;
        }
        return false;
    }
    
    /*************************/
   /****GETTERS & SETTERS****/
  /*************************/
    
    public Case getCase(int i, int j) {
    	return cases[i][j];
    }
    
    public void setCase(int i, int j, Case c) {
    	cases[i][j] = c;
    }
    
    public Case[][] getCases() {
		return cases;
	}

	public void setCases(Case[][] c) {
		cases = c;
	}
	
	public void setHauteur(int h) {
		hauteur = h;
	}

	public int getHauteur() {
		return hauteur;
	}
	
	public int getLargeur() {
		return largeur;
	}
	
	public void setLargeur(int l) {
		largeur = l;
	}

	public static Case getStart() {
		return start;
	}

	public static void setStart(Case s) {
		start = s;
	}

	public static ArrayList<Case> getListeCases() {
		return listeCases;
	}

	public static void setListeCases(ArrayList<Case> liste) {
		listeCases = liste;
	}

	public static ArrayList<ArrayList<Case>> getListeCycle() {
		return listeCycle;
	}

	public static void setListeCycle(ArrayList<ArrayList<Case>> listeCycle) {
		Tableau.listeCycle = listeCycle;
	}
}

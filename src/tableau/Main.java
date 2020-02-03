package tableau;

import java.util.ArrayList;

/**
 * Classe main.Main
 */
public class Main {
	
	/**
	 * Methode main
	 * @param args
	 * 			arguments
	 */
	
	public static void main(String[] args) {
		
		Tableau plateau = new Tableau(10,10);
		plateau.genererPlateau();

		long debut = System.currentTimeMillis();
		ArrayList<ArrayList<Case>> cycles = plateau.getCycles();
		long fin = System.currentTimeMillis();
		
		System.out.println("Le nombre de cycles trouvés : " +cycles.size());
		System.out.println("Le meilleur chemin a pour longueur : " +plateau.getMeilleurCycle(cycles).size());
		System.out.println("Le programme a mis " +((fin - debut)) +"ms pour éxécuter cette tâche");
		
	}
}

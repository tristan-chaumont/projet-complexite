package tableau;

import java.util.ArrayList;

public class Main {
	
	public static void main(String[] args) {
		//Création d'un plateau à la main
		Case[][] cases = new Case[5][5];
		
		cases[0][0] = new Case(Case.Type.ANGLE_HAUT_GAUCHE);
		cases[0][1] = new Case(Case.Type.ANGLE_HAUT_DROITE);
		cases[0][2] = new Case(Case.Type.BLANC);
		cases[0][3] = new Case(Case.Type.BLANC);
		cases[0][4] = new Case(Case.Type.BLANC);
		
		cases[1][0] = new Case(Case.Type.ANGLE_BAS_GAUCHE);
		cases[1][1] = new Case(Case.Type.ANGLE_BAS_DROITE);
		cases[1][2] = new Case(Case.Type.BLANC);
		cases[1][3] = new Case(Case.Type.BLANC);
		cases[1][4] = new Case(Case.Type.BLANC);
		
		cases[2][0] = new Case(Case.Type.BLANC);
		cases[2][1] = new Case(Case.Type.BLANC);
		cases[2][2] = new Case(Case.Type.ANGLE_HAUT_GAUCHE);
		cases[2][3] = new Case(Case.Type.HORIZONTAL);
		cases[2][4] = new Case(Case.Type.ANGLE_HAUT_DROITE);

		cases[3][0] = new Case(Case.Type.BLANC);
		cases[3][1] = new Case(Case.Type.BLANC);
		cases[3][2] = new Case(Case.Type.VERTICAL);
		cases[3][3] = new Case(Case.Type.BLANC);
		cases[3][4] = new Case(Case.Type.VERTICAL);
		
		cases[4][0] = new Case(Case.Type.BLANC);
		cases[4][1] = new Case(Case.Type.BLANC);
		cases[4][2] = new Case(Case.Type.ANGLE_BAS_GAUCHE);
		cases[4][3] = new Case(Case.Type.HORIZONTAL);
		cases[4][4] = new Case(Case.Type.ANGLE_BAS_DROITE);
		
		Plateau plateau = new Plateau(cases);
		
		long debut = System.currentTimeMillis();
		ArrayList<Integer> chemins = plateau.getChemins();
		long fin = System.currentTimeMillis();
		
		System.out.println("Les différentes longueurs des chemins sont : " +chemins);
		System.out.println("Le meilleur chemin a pour longueur : " +plateau.getMeilleur(chemins));
		System.out.println("Le programme a mis " +((fin - debut)) +"ms pour éxécuter cette tâche");
	}
}

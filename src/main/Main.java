package main;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import plateau.Graphe;
import tableau.Tableau;

import static main.Global.*;

public class Main {

    public static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        genererInterfaceConsole();
    }

    public static void genererInterfaceConsole() {
        String plateau = demanderPlateaux();

        System.out.print("Voulez-vous utiliser l'algorithme recursif (1), l'agorithme de backtracking (2) ou faire un comparatif des deux (3) : ");
        String algo = sc.next();
        while (!Pattern.matches("\\d", algo) || (Integer.parseInt(algo) < 1 || Integer.parseInt(algo) > 3)) {
            System.out.print(System.lineSeparator() + "Veuillez faire votre choix entre 1 et 3 : ");
            algo = sc.next();
        }
        System.out.println();
        if (Integer.parseInt(algo) != 3) {
            System.out.println("Voulez-vous lancer la Vue afin de voir les circuits detectes ? (1)");
            System.out.println("Voulez-vous lancer l'algorithme plusieurs fois pour avoir une moyenne des temps d'execution ? (2)");
            System.out.print("Votre choix : ");
            String choix = sc.next();
            while (!Pattern.matches("\\d", choix) || (Integer.parseInt(choix) < 1 || Integer.parseInt(choix) > 2)) {
                System.out.print(System.lineSeparator() + "Veuillez faire votre choix entre 1 et 2 : ");
                choix = sc.next();
            }

            System.out.println();

            switch (Integer.parseInt(choix)) {
                case 1:
                    try {
                        switch (Integer.parseInt(algo)) {
                            case 1:
                                new Vue(genererPlateauPrefait(plateau, "graphe"));
                                break;
                            case 2:
                                new Vue(genererPlateauPrefait(plateau, "tableau"));
                                break;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    lancerAlgoNFois(Integer.parseInt(algo), plateau);
                    break;
                default:
                    break;
            }
        } else {
            lancerComparatifNFois(plateau);
        }
    }

    public static String demanderPlateaux() {
        List<String> plateaux = Arrays.stream(Objects.requireNonNull(new File("plateaux/").listFiles())).map(File::getName).collect(Collectors.toList());
        System.out.println("Choisissez le plateau que vous voulez generer parmi la liste suivante (1, 2, ...) :");
        for (int i = 0; i < plateaux.size(); i++) {
            System.out.println(String.format("\t%d. %s", i + 1, plateaux.get(i)));
        }
        System.out.print(System.lineSeparator() + "Votre choix : ");
        String choix = sc.next();
        while (!Pattern.matches("\\d{1,2}", choix) || (Integer.parseInt(choix) < 1 || Integer.parseInt(choix) > plateaux.size())) {
            System.out.print(System.lineSeparator() + String.format("Veuillez faire votre choix entre 1 et %d : ", plateaux.size()));
            choix = sc.next();
        }
        System.out.println();

        return plateaux.get(Integer.parseInt(choix) - 1);
    }

    public static void lancerAlgoNFois(int algo, String choixPlateau) {
        System.out.println("Combien de fois voulez-vous lancer ce circuit (<= 10,000) ? : ");
        String n = sc.next();
        while (!Pattern.matches("\\d+", n) || (Integer.parseInt(n) < 0 || Integer.parseInt(n) > 10000)) {
            System.out.print("Veuillez choisir un nombre entre 1 et 10,000 : ");
            n = sc.next();
        }
        System.out.println();
        ArrayList<Long> tempsExecution = new ArrayList<>();
        Plateau plateau = genererPlateauPrefait(choixPlateau, algo == 1 ? "graphe" : "tableau");
        for (int i = 0; i < Integer.parseInt(n); i++) {
            long debut = System.nanoTime(), fin;
            switch (algo) {
                case 1:
                    ((Graphe) plateau).contientCycle();
                    fin = System.nanoTime();

                    ((Graphe) plateau).resetAll();
                    break;
                case 2:
                    Tableau.backtrack(null, 0, 0, false);
                    fin = System.nanoTime();
                    Tableau.reset();
                    break;
                default:
                    fin = System.nanoTime();
                    break;
            }
            tempsExecution.add(fin - debut);
            System.out.println(String.format("Le programme a mis %dns pour executer cette tache.", (fin - debut)));
        }
        System.out.println();
        System.out.println(String.format("Le temps moyen d'execution des %d algorithmes est de %dns.", Long.parseLong(n), tempsExecution.stream().mapToLong(Long::valueOf).sum() / Long.parseLong(n)));
    }

    public static void lancerComparatifNFois(String choixPlateau) {
        System.out.println("Combien de fois voulez-vous lancer ce circuit pour le comparatif (<= 10,000) ? : ");
        String n = sc.next();
        while (!Pattern.matches("\\d+", n) || (Integer.parseInt(n) < 0 || Integer.parseInt(n) > 10000)) {
            System.out.print("Veuillez choisir un nombre entre 1 et 10,000 : ");
            n = sc.next();
        }
        System.out.println();

        long sumR = 1;
        long sumB = 1;

        for (int i = 0; i < Integer.parseInt(n); i++) {
            long[] tab = new long[4];
            Plateau plateau = genererPlateauPrefait(choixPlateau, "graphe");
            tab[0] = System.nanoTime();
            ((Graphe) plateau).contientCycle();
            tab[1] = System.nanoTime();
            ((Graphe) plateau).resetAll();
            plateau = genererPlateauPrefait(choixPlateau, "tableau");
            tab[2] = System.nanoTime();
            Tableau.backtrack(null, 0, 0, false);
            tab[3] = System.nanoTime();
            Tableau.reset();
            System.out.println(String.format("Temps d'exec de l'algo recursif : %dns", tab[1] - tab[0]));
            System.out.println(String.format("Temps d'exec de l'algo de backtracking : %dns", tab[3] - tab[2]));
            System.out.println();
            sumR += (tab[1] - tab[0]);
            sumB += (tab[3] - tab[2]);
        }

        sumR = sumR / Integer.parseInt(n);
        sumB = sumB / Integer.parseInt(n);

        if (sumR >= sumB)
            System.out.println(String.format("L'algo de backtracking est en moyenne %sx plus rapide que l'algo recursif pour %d execution(s).",
                    new DecimalFormat("#.00").format((double) sumR / sumB), Integer.parseInt(n)));
        else
            System.out.println(String.format("L'algo recursif est en moyenne %sx plus rapide que l'algo de backtracking pour %d execution(s).",
                    new DecimalFormat("#.00").format((double) sumB / sumR), Integer.parseInt(n)));
    }
}

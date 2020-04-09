package main;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import plateau.Graphe;
import tableau.Tableau;
import main.Global.*;

import static main.Global.*;

public class Main {

    public static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        genererInterfaceConsole();
    }

    public static void genererInterfaceConsole() {
        String plateau = demanderPlateaux();

        System.out.print("Voulez-vous utiliser l'algorithme recursif (1) ou l'agorithme de backtracking (2) : ");
        String algo = sc.next();
        while (!Pattern.matches("\\d", algo) || (Integer.parseInt(algo) < 1 || Integer.parseInt(algo) > 2)) {
            System.out.print(System.lineSeparator() + "Veuillez faire votre choix entre 1 et 2 : ");
            algo = sc.next();
        }

        try {
            switch (Integer.parseInt(algo)) {
                case 1:
                    new Vue(genererPlateauPrefait(plateau, "graphe"));
                    break;
                case 2:
                    new Vue(genererPlateauPrefait(plateau, "tableau"));
                    break;
            }
        } catch(IOException e) {
            e.printStackTrace();
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
        while (!Pattern.matches("\\d{1,2}", choix) || (Integer.parseInt(choix) < 0 || Integer.parseInt(choix) > plateaux.size())) {
            System.out.print(System.lineSeparator() + String.format("Veuillez faire votre choix entre 1 et %d : ", plateaux.size()));
            choix = sc.next();
        }
        System.out.println();

        return plateaux.get(Integer.parseInt(choix) - 1);
    }
}

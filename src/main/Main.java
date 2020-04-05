package main;

import java.io.IOException;

import plateau.Graphe;
import tableau.Tableau;
import main.Global.*;

import static main.Global.*;

public class Main {

    public static void main(String[] args) {
        //Vue vue = new Vue(Global.genererGraphePrefait());
        try {
            Vue vue = new Vue(genererPlateauPrefait("GrandCircuit", "graphe"));
            //Vue vue = new Vue(Global.genererGrapheAleatoire());
        	//Vue vue = new Vue(new Tableau(10, 10, true));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ConnectException ce) {
            System.out.println(ce.getMessage());
        }
    }
}

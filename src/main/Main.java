package main;

import java.io.IOException;

import tableau.Tableau;

public class Main {

    public static void main(String[] args) {
        //Vue vue = new Vue(Global.genererGraphePrefait());
        try {
            //Vue vue = new Vue(Global.genererGrapheAleatoire());
        	Vue vue = new Vue(new Tableau(5, 5, false));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

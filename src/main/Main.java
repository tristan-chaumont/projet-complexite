package main;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        //Vue vue = new Vue(Global.genererGraphePrefait());
        try {
            Vue vue = new Vue(Global.genererGrapheAleatoire());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

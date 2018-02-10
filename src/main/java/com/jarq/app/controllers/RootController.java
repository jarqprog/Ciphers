package com.jarq.app.controllers;

import com.jarq.app.ciphers.Ciphre;
import com.jarq.app.ciphers.Playfair;
import com.jarq.app.ciphers.Rot13;
import com.jarq.app.factories.CiphreFactory;
import com.jarq.app.factories.Factory;
import com.jarq.app.tools.DataTool;
import com.jarq.app.views.RootView;

public class RootController {

    RootView view;
    Factory factory;
    Ciphre currentCiphre;
    boolean shouldQuit;

    public RootController() {
        view = new RootView();
        factory = new CiphreFactory();
        currentCiphre = null;
        shouldQuit = false;
    }

    public void chooseCiphre() {
        String message = "Choose ciphre:\n" +
                "           - r ---> Rot13\n" +
                "           - p ---> PlayFair\n" +
                "           - q ---> quit program";

        String userChoice = "";
        String[] correctChoices = {"r", "p", "q"};

        while(! DataTool.checkIfElementInArray(correctChoices, userChoice)) {

            userChoice = view.getUserInput(message);

        }

        switch(userChoice) {

            case("r"):
                currentCiphre = factory.getInstance(Rot13.class);
                break;
            case("p"):
                currentCiphre = factory.getInstance(Playfair.class);
                break;
            case("q"):
                shouldQuit = true;
                break;
        }
    }

    public void run() {
        String message = "Type text to encrypt ---> ";
        String textToEncrypt = view.getUserInput(message);




    }

}

package com.jarq.app.controllers;

import com.jarq.app.ciphers.Cipher;
import com.jarq.app.ciphers.Playfair;
import com.jarq.app.ciphers.Rot13;
import com.jarq.app.enums.Procedure;
import com.jarq.app.factories.CiphreFactory;
import com.jarq.app.factories.Factory;
import com.jarq.app.tools.DataTool;
import com.jarq.app.views.RootView;

public class RootController {

    RootView view;
    Factory factory;
    Cipher currentCipher;
    boolean shouldQuit;
    String mode;

    public RootController() {
        view = new RootView();
        factory = new CiphreFactory();
        currentCipher = null;
        shouldQuit = false;
        mode = Procedure.ENCRYPTION.getMode();
    }

    public void run() {

        view.displayIntro();
        executeMainLoop();
        view.displayOutro();

    }

    private void chooseCiphre() {
        String message = "Choose ciphre:\n" +
                "           - r ---> Rot13\n" +
                "           - p ---> PlayFair\n" +
                "           - m ---> change mode (current: " + mode + ")\n"+
                "           - q ---> quit program";
        String userChoice = "";
        String[] correctChoices = {"r", "p", "m", "q"};
        while(! DataTool.checkIfElementInArray(correctChoices, userChoice)
                ) {
            userChoice = view.getUserInput(message);
        }
        switch(userChoice) {
            case("r"):
                currentCipher = factory.getInstance(Rot13.class);
                break;
            case("p"):
                currentCipher = factory.getInstance(Playfair.class);
                break;
            case("m"):
                changeMode();
                break;
            case("q"):
                shouldQuit = true;
                break;
        }
    }



    private String takeTextToEncrypt() {
        String message = "\nType text to encrypt ---> ";
        return view.getUserInput(message);
    }

    private void changeMode() {


    }

    private void executeMainLoop() {
        while(! shouldQuit) {
            view.clearScreen();
            chooseCiphre();
            if(currentCipher != null) {
                view.displayMessage("\nYou've choose:\n" + currentCipher);
                // should add mode choice
                String encryptedText = currentCipher.execute(takeTextToEncrypt(), Procedure.ENCRYPTION.getMode());
                view.displayMessage("\n Encrypted text: " + encryptedText);
                view.handlePause();
                String decryptedText = currentCipher.execute(encryptedText, Procedure.DECRYPTION.getMode());
                view.displayMessage("\n Decrypted text: " + decryptedText);
                view.handlePause();
            }
        }
    }
}

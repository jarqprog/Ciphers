package com.jarq.app.controllers;

import com.jarq.app.ciphers.Ceasar;
import com.jarq.app.ciphers.Cipher;
import com.jarq.app.ciphers.Playfair;
import com.jarq.app.ciphers.Rot13;
import com.jarq.app.enums.Procedure;
import com.jarq.app.exceptions.InvalidKey;
import com.jarq.app.factories.CipherFactory;
import com.jarq.app.factories.Factory;
import com.jarq.app.tools.DataTool;
import com.jarq.app.views.RootView;

public class RootController {

    private RootView view;
    private Factory factory;
    private Cipher currentCipher;
    private boolean shouldQuit;
    private String mode;

    public RootController() {
        view = new RootView();
        factory = new CipherFactory();
        currentCipher = factory.getInstance(Ceasar.class); // by default
        shouldQuit = false;
        mode = Procedure.ENCRYPTION.getMode();
    }

    public void run() {

        view.displayIntro();
        executeMainLoop();
        view.displayOutro();
    }

    private void executeMainLoop() {
        while(! shouldQuit) {
            view.clearScreen();
            executeInnerMenu();
            if(currentCipher != null && !shouldQuit) {
                if(currentCipher.isKeyRequired()){
                    changeKey();
                }
                String encryptedText = currentCipher.execute(takeTextToEncrypt(), Procedure.ENCRYPTION.getMode());
                view.displayMessage("\n Cipher output: " + encryptedText);
                view.handlePause();
            }
        }
    }

    private void executeInnerMenu() {
        String cipherAndModeInfo = "\n\n\nCurrent cipher: " + currentCipher.toString() + "\n" +
                            "Current mode: " + mode;
        String options = "Choose an option:\n" +
                "           - r ---> Rot13\n" +
                "           - p ---> PlayFair\n" +
                "           - c ---> Ceasar\n" +
                "           - m ---> set mode (current: " + mode + ")\n"+
                "           - q ---> quit program";
        String userChoice = "";
        String[] correctChoices = {"r", "p", "c", "m", "q"};
        while(! DataTool.checkIfElementInArray(correctChoices, userChoice)) {
            view.clearScreen();
            view.displayMessage(cipherAndModeInfo);
            userChoice = view.getUserInput(options);
        }
        switch(userChoice) {
            case("r"):
                currentCipher = factory.getInstance(Rot13.class);
                break;
            case("p"):
                currentCipher = factory.getInstance(Playfair.class);
                break;
            case("c"):
                currentCipher = factory.getInstance(Ceasar.class);
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

        String message = "\nType a text ---> ";
        return view.getUserInput(message);
    }

    private void changeMode() {

        String userChoice = "";
        String[] correctChoices = {"e", "d", "0"};
        while(! DataTool.checkIfElementInArray(correctChoices, userChoice)) {
            String message = "\n\n\nCurrent mode ---> " + mode;
            view.displayMessage(message);
            userChoice = view.getUserInput("\nSelect mode:\n" +
                    "   - e (encryption)\n" +
                    "   - d (decryption)\n" +
                    "   - 0 stay with current mode");
        }
        switch(userChoice) {
            case("e"):
                setMode(Procedure.ENCRYPTION.getMode());
                break;
            case("d"):
                setMode(Procedure.DECRYPTION.getMode());
                break;
            case("0"):
                break;
        }
    }

    private void changeKey() {

        String newKey;
        boolean keyIsReady = false;
        while (!keyIsReady) {
            view.clearScreen();
            System.out.println(currentCipher);
            String message = "\n\n\nKey info ---> " + currentCipher.getKeyInfo();
            view.displayMessage(message);
            newKey = view.getUserInput("\nType a key for cipher ---> ");
            try {
                currentCipher.changeKey(newKey);
                keyIsReady = true;
            } catch (InvalidKey ex) {
                view.displayMessage(ex.getMessage());
            } catch (NumberFormatException ex) {
                view.displayMessage("Incorrect key");
            }
        }
        view.displayMessage("\nKey is ready");
    }

    private void setMode(String mode) {
        this.mode = mode;
    }
}

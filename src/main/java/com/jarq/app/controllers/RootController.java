package com.jarq.app.controllers;

import com.jarq.app.ciphers.*;
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
        this.view = new RootView();
        this.factory = new CipherFactory();
        this.currentCipher = factory.getInstance(Rot13.class); // by default
        this.shouldQuit = false;
        this.mode = Procedure.ENCRYPTION.getMode(); // by default
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
                String encryptedText = currentCipher.execute(takeTextToEncrypt(), getMode());
                view.displayMessage("\n Cipher output: " + encryptedText);
                view.handlePause();
            }
        }
    }

    private void executeInnerMenu() {
        String userChoice = "";
        String[] correctChoices = {"r", "p", "c", "a", "m", "q"};
        while(! DataTool.checkIfElementInArray(correctChoices, userChoice)) {
            String cipherAndModeInfo = "\n\n        Current cipher:" + currentCipher.toString() +
                    "\n        Current mode: " + getMode();
            if(currentCipher.isKeyRequired()) {
                cipherAndModeInfo += "\n        This cipher requires a proper key: " + currentCipher.getKeyInfo();
            }
            String options = "\n        Choose an option:\n\n" +
                    "           - r ---> Rot13\n" +
                    "           - p ---> PlayFair\n" +
                    "           - c ---> Ceasar\n" +
                    "           - a ---> Atbash\n" +
                    "           - m ---> set mode (current: " + getMode() + ")\n"+
                    "           - q ---> quit program\n";
            view.clearScreen();
            view.displayMessage(cipherAndModeInfo);
            userChoice = view.getUserInput(options);
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
                case("a"):
                    currentCipher = factory.getInstance(Atbash.class);
                    break;
                case("m"):
                    changeMode();
                    userChoice = "";
                    break;
                case("q"):
                    shouldQuit = true;
                    break;
            }
        }
    }

    private String takeTextToEncrypt() {
        view.clearScreen();
        view.displayMessage("        Current cipher: " + currentCipher.toString());
        view.displayMessage("        Current mode: " + this.getMode());

        String message = "\n        Type a text ---> ";
        return view.getUserInput(message);
    }

    private void changeMode() {

        String userChoice = "";
        String[] correctChoices = {"e", "d", "0"};
        while(! DataTool.checkIfElementInArray(correctChoices, userChoice)) {
            view.clearScreen();
            String message = "\n\n        Current mode: " + getMode();
            view.displayMessage(message);
            userChoice = view.getUserInput("\n\n        Select mode:\n\n" +
                    "           - e ---> (encryption)\n" +
                    "           - d ---> (decryption)\n" +
                    "           - 0 ---> (stay with current mode)\n");
        }
        switch(userChoice) {
            case("e"):
                this.setMode(Procedure.ENCRYPTION.getMode());
                break;
            case("d"):
                this.setMode(Procedure.DECRYPTION.getMode());
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
            String message = "\n\n        Key info ---> " + currentCipher.getKeyInfo();
            view.displayMessage(message);
            newKey = view.getUserInput("\n        Type a key for cipher ---> ");
            try {
                currentCipher.changeKey(newKey);
                keyIsReady = true;
            } catch (InvalidKey ex) {
                view.displayMessage(ex.getMessage());
            } catch (NumberFormatException ex) {
                view.displayMessage("        Incorrect key");
            }
        }
        view.displayMessage("\n        Key is ready");
    }

    private void setMode(String mode) {
        this.mode = mode;
    }

    private String getMode() {
        return this.mode;
    }
}

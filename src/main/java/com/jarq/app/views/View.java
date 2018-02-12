package com.jarq.app.views;

import com.jarq.app.ciphers.Cipher;

import java.util.Scanner;
import java.io.IOException;
import java.util.List;


public abstract class View {


    private static final String ANSI_CLS = "\u001b[2J";
    private static final String ANSI_HOME = "\u001b[H";
    protected String emptyLines = "\n\n";
    protected String space = " ";

    public <T extends Cipher> void displayCiphre(T ciphre) {
        System.out.println(ciphre);
    }

    public void displayManyCiphres(List<? extends Cipher> ciphres) {
    }

    public void displayMessage(String message) {
        System.out.println(message);
    }

    public String getUserInput(String message) {
        Scanner scanner = new Scanner(System.in);
        String userInput = "";
        int minimumUserInputLength = 1;
        while(userInput.length() < minimumUserInputLength){
            System.out.print(message);
            userInput = scanner.nextLine();
        }
        return userInput;
    }

    public void clearScreen() {
        try{
            final String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print(ANSI_CLS + ANSI_HOME);
                System.out.flush();
                System.out.println();
            }
        } catch (Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }

    public void handlePause() {
        System.out.println(emptyLines + space + "Press enter to continue.. ");
        try {
            System.in.read();
        }catch(IOException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }
}

package com.jarq.app.views;

import com.jarq.app.ciphers.AbstractCipher;
import com.jarq.app.ciphers.Cipher;
import com.jarq.app.tools.DataTool;

import java.util.Scanner;
import java.io.IOException;
import java.util.List;


public abstract class View {


    private static final String ANSI_CLS = "\u001b[2J";
    private static final String ANSI_HOME = "\u001b[H";
    protected String emptyLines = "\n\n";
    protected String space = " ";

    public void setEmptyLines(String newEmptyLines) {
        emptyLines = newEmptyLines;
    }

    public <T extends Cipher> void displayCiphre(T ciphre) {
        System.out.println(ciphre);
    }

    public void displayManyCiphres(List<? extends Cipher> ciphres) {

    }

    public void displayMessage(String message) {
        System.out.println(message);
    }

    public <T extends Object> void displayObject(T object) {
        System.out.println(object.toString());
    }

    public <T extends Object> void displayObjects(List<T> objects) {
        for (T object : objects){
            displayObject(object);
        }
    }

    public void displayHeaderAndElementsOfCollection(String[] collection, String header) {
        System.out.println(emptyLines + space + header);
        for(String element : collection) {
            System.out.println(space + element);
        }
    }

    public void displayEnumeratedElementsOfCollection(String[] collection) {
        System.out.println(emptyLines);
        int number = 0;
        for(String element : collection){
            System.out.println(space + "[" + number + "] " + element);
            number ++;
        }
    }

    public void displayElementsOfCollection(String[] collection) {
        for(String element : collection){
            System.out.println(element);
        }
    }

    public String getUserInput(String message) {
        Scanner scanner = new Scanner(System.in);
        String userInput = "";
        String delimiter = "\n";
        int minimumUserInputLength = 1;
        while(userInput.length() < minimumUserInputLength){
            System.out.print(message);
            scanner.useDelimiter(delimiter);
            userInput = scanner.next();
        }
        return DataTool.removeWhitespacesFromString(userInput);
    }

    public String getNumberFromUser(String message) {
        Scanner scanner = new Scanner(System.in);
        String userInput = "";
        String regex = ".*\\d+.*";
        int minimumUserInputLength = 1;
        while(! userInput.matches(regex) && userInput.length() < minimumUserInputLength){
            System.out.print(message);
            scanner.useDelimiter("\n");
            userInput = scanner.next().trim();
            if(! userInput.matches(regex)){
                displayMessage("    - Wrong input (number required)!");
            }
        }
        return userInput;
    }

    public int getNotNegativeNumberFromUser(String message){
        int number = -1;
        while(number < 0) {
            String input = getNumberFromUser(message);
            try {
                number = Integer.parseInt(input);
                if (number < 0) {
                    displayMessage("    - Number shouldn't be negative!");
                }
            } catch (NumberFormatException e){
                displayMessage("    - have You type an integer number?");
                number = -1;
            }
        }
        return number;
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

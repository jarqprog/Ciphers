package com.jarq.app.views;

public class RootView extends View {


    public void displayIntro(){
        clearScreen();
        System.out.println(emptyLines);
        System.out.println("    **********************************************************");
        System.out.println("    *                                                        *");
        System.out.println("    *                        Welcome to                      *");
        System.out.println("    *                                                        *");
        System.out.println("    *                   encryption robot v1.0                *");
        System.out.println("    *                          by jq 0                       *");
        System.out.println("    *                                                        *");
        System.out.println("    *                     jarqprog@gmail.com                 *");
        System.out.println("    *                                                        *");
        System.out.println("    *                                                        *");
        System.out.println("    **********************************************************");
        System.out.println(emptyLines);
        handlePause();
    }

    public void displayOutro(){
        clearScreen();
        System.out.println(emptyLines);
        System.out.println("    **********************************************************");
        System.out.println("    *                                                        *");
        System.out.println("    *                         Bye, bye!                      *");
        System.out.println("    *                                                        *");
        System.out.println("    *                   encryption robot v1.0                *");
        System.out.println("    *                          by jq 0                       *");
        System.out.println("    *                                                        *");
        System.out.println("    *                     jarqprog@gmail.com                 *");
        System.out.println("    *                                                        *");
        System.out.println("    *                                                        *");
        System.out.println("    **********************************************************");
        System.out.println(emptyLines);
        handlePause();
    }
}

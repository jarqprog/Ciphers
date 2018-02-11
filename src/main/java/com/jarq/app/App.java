package com.jarq.app;

import com.jarq.app.controllers.RootController;

public class App {

    public static void main( String[] args ) {
        RootController controller = new RootController();
        controller.run();
    }
}

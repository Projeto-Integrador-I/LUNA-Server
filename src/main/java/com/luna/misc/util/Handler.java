package com.luna.misc.util;

public class Handler {

    public static void handle( Exception e ) {

        //TODO: promptError
        e.printStackTrace();
        System.exit( 1 );
    }
}
